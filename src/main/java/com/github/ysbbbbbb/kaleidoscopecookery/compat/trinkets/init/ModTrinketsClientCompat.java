package com.github.ysbbbbbb.kaleidoscopecookery.compat.trinkets.init;

import com.github.ysbbbbbb.kaleidoscopecookery.compat.trinkets.renderer.StrawHatTrinketRenderer;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import eu.pb4.trinkets.api.client.TrinketRendererRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.jetbrains.annotations.Contract;

@Environment(EnvType.CLIENT)
public class ModTrinketsClientCompat {
    @Contract(pure = true)
     static void init() {
            TrinketRendererRegistry.registerRenderer(ModItems.STRAW_HAT.get(), new StrawHatTrinketRenderer(false));
            TrinketRendererRegistry.registerRenderer(ModItems.STRAW_HAT_FLOWER.get(), new StrawHatTrinketRenderer(true));
    }
}
