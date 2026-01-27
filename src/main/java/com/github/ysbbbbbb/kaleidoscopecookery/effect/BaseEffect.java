package com.github.ysbbbbbb.kaleidoscopecookery.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

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

    @Override
    public void applyInstantenousEffect(@NonNull ServerLevel serverLevel, @Nullable Entity entity, @Nullable Entity entity2, @NonNull LivingEntity livingEntity, int i, double d) {
    }
}
