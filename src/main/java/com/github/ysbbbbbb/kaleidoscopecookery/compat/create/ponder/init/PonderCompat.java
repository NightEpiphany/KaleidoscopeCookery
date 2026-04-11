package com.github.ysbbbbbb.kaleidoscopecookery.compat.create.ponder.init;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;

@Environment(EnvType.CLIENT)
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
