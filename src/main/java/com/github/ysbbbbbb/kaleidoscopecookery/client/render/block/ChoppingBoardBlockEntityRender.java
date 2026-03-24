package com.github.ysbbbbbb.kaleidoscopecookery.client.render.block;

import com.github.ysbbbbbb.kaleidoscopecookery.block.kitchen.ChoppingBoardBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen.ChoppingBoardBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.client.renderstates.ChoppingBoardBlockEntityRenderState;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModDataComponents;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
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
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class ChoppingBoardBlockEntityRender implements BlockEntityRenderer<ChoppingBoardBlockEntity, ChoppingBoardBlockEntityRenderState> {
    private final ItemModelResolver itemModelResolver;
    public ChoppingBoardBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
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
        blockEntityRenderState.rotation = blockEntity.getBlockState().getValue(ChoppingBoardBlock.FACING).getOpposite().get2DDataValue();

        if (blockEntityRenderState.modelId != null && !blockEntityRenderState.modelId.equals(Identifier.parse("minecraft:air"))) {
            Identifier modelId = blockEntityRenderState.modelId;
            if (!modelId.equals(blockEntityRenderState.previousModel)) {
                blockEntityRenderState.previousModel = modelId;
                blockEntityRenderState.cacheModels = new Identifier[blockEntityRenderState.maxCutCount + 1];
                for (int i = 0; i <= blockEntityRenderState.maxCutCount; i++) {
                    blockEntityRenderState.cacheModels[i] = Identifier.fromNamespaceAndPath(modelId.getNamespace(), "chopping_board/" + modelId.getPath() + "/" + i);
                }
            }
            if (blockEntityRenderState.cacheModels == null) return;
            int index = Math.min(blockEntityRenderState.currentCutCount, blockEntityRenderState.cacheModels.length - 1);
            Identifier cacheModel = blockEntityRenderState.cacheModels[index];
            if (cacheModel == null) return;
            ItemStack chopContent = ModItems.MODEL_DISPLAY.getDefaultInstance();
            chopContent.set(ModDataComponents.MODEL_DISPLAY_MODEL, cacheModel.getPath());
            this.itemModelResolver.updateForTopItem(blockEntityRenderState.contentModel, chopContent, ItemDisplayContext.NONE, blockEntity.getLevel(), null, 0);
        }
    }

    @Override
    public void submit(ChoppingBoardBlockEntityRenderState blockEntityRenderState, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {
        if (blockEntityRenderState.contentModel.isEmpty()) return;
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(blockEntityRenderState.rotation * 90));
        poseStack.translate(0, 0.625, 0);
        if (blockEntityRenderState.rotation == 0 || blockEntityRenderState.rotation == 3) {
            int i = blockEntityRenderState.rotation == 3 ? -1 : 1;
            poseStack.translate(0.45d, 0, i * 0.5d);
        } else {
            if (blockEntityRenderState.rotation == 1)
                poseStack.translate(-0.6d, 0, 0.5d);
            else poseStack.translate(-0.6d, 0, -0.5d);
        }
        blockEntityRenderState.contentModel.submit(
                poseStack,
                submitNodeCollector,
                blockEntityRenderState.lightCoords,
                OverlayTexture.NO_OVERLAY,
                0
        );
        poseStack.popPose();
    }
}
