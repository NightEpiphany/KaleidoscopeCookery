package com.github.ysbbbbbb.kaleidoscopecookery.init;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.effect.*;
import com.google.common.base.Suppliers;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public final class ModEffects {
    static final List<Supplier<?>> EFFECTS = new ArrayList<>();


    @NotNull
    private static <T extends MobEffect> Supplier<T> register(String id, Supplier<T> supplier) {
        var v = Suppliers.memoize(() ->
                Registry.register(BuiltInRegistries.MOB_EFFECT, new ResourceLocation(KaleidoscopeCookery.MOD_ID, id), supplier.get()));
        EFFECTS.add(v);
        return v;
    }

    public static final Supplier<MobEffect> FLATULENCE = register("flatulence", () -> new FlatulenceEffect(0xFFC6C6));
    public static final Supplier<MobEffect> TUNDRA_STRIDER = register("tundra_strider", () -> new BaseEffect(0xA1F8FC));
    public static final Supplier<MobEffect> WARMTH = register("warmth", () -> new WarmthEffect(0xFF5F0E));
    public static final Supplier<MobEffect> SATIATED_SHIELD = register("satiated_shield", () -> new BaseEffect(0xFF1313));
    public static final Supplier<MobEffect> VIGOR = register("vigor", () -> new VigorEffect(0x84C322));
    public static final Supplier<MobEffect> SULFUR = register("sulfur", () -> new SulfurEffect(0xE8B75E));
    public static final Supplier<MobEffect> MUSTARD = register("mustard", () -> new BaseEffect(0x5A6D09));
    public static final Supplier<MobEffect> PRESERVATION = register("preservation", () -> new BaseEffect(0xAEC639));
    public static final Supplier<MobEffect> HINDER = register("hinder", () -> new BaseEffect(0x9E7E5A));
    public static final Supplier<MobEffect> PROJECTILE_DODGE = register("projectile_dodge", () -> new BaseEffect(0x8E27F7));
    public static final Supplier<MobEffect> INSTANT_SMELTING = register("instant_smelting", () -> new BaseEffect(0xF07C1C));
    public static final Supplier<MobEffect> VITALITY = register("vitality", () -> new BaseEffect(0x6A9E4E));

    public static void registerEffects() {
        EFFECTS.forEach(Supplier::get);
        EFFECTS.clear();
    }

}
