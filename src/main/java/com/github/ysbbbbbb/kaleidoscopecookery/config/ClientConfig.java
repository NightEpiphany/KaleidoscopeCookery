package com.github.ysbbbbbb.kaleidoscopecookery.config;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.neoforged.fml.config.IConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec;

@Environment(EnvType.CLIENT)
public class ClientConfig {
    public static IConfigSpec init() {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        client(builder);
        return builder.build();
    }

    public static ModConfigSpec.BooleanValue SHOW_FOOD_EFFECT_TOOLTIPS;

    private static void client(ModConfigSpec.Builder builder) {
        builder.push("cookery");

        builder.comment("Whether to show food effect tooltips when hovering over food items.");
        SHOW_FOOD_EFFECT_TOOLTIPS = builder.define("ShowFoodEffectTooltips", true);

        builder.pop();
    }
}
