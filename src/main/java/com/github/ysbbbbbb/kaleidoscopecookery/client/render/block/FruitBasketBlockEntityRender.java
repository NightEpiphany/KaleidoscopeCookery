package com.github.ysbbbbbb.kaleidoscopecookery.client.render.block;

import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.decoration.FruitBasketBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.client.renderstate.FruitBasketBlockEntityRenderState;
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
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class FruitBasketBlockEntityRender implements BlockEntityRenderer<FruitBasketBlockEntity, FruitBasketBlockEntityRenderState> {
    private final BlockEntityRendererProvider.Context context;
    private final ItemModelResolver itemModelResolver;

    public FruitBasketBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.context = context;
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public FruitBasketBlockEntityRenderState createRenderState() {
        return new FruitBasketBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(FruitBasketBlockEntity blockEntity, FruitBasketBlockEntityRenderState blockEntityRenderState, float f, @NonNull Vec3 vec3, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, blockEntityRenderState, f, vec3, crumblingOverlay);
        blockEntityRenderState.items = NonNullList.withSize(blockEntity.getItems().size(), new ItemStackRenderState());
       for (var index = 0; index < blockEntityRenderState.items.size(); index++) {
           this.itemModelResolver.updateForTopItem(blockEntityRenderState.items.get(index), blockEntity.getItems().get(index), ItemDisplayContext.FIXED, blockEntity.getLevel(), null, index);
       }
    }

    @Override
    public void submit(FruitBasketBlockEntityRenderState blockEntityRenderState, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {
        var items = blockEntityRenderState.items;
        int rotation = blockEntityRenderState.blockState.getValue(BlockStateProperties.HORIZONTAL_FACING).get2DDataValue() * 90;
        poseStack.pushPose();
        poseStack.translate(0.5, 0, 0.5);
        poseStack.mulPose(Axis.YN.rotationDegrees(rotation));
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
                    poseStack.mulPose(Axis.YN.rotationDegrees(90));
                    poseStack.mulPose(Axis.XN.rotationDegrees(-30));
                    poseStack.scale(0.375f, 0.375f, 0.375f);
                    itemRenderState.submit(poseStack, submitNodeCollector, blockEntityRenderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
                    poseStack.popPose();
                }
            }
            poseStack.popPose();
            poseStack.translate(0, 0, 0.32);
        }
        poseStack.popPose();
    }
}
