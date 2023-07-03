package ru.omsu.imit.tails55.texgen.backend.generators;

import ru.omsu.imit.tails55.texgen.backend.binarylogic.TruthTable;
import ru.omsu.imit.tails55.texgen.backend.binarylogic.functions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class VariedGenerator implements IGenerator {
    private final Random random = new Random();

    public VariedGenerator() {
    }

    public FunctionTree generate(int params, String letters, int length) {
        List<FunctionTree> functions = new ArrayList<>();
        if(params<2)
            throw new IllegalArgumentException();
        if(length<1||length>3*params*(params-1))
            throw new IllegalArgumentException();

        for (int i = 0; i < length; i++) {
            FunctionTree symbol = new Symbol(params, i % params, letters.charAt(i % params));
            if (random.nextInt(100) < 35)
                symbol = new Inverse(params, symbol);
            functions.add(symbol);
        }

        Collections.shuffle(functions, random);

        while (functions.size() > 1) {
            int i = random.nextInt(functions.size());
            FunctionTree left = functions.get(i);
            functions.remove(i);

            int j = random.nextInt(functions.size());
            int startj = j;
            do {
                if (left.calculate().equals(functions.get(j).calculate()) ||
                        left.calculate().equals(TruthTable.not(functions.get(j).calculate())))
                    j = (j + 1) % functions.size();
                else break;
            } while (j != startj);

            if (left.calculate().equals(functions.get(j).calculate()) ||
                    left.calculate().equals(TruthTable.not(functions.get(j).calculate())))
                return generate(params, letters, length);

            FunctionTree right = functions.get(j);
            functions.remove(j);

            FunctionTree result;
            if (random.nextInt(5) == 1)
                result = new AndOperator(params, left, right);
            else if (random.nextInt(4) == 1)
                result = new OrOperator(params, left, right);
            else if (random.nextInt(3) == 1)
                result = new XorOperator(params,left,right);
            else if (random.nextInt(2) == 1)
                result = new EqualityOperator(params,left,right);
            else
                result = new ImplicationOperator(params,left,right);
            if (random.nextInt(100)<28)
                result = new Inverse(params, result);

            functions.add(random.nextInt(functions.size() + 1), result);
        }

        return functions.get(0);
    }
}
