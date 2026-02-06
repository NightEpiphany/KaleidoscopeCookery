package com.github.ysbbbbbb.kaleidoscopecookery.client.renderstates;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

import java.util.Collections;
import java.util.List;

@Environment(EnvType.CLIENT)
public class FruitBasketBlockEntityRenderState extends BlockEntityRenderState {
    public List<ItemStackRenderState> items = Collections.emptyList();
}
