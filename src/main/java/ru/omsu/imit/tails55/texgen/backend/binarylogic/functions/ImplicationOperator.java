package ru.omsu.imit.tails55.texgen.backend.binarylogic.functions;

import ru.omsu.imit.tails55.texgen.backend.binarylogic.TruthTable;

public class ImplicationOperator extends BiOperator {
    public ImplicationOperator(int params, FunctionTree left, FunctionTree right) {
        super(params, left, right);
    }

    @Override
    public TruthTable calculate() {
        return TruthTable.or(TruthTable.not(getLeft().calculate()), getRight().calculate());
    }

    @Override
    public String getSymbol() {
        return "\\Rightarrow ";
    }

    @Override
    public int getPriority() {
        return 50;
    }

}