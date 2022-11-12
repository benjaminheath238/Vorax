package com.vorax.client;

public interface Client {
    public void print(Object o);

    public void printf(String f, Object... o);

    public void println(Object o);

    public void println();
}
