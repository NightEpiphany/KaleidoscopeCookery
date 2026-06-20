package com.github.ysbbbbbb.kaleidoscopecookery.client.render.renderstate;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

@Environment(EnvType.CLIENT)
public class ChairBlockEntityRenderState extends BlockEntityRenderState {
    public boolean hasCarpet = false;
    public int rotation = 0;
    public ItemStackRenderState carpetModel = new ItemStackRenderState();
}
