package com.github.ysbbbbbb.kaleidoscopecookery.event.effect;

import com.github.ysbbbbbb.kaleidoscopecookery.init.ModEffects;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class HinderEvent {
    public static void register() {
        ServerLivingEntityEvents.AFTER_DAMAGE.register(HinderEvent::onEntityHurt);
    }

    private static void onEntityHurt(LivingEntity entity, DamageSource damageSource, float v, float v1, boolean b) {
        if (entity.level().isClientSide()) {
            return;
        }
        if (damageSource.getEntity() instanceof LivingEntity attacker && attacker.hasEffect(ModEffects.HINDER)) {
            entity.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 100, 1));
        }
    }
}
