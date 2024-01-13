package pl.coderslab;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {

    public static final int MIN_TASK_DESCRIPTION_LENGTH = 5;
    public static final int DATE_FORMAT_LENGTH = 10;
    public static final int DATE_ARRAY_LENGTH = 3;

    private static final String actionPrompt = ConsoleColors.BLUE + """
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
    public static String[][] tasks = new String[0][];

    public static void main(String[] args) {
        String filename = args[0];
        manageTasks(filename);
    }

    //    Manage file data
    public static void loadFile(String fileName) {
        File file = new File(fileName);
        StringBuilder stringBuilder = new StringBuilder();
        String[][] records;
        int numOfLines = 0;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine()).append("\n");
                numOfLines++;
            }
            String[] stringRecords = stringBuilder.toString().split("\n");
            records = new String[numOfLines][3];
            for (int i = 0; i < numOfLines; i++) {
                records[i] = stringRecords[i].split(",");
            }
            tasks = records;
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
    }


    public static void exitTaskManager(String fileName) {

            try (FileWriter fileWriter = new FileWriter(fileName, false)) {
                for (String[] taskArray : tasks) {
                    for (int j = 0; j < taskArray.length; j++) {
                        if (j != (taskArray.length - 1)) {
                            fileWriter.append(taskArray[j].strip()).append(", ");
                        } else {
                            fileWriter.append(taskArray[j].strip()).append("\n");
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error during saving occurred. Have you entered correct filepath?");

            }
        System.out.println(ConsoleColors.RED + "Bye, bye");

        }

    public static void listTasks() {
        if (tasks.length == 0) {
            System.out.println("No tasks added yet\n");
        } else {
            System.out.println("Nr : Description  Due Date  Important?");
            for (int i = 0; i < tasks.length; i++) {
                String[] task = tasks[i];
                System.out.println(i + " : " + task[0].strip() + "\t" + task[1].strip() + "\t" + ConsoleColors.PURPLE + task[2].strip());
            }
            System.out.println();
        }
    }

    //    get operation from user
    private static String getActionFromUser(Scanner scanner) {

        while (true) {
            String action = getInputFromUser(scanner, actionPrompt);
            if (action.equalsIgnoreCase("add") || action.equalsIgnoreCase("remove") || action.equalsIgnoreCase("list") || action.equalsIgnoreCase("exit")) {
                return action.toLowerCase();
            }
            System.out.println(tryAgainResponse);
        }
    }

    //    tasks removal
    private static String getNumberFromUser(Scanner scanner) {

        int taskLength = tasks.length;

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

    private static boolean removeTask(Scanner scanner) {
        String number = getNumberFromUser(scanner);
        if (number.equalsIgnoreCase(cancelWord)) {
            return true;
        } else if (number.equalsIgnoreCase(exitWord)) {
            return false;
        }
        int taskIndexToRemove = Integer.parseInt(number);
        String[][] tasksAfterRemoval = new String[tasks.length - 1][];
        for (int i = 0; i <= taskIndexToRemove; i++) {
            if (i == taskIndexToRemove) {
                if (!(taskIndexToRemove == tasks.length - 1)) {
                    tasksAfterRemoval[i] = tasks[i + 1];
                }
            } else {
                tasksAfterRemoval[i] = tasks[i];
            }
        }

        for (int i = taskIndexToRemove + 1; i < tasksAfterRemoval.length; i++) {
            tasksAfterRemoval[i] = tasks[i + 1];
        }
        tasks = tasksAfterRemoval;
        System.out.println("Task nr." + number + " successfully removed!");
        return true;
    }


    //    task addition
    private static boolean addTask(Scanner scanner) {
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
        String[] taskArray = {taskDescription, dueDate, taskSignificance};
        String[][] updatedTasks = Arrays.copyOf(tasks, tasks.length + 1);
        updatedTasks[updatedTasks.length - 1] = taskArray;
        tasks = updatedTasks;
        System.out.println(ConsoleColors.GREEN + "Task added successfully!");
        return true;
    }

    private static String getTaskDescription(Scanner scanner) {
        while (true) {
            String taskDescriptionPrompt = "Enter task description(min " + MIN_TASK_DESCRIPTION_LENGTH + " characters)" +
                    " or enter \"" + cancelWord + "\" to cancel or \"" + exitWord + "\" to exit:";
            String taskDescription = getInputFromUser(scanner, taskDescriptionPrompt);
            if (taskDescription.isBlank()) {
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


    private static String getTaskSignificance(Scanner scanner) {
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

    private static boolean checkInputLength(String input, int minLength) {
        return input.split("").length >= minLength;
    }

    private static boolean checkDateLength(String date, String regex, int dateLength) {
        return date.split(regex).length == dateLength;
    }

    private static String getDueDate(Scanner scanner) {
        while (true) {
            String dueDatePrompt = "Please add task due date in format YYYY-MM-DD(enter \"" + cancelWord + "\" to cancel or \"" + exitWord + "\" to exit):";
            String dueDate = getInputFromUser(scanner, dueDatePrompt).toLowerCase();
            if (dueDate.equalsIgnoreCase(cancelWord) || dueDate.equalsIgnoreCase(exitWord)) {
                return dueDate;
            } else if (checkDateLength(dueDate, "", DATE_FORMAT_LENGTH) && checkDateLength(dueDate, "-", DATE_ARRAY_LENGTH)) {
                if (validateDate(dueDate)) {
                    return dueDate;
                }
            } else {
                System.out.println("Incorrect due date format. Try again");
            }
        }
    }


    private static boolean isLeapYear(int year) {
        return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
    }


    private static boolean validateDate(String dueDate) {
        String[] stringArrayFromDate = dueDate.split("-");
        int year;
        int month;
        int day;

        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();
        int currentDay = LocalDate.now().getDayOfMonth();
        String invalidDateInformation = "Entered due date is invalid. Please enter valid date.";
        try {
            year = Integer.parseInt(stringArrayFromDate[0]);
            month = Integer.parseInt(stringArrayFromDate[1]);
            day = Integer.parseInt(stringArrayFromDate[2]);
        } catch (NumberFormatException nonNumericValue) {
            System.out.println(invalidDateInformation);
            return false;
        }

        if ((year > 2055) || (month < 1 || month > 12) || (day < 1 || day > 31)) {
            System.out.println(invalidDateInformation);
            return false;
        } else if (year < currentYear || (year == currentYear && month < currentMonth) || (year == currentYear && month == currentMonth && day < currentDay)) {
            System.out.println("You can enter only future due time.");
            return false;
        } else {
            String monthName = switch (month) {
                case 1 -> "January";
                case 2 -> "February";
                case 3 -> "March";
                case 4 -> "April";
                case 5 -> "May";
                case 6 -> "June";
                case 7 -> "July";
                case 8 -> "August";
                case 9 -> "September";
                case 10 -> "October";
                case 11 -> "November";
                case 12 -> "December";
                default -> "Invalid";
            };

            if (isLeapYear(year)) {
                if (month == 2 && day > 29) {
                    System.out.println("Year " + year + " is a leap year. " + monthName + " has only 29 days");
                    return false;
                }
            } else {
                int maxDay = switch (month) {
                    case 1, 3, 5, 7, 8, 10, 12 -> 31;
                    case 4, 6, 9, 11 -> 30;
                    case 2 -> 28;
                    default -> 0;
                };
                if (day > maxDay) {
                    System.out.println("Incorrect day of month entered. " + monthName + " has only " + maxDay + " days.");
                    return false;
                }
            }
        }
        return true;
    }

    //    Manage tasks
    public static void manageTasks(String filename) {
        Scanner scanner = new Scanner(System.in);
        loadFile(filename);
        String action;
        boolean toExitProgram = false;
        while (!toExitProgram) {
            action = getActionFromUser(scanner);
            clearConsoleScreen();
            switch (action) {
                case "add" -> toExitProgram = !addTask(scanner);
                case "remove" -> toExitProgram = !removeTask(scanner);
                case "list" -> listTasks();
                case "exit" -> toExitProgram = true;
            }
        }
        exitTaskManager(filename);

    }

    private static boolean checkForCancelOrExit(String input) {
        return !input.equalsIgnoreCase(exitWord);
    }

    private static String getInputFromUser(Scanner scanner, String prompt) {
        try {
            return System.console().readLine(prompt).strip();
        } catch (NullPointerException e) {
            System.out.println(prompt);
            return scanner.nextLine().strip();
        }
    }

    private static void clearConsoleScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else {
                System.out.print("\033\143");
            }
        } catch (IOException | InterruptedException ex) {}
    }
}


