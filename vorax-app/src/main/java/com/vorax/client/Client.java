package com.vorax.client;

import com.vorax.layer.framework.IOLayer;
import com.vorax.server.Server;

import lombok.Getter;

@Getter
public abstract class Client {
    protected IOLayer io;
    protected Server server;

    public int parse(String text) {
        return server.getParser().parse(text);
    }

    public String decode(int error) {
        return server.getParser().decode(error);
    }

    public String read() {
        return io.read();
    }

    public void print(Object object) {
        io.write(object.toString());
    }

    public void printf(String format, Object... args) {
        io.write(String.format(format, args));
    }

    public void println(Object object) {
        io.write(object.toString() + "\n");
    }

    public void println() {
        io.write("\n");
    }

    public void clear(boolean i, boolean o) {
        io.clear(i, o);
    }

    protected final void setIOLayer(IOLayer io) {
        this.io = io;
    }

    protected final void setServer(Server server) {
        this.server = server;
    }

    public abstract void start();
    
    public abstract void stop();
}
