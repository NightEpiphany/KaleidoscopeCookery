package com.github.ysbbbbbb.kaleidoscopecookery.init;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.advancements.critereon.ModEventTrigger;
import net.minecraft.advancements.triggers.CriterionTrigger;
import net.minecraft.advancements.triggers.DistanceTrigger;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

public final class ModTrigger {
    public static ModEventTrigger EVENT;
    public static DistanceTrigger FLATULENCE_FLY_HEIGHT;

    public static void init() {
        EVENT = register(modLoc("mod_event"), new ModEventTrigger());
        FLATULENCE_FLY_HEIGHT = register(modLoc("flatulence_fly_height"), new DistanceTrigger());
    }

    private static <T extends CriterionTrigger<?>> T register(final String name, final T criterion) {
        return Registry.register(BuiltInRegistries.TRIGGER_TYPES, name, criterion);
    }

    private static String modLoc(String id) {
        return Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, id).toString();
    }
}
