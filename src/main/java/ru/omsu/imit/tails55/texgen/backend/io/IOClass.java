package ru.omsu.imit.tails55.texgen.backend.io;

import java.io.FileWriter;
import java.nio.charset.StandardCharsets;

import org.w3c.dom.*;
import ru.omsu.imit.tails55.texgen.backend.converters.*;
import ru.omsu.imit.tails55.texgen.backend.generators.*;

import javax.xml.parsers.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


public class IOClass {

    private static final Map<String, Function<Integer, Integer>> generatorDefaultLengths = new HashMap<>();

    private static final Function<Integer, Integer> defaultGeneratorLength = (a) -> a * 3 / 2;

    static {
        generatorDefaultLengths.put("простой", (a) -> a);
        generatorDefaultLengths.put("simple", (a) -> a);

        ToStringConverter toStringConverter=new ToStringConverter();
        ConverterDB.add("",toStringConverter);

        DNFConverter dnfConverter = new DNFConverter();
        ConverterDB.add("днф", dnfConverter);
        ConverterDB.add("сднф", dnfConverter);
        ConverterDB.add("cnf", dnfConverter);
        ConverterDB.add("pcnf", dnfConverter);

        CNFConverter cnfConverter = new CNFConverter();
        ConverterDB.add("кнф", cnfConverter);
        ConverterDB.add("скнф", cnfConverter);
        ConverterDB.add("dnf", cnfConverter);
        ConverterDB.add("pdnf", cnfConverter);

        ZhegalkinConverter zhegalkinConverter = new ZhegalkinConverter();
        ConverterDB.add("пж", zhegalkinConverter);
        ConverterDB.add("полином", zhegalkinConverter);
        ConverterDB.add("жегалкин", zhegalkinConverter);
        ConverterDB.add("жегалкина", zhegalkinConverter);
        ConverterDB.add("zp", zhegalkinConverter);
        ConverterDB.add("polynom", zhegalkinConverter);
        ConverterDB.add("zhegalkin", zhegalkinConverter);
        ConverterDB.add("zhegalkins", zhegalkinConverter);

        TruthTableConverter truthTableConverter=new TruthTableConverter();
        ConverterDB.add("ти",truthTableConverter);
        ConverterDB.add("tt",truthTableConverter);

        PierceConverter pierceConverter = new PierceConverter();
        ConverterDB.add("пирс", pierceConverter);
        ConverterDB.add("пирса", pierceConverter);
        ConverterDB.add("стрелка", pierceConverter);
        ConverterDB.add("стрелки", pierceConverter);
        ConverterDB.add("pierce", pierceConverter);
        ConverterDB.add("pierces", pierceConverter);
        ConverterDB.add("arrow", pierceConverter);
        ConverterDB.add("arrows", pierceConverter);
        ConverterDB.add("webb", pierceConverter);
        ConverterDB.add("webbs", pierceConverter);
        ConverterDB.add("nor", pierceConverter);

        ShefferConverter shefferConverter = new ShefferConverter();
        ConverterDB.add("шеффер", shefferConverter);
        ConverterDB.add("шеффера", shefferConverter);
        ConverterDB.add("шиффер", shefferConverter);
        ConverterDB.add("шиффера", shefferConverter);
        ConverterDB.add("штрих", shefferConverter);
        ConverterDB.add("штрихи", shefferConverter);
        ConverterDB.add("sheffer", shefferConverter);
        ConverterDB.add("sheffers", shefferConverter);
        ConverterDB.add("stroke", shefferConverter);
        ConverterDB.add("strokes", shefferConverter);
        ConverterDB.add("nand", shefferConverter);

        VariedGenerator variedGenerator = new VariedGenerator();
        GeneratorDB.add("", variedGenerator);

        SimpleGenerator simpleGenerator = new SimpleGenerator();
        GeneratorDB.add("простой", simpleGenerator);
        GeneratorDB.add("simple", simpleGenerator);

        DNFGenerator dnfGenerator = new DNFGenerator();
        GeneratorDB.add("днф", dnfGenerator);
        GeneratorDB.add("dnf", dnfGenerator);
        GeneratorDB.add("сднф", dnfGenerator);
        GeneratorDB.add("pdnf", dnfGenerator);
    }


