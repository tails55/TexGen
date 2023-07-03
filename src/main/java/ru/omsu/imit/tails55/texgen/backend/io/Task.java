package ru.omsu.imit.tails55.texgen.backend.io;

import ru.omsu.imit.tails55.texgen.backend.binarylogic.functions.FunctionTree;
import ru.omsu.imit.tails55.texgen.backend.converters.IConverter;
import ru.omsu.imit.tails55.texgen.backend.generators.IGenerator;

public class Task {
    private static final String prefix = "\\item\n\\begin{samepage}";
    private static final String suffix = "\n\\end{samepage}\n";
    private static final String answerSuffix = "\nОтвет: ";
    private static final String newLine = "\\newline\n";
    private final String taskDescription;
    private final String input;
    private final String output;

    public Task(int params, String letters, int length, IGenerator generator, IConverter inputConverter, IConverter outputConverter, String taskDescription) {

        this.taskDescription = taskDescription;
        FunctionTree functionTree = generator.generate(params, letters, length);
        this.input = inputConverter.convert(functionTree, letters);
        this.output = outputConverter.convert(functionTree, letters);
    }


    public String printQuestion() {
        return prefix + taskDescription + newLine + input + suffix;
    }

    public String printAnswer() {
        return prefix + taskDescription + newLine + input + newLine + answerSuffix + output + "\n" + suffix;
    }
}