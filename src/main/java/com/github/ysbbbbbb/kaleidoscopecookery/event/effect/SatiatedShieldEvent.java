package com.github.ysbbbbbb.kaleidoscopecookery.event.effect;

import com.github.ysbbbbbb.kaleidoscopecookery.config.ConfigGetter;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModEffects;
import com.github.ysbbbbbb.kaleidoscopecookery.init.tag.TagMod;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class SatiatedShieldEvent {
    public static void register() {
        ServerLivingEntityEvents.ALLOW_DAMAGE.register(SatiatedShieldEvent::onEntityHurt);
    }

    @SuppressWarnings("deprecation")
    private static boolean onEntityHurt(LivingEntity entity, DamageSource damageSource, float amounts) {
        if (entity instanceof Player player && !damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            if (!ConfigGetter.getSatiatedShieldAbsorbEnabled()) {
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
        if (player.hasEffect(MobEffects.HUNGER) && ConfigGetter.getDisableSatiatedShieldWhenHunger()) {
            return false;
        }
        return player.getFoodData().getFoodLevel() >= ConfigGetter.getSatiatedShieldMinFoodLevel()
                && player.hasEffect(ModEffects.SATIATED_SHIELD);
    }

    public static float calculateFinalDamage(Player player, DamageSource source, float originalDamage) {
        double damageReductionPercent = ConfigGetter.getSatiatedShieldDamageReductionPercent();
        double maxDamageReduction = ConfigGetter.getSatiatedShieldMaxDamageReduction();
        double minDamage = ConfigGetter.getSatiatedShieldMinDamage();
        double weaknessDamageMultiplier = ConfigGetter.getSatiatedShieldWeaknessDamageMultiplier();
        float reducedDamage = (float) (originalDamage * damageReductionPercent);
        if (reducedDamage > maxDamageReduction) {
            reducedDamage = (float) maxDamageReduction;
        }

        float finalDamage = originalDamage - reducedDamage;
        if (originalDamage > minDamage) {
            finalDamage = (float) Math.max(finalDamage, minDamage);
            reducedDamage = originalDamage - finalDamage;
        }

        int exhaustionAmount = Math.round((float) (reducedDamage * ConfigGetter.getSatiatedShieldAdditionalExhaustionPerDamage()));
        if (source.is(TagMod.SATIATED_SHIELD_WEAKNESS)) {
            exhaustionAmount = Math.round((float) (exhaustionAmount * weaknessDamageMultiplier));
        }

        if (!ConfigGetter.getSatiatedShieldAbsorbExcessDamage()) {
            double percent = damageReductionPercent;
            if (percent > 0) {
                float absorbedDamage = (float) (player.getFoodData().getFoodLevel() * 4 / percent);
                if (source.is(TagMod.SATIATED_SHIELD_WEAKNESS)) {
                    absorbedDamage /= (float) weaknessDamageMultiplier;
                }
                finalDamage += Math.max(0, reducedDamage - absorbedDamage);
            }
        }

        player.causeFoodExhaustion(Math.max(0, exhaustionAmount));
        return finalDamage;
    }
}
