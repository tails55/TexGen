package ru.omsu.imit.tails55.texgen.backend.generators;

import ru.omsu.imit.tails55.texgen.backend.binarylogic.TruthTable;
import ru.omsu.imit.tails55.texgen.backend.binarylogic.functions.*;
import ru.omsu.imit.tails55.texgen.backend.io.GeneratorDB;

import java.util.*;

public class DNFGenerator implements IGenerator {
    private final Random random = new Random();
    private static final int[][] triangle = new int[27][];

    static {
        for (int i = 0; i <= 26; i++) {
            triangle[i] = new int[i + 1];
            for (int j = 0; j <= i; j++)
                if (j == 0 || j == i)
                    triangle[i][j] = 1;
                else
                    triangle[i][j] = triangle[i - 1][j - 1] + triangle[i - 1][j];
        }
    }

    public DNFGenerator() {
    }

    public FunctionTree generate(int params, String letters, int length) {
        TruthTable truthTable = new TruthTable(params);

        int[] totalValues = Arrays.copyOf(triangle[params], params + 1);
        int[] trueValues = Arrays.copyOf(triangle[params], params + 1);
        for (int i = 0; i <= params; i++) {
            trueValues[i] = (trueValues[i] + random.nextInt(2)) / 2;
        }
        for (int i = 0; i < 1 << params; i++) {
            int count = 0;
            int icopy = i;
            while (icopy > 0) {
                count += icopy & 1;
                icopy >>= 1;
            }
            if (random.nextInt(totalValues[count]) < trueValues[count]) {
                truthTable.setValue(i, true);
                trueValues[count]--;
            }
            totalValues[count]--;
        }

        FunctionTree result = null;
        for (int i = 0; i < 1 << params; i++) {
            if (truthTable.getValue(i)) {
                FunctionTree disjunct = null;
                for (int j = 0; j < params; j++) {
                    FunctionTree symbol = new Symbol(params, params - 1 - j, letters.charAt(params - 1 - j));
                    if (((i >> j) % 2) == 0)
                        symbol = new Inverse(params, symbol);
                    if (disjunct == null)
                        disjunct = symbol;
                    else
                        disjunct = new AndOperator(params, disjunct, symbol);
                }
                if (result == null)
                    result = disjunct;
                else
                    result = new OrOperator(params, result, disjunct);
            }
        }

        if (result == null)
            result = new Constant(params, false);
        return result;
    }
}
