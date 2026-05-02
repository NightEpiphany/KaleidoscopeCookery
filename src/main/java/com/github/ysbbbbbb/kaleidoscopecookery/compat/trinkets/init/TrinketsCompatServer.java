package com.github.ysbbbbbb.kaleidoscopecookery.compat.trinkets.init;

import net.fabricmc.loader.api.FabricLoader;

public class TrinketsCompatServer {
    public static final String ID = "trinkets_updated";
    public static boolean IS_LOADED = false;
    public static void init() {
        if (FabricLoader.getInstance().isModLoaded(ID)) {
            IS_LOADED = true;
            ModTrinketsCompat.init();
        }
    }
}
