package com.vorax.client;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModInstance {
    private ModScript script;
    
    private ModIdentifier identifier;
    private List<ModIdentifier> dependencies;
    private Configuration config;

    public ModInstance() {
        this.identifier = new ModIdentifier();
        this.dependencies = new ArrayList<>();
        this.config = new Configuration();
    }

    public void setName(String name) {
        identifier.setName(name);
    }

    public void setVersion(String version) {
        identifier.setVersion(version);
    }

    public void requires(String name, String version) {
        ModIdentifier id = new ModIdentifier();
        
        id.setName(name);
        id.setVersion(version);

        dependencies.add(id);
    }
}

