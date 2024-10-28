package pl.coderslab;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskManager {

    public static final int MIN_TASK_DESCRIPTION_LENGTH = 5;
    public static final int EXIT_RESULT_INTEGER = -1;

    private static final String ACTION_PROMPT = ConsoleColors.BLUE + """
            Please select an option:
            """ +  ConsoleColors.RESET +
            """
            add
            remove
            list
            exit:""";

    private static final String tryAgainResponse = "Incorrect input. Try again.";
    private static final String cancelWord = "cancel";
    private static final String exitWord = "exit";

    private List<Task> tasks = new ArrayList<>();
    private final String filename;

    public TaskManager(String filename) {
        this.filename = filename;
    }

    public void manageTasks() {
        Scanner scanner = new Scanner(System.in);
        String action;
        int resultFlagInt = 1;
        while (resultFlagInt != -1) {
            tasks = TaskManager.loadFile(filename);
            action = getActionFromUser(scanner);
            clearConsoleScreen();

            if (action.equalsIgnoreCase("add")) {
                resultFlagInt = addTask(scanner);
            } else if (action.equalsIgnoreCase("remove")) {
                resultFlagInt = removeTask(scanner);
            } else if (action.equalsIgnoreCase("list")) {
                listTasks();
            } else if (action.equalsIgnoreCase("exit")) {
                resultFlagInt = EXIT_RESULT_INTEGER;
            } else {
                System.out.println("Incorrect input action. Try again");
            }

        }

        System.out.println(ConsoleColors.RED + "Bye, bye");

    }

    public static List<Task> loadFile(String fileName) {
        File file = new File(fileName);

        ArrayList<Task> savedTasks = new ArrayList<>();
        int taskIndex = 0;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] taskDetails = scanner.nextLine().split(",");
                String taskDescription = taskDetails[0];
                String dueDateString = taskDetails[1];
                LocalDate dueDate = LocalDate.parse(dueDateString);
                boolean importance = Boolean.parseBoolean(taskDetails[2]);

                Task task = new Task(taskIndex, taskDescription, dueDate, importance);
                savedTasks.add(task);

                taskIndex++;
            }
        } catch (FileNotFoundException ex) {
            Path filePath = Paths.get(fileName);
            if (!Files.exists(filePath)) {
                try {
                    Files.createFile(filePath);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        return savedTasks;
    }


    private String getActionFromUser(Scanner scanner) {

        boolean validInput = false;
        String action = "";

        while (!validInput) {
            action = getInputFromUser(scanner, ACTION_PROMPT);
            if (action.equalsIgnoreCase("add")
                    || action.equalsIgnoreCase("remove")
                    || action.equalsIgnoreCase("list")
                    || action.equalsIgnoreCase("exit")) {
                validInput = true;
            } else {
                System.out.println(tryAgainResponse);
            }
        }
        return action;
    }

    private static String getInputFromUser(Scanner scanner, String prompt) {
        try {
            return System.console().readLine(prompt).strip();
        } catch (NullPointerException e) {
            System.out.println(prompt);
            return scanner.nextLine().strip();
        }
    }

    private void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks added yet\n");
        } else {
            System.out.printf("%-3s : %-20s %-10s %-10s%n", "Nr", "Description", "Due Date", "Important?");
            tasks.forEach(System.out::println);
            System.out.println();
        }
    }

    private int removeTask(Scanner scanner) {
        if (!tasks.isEmpty()) {
            String number = getNumberFromUser(scanner);
            if (number.equalsIgnoreCase(cancelWord)) {
                return 0;
            } else if (number.equalsIgnoreCase(exitWord)) {
                return -1;
            }
            int taskIndexToRemove = Integer.parseInt(number);

            String confirmation = "";
            while (!confirmation.equalsIgnoreCase("yes") && !confirmation.equalsIgnoreCase("no")) {
                System.out.println("Are you sure you want to delete task nr (yes/no): " + taskIndexToRemove);
                confirmation = scanner.nextLine();
            }

            if (confirmation.equalsIgnoreCase("yes")) {
                tasks.removeIf(s -> s.getNumber() == taskIndexToRemove);
                writeToFile();
                System.out.println("Task nr." + number + " successfully removed!");
            }

        }
        tasks = TaskManager.loadFile(filename);
        listTasks();
        return 1;
    }

    private String getNumberFromUser(Scanner scanner) {

        int taskLength = tasks.size();

        while (true) {
            listTasks();
            String removePrompt = "Please select number to remove(Enter \"" + cancelWord + "\" to go back or \"" + exitWord + "\" to exit): ";
            String userInput = getInputFromUser(scanner, removePrompt);
            if (userInput.equalsIgnoreCase(cancelWord) || userInput.equalsIgnoreCase(exitWord)) {
                return userInput;
            }
            try {
                int number = Integer.parseInt(userInput);
                if (number < 0) {
                    System.out.println("Incorrect argument passed. Please enter number greater or equal to 0");
                } else if (number > taskLength - 1) {
                    System.out.println("There is no such task with number " + number + " to remove. Try again");
                } else {
                    return userInput;
                }
            } catch (NumberFormatException e) {
                System.out.println("Incorrect argument passed. Please enter numeric value: ");
            }
        }
    }

