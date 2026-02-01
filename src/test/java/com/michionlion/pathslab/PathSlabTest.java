package com.michionlion.pathslab;

import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class PathSlabTest {

    @Test
    void modIdIsStable() {
        assertEquals("pathslab", PathSlab.MOD_ID);
    }

    @Test
    void modIdIsNotNull() {
        assertNotNull(PathSlab.MOD_ID);
    }

    @Test
    void modIdIsNotEmpty() {
        assertFalse(PathSlab.MOD_ID.isEmpty());
    }

    @Test
    void modCanBeInstantiated() {
        assertNotNull(new PathSlab());
    }

    @Test
    void fabricModJsonExists() {
        try (InputStream stream =
                PathSlabTest.class.getClassLoader().getResourceAsStream("fabric.mod.json")) {
            assertNotNull(stream, "fabric.mod.json should be on the classpath");
        } catch (Exception e) {
            fail("Failed to access fabric.mod.json", e);
        }
    }

    @Test
    void fabricModJsonIsValidJson() throws Exception {
        try (InputStream stream =
                        PathSlabTest.class.getClassLoader().getResourceAsStream("fabric.mod.json");
                InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            assertNotNull(stream, "fabric.mod.json should be on the classpath");
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            assertNotNull(json);
        }
    }

    @Test
    void fabricModJsonMatchesModId() throws Exception {
        try (InputStream stream =
                        PathSlabTest.class.getClassLoader().getResourceAsStream("fabric.mod.json");
                InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            assertNotNull(stream, "fabric.mod.json should be on the classpath");
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            assertTrue(json.has("id"), "fabric.mod.json should have an 'id' field");
            assertEquals(json.get("id").getAsString(), PathSlab.MOD_ID);
        }
    }

    @Test
    void fabricModJsonHasVersion() throws Exception {
        try (InputStream stream =
                        PathSlabTest.class.getClassLoader().getResourceAsStream("fabric.mod.json");
                InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            assertNotNull(stream);
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            assertTrue(json.has("version"), "fabric.mod.json should have a 'version' field");
            assertNotNull(json.get("version").getAsString());
        }
    }

    @Test
    void fabricModJsonHasMainEntrypoint() throws Exception {
        try (InputStream stream =
                        PathSlabTest.class.getClassLoader().getResourceAsStream("fabric.mod.json");
                InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            assertNotNull(stream, "fabric.mod.json should be on the classpath");
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            assertTrue(json.has("entrypoints"), "fabric.mod.json should have entrypoints");
            JsonObject entrypoints = json.getAsJsonObject("entrypoints");
            assertTrue(entrypoints.has("main"), "fabric.mod.json should define a main entrypoint");
            assertTrue(
                    entrypoints
                            .getAsJsonArray("main")
                            .toString()
                            .contains(PathSlab.class.getName()),
                    "Main entrypoint should include PathSlab");
        }
    }
}
