package com.github.ysbbbbbb.kaleidoscopecookery.client.render.block;

import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.decoration.RecipeBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.client.renderstates.RecipeBlockEntityRenderState;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModDataComponents;
import com.github.ysbbbbbb.kaleidoscopecookery.item.RecipeItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class RecipeBlockEntityRender implements BlockEntityRenderer<RecipeBlockEntity, RecipeBlockEntityRenderState> {
    private final ItemModelResolver itemModelResolver;

    public RecipeBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public RecipeBlockEntityRenderState createRenderState() {
        return new RecipeBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(RecipeBlockEntity blockEntity, RecipeBlockEntityRenderState blockEntityRenderState, float f, @NonNull Vec3 vec3, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, blockEntityRenderState, f, vec3, crumblingOverlay);
        int posLong = (int) blockEntity.getBlockPos().asLong();
        blockEntityRenderState.data = blockEntity.getItems().getStackInSlot(0).getOrDefault(ModDataComponents.RECIPE_RECORD, RecipeItem.RecipeRecord.INSTANCE);
        blockEntityRenderState.facing = blockEntity.getBlockState().getValue(HorizontalDirectionalBlock.FACING);
        blockEntityRenderState.attachFace = blockEntity.getBlockState().getValue(BlockStateProperties.ATTACH_FACE);
        this.itemModelResolver.updateForTopItem(blockEntityRenderState.targetItem, blockEntityRenderState.data.output(), ItemDisplayContext.FIXED, blockEntity.getLevel(), null, posLong);
    }

    @Override
    public void submit(RecipeBlockEntityRenderState blockEntityRenderState, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null || blockEntityRenderState.targetItem == null) {
            return;
        }
        if (blockEntityRenderState.data.output().isEmpty()) return;

        int rotationX = blockEntityRenderState.attachFace.ordinal();
        int rotationY = blockEntityRenderState.facing.get2DDataValue() + (blockEntityRenderState.attachFace == AttachFace.CEILING ? 2 : 0);

        poseStack.pushPose();
        poseStack.translate(0.5, 0.5, 0.5);
        poseStack.mulPose(Axis.YP.rotationDegrees(-rotationY * 90));
        poseStack.mulPose(Axis.XP.rotationDegrees(90 - rotationX * 90));
        poseStack.translate(-0.5, -0.5, -0.5);
        poseStack.scale(0.5f, 0.5f, 0.5f);

        if (blockEntityRenderState.attachFace == AttachFace.WALL) {
            poseStack.translate(1, 1.25, 0);
        } else {
            poseStack.translate(1, 0.75, 2);
        }

        blockEntityRenderState.targetItem.submit(poseStack, submitNodeCollector, blockEntityRenderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();
    }
}
