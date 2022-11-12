package com.vorax.module;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ModuleConfiguration {
    private Map<String, Object> values;
    private File file;

    public ModuleConfiguration(File path) {
        this.file = path;
    }

    public void set(String key, Object value) {
        values.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) values.get(key);
    }

    public void save() {
        try {
            new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(file, values);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void load() {
        if (file.length() == 0) {
            values = new HashMap<>();
            return;
        }

        try {
            values = new ObjectMapper().readValue(file, HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return String.format("{file=%s, values=%s}", file, values);
    }
}
