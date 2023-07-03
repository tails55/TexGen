package ru.omsu.imit.tails55.texgen.backend.binarylogic.functions;

import ru.omsu.imit.tails55.texgen.backend.binarylogic.TruthTable;

public class XorOperator extends BiOperator {
    public XorOperator(int params, FunctionTree left, FunctionTree right) {
        super(params, left, right);
    }

    @Override
    public TruthTable calculate() {
        return TruthTable.xor(getLeft().calculate(), getRight().calculate());
    }

    @Override
    public String getSymbol() {
        return "+";
    }

    @Override
    public int getPriority() {
        return 30;
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        if (getLeft() instanceof BiOperator && (((BiOperator) (getLeft())).getPriority() <= getPriority() && !(getLeft() instanceof XorOperator)))
            result.append("(").append(getLeft().toString()).append(")");
        else
            result.append(getLeft().toString());
        result.append(getSymbol());
        if (getRight() instanceof BiOperator && (((BiOperator) (getRight())).getPriority() <= getPriority() && !(getRight() instanceof XorOperator)))
            result.append("(").append(getRight().toString()).append(")");
        else
            result.append(getRight().toString());
        return result.toString();
    }
}