package com.github.ysbbbbbb.kaleidoscopecookery.client.render.block;

import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen.KitchenwareRacksBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.client.render.renderstate.KitchenwareRacksBlockEntityRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class KitchenwareRacksBlockEntityRender implements BlockEntityRenderer<KitchenwareRacksBlockEntity, KitchenwareRacksBlockEntityRenderState> {
    private final ItemModelResolver itemModelResolver;

    public KitchenwareRacksBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public void extractRenderState(@NonNull KitchenwareRacksBlockEntity blockEntity, @NonNull KitchenwareRacksBlockEntityRenderState blockEntityRenderState, float f, @NonNull Vec3 vec3, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, blockEntityRenderState, f, vec3, crumblingOverlay);
        int posLong = (int) blockEntity.getBlockPos().asLong();
        blockEntityRenderState.rotation = blockEntity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).get2DDataValue() * 90;
        this.itemModelResolver.updateForTopItem(blockEntityRenderState.left, blockEntity.getItemLeft(), ItemDisplayContext.FIXED, blockEntity.getLevel(), null, posLong);
        this.itemModelResolver.updateForTopItem(blockEntityRenderState.right, blockEntity.getItemRight(), ItemDisplayContext.FIXED, blockEntity.getLevel(), null, posLong + 1);
    }

    @Override
    public @NonNull KitchenwareRacksBlockEntityRenderState createRenderState() {
        return new KitchenwareRacksBlockEntityRenderState();
    }

    @Override
    public void submit(KitchenwareRacksBlockEntityRenderState blockEntityRenderState, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {
        var leftState = blockEntityRenderState.left;
        var rightState = blockEntityRenderState.right;

        poseStack.translate(0.5, 0, 0.5);
        poseStack.mulPose(Axis.YN.rotationDegrees(blockEntityRenderState.rotation));

        if (!leftState.isEmpty()) {
            poseStack.pushPose();
            poseStack.translate(-0.2, 0.4375, -0.3);
            poseStack.scale(0.75f, 0.75f, 0.75f);
            poseStack.mulPose(Axis.XN.rotationDegrees(180));
            poseStack.mulPose(Axis.YN.rotationDegrees(-25));
            poseStack.mulPose(Axis.ZN.rotationDegrees(45));
            leftState.submit(poseStack, submitNodeCollector, blockEntityRenderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
            poseStack.popPose();
        }

        if (!rightState.isEmpty()) {
            poseStack.pushPose();
            poseStack.translate(0.2, 0.4375, -0.3);
            poseStack.scale(0.75f, 0.75f, 0.75f);
            poseStack.mulPose(Axis.XN.rotationDegrees(180));
            poseStack.mulPose(Axis.YN.rotationDegrees(-25));
            poseStack.mulPose(Axis.ZN.rotationDegrees(45));
            rightState.submit(poseStack, submitNodeCollector, blockEntityRenderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
            poseStack.popPose();
        }
    }
}
