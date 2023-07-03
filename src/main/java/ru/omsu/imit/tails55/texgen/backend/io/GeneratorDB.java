package ru.omsu.imit.tails55.texgen.backend.io;

import ru.omsu.imit.tails55.texgen.backend.generators.IGenerator;

import java.util.HashMap;

public class GeneratorDB {
    private static final HashMap<String, IGenerator> generators = new HashMap<>();

    public static boolean add(String key, IGenerator generator) {
        return generators.put(key, generator) == null;
    }

    public static IGenerator get(String key) {
        return generators.get(key);
    }
}
