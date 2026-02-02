package com.github.ysbbbbbb.kaleidoscopecookery.client.render.block;

import com.github.ysbbbbbb.kaleidoscopecookery.api.client.render.ISoupBaseRender;
import com.github.ysbbbbbb.kaleidoscopecookery.api.recipe.soupbase.ISoupBase;
import com.github.ysbbbbbb.kaleidoscopecookery.block.kitchen.StockpotBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen.StockpotBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.client.renderstates.StockpotBlockEntityRenderState;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.soupbase.SoupBaseManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.function.Function;

public class StockpotBlockEntityRender implements BlockEntityRenderer<StockpotBlockEntity, StockpotBlockEntityRenderState> {
    private final Function<Identifier, ISoupBaseRender> soupBaseRender;
    private final ItemModelResolver itemModelResolver;
    private final EntityRenderDispatcher entityRenderDispatcher;

    public StockpotBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.soupBaseRender = Util.memoize(id -> {
            ISoupBase soupBase = SoupBaseManager.getSoupBase(id);
            if (soupBase != null) {
                return soupBase.getRender();
            }
            return null;
        });
        this.itemModelResolver = context.itemModelResolver();
        this.entityRenderDispatcher = context.entityRenderer();
    }

    @Override
    public StockpotBlockEntityRenderState createRenderState() {
        return new StockpotBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(StockpotBlockEntity blockEntity, StockpotBlockEntityRenderState blockEntityRenderState, float f, @NonNull Vec3 vec3, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, blockEntityRenderState, f, vec3, crumblingOverlay);
        int posLong = (int) blockEntity.getBlockPos().asLong();
        blockEntityRenderState.items = NonNullList.withSize(blockEntity.getInputs().size(), new ItemStackRenderState());
        for (var index = 0; index < blockEntityRenderState.items.size(); index++) {
            this.itemModelResolver.updateForTopItem(blockEntityRenderState.items.get(index), blockEntity.getInputs().get(index), ItemDisplayContext.FIXED, blockEntity.getLevel(), null, posLong + index);
        }
        if (blockEntity.renderEntity != null)
            blockEntityRenderState.renderEntity = this.entityRenderDispatcher.extractEntity(blockEntity.renderEntity, f);
        blockEntityRenderState.soupBaseID = blockEntity.getSoupBaseId();
        blockEntityRenderState.cookingTexture = blockEntity.recipe.value().cookingTexture();
        blockEntityRenderState.finishedTexture = blockEntity.recipe.value().finishedTexture();
        blockEntityRenderState.takeOutCount = blockEntity.getTakeoutCount();
        blockEntityRenderState.output = blockEntity.getResult();
    }

    @Override
    public void submit(StockpotBlockEntityRenderState blockEntityRenderState, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return;
        }
        if (blockEntityRenderState.blockState.getValue(StockpotBlock.HAS_LID)) {
            return;
        }
        int status = blockEntityRenderState.status;
        ISoupBaseRender soupBase = this.soupBaseRender.apply(blockEntityRenderState.soupBaseID);
        if (status == StockpotBlockEntity.PUT_INGREDIENT) {
            soupBase.renderWhenPutIngredient(blockEntityRenderState, 0, poseStack, submitNodeCollector, blockEntityRenderState.lightCoords, OverlayTexture.NO_OVERLAY, 0.38f, cameraRenderState);
            renderItems(blockEntityRenderState, poseStack, submitNodeCollector);
        } else if (status == StockpotBlockEntity.COOKING) {
            soupBase.renderWhenCooking(blockEntityRenderState, 0, poseStack, submitNodeCollector, blockEntityRenderState.lightCoords, OverlayTexture.NO_OVERLAY, blockEntityRenderState.cookingTexture, 0.38f, cameraRenderState);
            renderItems(blockEntityRenderState, poseStack, submitNodeCollector);
        } else if (status == StockpotBlockEntity.FINISHED) {
            int takeoutCount = blockEntityRenderState.takeOutCount;
            int maxCount = Math.min(blockEntityRenderState.output.getCount(), StockpotBlockEntity.MAX_TAKEOUT_COUNT);
            float soupHeight = 0.065f + 0.315f / maxCount * takeoutCount;
            soupBase.renderWhenFinished(blockEntityRenderState, 0, poseStack, submitNodeCollector, blockEntityRenderState.lightCoords, OverlayTexture.NO_OVERLAY, blockEntityRenderState.finishedTexture, soupHeight, cameraRenderState);
        }
    }

    private void renderItems(StockpotBlockEntityRenderState blockEntityRenderState, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector) {
        blockEntityRenderState.items.forEach(itemStackRenderState -> {
            if (!itemStackRenderState.isEmpty()) {
                int random = itemStackRenderState.hashCode();
                long time = random + System.currentTimeMillis();
                float offsetX = (random % 100) * 0.002f;
                float offsetZ = (float) (Math.sin(time * 0.0005) * 0.2);
                float offsetY = random % 50 * 0.004f;
                float yRot = (random % 2 == 0 ? -1 : 1) * 20 + random % 10;

                poseStack.pushPose();
                poseStack.mulPose(Axis.XP.rotationDegrees(85 + random % 10));
                poseStack.scale(0.5f, 0.5f, 0.5f);
                poseStack.translate(0.9 + offsetX, 0.9 + offsetY, -0.5 + offsetZ);
                poseStack.mulPose(Axis.YP.rotationDegrees(yRot));
                poseStack.mulPose(Axis.ZP.rotationDegrees(random % 360));
                itemStackRenderState.submit(poseStack, submitNodeCollector, blockEntityRenderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
                poseStack.popPose();
            }
        });
    }
}
