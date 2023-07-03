package ru.omsu.imit.tails55.texgen.backend.binarylogic.functions;

import ru.omsu.imit.tails55.texgen.backend.binarylogic.TruthTable;

public class Constant extends FunctionTree {
    private final TruthTable function;
    private final boolean value;

    public Constant(int params, boolean value) {
        super(params);
        this.value = value;
        if (value)
            this.function = TruthTable.not(new TruthTable(params));
        else
            this.function = new TruthTable(params);
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public TruthTable calculate() {
        return function;
    }

    @Override
    public String toString() {
        return value ? "1" : "0";
    }
}
