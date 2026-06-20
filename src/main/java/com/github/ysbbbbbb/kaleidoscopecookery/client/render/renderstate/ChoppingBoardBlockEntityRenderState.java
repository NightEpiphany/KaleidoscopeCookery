package com.github.ysbbbbbb.kaleidoscopecookery.client.render.renderstate;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class ChoppingBoardBlockEntityRenderState extends BlockEntityRenderState {
    @Nullable
    public Identifier modelId;
    @Nullable
    public Identifier previousModel;
    @Nullable
    public Identifier[] cacheModels;
    public int maxCutCount = 1;
    public int currentCutCount = 0;
    public int rotation = 0;
    public ItemStackRenderState contentModel = new ItemStackRenderState();
}
