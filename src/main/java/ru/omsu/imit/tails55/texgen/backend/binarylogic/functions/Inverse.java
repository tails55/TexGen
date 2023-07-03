package ru.omsu.imit.tails55.texgen.backend.binarylogic.functions;

import ru.omsu.imit.tails55.texgen.backend.binarylogic.TruthTable;

public class Inverse extends FunctionTree {
    private FunctionTree inverseOf;

    public Inverse(int params, FunctionTree inverseOf) {
        super(params);
        this.inverseOf = inverseOf;
    }

    public Inverse(FunctionTree inverseOf) {
        super(inverseOf.getParams());
        this.inverseOf = inverseOf;
    }

    public FunctionTree getInverseOf() {
        return inverseOf;
    }

    public void setInverseOf(FunctionTree inverseOf) {
        this.inverseOf = inverseOf;
    }

    @Override
    public TruthTable calculate() {
        return TruthTable.not(inverseOf.calculate());
    }

    @Override
    public String toString() {
        if (inverseOf instanceof BiOperator)
            return "\\lnot (" + inverseOf.toString() + ")";
        return "\\lnot " + inverseOf.toString();
    }
}