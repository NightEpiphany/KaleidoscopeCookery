package com.github.ysbbbbbb.kaleidoscopecookery.client.render.block;

import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.decoration.FruitBasketBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.client.render.renderstate.FruitBasketBlockEntityRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class FruitBasketBlockEntityRender implements BlockEntityRenderer<FruitBasketBlockEntity, FruitBasketBlockEntityRenderState> {
    private final ItemModelResolver itemModelResolver;

    public FruitBasketBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public @NonNull FruitBasketBlockEntityRenderState createRenderState() {
        return new FruitBasketBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(@NonNull FruitBasketBlockEntity blockEntity, @NonNull FruitBasketBlockEntityRenderState blockEntityRenderState, float f, @NonNull Vec3 vec3, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, blockEntityRenderState, f, vec3, crumblingOverlay);
        blockEntityRenderState.items = new ArrayList<>();
        blockEntityRenderState.rotation = blockEntity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).get2DDataValue() * 90;
        int posLong = (int) blockEntity.getBlockPos().asLong();
        for (var index = 0; index < blockEntity.getItems().size(); index++) {
            ItemStackRenderState itemStackRenderState = new ItemStackRenderState();
            this.itemModelResolver.updateForTopItem(itemStackRenderState, blockEntity.getItems().get(index), ItemDisplayContext.FIXED, blockEntity.getLevel(), null, posLong + index);
            blockEntityRenderState.items.add(itemStackRenderState);
        }
    }

    @Override
    public void submit(FruitBasketBlockEntityRenderState blockEntityRenderState, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {
        var items = blockEntityRenderState.items;
        poseStack.pushPose();
        poseStack.translate(0.5, 0, 0.5);
        poseStack.rotateDegrees(Axis.YN, blockEntityRenderState.rotation);
        poseStack.translate(-0.5, 0, -0.5);
        poseStack.translate(0.1, 0.3, 0.35);
        for (int i = 0; i < 2; i++) {
            poseStack.pushPose();
            for (int j = 0; j < 4; j++) {
                int index = i * 4 + j;
                var itemRenderState = items.get(index);
                if (!itemRenderState.isEmpty()) {
                    poseStack.translate(0.15, 0, 0);
                    poseStack.pushPose();
                    poseStack.translate(0, 0, index % 2 == 0 ? -0.01f : 0.01f);
                    poseStack.rotateDegrees(Axis.YN, 90);
                    poseStack.rotateDegrees(Axis.XN, -30);
                    poseStack.scale(0.375f, 0.375f, 0.375f);
                    itemRenderState.submit(
                            poseStack,
                            submitNodeCollector,
                            blockEntityRenderState.lightCoords,
                            OverlayTexture.NO_OVERLAY,
                            0
                    );
                    poseStack.popPose();
                }
            }
            poseStack.popPose();
            poseStack.translate(0, 0, 0.32);
        }
        poseStack.popPose();
    }
}
