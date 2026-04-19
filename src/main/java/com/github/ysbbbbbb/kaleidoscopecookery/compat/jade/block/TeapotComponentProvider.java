package com.github.ysbbbbbb.kaleidoscopecookery.compat.jade.block;

import com.github.ysbbbbbb.kaleidoscopecookery.api.blockentity.ITeapot;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen.TeapotBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.jade.ModPlugin;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.serializer.TeapotRecipeSerializer;
import com.github.ysbbbbbb.kaleidoscopecookery.util.fluids.TeaFluidHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum TeapotComponentProvider implements IBlockComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        if (!(accessor.getBlockEntity() instanceof TeapotBlockEntity teapot)) {
            return;
        }

        if (teapot.getStatus() == ITeapot.FINISHED) {
            ItemStack result = teapot.getResult();
            if (!result.isEmpty()) {
                tooltip.add(Component.translatable("jade.kaleidoscope_cookery.teapot.result", result.getHoverName()));
            }
            return;
        }

        ResourceLocation fluidId = teapot.getTeaFluidId();
        if (!TeapotRecipeSerializer.EMPTY_TEA_FLUID.equals(fluidId)) {
            tooltip.add(Component.translatable("jade.kaleidoscope_cookery.teapot.fluid", TeaFluidHelper.getDisplayName(fluidId)));
        }

        ItemStack input = teapot.getInput();
        if (!input.isEmpty()) {
            tooltip.add(Component.translatable("jade.kaleidoscope_cookery.teapot.ingredient", input.getHoverName()));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return ModPlugin.TEAPOT;
    }
}
