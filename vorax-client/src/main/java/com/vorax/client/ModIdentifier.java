package com.vorax.client;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * The {@code ModIdentifier} class represents the versioning and the name of a
 * {@code ModInstance}
 * 
 * @see ModInstance
 */
@EqualsAndHashCode
@Getter
public class ModIdentifier {
    /**
     * The name of this {@code ModIdentifier}
     */
    private String name = null;
    /**
     * The version of this {@code ModIdentifier} represented by an {@code Integer}
     * using semantic versioning. The patch is stored in the first 8 bits, 0 to 7,
     * the minor in the next 8 bits, 8 to 15, and the major in the next 8 bits, 16
     * to 23
     */
    private int versionNumber = -1;
    /**
     * The version of this {@code ModIdentifier} represented by a {@code String}
     * using semantic versioning
     */
    private String versionString = null;

    /**
     * Sets the version for this identifier from major, minor and patch components
     * 
     * @param major the version number
     * @param minor the version number
     * @param patch the version number
     */
    public void setVersion(int major, int minor, int patch) {
        // Check range of major, minor and patch
        if (major < 0 || major > 255
                || minor < 0 || minor > 255
                || patch < 0 || patch > 255) {
            throw new IllegalArgumentException("Version must be in the form N.N.N where 0 < N < 255");
        }

        // Bit-shift to correct positions
        versionNumber = patch << 0 | minor << 8 | major << 16;
        versionString = String.format("%s.%s.%s", major, minor, patch);
    }

    /**
     * Sets the version for this identifier from the integer representing the major,
     * minor and patch
     * 
     * @param version the version number
     */
    public void setVersion(int version) {
        // Delegate to the 3 arg method
        setVersion((version >> 16) & 0xFF, (version >> 8) & 0xFF, (version >> 0) & 0xFF);
    }

    /**
     * Sets the version for this identifier from the {@code String} representing the
     * major,
     * minor and patch
     * 
     * @param version the version string
     */
    public void setVersion(String version) {
        String[] smmp = version.split("\\.");
        int[] immp = new int[3];

        // Decode the semver numbers from the string
        for (int i = 0; i < 3; i++) {
            try {
                immp[i] = Integer.decode(smmp[i]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Version must be in the form N.N.N where 0 < N < 255");
            }
        }

        // Delegate to the 3 arg method
        setVersion(immp[0] << 16 | immp[1] << 8 | immp[2] << 0);
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("{name=%s, version=[%s, %s]}", name, versionString, versionNumber);
    }
}
