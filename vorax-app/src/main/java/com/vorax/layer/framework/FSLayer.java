package com.vorax.layer.framework;

import java.io.Closeable;
import java.io.File;

public interface FSLayer extends Closeable {
    public void mkdirs(String name);
    public void mkdirs(String... name);

    public void touch(String name);
    public void touch(String... name);

    public void delete(String name);
    public void delete(String... name);

    public void copy(String from, String to);
    
    public void extract(String from, String to);

    public void move(String from, String to);

    public File open(String name);
}
