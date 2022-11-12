package com.vorax.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ModuleIdentifierTest {
    @Test
    void testCompareToSameVersion() {
        ModuleIdentifier id0 = new ModuleIdentifier("MODULE_0", "1.0.0");
        ModuleIdentifier id1 = new ModuleIdentifier("MODULE_1", "1.0.0");

        assertTrue(id0.compareTo(id1) == 0);
    }

    @Test
    void testCompareToNewerVersion() {
        ModuleIdentifier id0 = new ModuleIdentifier("MODULE_0", "1.0.0");
        ModuleIdentifier id1 = new ModuleIdentifier("MODULE_1", "2.0.0");

        assertTrue(id0.compareTo(id1) < 0);
    }

    @Test
    void testCompareToOlderVersion() {
        ModuleIdentifier id0 = new ModuleIdentifier("MODULE_0", "2.0.0");
        ModuleIdentifier id1 = new ModuleIdentifier("MODULE_1", "1.0.0");

        assertTrue(id0.compareTo(id1) > 0);
    }

    @Test
    void testEqualsSameVersionSameName() {
        ModuleIdentifier id0 = new ModuleIdentifier("MODULE_0", "1.0.0");
        ModuleIdentifier id1 = new ModuleIdentifier("MODULE_0", "1.0.0");

        assertTrue(id0.equals(id1));
    }

    @Test
    void testEqualsSameVersionDifferentName() {
        ModuleIdentifier id0 = new ModuleIdentifier("MODULE_1", "1.0.0");
        ModuleIdentifier id1 = new ModuleIdentifier("MODULE_0", "1.0.0");

        assertFalse(id0.equals(id1));
    }

    @Test
    void testEqualsDifferentVersionSameName() {
        ModuleIdentifier id0 = new ModuleIdentifier("MODULE_0", "2.0.0");
        ModuleIdentifier id1 = new ModuleIdentifier("MODULE_0", "1.0.0");

        assertTrue(id0.equals(id1));
    }


    @Test
    void testEqualsDifferentVersionDifferentName() {
        ModuleIdentifier id0 = new ModuleIdentifier("MODULE_1", "2.0.0");
        ModuleIdentifier id1 = new ModuleIdentifier("MODULE_0", "1.0.0");

        assertFalse(id0.equals(id1));
    }

    @Test
    void testSetVersionAgaintsNumberMajor() {
        ModuleIdentifier id0 = new ModuleIdentifier("MODULE_0", "1.0.0");

        assertEquals(65536, id0.getVersionNumber());    
    }

    @Test
    void testSetVersionAgaintsNumberMajorMinor() {
        ModuleIdentifier id0 = new ModuleIdentifier("MODULE_0", "1.3.0");

        assertEquals(66304, id0.getVersionNumber());    
    }

    @Test
    void testSetVersionAgaintsNumberMajorMinorPatch() {
        ModuleIdentifier id0 = new ModuleIdentifier("MODULE_0", "1.3.5");

        assertEquals(66309, id0.getVersionNumber());    
    }

    @Test
    void testSetVersionAgaintsString() {
        ModuleIdentifier id0 = new ModuleIdentifier("MODULE_0", "1.0.0");

        assertEquals("1.0.0", id0.getVersionString());    
    }
}
