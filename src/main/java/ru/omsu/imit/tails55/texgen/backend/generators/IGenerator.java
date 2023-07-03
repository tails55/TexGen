package ru.omsu.imit.tails55.texgen.backend.generators;

import ru.omsu.imit.tails55.texgen.backend.binarylogic.functions.FunctionTree;

public interface IGenerator {
    public FunctionTree generate(int params, String letters, int length);
}
