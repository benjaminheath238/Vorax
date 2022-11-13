package com.vorax.extern;

import java.util.HashMap;
import java.util.Map;

public class Registry<T> {
    private Map<String, T> contents;

    public Registry() {
        this.contents = new HashMap<>();
    }

    public void register(String key, T value) {
        contents.put(key, value);
    }

    public T find(String key) {
        return contents.get(key);
    }
}
