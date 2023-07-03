package ru.omsu.imit.tails55.texgen.backend.converters;


import ru.omsu.imit.tails55.texgen.backend.binarylogic.TruthTable;
import ru.omsu.imit.tails55.texgen.backend.binarylogic.functions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class ZhegalkinConverter implements IConverter {

    public String convert(FunctionTree tree, String letters) {
        TruthTable truthTable = tree.calculate();
        int params = truthTable.getParams();

        boolean[] polynom = new boolean[1 << params];
        TreeSet<List<Integer>> addends = new TreeSet<>((a, b) -> {
            if (a.size() != b.size())
                return b.size() - a.size();
            for (int i = 0; i < a.size(); i++)
                if (!a.get(i).equals(b.get(i)))
                    return a.get(i) - b.get(i);
            return 0;
        });
        //по сути, цикл по j от 0 до (1<<params)-1, в котором i - log2(j)
        for (int i = 0; i <= params; i++) {
            for (int j = (1 << i) >> 1; j < 1 << i; j++) {

                if (truthTable.getValue(j)) {
                    for (int k = j; k < polynom.length; k++)
                        if ((k & j) == j)
                            polynom[k] = !polynom[k];
                }

                if (polynom[j]) {
                    List<Integer> addend = new ArrayList<>();
                    for (int k = i - 1; k >= 0; k--) {
                        if ((j & (1 << k)) != 0) {
                            addend.add(params - k - 1);
                        }
                    }
                    addends.add(addend);

                }
            }
        }
        FunctionTree result = null;
        for (List<Integer> addendAsList : addends) {
            FunctionTree addend = null;
            for (Integer id : addendAsList) {
                FunctionTree symbol = new Symbol(params, id, letters.charAt(id));
                if (addend == null)
                    addend = symbol;
                else
                    addend = new AndOperatorNoSymbol(params, addend, symbol);
            }
            if (addend == null)
                addend = new Constant(params, true);
            if (result == null)
                result = addend;
            else
                result = new XorOperator(params, result, addend);
        }

        if (result == null)
            result = new Constant(params, false);

        return "$" + result + "$";
    }
}
