package com.github.ysbbbbbb.kaleidoscopecookery.client.render.block;

import com.github.ysbbbbbb.kaleidoscopecookery.block.kitchen.ShawarmaSpitBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen.ShawarmaSpitBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.client.renderstates.ShawarmaSpitBlockEntityRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class ShawarmaSpitBlockEntityRender implements BlockEntityRenderer<ShawarmaSpitBlockEntity, ShawarmaSpitBlockEntityRenderState> {
    private final ItemModelResolver itemModelResolver;

    public ShawarmaSpitBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public void extractRenderState(ShawarmaSpitBlockEntity blockEntity, ShawarmaSpitBlockEntityRenderState blockEntityRenderState, float f, @NonNull Vec3 vec3, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, blockEntityRenderState, f, vec3, crumblingOverlay);
        int posLong = (int) blockEntity.getBlockPos().asLong();
        this.itemModelResolver.updateForTopItem(blockEntityRenderState.cookedItem, blockEntity.cookedItem, ItemDisplayContext.FIXED, blockEntity.getLevel(), null, posLong);
        this.itemModelResolver.updateForTopItem(blockEntityRenderState.cookingItem, blockEntity.cookingItem, ItemDisplayContext.FIXED, blockEntity.getLevel(), null, posLong + 1);
        blockEntityRenderState.count = blockEntity.cookedItem.getCount();
    }

    @Override
    public ShawarmaSpitBlockEntityRenderState createRenderState() {
        return new ShawarmaSpitBlockEntityRenderState();
    }

    @Override
    public void submit(ShawarmaSpitBlockEntityRenderState blockEntityRenderState, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {
        if (blockEntityRenderState.cookedItem.isEmpty()) return;
        BlockState blockState = blockEntityRenderState.blockState;
        Boolean powered = blockState.getValue(BlockStateProperties.POWERED);
        DoubleBlockHalf half = blockState.getValue(ShawarmaSpitBlock.HALF);

        // 如果是充能状态，那么一直旋转
        if (powered) {
            long time = System.currentTimeMillis() % 360_0;
            poseStack.rotateAround(Axis.YP.rotationDegrees(time / 10f), 0.5f, 0, 0.5f);
        }

        // 如果是上半部分
        if (half == DoubleBlockHalf.UPPER) {
            poseStack.translate(0.25, 0.5, 0.25);
            for (int i = 0; i < blockEntityRenderState.count; i++) {
                poseStack.pushPose();
                poseStack.rotateAround(Axis.YP.rotationDegrees(i * 45), 0.25f, 0, 0.25f);
                poseStack.scale(0.65F, 0.65F, 0.65F);
                if (!blockEntityRenderState.cookingItem.isEmpty())
                    blockEntityRenderState.cookingItem.submit(poseStack, submitNodeCollector, blockEntityRenderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
                else blockEntityRenderState.cookedItem.submit(poseStack, submitNodeCollector, blockEntityRenderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
                poseStack.popPose();
            }
        }

        // 如果是下半部分
        else if (half == DoubleBlockHalf.LOWER) {
            poseStack.translate(0.25, 0.875, 0.25);
            for (int i = 0; i < blockEntityRenderState.count; i++) {
                poseStack.pushPose();
                poseStack.rotateAround(Axis.YP.rotationDegrees(i * 45), 0.25f, 0, 0.25f);
                poseStack.scale(0.65F, 0.65F, 0.65F);
                if (!blockEntityRenderState.cookingItem.isEmpty())
                    blockEntityRenderState.cookingItem.submit(poseStack, submitNodeCollector, blockEntityRenderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
                else blockEntityRenderState.cookedItem.submit(poseStack, submitNodeCollector, blockEntityRenderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
                poseStack.popPose();
            }
        }
    }
}
