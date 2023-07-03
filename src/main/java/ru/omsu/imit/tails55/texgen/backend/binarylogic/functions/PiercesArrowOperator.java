package ru.omsu.imit.tails55.texgen.backend.binarylogic.functions;

import ru.omsu.imit.tails55.texgen.backend.binarylogic.TruthTable;

public class PiercesArrowOperator extends BiOperator {
    public PiercesArrowOperator(int params, FunctionTree left, FunctionTree right) {
        super(params, left, right);
    }

    @Override
    public TruthTable calculate() {
        return TruthTable.not(TruthTable.or(getLeft().calculate(), getRight().calculate()));
    }

    @Override
    public String getSymbol() {
        return "\\downarrow ";
    }

    @Override
    public int getPriority() {
        return 100;
    }
}