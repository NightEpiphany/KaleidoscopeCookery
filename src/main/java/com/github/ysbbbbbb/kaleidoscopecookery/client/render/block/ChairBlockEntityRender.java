package com.github.ysbbbbbb.kaleidoscopecookery.client.render.block;

import com.github.ysbbbbbb.kaleidoscopecookery.api.client.render.ICustomModel;
import com.github.ysbbbbbb.kaleidoscopecookery.block.decoration.ChairBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.decoration.ChairBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.client.render.renderstate.ChairBlockEntityRenderState;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class ChairBlockEntityRender implements BlockEntityRenderer<ChairBlockEntity, ChairBlockEntityRenderState>, ICustomModel {
    private final ItemModelResolver itemModelResolver;
    private static final String MODEL_KEY_PREFIX = "carpet/chair/";
    public ChairBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public @NonNull ChairBlockEntityRenderState createRenderState() {
        return new ChairBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(@NonNull ChairBlockEntity blockEntity, @NonNull ChairBlockEntityRenderState blockEntityRenderState, float f, @NonNull Vec3 vec3, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, blockEntityRenderState, f, vec3, crumblingOverlay);
        blockEntityRenderState.hasCarpet = blockEntity.getBlockState().getValue(ChairBlock.HAS_CARPET);
        blockEntityRenderState.rotation = blockEntity.getBlockState().getValue(HorizontalDirectionalBlock.FACING).get2DDataValue();
        if (blockEntityRenderState.hasCarpet) {
            ItemStack carpet = getBaseModelDisplay(MODEL_KEY_PREFIX + blockEntity.getColor().getName());
            this.itemModelResolver.updateForTopItem(blockEntityRenderState.carpetModel, carpet, ItemDisplayContext.NONE, blockEntity.getLevel(), null, 0);
        }
    }

    @Override
    public void submit(ChairBlockEntityRenderState blockEntityRenderState, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {
        if (!blockEntityRenderState.hasCarpet) return;
        poseStack.pushPose();
        poseStack.rotateDegrees(Axis.YP, -blockEntityRenderState.rotation * 90);
        int i;
        float j;
        switch (blockEntityRenderState.rotation) {
            case 0 -> {
                i = 1;
                j = 1.0F;
            }
            case 1 -> {
                i = 1;
                j = -0.72F;
            }
            case 2 -> {
                i = -1;
                j = -0.72F;
            }
            default -> {
                i = -1;
                j = 1.0F;
            }

        }
        poseStack.translate(i * 0.5f, 0.5f, j * 0.58f);
        if (!blockEntityRenderState.carpetModel.isEmpty())
            blockEntityRenderState.carpetModel.submit(
                    poseStack,
                    submitNodeCollector,
                    blockEntityRenderState.lightCoords,
                    OverlayTexture.NO_OVERLAY,
                    0
            );
        poseStack.popPose();
    }
}
