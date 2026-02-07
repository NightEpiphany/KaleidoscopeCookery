package com.github.ysbbbbbb.kaleidoscopecookery.client.renderstates;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.List;

@Environment(EnvType.CLIENT)
public class StockpotBlockEntityRenderState extends BlockEntityRenderState {
    public int status;
    public List<ItemStackRenderState> items = Collections.emptyList();
    @Nullable
    public EntityRenderState renderEntity;
    public Identifier soupBaseID;
    public Identifier cookingTexture;
    public Identifier finishedTexture;
    public int takeOutCount;
    public ItemStack output;
    public List<Integer> randomSeeds = Collections.emptyList();
}
