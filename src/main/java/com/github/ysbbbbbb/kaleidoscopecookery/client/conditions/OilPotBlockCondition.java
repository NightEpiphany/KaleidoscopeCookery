package com.github.ysbbbbbb.kaleidoscopecookery.client.conditions;

import com.github.ysbbbbbb.kaleidoscopecookery.item.OilPotItem;
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
public class OilPotBlockCondition implements ConditionalItemModelProperty {

    public static final MapCodec<OilPotBlockCondition> MAP_CODEC = MapCodec.unit(new OilPotBlockCondition());

    @Override
    public @NonNull MapCodec<? extends ConditionalItemModelProperty> type() {
        return MAP_CODEC;
    }

    @Override
    public boolean get(@NonNull ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i, @NonNull ItemDisplayContext itemDisplayContext) {
        return OilPotItem.getOilCount(itemStack) > 0;
    }
}
