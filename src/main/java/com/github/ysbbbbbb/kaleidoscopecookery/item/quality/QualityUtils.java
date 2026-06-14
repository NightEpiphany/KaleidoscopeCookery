package com.github.ysbbbbbb.kaleidoscopecookery.item.quality;

import com.github.ysbbbbbb.kaleidoscopecookery.init.ModDataComponents;
import com.google.common.collect.Lists;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.item.consume_effects.ConsumeEffect;

import java.util.List;

public final class QualityUtils {
    public static void setQuality(ItemStack food, Quality quality) {
        food.set(ModDataComponents.QUALITY, quality);
    }

    public static Quality getQuality(ItemStack food) {
        if (food.has(ModDataComponents.QUALITY)) {
            return food.get(ModDataComponents.QUALITY);
        }
        return Quality.STANDARD;
    }

    public static boolean hasQuality(ItemStack food) {
        return food.has(ModDataComponents.QUALITY);
    }

    public static List<MobEffectInstance> modifyEffects(List<MobEffectInstance> effectInstances, Quality quality) {
        List<MobEffectInstance> list = Lists.newArrayList();
        for (MobEffectInstance instance : effectInstances) {
            int duration = (int) Math.round(quality.getRatio() * instance.getDuration());
            if (duration > 0) {
                list.add(new MobEffectInstance(instance.getEffect(), duration, instance.getAmplifier()));
            }
        }
        return list;
    }

    public static Consumable modifyFoodConsumables(Consumable raw, Quality quality) {
        double ratio = quality.getRatio();
        List<ConsumeEffect> effects = Lists.newArrayList();
        raw.onConsumeEffects().forEach(consumeEffect -> {
            if (consumeEffect instanceof ApplyStatusEffectsConsumeEffect(
                    List<MobEffectInstance> instances, float probability
            )) {
                List<MobEffectInstance> list = Lists.newArrayList();
                for (var instance : instances) {
                    if (probability >= 1F) {
                        int duration = (int) Math.round(ratio * instance.getDuration());
                        int amplifier = instance.getAmplifier();
                        if (duration > 0) {
                            MobEffectInstance newEffect = new MobEffectInstance(instance.getEffect(), duration, amplifier);
                            list.add(newEffect);
                        }
                    }
                }
                effects.add(new ApplyStatusEffectsConsumeEffect(list, probability));
            } else {
                effects.add(consumeEffect);
            }
        });
        return new Consumable(
                raw.consumeSeconds(),
                raw.animation(),
                raw.sound(),
                raw.hasConsumeParticles(),
                effects
        );
    }

    public static FoodProperties modifyFoodProperties(FoodProperties raw, Quality quality) {
        double ratio = quality.getRatio();

        int nutrition = (int) Math.round(ratio * raw.nutrition());
        float saturation = (float) ratio * raw.saturation();

        return new FoodProperties(
                nutrition,
                saturation,
                raw.canAlwaysEat()
        );
    }
}
