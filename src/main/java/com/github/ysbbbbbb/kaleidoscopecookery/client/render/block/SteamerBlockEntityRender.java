package com.github.ysbbbbbb.kaleidoscopecookery.client.render.block;

import com.github.ysbbbbbb.kaleidoscopecookery.block.kitchen.SteamerBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen.SteamerBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.client.renderstates.SteamerBlockEntityRenderState;
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
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class SteamerBlockEntityRender implements BlockEntityRenderer<SteamerBlockEntity, SteamerBlockEntityRenderState> {
    private final ItemModelResolver itemModelResolver;

    public SteamerBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public void extractRenderState(SteamerBlockEntity blockEntity, SteamerBlockEntityRenderState blockEntityRenderState, float f, @NonNull Vec3 vec3, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, blockEntityRenderState, f, vec3, crumblingOverlay);
        int posLong = (int) blockEntity.getBlockPos().asLong();
        blockEntityRenderState.items = new ArrayList<>();
        for (var index = 0; index < blockEntity.getItems().size(); index++) {
            ItemStackRenderState itemStackRenderState = new ItemStackRenderState();
            ItemStack itemStack = blockEntity.getItems().get(index);
            if (!itemStack.isEmpty())
                this.itemModelResolver.updateForTopItem(itemStackRenderState, itemStack, ItemDisplayContext.FIXED, blockEntity.getLevel(), null, posLong + index);
            blockEntityRenderState.items.add(itemStackRenderState);
        }
    }

    @Override
    public SteamerBlockEntityRenderState createRenderState() {
        return new SteamerBlockEntityRenderState();
    }

    @Override
    public void submit(SteamerBlockEntityRenderState blockEntityRenderState, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return;
        }
        // 盖住了就不渲染
        if (blockEntityRenderState.blockState.getValue(SteamerBlock.HAS_LID)) {
            return;
        }
        List<ItemStackRenderState> items = blockEntityRenderState.items;
        for (int i = 0; i < items.size(); i++) {
            ItemStackRenderState stack = items.get(i);
            if (stack.isEmpty()) {
                continue;
            }

            double x = (i % 2) * 0.3 + 0.35;
            double y = (i / 4) * 0.5 + 0.25 + (i % 4) * 0.01;
            double z = ((i / 2) % 2) * 0.3 + 0.35;
            poseStack.pushPose();
            poseStack.translate(x, y, z);
            poseStack.mulPose(Axis.XN.rotationDegrees(90));

            poseStack.scale(0.5F, 0.5F, 0.5F);
            stack.submit(poseStack, submitNodeCollector, blockEntityRenderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
            poseStack.popPose();
        }
    }
}
