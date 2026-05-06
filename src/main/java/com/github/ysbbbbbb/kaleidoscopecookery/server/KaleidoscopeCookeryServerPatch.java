package com.github.ysbbbbbb.kaleidoscopecookery.server;

import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.SERVER)
public class KaleidoscopeCookeryServerPatch {
    public static void patch() {
        ModItems.STRAW_HAT.get();
        ModItems.STRAW_HAT_FLOWER.get();
    }
}
