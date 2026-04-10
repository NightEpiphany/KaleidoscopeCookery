package com.github.ysbbbbbb.kaleidoscopecookery.config;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraftforge.common.ForgeConfigSpec;

@Environment(EnvType.CLIENT)
public class ClientConfig {
    public static ForgeConfigSpec init() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        client(builder);
        return builder.build();
    }

    public static ForgeConfigSpec.BooleanValue SHOW_FOOD_EFFECT_TOOLTIPS;

    private static void client(ForgeConfigSpec.Builder builder) {
        builder.push("cookery");

        builder.comment("Whether to show food effect tooltips when hovering over food items.");
        SHOW_FOOD_EFFECT_TOOLTIPS = builder.define("ShowFoodEffectTooltips", true);

        builder.pop();
    }
}
