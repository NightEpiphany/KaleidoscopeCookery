package com.github.ysbbbbbb.kaleidoscopecookery.client.render.block;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.block.decoration.ChairBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.decoration.ChairBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.client.init.ModModelLoading;
import com.github.ysbbbbbb.kaleidoscopecookery.client.renderstates.ChairBlockEntityRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.block.model.BlockDisplayContext;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class ChairBlockEntityRender implements BlockEntityRenderer<ChairBlockEntity, ChairBlockEntityRenderState> {
    private static final Function<DyeColor, Identifier> CACHE_MODEL = Util.memoize(color ->
            Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "block/carpet/chair/" + color.getName()));
    private static final BlockDisplayContext BLOCK_DISPLAY_CONTEXT = BlockDisplayContext.create();
    private static final long MODEL_SEED = 42L;

    public ChairBlockEntityRender(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public ChairBlockEntityRenderState createRenderState() {
        return new ChairBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(ChairBlockEntity blockEntity, ChairBlockEntityRenderState blockEntityRenderState, float f, @NonNull Vec3 vec3, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, blockEntityRenderState, f, vec3, crumblingOverlay);
        blockEntityRenderState.hasCarpet = blockEntity.getBlockState().getValue(ChairBlock.HAS_CARPET);
        blockEntityRenderState.color = blockEntity.getColor();
        blockEntityRenderState.blockState = blockEntity.getBlockState();
        blockEntityRenderState.rotation = blockEntity.getBlockState().getValue(HorizontalDirectionalBlock.FACING).getOpposite().get2DDataValue();
    }

    @Override
    public void submit(ChairBlockEntityRenderState blockEntityRenderState, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {
        if (!blockEntityRenderState.hasCarpet) return;
        Identifier cacheModel = CACHE_MODEL.apply(blockEntityRenderState.color);
        if (!ModModelLoading.isRegistered(cacheModel)) return;
        BlockModel model = ModModelLoading.getModel(cacheModel);
        if (model == null) return;
        BlockModelRenderState renderState = new BlockModelRenderState();
        model.update(renderState, blockEntityRenderState.blockState, BLOCK_DISPLAY_CONTEXT, MODEL_SEED);
        if (renderState.isEmpty()) return;
        poseStack.pushPose();
        poseStack.translate(0.5, 0, 0.5);
        poseStack.mulPose(Axis.YP.rotationDegrees(-blockEntityRenderState.rotation * 90));
        poseStack.translate(-0.5, 0, -0.5);
        renderState.submit(poseStack, submitNodeCollector, blockEntityRenderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();
    }
}
