package com.michionlion.pathslab;

import net.fabricmc.api.ModInitializer;

public class PathSlab implements ModInitializer {
    public static final String MOD_ID = "pathslab";

    @Override
    public void onInitialize() {
        PathSlabBlocks.register();
    }
}
