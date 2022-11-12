package com.vorax.layer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.vorax.layer.framework.FSLayer;

public final class FileSystemLayer implements FSLayer {
    private File home;

    public FileSystemLayer(String home) {
        this.home = new File(home);
    }

    @Override
    public void mkdirs(String name) {
        open(name).mkdirs();
    }

    @Override
    public void mkdirs(String... name) {
        for (String dir : name) {
            mkdirs(dir);
        }
    }

    @Override
    public void touch(String name) {
        try {
            open(name).createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void touch(String... name) {
        for (String file : name) {
            touch(file);
        }
    }

    @Override
    public void delete(String name) {
        open(name).delete();
    }

    @Override
    public void delete(String... name) {
        for (String file : name) {
            delete(file);
        }
    }

    @Override
    public void copy(String from, String to) {
        try (InputStream in = new FileInputStream(open(from));
                OutputStream out = new FileOutputStream(open(to))) {
            out.write(in.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void extract(String from, String to) {
        try (InputStream in = FileSystemLayer.class.getResourceAsStream(from);
                OutputStream out = new FileOutputStream(open(to))) {
            out.write(in.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void move(String from, String to) {
        copy(from, to);
        delete(from);
    }

    @Override
    public File open(String name) {
        return new File(home, name);
    }

    @Override
    public void close() throws IOException {

    }
}
