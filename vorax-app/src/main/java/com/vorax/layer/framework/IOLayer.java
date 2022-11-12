package com.vorax.layer.framework;

import java.io.Closeable;

public interface IOLayer extends Closeable {
    public void write(String string);

    public String read();

    public void clear(boolean input, boolean output);
}
