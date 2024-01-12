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

    private static final int MIN_TASK_DESCRIPTION_LENGTH = 5;
    private static final int DATE_FORMAT_LENGTH = 10;
    private static final int DATE_ARRAY_LENGTH = 3;
    private String filename = "src/main/java/pl/coderslab/tasks.csv";

    public static void main(String[] args) {
//        String[][] returned = loadFile("src/main/java/pl/coderslab/tasks.csv");
//        listTasks(returned);

//        exitTaskManager(addTask(returned, new Scanner(System.in)), "src/main/java/pl/coderslab/tasks.csv");
        manageTasks();
    }

    public static String[][] loadFile(String fileName) {
        File file = new File(fileName);
        StringBuilder stringBuilder = new StringBuilder();
        String[][] records = {{}};
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
        return records;
    }


    public static void exitTaskManager(String[][] tasksToWrite, String fileName) {
        try (FileWriter fileWriter = new FileWriter((fileName))) {
            for (String[] taskArray : tasksToWrite) {
                for (int j = 0; j < taskArray.length; j++) {
                    if (j != (taskArray.length - 1)) {
                        fileWriter.append(taskArray[j]).append(", ");
                    } else {
                        fileWriter.append(taskArray[j]).append("\n");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error during saving occurred.");
        }
    }

    public static void listTasks(String[][] tasks) {
        if (tasks.length == 0) {
            System.out.println("No tasks added yet");
        } else {
            for (int i = 0; i < tasks.length; i++) {
                String[] task = tasks[i];
                System.out.println(i + " : " + task[0] + " " + task[1] + " " + task[2]);
            }
        }
    }

    public static String[][] removeTask(Scanner scanner, String[][] tasks) {
        String number = getNumberFromUser(tasks, scanner);
        if (number.equalsIgnoreCase("cancel")) {
            return tasks;
        }
        int taskIndexToRemove = Integer.parseInt(getNumberFromUser(tasks, scanner));
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
        return tasksAfterRemoval;
    }


    public static String getNumberFromUser(String[][] tasks, Scanner scanner) {

        int taskLength = tasks.length;

        while (true) {
            listTasks(tasks);
            System.out.println("Please select number to remove: ");
            String userInput = scanner.nextLine().strip();
            if (userInput.equalsIgnoreCase("cancel")) {
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

    public static String getActionFromUser(Scanner scanner) {

        while (true) {
            System.out.println("""
                    Please select an option: 
                    add
                    remove
                    list 
                    exit""");
            String action = scanner.nextLine().strip();
            if (action.equalsIgnoreCase("add") || action.equalsIgnoreCase("remove") || action.equalsIgnoreCase("list") || action.equalsIgnoreCase("exit")) {
                return action.toLowerCase();
            }
            System.out.println("Incorrect input. Try again.");
        }
    }

    public static String[][] addTask(String[][] tasks, Scanner scanner) {
        String taskDescription = getTaskDescription(scanner);
        if (taskDescription.equalsIgnoreCase("cancel")) {
            return tasks;
        } else if (taskDescription.equalsIgnoreCase("exit")) {
            exitTaskManager(tasks, "src/main/java/pl/coderslab/tasks.csv");
        }
        String dueDate = getDueDate(scanner);
        if (dueDate.equalsIgnoreCase("cancel")) {
            return tasks;
        }
        String taskSignificance = getTaskSignificance(scanner);
        if (taskSignificance.equalsIgnoreCase("cancel")) {
            return tasks;
        }
        String[] taskArray = {taskDescription, dueDate, taskSignificance};
        String[][] updatedTasks = Arrays.copyOf(tasks, tasks.length + 1);
        updatedTasks[updatedTasks.length - 1] = taskArray;
        return updatedTasks;
    }

    public static String getTaskDescription(Scanner scanner) {
        while (true) {
            System.out.println("Enter task description(min " + MIN_TASK_DESCRIPTION_LENGTH + " characters):");
            String taskDescription = scanner.nextLine().strip();
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


    public static String getTaskSignificance(Scanner scanner) {
        while (true) {
            System.out.println("Is your task important: true/false");
            String taskSignificance = scanner.nextLine();
            if (taskSignificance.equalsIgnoreCase("t") || taskSignificance.equalsIgnoreCase("true")) {
                return "true";
            } else if (taskSignificance.equalsIgnoreCase("f") || taskSignificance.equalsIgnoreCase("false")) {
                return "false";
            } else if (taskSignificance.equalsIgnoreCase("cancel")) {
                return "cancel";
            } else {
                System.out.println("Incorrect input. Try again.");
            }
        }
    }

    public static boolean checkInputLength(String input, int minLength) {
        return input.split("").length >= minLength;
    }

    public static boolean checkDateLength(String date, String regex, int dateLength) {
        return date.split(regex).length == dateLength;
    }

    public static String getDueDate(Scanner scanner) {
        while (true) {
            System.out.println("Please add task due date in format YYYY-MM-DD: ");
            String dueDate = scanner.nextLine();
            if (dueDate.equalsIgnoreCase("cancel")) {
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


    public static boolean isLeapYear(int year) {
        return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
    }


    public static boolean validateDate(String dueDate) {
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

    public static void manageTasks() {
        Scanner scanner = new Scanner(System.in);
        String[][] loadedTasks = loadFile("src/main/java/pl/coderslab/tasks.csv");
        String action = "";
        while (!action.equalsIgnoreCase("exit")) {
            action = getActionFromUser(scanner);

            switch (action) {
                case "add" -> {
                    loadedTasks = addTask(loadedTasks, scanner);
                    listTasks(loadedTasks);
                }
                case "remove" -> {
                    loadedTasks = removeTask(scanner, loadedTasks);
                    listTasks(loadedTasks);
                }

                case "list" -> listTasks(loadedTasks);
                case "exit" -> exitTaskManager(loadedTasks, "src/main/java/pl/coderslab/tasks.csv");
            }
        }
    }
}
