package ru.omsu.imit.tails55.texgen.backend.converters;

import ru.omsu.imit.tails55.texgen.backend.binarylogic.functions.FunctionTree;

public interface IConverter {
    public String convert(FunctionTree tree, String letters);
}
