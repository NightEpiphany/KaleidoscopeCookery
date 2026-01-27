package com.github.ysbbbbbb.kaleidoscopecookery.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.NonNull;

public class VigorEffect extends BaseEffect {
    public VigorEffect(int color) {
        super(color);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(@NonNull ServerLevel serverLevel, @NonNull LivingEntity livingEntity, int amplifier) {
        if (livingEntity instanceof Player player && player.isSprinting()) {
            player.getFoodData().addExhaustion(0);
        }
        return true;
    }
}
