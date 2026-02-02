package com.github.ysbbbbbb.kaleidoscopecookery.client.renderstates;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.NonNullList;

@Environment(EnvType.CLIENT)
public class SteamerBlockEntityRenderState extends BlockEntityRenderState {
    public NonNullList<ItemStackRenderState> items;
}
