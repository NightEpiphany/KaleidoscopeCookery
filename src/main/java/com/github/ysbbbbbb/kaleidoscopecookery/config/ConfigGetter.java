package com.github.ysbbbbbb.kaleidoscopecookery.config;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;

public interface ConfigGetter {
    String ID = "forgeconfigapiport";

    static boolean getSatiatedShieldAbsorbEnabled() {
        return !FabricLoader.getInstance().isModLoaded(ID) || GeneralConfig.SATIATED_SHIELD_ABSORB_ENABLED.get();
    }

    static boolean getSatiatedShieldAbsorbExcessDamage() {
        return !FabricLoader.getInstance().isModLoaded(ID) || GeneralConfig.SATIATED_SHIELD_ABSORB_EXCESS_DAMAGE.get();
    }

    static boolean getStoveFiringEnabled() {
        return FabricLoader.getInstance().isModLoaded(ID) && GeneralConfig.STOVE_FIRING_ENABLED.get();
    }

    static boolean getDisableSatiatedShieldWhenHunger() {
        return !FabricLoader.getInstance().isModLoaded(ID) || GeneralConfig.IS_SATIATED_SHIELD_DISABLE_WHEN_HUNGRY_EFFECT.get();
    }

    static int getSatiatedShieldMinFoodLevel() {
        return !FabricLoader.getInstance().isModLoaded(ID) ? 4 : GeneralConfig.SATIATED_SHIELD_MIN_FOOD_LEVEL.get();
    }

    static double getSatiatedShieldAdditionalExhaustionPerDamage() {
        return !FabricLoader.getInstance().isModLoaded(ID) ? 2.0 : GeneralConfig.SATIATED_SHIELD_ADDITIONAL_EXHAUSTION_PER_DAMAGE.get();
    }

    static double getSatiatedShieldDamageReductionPercent() {
        return !FabricLoader.getInstance().isModLoaded(ID) ? 1.0 : GeneralConfig.SATIATED_SHIELD_DAMAGE_REDUCTION_PERCENT.get();
    }

    static double getSatiatedShieldMaxDamageReduction() {
        return !FabricLoader.getInstance().isModLoaded(ID) ? 64.0 : GeneralConfig.SATIATED_SHIELD_MAX_DAMAGE_REDUCTION.get();
    }

    static double getSatiatedShieldMinDamage() {
        return !FabricLoader.getInstance().isModLoaded(ID) ? 0.0 : GeneralConfig.SATIATED_SHIELD_MIN_DAMAGE.get();
    }

    static double getSatiatedShieldWeaknessDamageMultiplier() {
        return !FabricLoader.getInstance().isModLoaded(ID) ? 2.0 : GeneralConfig.SATIATED_SHIELD_WEAKNESS_DAMAGE_MULTIPLIER.get();
    }

    static boolean getCreateAutomationEnabled() {
        return !FabricLoader.getInstance().isModLoaded(ID) || GeneralConfig.CREATE_AUTOMATION_ENABLED.get();
    }


    @Environment(EnvType.CLIENT)
    interface Client {
        static boolean getShowFoodEffectTooltips() {
            return !FabricLoader.getInstance().isModLoaded(ID) || ClientConfig.SHOW_FOOD_EFFECT_TOOLTIPS.get();
        }
    }
}
