package com.github.ysbbbbbb.kaleidoscopecookery.client.render.block;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.block.decoration.TableBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.decoration.TableBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.client.renderstates.TableBlockEntityRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.fabric.api.client.model.loading.v1.ExtraModelKey;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.function.BiFunction;

public class TableBlockEntityRender implements BlockEntityRenderer<TableBlockEntity, TableBlockEntityRenderState> {
    private final ItemModelResolver itemModelResolver;
    private static final BiFunction<DyeColor, Integer, Identifier> CACHE_MODEL = Util.memoize((color, position) -> {
        String name = color.getName();
        if (position == TableBlock.SINGLE) {
            return Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "block/carpet/table/" + name + "_single");
        }
        if (position == TableBlock.MIDDLE) {
            return Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "block/carpet/table/" + name + "_middle");
        }
        if (position == TableBlock.LEFT) {
            return Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "block/carpet/table/" + name + "_left");
        }
        return Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "block/carpet/table/" + name + "_right");
    });

    public TableBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }


    private void rotation(PoseStack poseStack, Direction.Axis axis) {
        if (axis == Direction.Axis.X) {
            poseStack.mulPose(Axis.YP.rotationDegrees(180));
        } else {
            poseStack.mulPose(Axis.YP.rotationDegrees(90));
        }
    }

    @Override
    public TableBlockEntityRenderState createRenderState() {
        return new TableBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(TableBlockEntity blockEntity, TableBlockEntityRenderState blockEntityRenderState, float f, @NonNull Vec3 vec3, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, blockEntityRenderState, f, vec3, crumblingOverlay);
        int posLong = (int) blockEntity.getBlockPos().asLong();
        blockEntityRenderState.items = NonNullList.withSize(blockEntity.getItems().size(), new ItemStackRenderState());
        for (var index = 0; index < blockEntityRenderState.items.size(); index++) {
            this.itemModelResolver.updateForTopItem(blockEntityRenderState.items.get(index), blockEntity.getItems().get(index), ItemDisplayContext.FIXED, blockEntity.getLevel(), null, posLong + index);
        }
        blockEntityRenderState.color = blockEntity.getColor();
    }

    @Override
    public void submit(TableBlockEntityRenderState blockEntityRenderState, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {
        BlockState blockState = blockEntityRenderState.blockState;
        Direction.Axis axis = blockState.getValue(TableBlock.AXIS);

        if (blockState.getValue(TableBlock.HAS_CARPET)) {
            int position = blockState.getValue(TableBlock.POSITION);
            Identifier cacheModel = CACHE_MODEL.apply(blockEntityRenderState.color, position);
            if (cacheModel == null) {
                return;
            }
            int rotation = axis == Direction.Axis.X ? 180 : 270;
            poseStack.pushPose();
            poseStack.translate(0.5, 0, 0.5);
            poseStack.mulPose(Axis.YP.rotationDegrees(-rotation));
            poseStack.translate(-0.5, 0, -0.5);
            RenderType renderType = RenderTypes.entityCutoutNoCull(TextureAtlas.LOCATION_BLOCKS);
            BlockStateModel model = Minecraft.getInstance().getModelManager().getModel(ExtraModelKey.create(cacheModel::toString));
            if (model != null)
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

        var items = blockEntityRenderState.items;

        int count = 0;
        for (ItemStackRenderState item : items) {
            if (item.isEmpty()) {
                continue;
            }
            count++;
        }

        if (count == 0) {
            return;
        }

        poseStack.pushPose();
        poseStack.translate(0.5, 1.3125, 0.5);
        poseStack.scale(0.65F, 0.65F, 0.65F);

        if (count == 1) {
            this.rotation(poseStack, axis);
            ItemStackRenderState stack1 = items.get(0);
            stack1.submit(
                    poseStack,
                    submitNodeCollector,
                    blockEntityRenderState.lightCoords,
                    OverlayTexture.NO_OVERLAY,
                    0
            );
        } else if (count == 2) {
            poseStack.pushPose();
            this.rotation(poseStack, axis);
            poseStack.translate(-0.25, 0, 0.1);
            ItemStackRenderState stack1 = items.get(0);
            stack1.submit(
                    poseStack,
                    submitNodeCollector,
                    blockEntityRenderState.lightCoords,
                    OverlayTexture.NO_OVERLAY,
                    0
            );
            poseStack.popPose();

            poseStack.pushPose();
            this.rotation(poseStack, axis);
            poseStack.translate(0.25, 0.01, -0.1);
            ItemStackRenderState stack2 = items.get(1);
            stack2.submit(
                   poseStack,
                   submitNodeCollector,
                   blockEntityRenderState.lightCoords,
                   OverlayTexture.NO_OVERLAY,
                   0
            );
            poseStack.popPose();
        } else if (count == 3) {
            poseStack.pushPose();
            this.rotation(poseStack, axis);
            poseStack.translate(0.25, 0, -0.2);
            ItemStackRenderState stack1 = items.get(0);
            stack1.submit(
                    poseStack,
                    submitNodeCollector,
                    blockEntityRenderState.lightCoords,
                    OverlayTexture.NO_OVERLAY,
                    0
            );
            poseStack.popPose();

            poseStack.pushPose();
            this.rotation(poseStack, axis);
            poseStack.translate(-0.25, 0.01, 0);
            ItemStackRenderState stack2 = items.get(1);
            stack2.submit(
                    poseStack,
                    submitNodeCollector,
                    blockEntityRenderState.lightCoords,
                    OverlayTexture.NO_OVERLAY,
                    0
            );
            poseStack.popPose();

            poseStack.pushPose();
            this.rotation(poseStack, axis);
            poseStack.translate(0.24, 0.02, 0.2);
            ItemStackRenderState stack3 = items.get(2);
            stack3.submit(
                    poseStack,
                    submitNodeCollector,
                    blockEntityRenderState.lightCoords,
                    OverlayTexture.NO_OVERLAY,
                    0
            );
            poseStack.popPose();
        } else {
            poseStack.pushPose();
            this.rotation(poseStack, axis);
            poseStack.translate(0.25, 0, -0.3);
            ItemStackRenderState stack1 = items.get(0);
            stack1.submit(
                    poseStack,
                    submitNodeCollector,
                    blockEntityRenderState.lightCoords,
                    OverlayTexture.NO_OVERLAY,
                    0
            );
            poseStack.popPose();

            poseStack.pushPose();
            this.rotation(poseStack, axis);
            poseStack.translate(-0.24, 0.01, -0.1);
            ItemStackRenderState stack2 = items.get(1);
            stack2.submit(
                    poseStack,
                    submitNodeCollector,
                    blockEntityRenderState.lightCoords,
                    OverlayTexture.NO_OVERLAY,
                    0
            );
            poseStack.popPose();

            poseStack.pushPose();
            this.rotation(poseStack, axis);
            poseStack.translate(0.24, 0.02, 0.1);
            ItemStackRenderState stack3 = items.get(2);
            stack3.submit(
                    poseStack,
                    submitNodeCollector,
                    blockEntityRenderState.lightCoords,
                    OverlayTexture.NO_OVERLAY,
                    0
            );
            poseStack.popPose();

            poseStack.pushPose();
            this.rotation(poseStack, axis);
            poseStack.translate(-0.25, 0.03, 0.3);
            ItemStackRenderState stack4 = items.get(3);
            stack4.submit(
                    poseStack,
                    submitNodeCollector,
                    blockEntityRenderState.lightCoords,
                    OverlayTexture.NO_OVERLAY,
                    0
            );
            poseStack.popPose();
        }

        poseStack.popPose();
    }
}
