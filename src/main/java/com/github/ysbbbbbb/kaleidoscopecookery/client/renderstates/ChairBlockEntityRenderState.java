package com.github.ysbbbbbb.kaleidoscopecookery.client.renderstates;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

@Environment(EnvType.CLIENT)
public class ChairBlockEntityRenderState extends BlockEntityRenderState {
    public DyeColor color = DyeColor.WHITE;
    public boolean hasCarpet = false;
    public int rotation = 0;
    public BlockState blockState = Blocks.AIR.defaultBlockState();
}
