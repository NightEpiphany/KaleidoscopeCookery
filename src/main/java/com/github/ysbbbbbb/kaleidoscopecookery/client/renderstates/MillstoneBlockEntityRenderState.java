package com.github.ysbbbbbb.kaleidoscopecookery.client.renderstates;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Collections;
import java.util.List;

@Environment(EnvType.CLIENT)
public class MillstoneBlockEntityRenderState extends BlockEntityRenderState {
    public Level levelAccessor;
    public boolean hasEntity;
    public float cacheRot;
    public float rot;
    public float liftAngle;
    public int randomSeed;
    public ItemStack input;
    public List<ItemStackRenderState> inputs = Collections.emptyList();
    public ItemStack output;
    public List<ItemStackRenderState> outputs = Collections.emptyList();
}
