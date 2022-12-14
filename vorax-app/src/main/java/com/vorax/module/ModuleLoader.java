package com.vorax.module;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ImportCustomizer;

import com.vorax.core.Client;
import com.vorax.core.FileSystem;

import groovy.lang.GroovyShell;
import lombok.Getter;

public final class ModuleLoader {
    @Getter
    private GroovyShell shell;
    @Getter
    private Map<ModuleIdentifier, ModuleInstance> modules;
    private FileSystem fs;

    public ModuleLoader(FileSystem fs) {
        this.fs = fs;
        this.modules = new HashMap<>();

        ImportCustomizer imports = new ImportCustomizer();
        imports.addStarImports();

        CompilerConfiguration config = new CompilerConfiguration();
        config.setScriptBaseClass(ModuleScript.class.getCanonicalName());
        config.addCompilationCustomizers(imports);

        this.shell = new GroovyShell(config);
    }

    public void reload(Client client) {
        for (ModuleInstance module : modules.values()) {
            module.setScript(compile(module.getScriptFile()));
            module.getConfig().load();
        }

        for (ModuleInstance module : modules.values()) {
            module.execute(client, ModuleScript.POST_INIT);
        }
    }

    public void load(Client client) {
        fs.mkdirs("modules", "configs");

        // Module loading
        for (File file : fs.open("modules").listFiles()) {
            createModule(file, client);
        }

        // Module running
        for (ModuleInstance module : modules.values()) {
            checkDependencies(module, client);

            if (!module.isDisabled()) {
                module.execute(client, ModuleScript.POST_INIT);
            }
        }
    }

    public void cache(ModuleInstance module) {
        modules.put(module.getIdentifier(), module);
    }

    public ModuleScript compile(File file) {
        try {
            return (ModuleScript) shell.parse(file);
        } catch (CompilationFailedException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void createModule(File file, Client client) {
        ModuleInstance module = new ModuleInstance();

        module.setScript(compile(file));
        module.setScriptFile(file);

        if (module.getScript() == null) {
            module.setDisabled(true);
        }

        module.execute(client, ModuleScript.PRE_INIT);

        // Identifier initialization
        if (module.getIdentifier().getName() == null) {
            module.getIdentifier().setName(file.getName());
        }

        if (module.getIdentifier().getVersionString() == null) {
            module.getIdentifier().setVersion("0.0.0");
        }

        // Config Loading
        File cfgFile = fs.open("configs/" + module.getIdentifier().getName() + ".json");

        if (!cfgFile.exists()) {
            fs.touch("configs/" + module.getIdentifier().getName() + ".json");
        }

        ModuleConfiguration config = new ModuleConfiguration(cfgFile);
        config.load();

        module.setConfig(config);

        module.execute(client, ModuleScript.INIT);

        // Store for runtime access
        cache(module);
    }

    private void checkDependencies(ModuleInstance module, Client client) {
        for (ModuleIdentifier dependency : module.getDependencies()) {
            if (!modules.containsKey(dependency)) {
                client.printf("The module %s requires dependency %s but it is not present\n",
                        module.getIdentifier(), dependency);
                module.setDisabled(true);
            }

            if (modules.get(dependency).getIdentifier().compareTo(dependency) < 0) {
                client.printf("The module %s requires %s but %s is present\n", module.getIdentifier(), dependency,
                        modules.get(dependency).getIdentifier());
                module.setDisabled(true);
            }
        }
    }

    @Override
    public String toString() {
        return String.format("{modules=%s}", modules);
    }
}
