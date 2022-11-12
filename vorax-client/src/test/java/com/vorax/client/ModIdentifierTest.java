package com.vorax.client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ModIdentifierTest {
    @Test
    void testSetVersionFromSeparate() {
        ModIdentifier identifier = new ModIdentifier();

        identifier.setVersion(2, 5, 2);

        assertEquals(132354, identifier.getVersionNumber());
        assertEquals("2.5.2", identifier.getVersionString());
    }
    
    @Test
    void testSetVersionFromInteger() {
        ModIdentifier identifier = new ModIdentifier();

        identifier.setVersion(132354);
        
        assertEquals(132354, identifier.getVersionNumber());
        assertEquals("2.5.2", identifier.getVersionString());
    }
    
    @Test
    void testSetVersionFromString() {
        ModIdentifier identifier = new ModIdentifier();

        identifier.setVersion("2.5.2");
        
        assertEquals(132354, identifier.getVersionNumber());
        assertEquals("2.5.2", identifier.getVersionString());
    }
}
