package com.github.ysbbbbbb.kaleidoscopecookery.compat.trinkets.init;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;

@Environment(EnvType.CLIENT)
public class TrinketsCompactClient {
    public static final String ID = "trinkets";
    public static boolean IS_LOADED = false;
    public static void init() {
        if (FabricLoader.getInstance().isModLoaded(ID)) {
            IS_LOADED = true;
            ModTrinketsClientCompat.init();
        }
    }
}
