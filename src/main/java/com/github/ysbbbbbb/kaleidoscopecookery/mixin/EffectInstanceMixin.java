package com.github.ysbbbbbb.kaleidoscopecookery.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * 一个很奇怪的bug，如果在开发环境出现由MobEffectInstance导致的NPE，请加上这个mixin
 */
@SuppressWarnings("all")
@Mixin(ApplyStatusEffectsConsumeEffect.class)
public class EffectInstanceMixin {
    @WrapOperation(method = "apply", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;addEffect(Lnet/minecraft/world/effect/MobEffectInstance;)Z"))
    private boolean kaleidoscope_cookery$addEffect(LivingEntity instance, MobEffectInstance newEffect, Operation<Boolean> original) {
        if (newEffect == null || newEffect.getEffect() == null || newEffect.getEffect().value() == null) {
            return false;
        } else return original.call(instance, newEffect);
    }
}
