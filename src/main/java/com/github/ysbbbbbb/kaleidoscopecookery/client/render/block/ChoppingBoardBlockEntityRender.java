package com.github.ysbbbbbb.kaleidoscopecookery.client.render.block;

import com.github.ysbbbbbb.kaleidoscopecookery.block.kitchen.ChoppingBoardBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen.ChoppingBoardBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.client.init.ModModelKeys;
import com.github.ysbbbbbb.kaleidoscopecookery.client.renderstates.ChoppingBoardBlockEntityRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class ChoppingBoardBlockEntityRender implements BlockEntityRenderer<ChoppingBoardBlockEntity, ChoppingBoardBlockEntityRenderState> {
    private final Minecraft minecraft = Minecraft.getInstance();
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
        BlockStateModel model = minecraft.getModelManager().getModel(ModModelKeys.get(cacheModel));
        if (model == null) return;
        RenderType renderType = Sheets.cutoutBlockSheet();
        poseStack.pushPose();
        int rotation = blockEntityRenderState.blockState.getValue(ChoppingBoardBlock.FACING).get2DDataValue();
        poseStack.translate(0.5D, 0, 0.5D);
        poseStack.mulPose(Axis.YP.rotationDegrees(rotation * 90));
        poseStack.translate(-0.5D, 0.125, -0.5D);
        submitNodeCollector.submitBlockModel(
                poseStack,
                renderType,
                model,
                1.0F,
                1.0F,
                1.0F,
                15728880,
                OverlayTexture.NO_OVERLAY,
                0
        );
        poseStack.popPose();
    }
}
