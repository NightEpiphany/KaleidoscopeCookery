package com.github.ysbbbbbb.kaleidoscopecookery.init;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.effect.*;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;

public class ModEffects {
    public static Holder<MobEffect> FLATULENCE;
    public static Holder<MobEffect> TUNDRA_STRIDER;
    public static Holder<MobEffect> WARMTH;
    public static Holder<MobEffect> SATIATED_SHIELD;
    public static Holder<MobEffect> VIGOR;
    public static Holder<MobEffect> SULFUR;
    public static Holder<MobEffect> MUSTARD;
    public static Holder<MobEffect> PRESERVATION;
    public static Holder<MobEffect> HINDER;
    public static Holder<MobEffect> PROJECTILE_DODGE;
    public static Holder<MobEffect> INSTANT_SMELTING;
    public static Holder<MobEffect> VITALITY;

    public static void registerEffects() {
        FLATULENCE = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "flatulence"), new FlatulenceEffect(0xFFC6C6));
        TUNDRA_STRIDER = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "tundra_strider"), new BaseEffect(0xA1F8FC));
        WARMTH = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "warmth"), new WarmthEffect(0xFF5F0E));
        SATIATED_SHIELD = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "satiated_shield"), new BaseEffect(0xFF1313));
        VIGOR = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "vigor"), new VigorEffect(0x84C322));
        SULFUR = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "sulfur"), new SulfurEffect(0xE8B75E));
        MUSTARD = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "mustard"), new BaseEffect(0x5A6D09));
        PRESERVATION = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "preservation"), new BaseEffect(0xAEC639));
        HINDER = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "hinder"), new BaseEffect(0x9E7E5A));
        PROJECTILE_DODGE = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "projectile_dodge"), new BaseEffect(0x8E27F7));
        INSTANT_SMELTING = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "instant_smelting"), new BaseEffect(0xF07C1C));
        VITALITY = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "vitality"), new BaseEffect(0x6A9E4E));
    }
}
