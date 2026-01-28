package com.github.ysbbbbbb.kaleidoscopecookery.client.renderstate;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.world.item.DyeColor;

@Environment(EnvType.CLIENT)
public class ChairBlockEntityRenderState extends BlockEntityRenderState {
    public DyeColor color = DyeColor.WHITE;
}
