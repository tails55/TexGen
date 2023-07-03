package ru.omsu.imit.tails55.texgen.backend.binarylogic;

import java.util.Arrays;
import java.util.Objects;

public class TruthTable {
    private int params;
    private boolean[] values;

    TruthTable(int params, boolean[] values) {
        this.params = params;
        this.values = values;
    }

    public TruthTable(int params) {
        this.params = params;
        this.values = new boolean[1 << params];
    }

    public TruthTable(int params, int trueParamIndex) {
        if (trueParamIndex < 0 || trueParamIndex >= params)
            throw new IllegalArgumentException();
        this.params = params;
        this.values = new boolean[1 << params];

        for (int i = 0; i < this.values.length; i++) {
            this.values[i] = (i >> (params-1-trueParamIndex)) % 2 == 1;
        }

    }

    public static int inputToIndex(boolean[] input) {
        int i = 0;
        if (input == null)
            throw new NullPointerException();
        for (int j = input.length - 1; j >= 0; j--) {
            if (input[j])
                i++;
            i <<= 1;
        }
        return i;
    }

    public int getParams() {
        return params;
    }

    boolean[] getValues() {
        return values;
    }

    public boolean getValue(int index) {
        return values[index];
    }

    public boolean getValue(boolean[] input) {
        if (input.length != params)
            throw new IllegalArgumentException();
        return values[inputToIndex(input)];
    }

    public void setValue(int index, boolean value) {
        values[index] = value;
    }

    public void setValue(boolean[] input, boolean value) {
        values[inputToIndex(input)] = value;
    }

    public static TruthTable not(TruthTable source) {
        if (source == null)
            throw new NullPointerException();
        TruthTable output = new TruthTable(source.params);
        for (int i = 0; i < output.values.length; i++)
            output.values[i] = !source.values[i];
        return output;
    }

    public static TruthTable and(TruthTable a, TruthTable b) {
        if (a == null || b == null)
            throw new NullPointerException();
        if (a.params != b.params)
            throw new IllegalArgumentException();
        TruthTable output = new TruthTable(a.params);
        for (int i = 0; i < output.values.length; i++)
            output.values[i] = a.values[i] && b.values[i];
        return output;
    }

    public static TruthTable or(TruthTable a, TruthTable b) {
        if (a == null || b == null)
            throw new NullPointerException();
        if (a.params != b.params)
            throw new IllegalArgumentException();
        TruthTable output = new TruthTable(a.params);
        for (int i = 0; i < output.values.length; i++)
            output.values[i] = a.values[i] || b.values[i];
        return output;
    }

    public static TruthTable xor(TruthTable a, TruthTable b) {
        if (a == null || b == null)
            throw new NullPointerException();
        if (a.params != b.params)
            throw new IllegalArgumentException();
        TruthTable output = new TruthTable(a.params);
        for (int i = 0; i < output.values.length; i++)
            output.values[i] = a.values[i] ^ b.values[i];
        return output;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TruthTable that = (TruthTable) o;
        return params == that.params &&
                Arrays.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(params);
        result = 31 * result + Arrays.hashCode(values);
        return result;
    }

    @Override
    public String toString() {
        return "BinaryFunction{" +
                "values=" + Arrays.toString(values) +
                '}';
    }
}
