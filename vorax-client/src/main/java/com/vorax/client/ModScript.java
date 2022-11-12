package com.vorax.client;

import groovy.lang.Script;

public class ModScript extends Script {
    protected ModInstance mod;
    protected Client client;

    @Override
    public Object run() {
        return null;
    }

    public void run(ModInstance mod, Client client) {
        this.mod = mod;
        this.client = client;
    }

    // Pre-init phase

    public void setName(String name) {
        mod.setName(name);
    }

    public void setVersion(String version) {
        mod.setVersion(version);
    }
 
    public void requires(String name, String version) {
        mod.requires(name, version);
    }

    // Init Phase
}
