package com.github.ysbbbbbb.kaleidoscopecookery.client.render.renderstate;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

@Environment(EnvType.CLIENT)
public class ShawarmaSpitBlockEntityRenderState extends BlockEntityRenderState {
    public ItemStackRenderState cookingItem = new ItemStackRenderState();
    public ItemStackRenderState cookedItem = new ItemStackRenderState();
    public int count;
    public BlockState blockState = Blocks.AIR.defaultBlockState();
}
