package com.github.ysbbbbbb.kaleidoscopecookery.client.renderstates;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

@Environment(EnvType.CLIENT)
public class ShawarmaSpitBlockEntityRenderState extends BlockEntityRenderState {
    public ItemStackRenderState cookingItem;
    public ItemStackRenderState cookedItem;
    public int count;
}
