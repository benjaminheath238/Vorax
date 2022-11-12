package com.vorax.core;

import java.util.HashMap;
import java.util.Map;

public class State {
    private Map<String, Object> values;

    public State() {
        this.values = new HashMap<>();
    }

    public void write(String key, Object value) {
        values.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T read(String key) {
        return (T) values.get(key);
    }    
}
