package pl.coderslab;

import java.time.LocalDate;

public class Task {

    private int number;
    private String description;
    private LocalDate dueDate;
    private boolean important;

    public Task(int number, String description, LocalDate dueDate, boolean important) {
        this.number = number;
        this.description = description;
        this.dueDate = dueDate;
        this.important = important;
    }

    @Override
    public String toString() {
        String importance = ConsoleColors.PURPLE + important + ConsoleColors.RESET;
        return "%03d : %-20s %-15s %-10s".formatted(number, description, dueDate, importance);
    }

    public String generateCsvData() {
        return "%s,%s,%s".formatted(description, dueDate, important);
    }
}
