package com.github.ysbbbbbb.kaleidoscopecookery.client.render.soupbase;

import com.github.ysbbbbbb.kaleidoscopecookery.api.client.render.ISoupBaseRender;
import com.github.ysbbbbbb.kaleidoscopecookery.client.renderstates.StockpotBlockEntityRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jspecify.annotations.NonNull;

public class FluidSoupBaseRender implements ISoupBaseRender {
    private final Fluid fluid;

    public FluidSoupBaseRender(Fluid fluid) {
        this.fluid = fluid;
    }

    @Override
    public void renderWhenPutIngredient(StockpotBlockEntityRenderState stockpot, float partialTick, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, int packedOverlay, float soupHeight, @NonNull CameraRenderState cameraRenderState) {
        ISoupBaseRender.renderSurface(getStillFluidSprite(fluid), getFluidColor(fluid), poseStack, packedLight, soupHeight);
    }

    @Override
    public void renderWhenCooking(StockpotBlockEntityRenderState stockpot, float partialTick, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, int packedOverlay, Identifier cookingTexture, float soupHeight, @NonNull CameraRenderState cameraRenderState) {
        TextureAtlasSprite sprite = Minecraft.getInstance().getModelManager().atlasManager.get(new Material(TextureAtlas.LOCATION_BLOCKS, cookingTexture));
        ISoupBaseRender.renderSurface(sprite, 0xFFFFFFFF, poseStack, packedLight, soupHeight);
    }

    @Override
    public void renderWhenFinished(StockpotBlockEntityRenderState stockpot, float partialTick, PoseStack poseStack, SubmitNodeCollector buffer, int packedLight, int packedOverlay, Identifier finishedTexture, float soupHeight, @NonNull CameraRenderState cameraRenderState) {
        TextureAtlasSprite sprite = Minecraft.getInstance().getModelManager().atlasManager.get(new Material(TextureAtlas.LOCATION_BLOCKS, finishedTexture));
        ISoupBaseRender.renderSurface(sprite, 0xFFFFFFFF, poseStack, packedLight, soupHeight);
    }

    private TextureAtlasSprite getStillFluidSprite(Fluid fluid) {
        FluidRenderHandler renderHandler = FluidRenderHandlerRegistry.INSTANCE.get(fluid);
        if (renderHandler != null) {
            FluidState fluidState = fluid.defaultFluidState();
            TextureAtlasSprite[] sprites = renderHandler.getFluidSprites(null, null, fluidState);
            if (sprites.length > 0) {
                return sprites[0];
            }
        }
        // 如果没有找到渲染处理器，使用默认水纹理作为后备
        return Minecraft.getInstance().getModelManager().atlasManager.get(new Material(TextureAtlas.LOCATION_BLOCKS, Identifier.fromNamespaceAndPath("minecraft", "block/water_still")));
    }

    private int getFluidColor(Fluid fluid) {
        if (fluid == Fluids.WATER) return -12618012;
        FluidRenderHandler renderHandler = FluidRenderHandlerRegistry.INSTANCE.get(fluid);
        if (renderHandler != null) {
            FluidState fluidState = fluid.defaultFluidState();
            return renderHandler.getFluidColor(null, null, fluidState);
        }
        // 默认颜色（白色）
        return 0xFFFFFFFF;
    }
}
