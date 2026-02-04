package com.github.ysbbbbbb.kaleidoscopecookery.client.conditions;

import com.github.ysbbbbbb.kaleidoscopecookery.init.ModBlocks;
import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.TypedEntityData;
import net.minecraft.world.level.storage.TagValueInput;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class SteamerCondition implements ConditionalItemModelProperty {

    public static final MapCodec<SteamerCondition> MAP_CODEC = MapCodec.unit(new SteamerCondition());
    @Override
    public @NonNull MapCodec<? extends ConditionalItemModelProperty> type() {
        return MAP_CODEC;
    }

    @Override
    public boolean get(@NonNull ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i, @NonNull ItemDisplayContext itemDisplayContext) {
        CompoundTag data = itemStack.getOrDefault(DataComponents.BLOCK_ENTITY_DATA, TypedEntityData.of(ModBlocks.STEAMER_BE, new CompoundTag())).copyTagWithoutId();
        if (itemStack.has(DataComponents.BLOCK_ENTITY_DATA)) {
            NonNullList<ItemStack> items = NonNullList.withSize(8, ItemStack.EMPTY);
            if (clientLevel != null) {
                ContainerHelper.loadAllItems(TagValueInput.create(ProblemReporter.DISCARDING, clientLevel.registryAccess(), data), items);
                return !items.getFirst().isEmpty() || !items.get(4).isEmpty();
            }
        }
        return false;
    }
}
