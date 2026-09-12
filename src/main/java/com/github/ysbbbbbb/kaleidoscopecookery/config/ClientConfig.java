package com.github.ysbbbbbb.kaleidoscopecookery.config;

import fuzs.forgeconfigapiport.fabric.api.v5.ConfigRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.neoforged.fml.config.IConfigSpec;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

import static com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery.MOD_ID;

@Environment(EnvType.CLIENT)
public final class ClientConfig {
    public static IConfigSpec initConfig() {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        client(builder);
        return builder.build();
    }

    static ModConfigSpec.BooleanValue SHOW_FOOD_EFFECT_TOOLTIPS;

    private static void client(ModConfigSpec.Builder builder) {
        builder.push("cookery");

        builder.comment("Whether to show food effect tooltips when hovering over food items.");
        SHOW_FOOD_EFFECT_TOOLTIPS = builder.define("ShowFoodEffectTooltips", true);

        builder.pop();
    }

    public static void init() {
        ConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.CLIENT, ClientConfig.initConfig());
    }
}
