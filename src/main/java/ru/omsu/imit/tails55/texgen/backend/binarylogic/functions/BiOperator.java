package ru.omsu.imit.tails55.texgen.backend.binarylogic.functions;

public abstract class BiOperator extends FunctionTree {
    private FunctionTree left;
    private FunctionTree right;

    public BiOperator(int params, FunctionTree left, FunctionTree right) {
        super(params);
        this.left = left;
        this.right = right;
    }

    public FunctionTree getLeft() {
        return left;
    }

    public void setLeft(FunctionTree left) {
        this.left = left;
    }

    public FunctionTree getRight() {
        return right;
    }

    public void setRight(FunctionTree right) {
        this.right = right;
    }

    public abstract String getSymbol();

    public abstract int getPriority();

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        if (getLeft() instanceof BiOperator && ((BiOperator) (getLeft())).getPriority() <= getPriority())
            result.append("(").append(getLeft().toString()).append(")");
        else
            result.append(getLeft().toString());
        result.append(getSymbol());
        if (getRight() instanceof BiOperator && ((BiOperator) (getRight())).getPriority() <= getPriority())
            result.append("(").append(getRight().toString()).append(")");
        else
            result.append(getRight().toString());
        return result.toString();
    }
}