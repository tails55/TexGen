package ru.omsu.imit.tails55.texgen.backend.binarylogic.functions;

import ru.omsu.imit.tails55.texgen.backend.binarylogic.TruthTable;

public class AndOperator extends BiOperator {
    public AndOperator(int params, FunctionTree left, FunctionTree right) {
        super(params, left, right);
    }

    @Override
    public TruthTable calculate() {
        return TruthTable.and(getLeft().calculate(), getRight().calculate());
    }

    @Override
    public String getSymbol() {
        return "\\land ";
    }

    @Override
    public int getPriority() {
        return 90;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        if (getLeft() instanceof BiOperator && (((BiOperator) (getLeft())).getPriority() <= getPriority() && !(getLeft() instanceof AndOperator)))
            result.append("(").append(getLeft().toString()).append(")");
        else
            result.append(getLeft().toString());
        result.append(getSymbol());
        if (getRight() instanceof BiOperator && (((BiOperator) (getRight())).getPriority() <= getPriority() && !(getRight() instanceof AndOperator)))
            result.append("(").append(getRight().toString()).append(")");
        else
            result.append(getRight().toString());
        return result.toString();
    }
}