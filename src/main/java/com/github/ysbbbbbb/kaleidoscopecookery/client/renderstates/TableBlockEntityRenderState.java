package com.github.ysbbbbbb.kaleidoscopecookery.client.renderstates;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Collections;
import java.util.List;

@Environment(EnvType.CLIENT)
public class TableBlockEntityRenderState extends BlockEntityRenderState {
    public List<ItemStackRenderState> items = Collections.emptyList();
    public DyeColor color;
    public BlockState blockState = Blocks.AIR.defaultBlockState();
}
