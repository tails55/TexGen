package ru.omsu.imit.tails55.texgen.backend.binarylogic.functions;

import ru.omsu.imit.tails55.texgen.backend.binarylogic.TruthTable;

public abstract class FunctionTree {
    private int params;

    public FunctionTree(int params) {
        this.params = params;
    }

    public int getParams() {
        return params;
    }

    public void setParams(int params) {
        this.params = params;
    }

    public abstract TruthTable calculate();
}
