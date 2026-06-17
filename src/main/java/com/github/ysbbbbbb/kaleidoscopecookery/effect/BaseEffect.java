package com.github.ysbbbbbb.kaleidoscopecookery.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.jspecify.annotations.NonNull;

public class BaseEffect extends MobEffect {
    public BaseEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    public BaseEffect(int color) {
        this(MobEffectCategory.BENEFICIAL, color);
    }

    @Override
    public boolean applyEffectTick(@NonNull ServerLevel serverLevel, @NonNull LivingEntity livingEntity, int i) {
        return true;
    }

}
