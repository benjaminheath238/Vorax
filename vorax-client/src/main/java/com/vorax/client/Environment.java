package com.vorax.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Environment {
    private File home;

    public Environment(String home) {
        this.home = new File(home);
    }

    public File mkdirs(String dirName) {
        File dir = new File(home, dirName);

        dir.mkdirs();

        return dir;
    }

    public File[] mkdirs(String... dirNames) {
        File[] files = new File[dirNames.length - 1];

        for (int i = 0; i < dirNames.length; i++) {
            files[i] = mkdirs(dirNames[i]);
        }

        return files;
    }

    public File touch(String fileName) {
        File dir = new File(home, fileName);

        try {
            dir.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dir;
    }

    public File[] touch(String... fileNames) {
        File[] files = new File[fileNames.length - 1];

        for (int i = 0; i < fileNames.length; i++) {
            files[i] = touch(fileNames[i]);
        }

        return files;
    }

    public File open(String fileName) {
        return new File(home, fileName);
    }

    public String absolute(String fileName) {
        return open(fileName).getAbsolutePath();
    }

    public boolean exists(String fileName) {
        return open(fileName).exists();
    }

    public File extract(String srcName, String destName) {
        File file = new File(home, destName);

        try (InputStream input = Environment.class.getResourceAsStream(srcName);
                OutputStream output = new FileOutputStream(file)) {
            output.write(input.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

    public File copy(String srcName, String destName) {
        File file = new File(home, destName);

        try (InputStream input = new FileInputStream(srcName);
                OutputStream output = new FileOutputStream(file)) {
            output.write(input.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }
}
