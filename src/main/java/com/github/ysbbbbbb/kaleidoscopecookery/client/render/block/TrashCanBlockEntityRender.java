package com.github.ysbbbbbb.kaleidoscopecookery.client.render.block;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.block.misc.TrashCanBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.misc.TrashCanBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.client.model.TrashCanModel;
import com.github.ysbbbbbb.kaleidoscopecookery.client.render.renderstate.TrashCanBlockEntityRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class TrashCanBlockEntityRender implements BlockEntityRenderer<TrashCanBlockEntity, TrashCanBlockEntityRenderState> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "textures/block/trash_can.png");
    private final TrashCanModel model;

    public TrashCanBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.model = new TrashCanModel(context.bakeLayer(TrashCanModel.LAYER_LOCATION));
    }

    @Override
    public @NonNull TrashCanBlockEntityRenderState createRenderState() {
        return new TrashCanBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(@NonNull TrashCanBlockEntity blockEntity, @NonNull TrashCanBlockEntityRenderState blockEntityRenderState, float f, @NonNull Vec3 vec3, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, blockEntityRenderState, f, vec3, crumblingOverlay);
        blockEntityRenderState.levelAccessor = blockEntity.getLevel();
        blockEntityRenderState.facingDeg = blockEntity.getBlockState().getValue(TrashCanBlock.FACING).get2DDataValue() * 90;
        blockEntityRenderState.ageInTicks = blockEntity.getLevel() != null ? blockEntity.getLevel().getGameTime() + f : f;
        blockEntityRenderState.putState.copyFrom(blockEntity.putState);
        blockEntityRenderState.withdrawState.copyFrom(blockEntity.withdrawState);
        blockEntityRenderState.player1State.copyFrom(blockEntity.player1State);
        blockEntityRenderState.player2State.copyFrom(blockEntity.player2State);
        blockEntityRenderState.enterState.copyFrom(blockEntity.enterState);
    }

    @Override
    public void submit(@NonNull TrashCanBlockEntityRenderState blockEntityRenderState, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {
        if (blockEntityRenderState.levelAccessor == null) {
            return;
        }
        TrashCanModel.State state = new TrashCanModel.State(
                blockEntityRenderState.ageInTicks,
                blockEntityRenderState.putState,
                blockEntityRenderState.withdrawState,
                blockEntityRenderState.player1State,
                blockEntityRenderState.player2State,
                blockEntityRenderState.enterState
        );
        this.model.root().getAllParts().forEach(ModelPart::resetPose);
        this.model.setupAnim(state);
        poseStack.pushPose();
        poseStack.translate(0.5, 1.5, 0.5);
        poseStack.rotateDegrees(Axis.ZN, 180);
        poseStack.rotateDegrees(Axis.YN, 180 - blockEntityRenderState.facingDeg);
        RenderType renderType = RenderTypes.entityCutout(TEXTURE);
        submitNodeCollector.submitModel(
                model,
                state,
                poseStack,
                renderType,
                blockEntityRenderState.lightCoords,
                OverlayTexture.NO_OVERLAY,
                0
        );
        poseStack.popPose();
    }
}
