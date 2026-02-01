package com.michionlion.pathslab;

import com.michionlion.pathslab.block.DirtPathSlabBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public final class PathSlabBlocks {
    public static final Block PATH_SLAB = new DirtPathSlabBlock(pathProperties("dirt_path_slab"));

    public static final Item PATH_SLAB_ITEM = new BlockItem(PATH_SLAB, itemProperties("dirt_path_slab"));

    private PathSlabBlocks() {
    }

    public static void register() {
        registerBlock("dirt_path_slab", PATH_SLAB, PATH_SLAB_ITEM);
        registerCreativeEntries();
    }

    private static void registerBlock(String id, Block block, Item item) {
        Identifier key = Identifier.fromNamespaceAndPath(PathSlab.MOD_ID, id);
        Registry.register(BuiltInRegistries.BLOCK, key, block);
        Registry.register(BuiltInRegistries.ITEM, key, item);
    }

    private static BlockBehaviour.Properties pathProperties(String id) {
        return BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT_PATH)
            .isSuffocating((state, level, pos) -> state.blocksMotion() && state.isCollisionShapeFullBlock(level, pos))
            .isViewBlocking((state, level, pos) -> state.blocksMotion() && state.isCollisionShapeFullBlock(level, pos))
            .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(PathSlab.MOD_ID, id)));
    }

    private static Item.Properties itemProperties(String id) {
        return new Item.Properties()
            .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(PathSlab.MOD_ID, id)))
            .useBlockDescriptionPrefix();
    }

    private static void registerCreativeEntries() {
        ItemGroupEvents.MODIFY_ENTRIES_ALL.register((group, entries) -> {
            boolean hasDirtPath = entries.getDisplayStacks().stream()
                .anyMatch(stack -> stack.is(Items.DIRT_PATH));
            if (hasDirtPath) {
                entries.addAfter(Items.DIRT_PATH, PATH_SLAB_ITEM);
            }
        });
    }
}
