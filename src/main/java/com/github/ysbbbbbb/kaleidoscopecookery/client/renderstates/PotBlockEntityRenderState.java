package com.github.ysbbbbbb.kaleidoscopecookery.client.renderstates;

import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen.PotBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

import java.util.List;

@Environment(EnvType.CLIENT)
public class PotBlockEntityRenderState extends BlockEntityRenderState {
    public PotBlockEntity.StirFryAnimationData data;
    public long seed;
    public int status;
    public List<ItemStackRenderState> inputs;
    public ItemStackRenderState output;
    public boolean hasCarrier;
    public int currentTick;
}
