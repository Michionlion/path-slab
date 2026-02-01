package com.michionlion.pathslab.test;

import com.michionlion.pathslab.PathSlab;
import com.michionlion.pathslab.PathSlabBlocks;
import com.mojang.authlib.GameProfile;
import io.netty.channel.embedded.EmbeddedChannel;
import java.util.UUID;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.GameType;

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

    @GameTest
    public void dirtPathSlabRecipeIsRegistered(GameTestHelper helper) {
        Identifier id = Identifier.fromNamespaceAndPath(PathSlab.MOD_ID, "dirt_path_slab");
        ResourceKey<Recipe<?>> recipeKey = ResourceKey.create(Registries.RECIPE, id);
        var recipe = helper.getLevel().getServer().getRecipeManager().byKey(recipeKey);

        if (recipe.isEmpty()) {
            helper.fail("dirt_path_slab recipe not registered");
        }

        helper.succeed();
    }

    @GameTest
    public void dirtPathSlabAdvancementIsRegistered(GameTestHelper helper) {
        Identifier id =
                Identifier.fromNamespaceAndPath(PathSlab.MOD_ID, "recipes/building/dirt_path_slab");
        var advancement = helper.getLevel().getServer().getAdvancements().get(id);

        if (advancement == null) {
            helper.fail("dirt_path_slab advancement not registered");
        }

        helper.succeed();
    }

    @GameTest
    public void dirtPathSlabAdvancementTriggersFromCoarseDirtAndRecipe(GameTestHelper helper) {
        Identifier advancementId =
                Identifier.fromNamespaceAndPath(PathSlab.MOD_ID, "recipes/building/dirt_path_slab");
        AdvancementHolder advancement =
                helper.getLevel().getServer().getAdvancements().get(advancementId);

        if (advancement == null) {
            helper.fail("dirt_path_slab recipe learning advancement not registered");
        }

        Identifier recipeId = Identifier.fromNamespaceAndPath(PathSlab.MOD_ID, "dirt_path_slab");
        ResourceKey<Recipe<?>> recipeKey = ResourceKey.create(Registries.RECIPE, recipeId);
        RecipeHolder<?> recipeHolder =
                helper.getLevel().getServer().getRecipeManager().byKey(recipeKey).orElse(null);

        if (recipeHolder == null) {
            helper.fail("dirt_path_slab recipe not registered");
        }

        ServerPlayer player = makeMockServerPlayer(helper);
        player.setGameMode(GameType.SURVIVAL);

        ItemStack coarseDirt = new ItemStack(Items.COARSE_DIRT, 3);
        player.getInventory().add(coarseDirt);
        helper.runAfterDelay(
                1,
                () -> {
                    AdvancementProgress progress =
                            player.getAdvancements().getOrStartProgress(advancement);
                    if (!progress.isDone()) {
                        helper.fail("dirt_path_slab advancement not triggered");
                    }

                    helper.succeed();
                });
    }

    @GameTest
    public void dirtPathSlabCraftingProducesPathSlabs(GameTestHelper helper) {
        Identifier recipeId = Identifier.fromNamespaceAndPath(PathSlab.MOD_ID, "dirt_path_slab");
        ResourceKey<Recipe<?>> recipeKey = ResourceKey.create(Registries.RECIPE, recipeId);
        RecipeHolder<?> recipeHolder =
                helper.getLevel().getServer().getRecipeManager().byKey(recipeKey).orElse(null);

        if (recipeHolder == null) {
            helper.fail("dirt_path_slab recipe not registered");
        }

        @SuppressWarnings("unchecked")
        Recipe<CraftingInput> recipe = (Recipe<CraftingInput>) recipeHolder.value();

        CraftingInput input =
                CraftingInput.of(
                        3,
                        1,
                        java.util.List.of(
                                new ItemStack(Items.COARSE_DIRT),
                                new ItemStack(Items.COARSE_DIRT),
                                new ItemStack(Items.COARSE_DIRT)));

        if (!recipe.matches(input, helper.getLevel())) {
            helper.fail("dirt_path_slab recipe did not match coarse dirt input");
        }

        ItemStack output = recipe.assemble(input, helper.getLevel().registryAccess());
        if (!output.is(PathSlabBlocks.PATH_SLAB_ITEM)) {
            helper.fail("dirt_path_slab recipe did not output PATH_SLAB_ITEM");
        }

        if (output.getCount() != 6) {
            helper.fail("dirt_path_slab recipe did not output 6 slabs");
        }

        helper.succeed();
    }

    private static ServerPlayer makeMockServerPlayer(GameTestHelper helper) {
        CommonListenerCookie commonListenerCookie =
                CommonListenerCookie.createInitial(
                        new GameProfile(UUID.randomUUID(), "test-mock-player"), false);
        ServerPlayer serverPlayer =
                new ServerPlayer(
                        helper.getLevel().getServer(),
                        helper.getLevel(),
                        commonListenerCookie.gameProfile(),
                        commonListenerCookie.clientInformation());
        Connection connection = new Connection(PacketFlow.SERVERBOUND);
        new EmbeddedChannel(connection);
        helper.getLevel()
                .getServer()
                .getPlayerList()
                .placeNewPlayer(connection, serverPlayer, commonListenerCookie);
        return serverPlayer;
    }
}
