package com.vorax.module;

import java.util.function.Function;

import com.vorax.core.Client;

import groovy.lang.Script;

public class ModuleScript extends Script {
    public static final int PRE_INIT = 0;
    public static final int INIT = 1;
    public static final int POST_INIT = 2;

    protected Client client;
    protected ModuleInstance module;
    protected int phase;

    @Override
    public Object run() {
        return null;
    }

    public void run(Client client, ModuleInstance module, int phase) {
        this.client = client;
        this.module = module;
        this.phase = phase;

        run();
    }

    public void setName(String name) {
        module.getIdentifier().setName(name);
    }

    public void setVersion(String version) {
        module.getIdentifier().setVersion(version);
    }

    public void require(String name, String version) {
        module.require(name, version);
    }

    public boolean isPhase(int phase) {
        return this.phase == phase;
    }

    public boolean isModuleLoaded(String name, String version) {
        ModuleIdentifier id = new ModuleIdentifier(name, version);
        return client
                .getServer()
                .getLoader()
                .getModules()
                .containsKey(id)
                        ? client
                                .getServer()
                                .getLoader()
                                .getModules()
                                .get(id)
                                .getIdentifier()
                                .compareTo(id) >= 0
                        : false;
    }

    public void set(String key, Object value) {
        module.getConfig().set(key, value);
    }

    public <T> T get(String key) {
        return module.getConfig().get(key);
    }

    public void write(String key, Object value) {
        client.getServer().getState().write(key, value);
    }

    public <T> T read(String key) {
        return client.getServer().getState().read(key);
    }

    public void encode(int error, String message) {
        client.getServer().getParser().encode(error, message);
    }

    public String decode(int error) {
        return client.getServer().getParser().decode(error);
    }

    public void register(String name, Function<String[], Integer> function) {
        client.getServer().getParser().register(name, function);
    }

    @Override
    public void print(Object value) {
        client.print(value);
    }

    @Override
    public void printf(String format, Object value) {
        client.printf(format, value);
    }

    @Override
    public void printf(String format, Object[] values) {
        client.printf(format, values);
    }

    @Override
    public void println(Object value) {
        client.println(value);
    }

    @Override
    public void println() {
        client.println();
    }

    @Override
    public String toString() {
        return String.format("{phase=%s, client=%s}", phase, client);
    }
}
