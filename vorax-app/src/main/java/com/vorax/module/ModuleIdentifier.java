package com.vorax.module;

import lombok.Getter;

@Getter
public final class ModuleIdentifier implements Comparable<ModuleIdentifier> {
    private String name;
    private String versionString;
    private int versionNumber;

    public ModuleIdentifier() {
    }

    public ModuleIdentifier(String name) {
        this();
        setName(name);
    }

    public ModuleIdentifier(String name, String version) {
        this(name);
        setVersion(version);
    }

    public void setName(String name) {
        if (name == null) {
            name = "";
        }

        this.name = name;
    }

    public void setVersion(String version) {
        if (version == null || version.isEmpty() || version.isBlank()) {
            versionString = "0.0.0";
            versionNumber = 0;
        }

        String[] smmp = version.split("\\.");
        int[] immp = new int[3];

        for (int i = 0; i < 3; i++) {
            immp[i] = Integer.decode(smmp[i]);
        }

        versionNumber = immp[2] << 0 | immp[1] << 8 | immp[0] << 16;
        versionString = version;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj != null)
                && (obj == this)
                || (obj instanceof ModuleIdentifier && this.hashCode() == obj.hashCode());
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public int compareTo(ModuleIdentifier module) {
        return this.versionNumber - module.versionNumber;
    }

    @Override
    public String toString() {
        return String.format("{name=%s, version=[%s, %s]}", name, versionNumber, versionString);
    }
}
