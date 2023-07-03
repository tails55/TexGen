package ru.omsu.imit.tails55.texgen.backend.converters;


import ru.omsu.imit.tails55.texgen.backend.binarylogic.TruthTable;
import ru.omsu.imit.tails55.texgen.backend.binarylogic.functions.FunctionTree;

public class TruthTableConverter implements IConverter {

    public String convert(FunctionTree tree, String letters) {
        TruthTable function = tree.calculate();
        StringBuilder result = new StringBuilder();
        int params = function.getParams();
        result.append("\\begin{center}\n");
        result.append("\\begin{tabular}{||");
        for (int i = 0; i <= params; i++) {
            if (i != 0)
                result.append(' ');
            result.append('c');
        }
        result.append("||}\n");
        result.append(" \\hline\n");
        for (int i = 0; i < params; i++) {
            if (i != 0)
                result.append(" &");
            result.append(" ").append(letters.charAt(i));
        }
        result.append(" & f() \\\\\n");
        result.append(" \\hline\\hline\n");
        for (int i = 0; i < 1 << params; i++) {
            for (int j = params - 1; j >= 0; j--) {
                result.append((i >> j) & 1).append(" & ");
            }
            result.append(function.getValue(i) ? "1" : "0").append(" \\\\\n");
            result.append(" \\hline\n");
        }
        result.append("\\end{tabular}\n");
        result.append("\\end{center}~");
        return result.toString();
    }
}
