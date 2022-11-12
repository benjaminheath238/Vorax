package com.vorax.server;

import com.vorax.layer.framework.FSLayer;
import com.vorax.module.ModuleLoader;

import lombok.Getter;

@Getter
public final class Server {
    private Parser parser;
    private ModuleLoader loader;
    private FSLayer fs;

    public Server(FSLayer fs) {
        this.fs = fs;
        this.parser = new Parser();
        this.loader = new ModuleLoader(fs);
    }
}
