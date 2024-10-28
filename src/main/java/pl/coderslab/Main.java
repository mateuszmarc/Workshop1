package pl.coderslab;

public class Main {

    public static void main(String[] args) {

        String filename = "tasks.csv";
        TaskManager taskManager = new TaskManager(filename);
        taskManager.manageTasks();
    }
}
