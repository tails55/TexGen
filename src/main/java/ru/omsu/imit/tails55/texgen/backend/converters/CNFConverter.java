package ru.omsu.imit.tails55.texgen.backend.converters;


import ru.omsu.imit.tails55.texgen.backend.binarylogic.TruthTable;
import ru.omsu.imit.tails55.texgen.backend.binarylogic.functions.*;

public class CNFConverter implements IConverter {

    public String convert(FunctionTree tree, String letters) {
        TruthTable truthTable = tree.calculate();
        int params = truthTable.getParams();

        FunctionTree result = null;
        for (int i = 0; i < 1 << params; i++) {
            if (!truthTable.getValue(i)) {
                FunctionTree disjunct = null;
                for (int j = 0; j < params; j++) {
                    FunctionTree symbol = new Symbol(params, params - 1 - j, letters.charAt(params - 1 - j));
                    if (((i >> j) % 2) != 0)
                        symbol = new Inverse(params, symbol);
                    if (disjunct == null)
                        disjunct = symbol;
                    else
                        disjunct = new OrOperator(params, disjunct, symbol);
                }
                if (result == null)
                    result = disjunct;
                else
                    result = new AndOperator(params, result, disjunct);
            }
        }

        if (result == null)
            result = new Constant(params, true);

        return "$" + result + "$";
    }
}
