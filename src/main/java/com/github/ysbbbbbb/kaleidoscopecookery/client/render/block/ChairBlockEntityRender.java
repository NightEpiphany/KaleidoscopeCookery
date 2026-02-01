package com.github.ysbbbbbb.kaleidoscopecookery.client.render.block;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.block.decoration.ChairBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.decoration.ChairBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.client.renderstate.ChairBlockEntityRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.loading.v1.ExtraModelKey;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
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
    final Minecraft minecraft = Minecraft.getInstance();
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
    }

    @Override
    public void submit(ChairBlockEntityRenderState blockEntityRenderState, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {
        if (!blockEntityRenderState.hasCarpet) return;
        Identifier cacheModel = CACHE_MODEL.apply(blockEntityRenderState.color);
        BlockStateModel model = minecraft.getModelManager().getModel(ExtraModelKey.create(cacheModel::toString));
        if (model != null) {
            poseStack.pushPose();
            int rotation = blockEntityRenderState.blockState.getValue(HorizontalDirectionalBlock.FACING).getOpposite().get2DDataValue();
            poseStack.translate(0.5, 0, 0.5);
            poseStack.mulPose(Axis.YP.rotationDegrees(-rotation * 90));
            poseStack.translate(-0.5, 0, -0.5);
            RenderType renderType = RenderTypes.entityCutoutNoCull(Identifier.withDefaultNamespace("textures/atlas/blocks.png"));
            submitNodeCollector.submitBlockModel(
                    poseStack,
                    renderType,
                    model,
                    1.0F,
                    1.0F,
                    1.0F,
                    blockEntityRenderState.lightCoords,
                    OverlayTexture.NO_OVERLAY,
                    0
            );
            poseStack.popPose();
        }
    }
}
