package com.vorax.core;

import com.vorax.module.ModuleLoader;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public final class Server {
    private Parser parser;
    private ModuleLoader loader;
    private FileSystem fs;
    private State state;

    public Server(FileSystem fs) {
        this.fs = fs;
        this.parser = new Parser();
        this.loader = new ModuleLoader(fs);
        this.state = new State();
    }

    @Override
    public String toString() {
        return String.format("{parser=%s, loader=%s, env=%s, state=%s}", parser, loader, fs, state);
    }
}
