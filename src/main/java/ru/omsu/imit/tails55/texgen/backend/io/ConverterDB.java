package ru.omsu.imit.tails55.texgen.backend.io;

import ru.omsu.imit.tails55.texgen.backend.converters.IConverter;
import ru.omsu.imit.tails55.texgen.backend.generators.IGenerator;

import java.util.HashMap;

public class ConverterDB {
    private static final HashMap<String, IConverter> converters = new HashMap<>();

    public static boolean add(String key, IConverter converter) {
        return converters.put(key, converter) == null;
    }

    public static IConverter get(String key) {
        return converters.get(key);
    }
}
