package com.vorax.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public final class Environment {
    private File home;

    public Environment(String home) {
        this.home = new File(home);
    }

    public void mkdirs(String name) {
        open(name).mkdirs();
    }

    public void mkdirs(String... name) {
        for (String dir : name) {
            mkdirs(dir);
        }
    }

    public void touch(String name) {
        try {
            open(name).createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void touch(String... name) {
        for (String file : name) {
            touch(file);
        }
    }

    public void delete(String name) {
        open(name).delete();
    }

    public void delete(String... name) {
        for (String file : name) {
            delete(file);
        }
    }

    public void copy(String from, String to) {
        try (InputStream in = new FileInputStream(open(from));
                OutputStream out = new FileOutputStream(open(to))) {
            out.write(in.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void extract(String from, String to) {
        try (InputStream in = Environment.class.getResourceAsStream(from);
                OutputStream out = new FileOutputStream(open(to))) {
            out.write(in.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void move(String from, String to) {
        copy(from, to);
        delete(from);
    }

    public File open(String name) {
        return new File(home, name);
    }

    @Override
    public String toString() {
        return String.format("{home=%s}", home);
    }
}
