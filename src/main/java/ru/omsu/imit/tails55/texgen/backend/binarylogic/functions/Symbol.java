package ru.omsu.imit.tails55.texgen.backend.binarylogic.functions;

import ru.omsu.imit.tails55.texgen.backend.binarylogic.TruthTable;

public class Symbol extends FunctionTree {
    private int varId;
    private final TruthTable function;
    private final char symbol;

    public Symbol(int params, int varId, char symbol) {
        super(params);
        this.varId = varId;
        function = new TruthTable(getParams(), varId);
        this.symbol = symbol;
    }

    public int getVarId() {
        return varId;
    }

    @Override
    public TruthTable calculate() {
        return function;
    }

    @Override
    public String toString() {
        return String.valueOf(symbol);
    }


}