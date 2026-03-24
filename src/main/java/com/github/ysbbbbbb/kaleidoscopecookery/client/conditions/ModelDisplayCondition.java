package com.github.ysbbbbbb.kaleidoscopecookery.client.conditions;

import com.github.ysbbbbbb.kaleidoscopecookery.client.conditions.prop.ExtraModelLoadingProperty;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModDataComponents;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public class ModelDisplayCondition implements ExtraModelLoadingProperty {
    public static final MapCodec<ModelDisplayCondition> MAP_CODEC = MapCodec.unit(new ModelDisplayCondition());
    @Override
    public String get(ItemStack itemStack, @Nullable ClientLevel level, @Nullable LivingEntity owner, int seed, ItemDisplayContext displayContext) {
        if (itemStack.has(ModDataComponents.MODEL_DISPLAY_MODEL)) {
            return itemStack.get(ModDataComponents.MODEL_DISPLAY_MODEL);
        }
        return "";
    }

    @Override
    public MapCodec<? extends ExtraModelLoadingProperty> type() {
        return MAP_CODEC;
    }
}
