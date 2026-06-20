package com.github.ysbbbbbb.kaleidoscopecookery.client.render.renderstate;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.level.Level;

@Environment(EnvType.CLIENT)
public class TrashCanBlockEntityRenderState extends BlockEntityRenderState {
    public Level levelAccessor;
    public int facingDeg;
    public float ageInTicks;
    public AnimationState putState = new AnimationState();
    public AnimationState withdrawState = new AnimationState();
    public AnimationState player1State = new AnimationState();
    public AnimationState player2State = new AnimationState();
    public AnimationState enterState = new AnimationState();
}
