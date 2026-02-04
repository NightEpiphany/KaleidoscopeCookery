package com.github.ysbbbbbb.kaleidoscopecookery.client.conditions;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class StockpotLidCondition implements ConditionalItemModelProperty {

    public static final MapCodec<StockpotLidCondition> MAP_CODEC = MapCodec.unit(new StockpotLidCondition());
    @Override
    public @NonNull MapCodec<? extends ConditionalItemModelProperty> type() {
        return MAP_CODEC;
    }

    @Override
    public boolean get(@NonNull ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i, @NonNull ItemDisplayContext itemDisplayContext) {
        return livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack;
    }
}
