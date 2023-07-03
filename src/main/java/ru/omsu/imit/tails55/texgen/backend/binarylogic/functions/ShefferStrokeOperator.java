package ru.omsu.imit.tails55.texgen.backend.binarylogic.functions;

import ru.omsu.imit.tails55.texgen.backend.binarylogic.TruthTable;

public class ShefferStrokeOperator extends BiOperator {
    public ShefferStrokeOperator(int params, FunctionTree left, FunctionTree right) {
        super(params, left, right);
    }

    @Override
    public TruthTable calculate() {
        return TruthTable.not(TruthTable.and(getLeft().calculate(), getRight().calculate()));
    }

    @Override
    public String getSymbol() {
        return "\\,|\\allowbreak\\,";
    }

    @Override
    public int getPriority() {
        return 100;
    }
}