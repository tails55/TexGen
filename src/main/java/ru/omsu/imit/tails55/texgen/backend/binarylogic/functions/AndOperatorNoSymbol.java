package ru.omsu.imit.tails55.texgen.backend.binarylogic.functions;

public class AndOperatorNoSymbol extends AndOperator {
    public AndOperatorNoSymbol(int params, FunctionTree left, FunctionTree right) {
        super(params, left, right);
    }

    @Override
    public String getSymbol() {
        return "";
    }
}