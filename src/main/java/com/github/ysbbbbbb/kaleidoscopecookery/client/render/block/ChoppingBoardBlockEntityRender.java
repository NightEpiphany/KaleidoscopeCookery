package com.github.ysbbbbbb.kaleidoscopecookery.client.render.block;

import com.github.ysbbbbbb.kaleidoscopecookery.block.kitchen.ChoppingBoardBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen.ChoppingBoardBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.client.init.ModModelLoading;
import com.github.ysbbbbbb.kaleidoscopecookery.client.renderstates.ChoppingBoardBlockEntityRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.block.model.BlockDisplayContext;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class ChoppingBoardBlockEntityRender implements BlockEntityRenderer<ChoppingBoardBlockEntity, ChoppingBoardBlockEntityRenderState> {
    private static final long MODEL_SEED = 42L;
    private static final BlockDisplayContext BLOCK_DISPLAY_CONTEXT = BlockDisplayContext.create();
    public ChoppingBoardBlockEntityRender(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public ChoppingBoardBlockEntityRenderState createRenderState() {
        return new ChoppingBoardBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(ChoppingBoardBlockEntity blockEntity, ChoppingBoardBlockEntityRenderState blockEntityRenderState, float f, @NonNull Vec3 vec3, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, blockEntityRenderState, f, vec3, crumblingOverlay);
        blockEntityRenderState.modelId = blockEntity.getModelId();
        blockEntityRenderState.previousModel = blockEntity.previousModel;
        blockEntityRenderState.cacheModels = blockEntity.cacheModels;
        blockEntityRenderState.maxCutCount = blockEntity.getMaxCutCount();
        blockEntityRenderState.currentCutCount = blockEntity.getCurrentCutCount();
        blockEntityRenderState.blockState = blockEntity.getBlockState();
        blockEntityRenderState.rotation = blockEntity.getBlockState().getValue(ChoppingBoardBlock.FACING).getOpposite().get2DDataValue();
    }

    @Override
    public void submit(ChoppingBoardBlockEntityRenderState blockEntityRenderState, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {
        Identifier modelId = blockEntityRenderState.modelId;
        if (modelId == null) return;
        if (!modelId.equals(blockEntityRenderState.previousModel)) {
            blockEntityRenderState.previousModel = modelId;
            blockEntityRenderState.cacheModels = new Identifier[blockEntityRenderState.maxCutCount + 1];
            for (int i = 0; i <= blockEntityRenderState.maxCutCount; i++) {
                blockEntityRenderState.cacheModels[i] = Identifier.fromNamespaceAndPath(modelId.getNamespace(), "chopping_board/" + modelId.getPath() + "/" + i);
            }
        }
        if (blockEntityRenderState.cacheModels == null) {
            return;
        }
        int index = Math.min(blockEntityRenderState.currentCutCount, blockEntityRenderState.cacheModels.length - 1);
        Identifier cacheModel = blockEntityRenderState.cacheModels[index];
        if (cacheModel == null) {
            return;
        }
        BlockModel model = ModModelLoading.getModel(cacheModel);
        if (model == null) return;
        BlockModelRenderState renderState = new BlockModelRenderState();
        model.update(renderState, blockEntityRenderState.blockState, BLOCK_DISPLAY_CONTEXT, MODEL_SEED);
        if (renderState.isEmpty()) return;
        poseStack.pushPose();
        poseStack.translate(0.5D, 0, 0.5D);
        poseStack.mulPose(Axis.YP.rotationDegrees(blockEntityRenderState.rotation * 90));
        poseStack.translate(-0.5D, 0.125, -0.5D);
        renderState.submit(poseStack, submitNodeCollector, blockEntityRenderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();
    }
}
