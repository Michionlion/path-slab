package com.michionlion.pathslab.test;

import com.michionlion.pathslab.PathSlab;
import com.michionlion.pathslab.PathSlabBlocks;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.Identifier;

public class PathSlabGameTest {

    @GameTest
    public void pathSlabBlockIsRegistered(GameTestHelper helper) {
        Identifier id = Identifier.fromNamespaceAndPath(PathSlab.MOD_ID, "dirt_path_slab");
        var block = BuiltInRegistries.BLOCK.get(id);

        if (block.isEmpty()) {
            helper.fail("PATH_SLAB block not registered");
        }

        if (block.get().value() != PathSlabBlocks.PATH_SLAB) {
            helper.fail("Registered block doesn't match PATH_SLAB field");
        }

        helper.succeed();
    }

    @GameTest
    public void pathSlabItemIsRegistered(GameTestHelper helper) {
        Identifier id = Identifier.fromNamespaceAndPath(PathSlab.MOD_ID, "dirt_path_slab");
        var item = BuiltInRegistries.ITEM.get(id);

        if (item.isEmpty()) {
            helper.fail("PATH_SLAB_ITEM not registered");
        }

        if (item.get().value() != PathSlabBlocks.PATH_SLAB_ITEM) {
            helper.fail("Registered item doesn't match PATH_SLAB_ITEM field");
        }

        helper.succeed();
    }

    @GameTest
    public void pathSlabCanBePlaced(GameTestHelper helper) {
        helper.setBlock(1, 1, 1, PathSlabBlocks.PATH_SLAB);
        helper.assertBlockPresent(PathSlabBlocks.PATH_SLAB, 1, 1, 1);
        helper.succeed();
    }
}
