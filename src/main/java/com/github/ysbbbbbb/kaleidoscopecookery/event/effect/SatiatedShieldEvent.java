package com.github.ysbbbbbb.kaleidoscopecookery.event.effect;

import com.github.ysbbbbbb.kaleidoscopecookery.config.GeneralConfig;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModEffects;
import com.github.ysbbbbbb.kaleidoscopecookery.init.tag.TagMod;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import static com.github.ysbbbbbb.kaleidoscopecookery.config.GeneralConfig.*;
import static com.github.ysbbbbbb.kaleidoscopecookery.config.GeneralConfig.SATIATED_SHIELD_ADDITIONAL_EXHAUSTION_PER_DAMAGE;
import static com.github.ysbbbbbb.kaleidoscopecookery.config.GeneralConfig.SATIATED_SHIELD_DAMAGE_REDUCTION_PERCENT;
import static com.github.ysbbbbbb.kaleidoscopecookery.config.GeneralConfig.SATIATED_SHIELD_MAX_DAMAGE_REDUCTION;
import static com.github.ysbbbbbb.kaleidoscopecookery.config.GeneralConfig.SATIATED_SHIELD_MIN_DAMAGE;
import static com.github.ysbbbbbb.kaleidoscopecookery.config.GeneralConfig.SATIATED_SHIELD_WEAKNESS_DAMAGE_MULTIPLIER;

public class SatiatedShieldEvent {
    public static void register() {
        ServerLivingEntityEvents.ALLOW_DAMAGE.register(SatiatedShieldEvent::onEntityHurt);
    }

    private static boolean onEntityHurt(LivingEntity entity, DamageSource damageSource, float amounts) {
        if (entity instanceof Player player && !damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            if (!GeneralConfig.SATIATED_SHIELD_ABSORB_ENABLED.get()) {
                return true;
            }
            if (!isSatiatedShieldApply(player)) {
                return true;
            }
            float finalDamage = calculateFinalDamage(player, damageSource, amounts);
            if (finalDamage <= 0) {
                return false;
            }
            player.hurt(damageSource, finalDamage);
        }
        return true;
    }

    public static boolean isSatiatedShieldApply(Player player) {
        if (player.hasEffect(MobEffects.HUNGER) && IS_SATIATED_SHIELD_DISABLE_WHEN_HUNGRY_EFFECT.get()) {
            return false;
        }
        return player.getFoodData().getFoodLevel() >= SATIATED_SHIELD_MIN_FOOD_LEVEL.get()
                && player.hasEffect(ModEffects.SATIATED_SHIELD);
    }

    public static float calculateFinalDamage(Player player, DamageSource source, float originalDamage) {
        float reducedDamage = (float) (originalDamage * SATIATED_SHIELD_DAMAGE_REDUCTION_PERCENT.get());
        if (reducedDamage > SATIATED_SHIELD_MAX_DAMAGE_REDUCTION.get()) {
            reducedDamage = SATIATED_SHIELD_MAX_DAMAGE_REDUCTION.get().floatValue();
        }

        float finalDamage = originalDamage - reducedDamage;
        if (originalDamage > SATIATED_SHIELD_MIN_DAMAGE.get()) {
            finalDamage = (float) Math.max(finalDamage, SATIATED_SHIELD_MIN_DAMAGE.get());
            reducedDamage = originalDamage - finalDamage;
        }

        int exhaustionAmount = Math.round((float) (reducedDamage * SATIATED_SHIELD_ADDITIONAL_EXHAUSTION_PER_DAMAGE.get()));
        if (source.is(TagMod.SATIATED_SHIELD_WEAKNESS)) {
            exhaustionAmount = Math.round((float) (exhaustionAmount * SATIATED_SHIELD_WEAKNESS_DAMAGE_MULTIPLIER.get()));
        }

        if (!SATIATED_SHIELD_ABSORB_EXCESS_DAMAGE.get()) {
            double percent = SATIATED_SHIELD_DAMAGE_REDUCTION_PERCENT.get();
            if (percent > 0) {
                float absorbedDamage = (float) (player.getFoodData().getFoodLevel() * 4 / percent);
                if (source.is(TagMod.SATIATED_SHIELD_WEAKNESS)) {
                    absorbedDamage /= SATIATED_SHIELD_WEAKNESS_DAMAGE_MULTIPLIER.get().floatValue();
                }
                finalDamage += Math.max(0, reducedDamage - absorbedDamage);
            }
        }

        player.causeFoodExhaustion(Math.max(0, exhaustionAmount));
        return finalDamage;
    }
}