    public static String getDefaultLetters(int params) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < params; i++)
            result.append((char) ('a' + i));
        return result.toString();
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 5)
            throw new IllegalArgumentException("This method requires at least 5 arguments!");

        String variantNumber = "1";
        if (args.length >= 6)
            variantNumber = args[5];

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        FileInputStream input = new FileInputStream(args[0]);
        Document doc = builder.parse(input);

        File file = new File(args[1]);
        FileInputStream input2 = new FileInputStream(file);
        byte[] bytes = new byte[(int) file.length()];
        input2.read(bytes);
        String template = new String(bytes, StandardCharsets.UTF_8);

        file = new File(args[2]);
        input2 = new FileInputStream(file);
        bytes = new byte[(int) file.length()];
        input2.read(bytes);
        String answersTemplate = new String(bytes, StandardCharsets.UTF_8);

        TestVariant variant = new TestVariant();
        NodeList tasks = doc.getDocumentElement().getElementsByTagName("task");
        for (int i = 0; i < tasks.getLength(); i++) {
            Element task = (Element) tasks.item(i);
            String varNames;
            int params;

            if (task.getAttribute("variables").equals(""))
                params = 2;
            else {
                params = Integer.parseInt(task.getAttribute("variables"));
                if (params <= 0 || params >= 27)
                    throw new IllegalArgumentException("The target function in task " + (i + 1) + " has " + params + " variables, must be between 1 and 26!");
            }

            varNames = task.getAttribute("varNames");
            if (varNames.length() != params)
                varNames = getDefaultLetters(params);

            IGenerator generator;
            IConverter inputConverter;
            IConverter outputConverter;
            int length;
            generator = GeneratorDB.get(task.getAttribute("generator").toLowerCase());
            if (generator == null)
                generator = GeneratorDB.get("");
            if (generator == null)
                throw new NullPointerException("Default generator not set!");
            inputConverter = ConverterDB.get(task.getAttribute("input").toLowerCase());
            if (inputConverter == null)
                inputConverter = ConverterDB.get("");
            if (inputConverter == null)
                throw new NullPointerException("Default converter not set!");
            outputConverter = ConverterDB.get(task.getAttribute("output").toLowerCase());
            if (outputConverter == null)
                inputConverter = ConverterDB.get("");
            if (outputConverter == null)
                throw new NullPointerException("Default converter not set!");

            length = generatorDefaultLengths.getOrDefault(task.getAttribute("generator").toLowerCase(), defaultGeneratorLength).apply(params);
            if (!task.getAttribute("length").equals(""))
                length = Integer.parseInt(task.getAttribute("length"));

            variant.addTask(new Task(params, varNames, length, generator, inputConverter, outputConverter,
                    task.getTextContent()));
        }

        template = template.replaceAll("(?<=[^@]|^)(?>(?>@@)*)(@questions)", variant.printQuestions());
        answersTemplate = answersTemplate.replaceAll("(?<=[^@]|^)(?>(?>@@)*)(@questions)", variant.printAnswers());

        template = template.replaceAll("(?<=[^@]|^)(?>(?>@@)*)(@variant)", variantNumber);
        answersTemplate = answersTemplate.replaceAll("(?<=[^@]|^)(?>(?>@@)*)(@variant)", variantNumber);

        template = template.replaceAll("@@", "@");
        answersTemplate = answersTemplate.replaceAll("@@", "@");

        if (new File(args[3]).getParentFile() != null)
            new File(args[3]).getParentFile().mkdirs();
        if (new File(args[4]).getParentFile() != null)
            new File(args[4]).getParentFile().mkdirs();

        new File(args[3]).delete();
        new File(args[4]).delete();

        FileWriter writer = new FileWriter(args[3]);
        writer.write(template);
        writer.close();

        writer = new FileWriter(args[4]);
        writer.write(answersTemplate);
        writer.close();
    }

}
