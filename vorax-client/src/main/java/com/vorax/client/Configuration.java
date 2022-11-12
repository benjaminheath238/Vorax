package com.vorax.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXB;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Configuration {
    private Map<String, Object> values;
    private File file;

    public Configuration() {
        this.values = new HashMap<>();
    }

    public Configuration(String file) {
        this.file = new File(file);
    }

    public void save(FileType type) {
        switch (type) {
            case JSON:
                saveAsJson();
                break;
            case XML:
                saveAsXml();
                break;
            case BINARY:
                saveAsBinary();
                break;
            default:
                break;
        }
    }

    public void saveAsJson() {
        try {
            new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(file, values);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAsXml() {
        JAXB.marshal(values, file);
    }

    public void saveAsBinary() {
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file))) {
            stream.writeObject(values);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load(FileType type) {
        switch (type) {
            case JSON:
                loadFromJson();
                break;
            case XML:
                loadFromXml();
                break;
            case BINARY:
                loadFromBinary();
                break;
            default:
                break;
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromJson() {
        try {
            values = new ObjectMapper().readValue(file, HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromXml() {
        values = JAXB.unmarshal(file, HashMap.class);
    }

    @SuppressWarnings("unchecked")
    public void loadFromBinary() {
        try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(file))) {
            values = (Map<String, Object>) stream.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    public enum FileType {
        XML,
        JSON,
        BINARY;
    }
}
