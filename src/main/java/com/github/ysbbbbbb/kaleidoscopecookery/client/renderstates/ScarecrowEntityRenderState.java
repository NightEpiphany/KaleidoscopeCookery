package com.github.ysbbbbbb.kaleidoscopecookery.client.renderstates;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class ScarecrowEntityRenderState extends ArmedEntityRenderState {
    public ItemStack headItem;
    @Nullable
    public LivingEntity entityOnShoulder;
    public float partialTicks;
    public long lastHit;
    public long time;
}
