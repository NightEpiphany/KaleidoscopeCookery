package com.github.ysbbbbbb.kaleidoscopecookery.client.renderstates;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

@Environment(EnvType.CLIENT)
public class KitchenwareRacksBlockEntityRenderState extends BlockEntityRenderState {
    public ItemStackRenderState left = new ItemStackRenderState();
    public ItemStackRenderState right = new ItemStackRenderState();
    public int rotation = 0;
}
