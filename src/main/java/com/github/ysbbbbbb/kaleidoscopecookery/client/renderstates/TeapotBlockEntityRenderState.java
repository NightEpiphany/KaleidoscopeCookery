package com.github.ysbbbbbb.kaleidoscopecookery.client.renderstates;

import com.github.ysbbbbbb.kaleidoscopecookery.api.blockentity.ITeapot;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

@Environment(EnvType.CLIENT)
public class TeapotBlockEntityRenderState extends BlockEntityRenderState {
    public Level levelAccessor;
    public int facingDeg;
    public float ageInTicks;
    public BlockPos pos = BlockPos.ZERO;
    public int status = ITeapot.PUT_INGREDIENT;
    public int variant;
    public ItemStack input;
    public ItemStack result;
    public AnimationState boilingState = new AnimationState();
    public Component statusText;
    public Identifier teaFluidId;
}
