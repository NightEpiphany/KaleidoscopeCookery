package com.github.ysbbbbbb.kaleidoscopecookery.client.render.block;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.block.kitchen.TeapotBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen.TeapotBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.client.model.TeapotModel;
import com.github.ysbbbbbb.kaleidoscopecookery.client.render.renderstate.TeapotBlockEntityRenderState;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.serializer.TeapotRecipeSerializer;
import com.github.ysbbbbbb.kaleidoscopecookery.util.fluids.TeaFluidHelper;
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
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class TeapotBlockEntityRender implements BlockEntityRenderer<TeapotBlockEntity, TeapotBlockEntityRenderState> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "textures/block/teapot.png");
    private final TeapotModel model;

    private final Function<Identifier, Component> fluidNameCache = Util.memoize(id -> {
        if (id.equals(TeapotRecipeSerializer.EMPTY_TEA_FLUID)) {
            return Component.translatable("mco.configure.world.slot.empty");
        }
        return TeaFluidHelper.getDisplayName(id);
    });

    public TeapotBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.model = new TeapotModel(context.bakeLayer(TeapotModel.LAYER_LOCATION));
    }

    @Override
    public @NonNull TeapotBlockEntityRenderState createRenderState() {
        return new TeapotBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(@NonNull TeapotBlockEntity blockEntity, @NonNull TeapotBlockEntityRenderState blockEntityRenderState, float f, @NonNull Vec3 vec3, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, blockEntityRenderState, f, vec3, crumblingOverlay);
        blockEntityRenderState.levelAccessor = blockEntity.getLevel();
        blockEntityRenderState.facingDeg = blockEntity.getBlockState().getValue(TeapotBlock.FACING).get2DDataValue() * 90;
        blockEntityRenderState.pos = blockEntity.getBlockPos();
        blockEntityRenderState.statusText = blockEntity.getStatusText();
        blockEntityRenderState.status = blockEntity.getStatus();
        blockEntityRenderState.variant = blockEntity.getBlockState().getValue(TeapotBlock.VARIANT);
        blockEntityRenderState.ageInTicks = blockEntity.getLevel() != null ? blockEntity.getLevel().getGameTime() + f : f;
        blockEntityRenderState.input = blockEntity.getInput();
        blockEntityRenderState.result = blockEntity.getResult();
        blockEntityRenderState.boilingState.copyFrom(blockEntity.boilingState);
        blockEntityRenderState.teaFluidId = blockEntity.getTeaFluidId();
    }

    @Override
    public void submit(@NonNull TeapotBlockEntityRenderState blockEntityRenderState, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {
        if (blockEntityRenderState.levelAccessor == null) {
            return;
        }

        this.model.root().getAllParts().forEach(ModelPart::resetPose);
        TeapotModel.State state = new TeapotModel.State(blockEntityRenderState.ageInTicks, blockEntityRenderState.boilingState, blockEntityRenderState.variant);
        this.model.setupAnim(state);

        poseStack.pushPose();
        poseStack.translate(0.5, 1.5, 0.5);
        poseStack.mulPose(Axis.ZN.rotationDegrees(180));
        poseStack.mulPose(Axis.YN.rotationDegrees(180 - blockEntityRenderState.facingDeg));
        RenderType renderType = RenderTypes.entityCutout(TEXTURE);
        submitNodeCollector.submitModel(
                model,
                state,
                poseStack,
                renderType,
                blockEntityRenderState.lightCoords,
                OverlayTexture.NO_OVERLAY,
                0,
                null
        );
        poseStack.popPose();
    }
}
