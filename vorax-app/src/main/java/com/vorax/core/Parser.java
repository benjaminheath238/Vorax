package com.vorax.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public final class Parser {
    private Map<String, Function<String[], Integer>> functions;
    private Map<Integer, String> errors;

    public Parser() {
        this.functions = new HashMap<>();
        this.errors = new HashMap<>();
    }

    public void encode(int error, String message) {
        if (errors.containsKey(error)) {
            throw new IllegalArgumentException("An error message already exists with that key");
        }

        errors.put(error, message);
    }

    public String decode(int error) {
        return errors.containsKey(error) ? errors.get(error) : String.format("Undefined error %s", error);
    }

    public void register(String name, Function<String[], Integer> function) {
        if (name == null) {
            throw new NullPointerException("Function names can not be null");
        }
        
        if (name.contains(" ")) {
            throw new IllegalArgumentException("Function names can not contain white space");
        }

        if (name.isEmpty()) {
            throw new IllegalArgumentException("Function names can not be of length 0");
        }

        if (function == null) {
            throw new NullPointerException("Functions must not be null");
        }

        functions.put(name, function);
    }

    public int parse(String text) {
        if (text == null || text.isEmpty() || text.isBlank()) {
            return 1;
        }

        return parse(text.split(" "));
    }

    public int parse(String[] text) {
        String[] args = new String[text.length - 1];

        System.arraycopy(text, 1, args, 0, args.length);

        return parse(text[0], args);
    }

    public int parse(String name, String[] args) {
        if (!functions.containsKey(name)) {
            return 1;
        }

        Integer error = functions.get(name).apply(args);

        if (error == null) {
            throw new NullPointerException("Functions must have a defined integer return value");
        } else {
            return error;
        }
    }

    public Map<Integer, String> getErrors() {
        return errors;
    }

    public Set<String> getFunctions() {
        return functions.keySet();
    }

    @Override
    public String toString() {
        return String.format("{functions=%s, errors=%s}", functions.keySet(), errors);
    }
}
