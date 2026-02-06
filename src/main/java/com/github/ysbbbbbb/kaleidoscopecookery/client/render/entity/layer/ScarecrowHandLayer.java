package com.github.ysbbbbbb.kaleidoscopecookery.client.render.entity.layer;

import com.github.ysbbbbbb.kaleidoscopecookery.client.model.ScarecrowModel;
import com.github.ysbbbbbb.kaleidoscopecookery.client.render.entity.ScarecrowRender;
import com.github.ysbbbbbb.kaleidoscopecookery.client.renderstates.ScarecrowEntityRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class ScarecrowHandLayer extends ItemInHandLayer<ScarecrowEntityRenderState, ScarecrowModel> {
    private final BlockRenderDispatcher blockRenderer;

    public ScarecrowHandLayer(ScarecrowRender entityRenderer, BlockRenderDispatcher blockRenderDispatcher) {
        super(entityRenderer);
        this.blockRenderer = blockRenderDispatcher;
    }

    @Override
    protected void submitArmWithItem(
            ScarecrowEntityRenderState armedEntityRenderState,
            @NonNull ItemStackRenderState itemStackRenderState,
            @NonNull ItemStack stack,
            @NonNull HumanoidArm arm,
            @NonNull PoseStack poseStack,
            @NonNull SubmitNodeCollector submitNodeCollector,
            int i
    ) {
        if (!(armedEntityRenderState.leftHandItemStack.isEmpty() && armedEntityRenderState.rightHandItemStack.isEmpty())) {
            poseStack.pushPose();
            this.getParentModel().translateToHand(armedEntityRenderState, arm, poseStack);
            poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
            boolean isLeft = arm == HumanoidArm.LEFT;
            if (isLeft) {
                if (stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof LanternBlock lanternBlock) {
                    poseStack.translate(-1.865, 0.375, -2);
                    poseStack.mulPose(Axis.XP.rotationDegrees(90));
                    BlockState blockState = lanternBlock.defaultBlockState();
                    BlockStateModel blockStateModel = this.blockRenderer.getBlockModel(blockState);
                    poseStack.scale(0.75F, 0.75F, 0.75F);
                    submitNodeCollector.submitBlockModel(
                            poseStack,
                            ItemBlockRenderTypes.getRenderType(blockState),
                            blockStateModel,
                            1.0F,
                            1.0F,
                            1.0F, 15728880,
                            OverlayTexture.NO_OVERLAY,
                            0
                    );
                }
            } else {
                poseStack.translate(0.125, 0, -1.375);
                poseStack.mulPose(Axis.ZP.rotationDegrees(-90));
                poseStack.mulPose(Axis.XP.rotationDegrees(85));
                poseStack.scale(0.75F, 0.75F, 0.75F);
                itemStackRenderState.submit(
                        poseStack,
                        submitNodeCollector,
                        armedEntityRenderState.lightCoords,
                        OverlayTexture.NO_OVERLAY,
                        0
                );
            }
            poseStack.popPose();
        }
    }
}