private int addTask(Scanner scanner) {
    String taskDescription = getTaskDescription(scanner);
    if (taskDescription.equalsIgnoreCase(cancelWord) || taskDescription.equalsIgnoreCase(exitWord)) {
        return checkForCancelOrExit(taskDescription);
    }
    String dueDate = getDueDate(scanner);
    if (dueDate.equalsIgnoreCase(cancelWord) || dueDate.equalsIgnoreCase(exitWord)) {
        return checkForCancelOrExit(dueDate);
    }
    String taskSignificance = getTaskSignificance(scanner);
    if (taskSignificance.equalsIgnoreCase(cancelWord) || taskSignificance.equalsIgnoreCase(exitWord)) {
        return checkForCancelOrExit(taskSignificance);
    }

    Task task = new Task(taskDescription, LocalDate.parse(dueDate), Boolean.parseBoolean(taskSignificance));
    tasks.add(task);
    System.out.println(ConsoleColors.GREEN + "Task added successfully!");

    writeToFile();
    return 1;
}

    private void writeToFile() {
        Path path = Path.of(filename);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {

            for (Task task1 : tasks) {
                bufferedWriter.write(task1.generateCsvData());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            System.out.println("Something went wrong. Please try again.");
        }
    }

    private String getTaskDescription(Scanner scanner) {
        while (true) {
            String taskDescriptionPrompt = "Enter task description(min " + MIN_TASK_DESCRIPTION_LENGTH + " characters)" +
                    " or enter \"" + cancelWord + "\" to cancel or \"" + exitWord + "\" to exit:";
            String taskDescription = getInputFromUser(scanner, taskDescriptionPrompt);
            if (taskDescription.isBlank() || taskDescription.isEmpty()) {
                System.out.println("You did not enter any description.");
            } else {
                if (checkInputLength(taskDescription, MIN_TASK_DESCRIPTION_LENGTH) || taskDescription.equalsIgnoreCase("cancel") || taskDescription.equalsIgnoreCase("exit")) {
                    return taskDescription;
                } else {
                    System.out.println("Your task description is not sufficient.");
                }
            }
        }
    }

    private String getTaskSignificance(Scanner scanner) {
        while (true) {
            String taskSignificancePrompt = "Is your task important: true/false(enter \"" + cancelWord + "\" to cancel or \"" + exitWord + "\" to exit):";
            String taskSignificance = getInputFromUser(scanner, taskSignificancePrompt);
            if (taskSignificance.equalsIgnoreCase("t") || taskSignificance.equalsIgnoreCase("true")) {
                return "true";
            } else if (taskSignificance.equalsIgnoreCase("f") || taskSignificance.equalsIgnoreCase("false")) {
                return "false";
            } else if (taskSignificance.equalsIgnoreCase("cancel") || taskSignificance.equalsIgnoreCase("exit")) {
                return taskSignificance.toLowerCase();
            } else {
                System.out.println("Incorrect input. Try again.");
            }
        }
    }

    private boolean checkInputLength(String input, int minLength) {
        return input.split("").length >= minLength;
    }


    private String getDueDate(Scanner scanner) {
        while (true) {
            String dueDatePrompt = "Please add task due date in format YYYY-MM-DD(enter \"" + cancelWord + "\" to cancel or \"" + exitWord + "\" to exit):";
            String dueDate = getInputFromUser(scanner, dueDatePrompt).toLowerCase().strip();
            if (dueDate.equalsIgnoreCase(cancelWord) || dueDate.equalsIgnoreCase(exitWord)) {
                return dueDate;
            }

            try {
                LocalDate date = LocalDate.parse(dueDate);
                if (validateDate(date)) {
                    return date.toString();
                }
            } catch (Exception e) {
                System.out.println("Incorrect input for due date. Try again");
            }

        }
    }

    private boolean validateDate(LocalDate date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();

        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();
        int currentDay = LocalDate.now().getDayOfMonth();

        if ((year > 2055)) {
            System.out.println("You cannot enter due time later than 2055");
            return false;
        } else if (year < currentYear || (year == currentYear && month < currentMonth) || (year == currentYear && month == currentMonth && day < currentDay)) {
            System.out.println("You can enter only future due time.");
            return false;
        }
        return true;
    }

    private int checkForCancelOrExit(String input) {
        if (input.equalsIgnoreCase("cancel")) {
            return 0;
        }
        return -1;
    }

    private void clearConsoleScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else {
                System.out.print("\033\143");
            }
        } catch (IOException | InterruptedException ex) {
            ex.getMessage();
        }
    }
}


