package com.michionlion.pathslab;

import net.fabricmc.api.ModInitializer;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PathSlabTest {
    @Test
    void modIdIsStable() {
        assertEquals("pathslab", PathSlab.MOD_ID);
    }

    @Test
    void modImplementsInitializer() {
        assertTrue(ModInitializer.class.isAssignableFrom(PathSlab.class));
    }

    @Test
    void fabricModJsonMatchesModId() throws Exception {
        try (InputStream stream = PathSlabTest.class.getClassLoader().getResourceAsStream("fabric.mod.json")) {
            assertNotNull(stream, "fabric.mod.json should be on the classpath");
            String json = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
            assertTrue(json.contains("\"id\": \"" + PathSlab.MOD_ID + "\""));
        }
    }
}
