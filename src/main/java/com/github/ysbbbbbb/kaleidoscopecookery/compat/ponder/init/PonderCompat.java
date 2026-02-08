package com.github.ysbbbbbb.kaleidoscopecookery.compat.ponder.init;


import net.fabricmc.loader.api.FabricLoader;

public class PonderCompat {
    public static final String ID = "ponder";

    public static boolean PONDER_LOADED = false;

    public static void init() {
        if (FabricLoader.getInstance().isModLoaded(ID)) {
            PONDER_LOADED = true;
            KitchenPonderPlugin.init();
        }
    }
}
