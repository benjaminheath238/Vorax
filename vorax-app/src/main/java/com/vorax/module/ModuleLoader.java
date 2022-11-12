package com.vorax.module;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilerConfiguration;

import com.vorax.core.Client;
import com.vorax.core.Environment;
import com.vorax.core.Parser;

import groovy.lang.GroovyShell;

public final class ModuleLoader {
    private GroovyShell shell;
    private Map<ModuleIdentifier, ModuleInstance> modules;
    private Environment env;

    public ModuleLoader(Environment env) {
        this.env = env;
        this.modules = new HashMap<>();

        CompilerConfiguration config = new CompilerConfiguration();
        config.setScriptBaseClass(ModuleScript.class.getCanonicalName());

        this.shell = new GroovyShell(config);
    }

    public void load(Parser parser, Client client) {
        env.mkdirs("modules", "configs");

        for (File file : env.open("modules").listFiles()) {
            ModuleInstance module = new ModuleInstance();

            module.setScript(compile(file));

            module.execute(parser, client, ModuleScript.PRE_INIT);

            // Identifier initialization
            if (module.getIdentifier().getName() == null) {
                module.getIdentifier().setName(file.getName());
            }

            if (module.getIdentifier().getVersionString() == null) {
                module.getIdentifier().setVersion("0.0.0");
            }

            // Config Loading
            File cfgFile = env.open("configs/" + module.getIdentifier().getName() + ".json");

            if (!cfgFile.exists()) {
                env.touch("configs/" + module.getIdentifier().getName() + ".json");
            }

            ModuleConfiguration config = new ModuleConfiguration(cfgFile);
            config.load();

            module.setConfig(config);

            cache(module);
        }

        for (ModuleInstance module : modules.values()) {
            // Dependency checking
            for (ModuleIdentifier dependency : module.getDependencies()) {
                if (!modules.containsKey(dependency)) {
                    client.printf("The module %s requires dependency %s but it is not present\n", module.getIdentifier(), dependency);
                    module.setDisabled(true);
                }
                
                if (modules.get(dependency).getIdentifier().compareTo(dependency) < 0) {
                    client.printf("The module %s requires %s but %s is present\n", module.getIdentifier(), dependency, modules.get(dependency).getIdentifier());
                    module.setDisabled(true);
                }
            }
            
            if (!module.isDisabled()) {
                module.execute(parser, client, ModuleScript.INIT);
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
}
