package com.vorax.module;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.vorax.core.Client;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class ModuleInstance {
    private ModuleScript script;
    private File scriptFile;

    private ModuleIdentifier identifier;
    private List<ModuleIdentifier> dependencies;
    private ModuleConfiguration config;

    private boolean disabled;

    public ModuleInstance() {
        this.identifier = new ModuleIdentifier();
        this.script = null;
        this.dependencies = new ArrayList<>();
        this.disabled = false;
    }

    public void execute(Client client, int phase) {
        if (script == null || client == null) {
            throw new NullPointerException("Neither the parser, client or script can be null");
        }

        script.run(client, this, phase);
    }

    public void require(String name, String version) {
        dependencies.add(new ModuleIdentifier(name, version));
    }

    @Override
    public boolean equals(Object obj) {
        return identifier.equals(obj);
    }

    @Override
    public int hashCode() {
        return identifier.hashCode();
    }

    @Override
    public String toString() {
        return String.format("{identifier=%s, disabled=%s, dependencies=%s, config=%s}", identifier, disabled,
                dependencies, config);
    }
}
