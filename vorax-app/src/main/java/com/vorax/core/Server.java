package com.vorax.core;

import com.vorax.module.ModuleLoader;

import lombok.Getter;

@Getter
public final class Server {
    private Parser parser;
    private ModuleLoader loader;
    private Environment env;
    private State state;

    public Server(Environment env) {
        this.env = env;
        this.parser = new Parser();
        this.loader = new ModuleLoader(env);
        this.state = new State();
    }
}
