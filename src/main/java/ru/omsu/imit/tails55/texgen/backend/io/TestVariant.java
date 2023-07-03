package ru.omsu.imit.tails55.texgen.backend.io;

import java.util.ArrayList;
import java.util.List;

public class TestVariant {
    private List<Task> tasks=new ArrayList<>();

    public void addTask(Task task) {
        tasks.add(task);
    }

    public String printQuestions() {
        StringBuilder result = new StringBuilder();
        for(Task task:tasks)
            result.append(task.printQuestion());
        return result.toString()
                .replaceAll("\\\\", "\\\\\\\\")
                .replaceAll("\\$", "\\\\\\$");
    }

    public String printAnswers() {
        StringBuilder result = new StringBuilder();
        for(Task task:tasks)
            result.append(task.printAnswer());
        return result.toString()
                .replaceAll("\\\\", "\\\\\\\\")
                .replaceAll("\\$", "\\\\\\$");
    }
}
