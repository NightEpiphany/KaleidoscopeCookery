package com.github.ysbbbbbb.kaleidoscopecookery.client.renderstates;

import com.github.ysbbbbbb.kaleidoscopecookery.item.RecipeItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

@Environment(EnvType.CLIENT)
public class RecipeBlockEntityRenderState extends BlockEntityRenderState {
    public ItemStackRenderState targetItem;
    public RecipeItem.RecipeRecord data;
}
