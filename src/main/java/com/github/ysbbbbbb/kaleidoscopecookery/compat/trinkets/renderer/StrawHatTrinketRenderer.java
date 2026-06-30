package com.github.ysbbbbbb.kaleidoscopecookery.compat.trinkets.renderer;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.client.model.StrawHatModel;
import com.github.ysbbbbbb.kaleidoscopecookery.item.StrawHatItem;
import com.mojang.blaze3d.vertex.PoseStack;
import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.client.TrinketRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public class StrawHatTrinketRenderer implements TrinketRenderer {
    private final boolean hasFlower;
    private StrawHatModel cachedModel = null;
    public StrawHatTrinketRenderer(boolean hasFlower) {
        super();
        this.hasFlower = hasFlower;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void submit(ItemStack itemStack, TrinketSlotAccess slotReference, EntityModel<? extends LivingEntityRenderState> contextModel, PoseStack poseStack, SubmitNodeCollector submit, int light, LivingEntityRenderState state, float limbAngle, float limbDistance) {
        if (!(contextModel instanceof HumanoidModel<?> humanoidModel)) return;
        if (!(state instanceof HumanoidRenderState humanoidRenderState)) return;
        if (!(itemStack.getItem() instanceof StrawHatItem)) return;
        if (cachedModel == null) {
            cachedModel = new StrawHatModel(StrawHatModel.createBodyLayer().bakeRoot());
        }
        ModelPart head = cachedModel.getHead();
        head.loadPose(humanoidModel.head.storePose());
        poseStack.pushPose();
        var texture = hasFlower ?
                Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "textures/models/armor/straw_hat_flower.png")
                :
                Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "textures/models/armor/straw_hat.png");
        poseStack.scale(1.275f, 1.275f, 1.275f);
        poseStack.translate(0, -0.025f, 0);
        ArmorRenderer.submitTransformCopyingModel(
                (HumanoidModel<HumanoidRenderState>) humanoidModel,
                humanoidRenderState,
                cachedModel,
                new HumanoidRenderState(),
                false,
                submit,
                poseStack,
                RenderTypes.entityCutout(texture),
                state.lightCoords,
                OverlayTexture.NO_OVERLAY,
                0
        );
        poseStack.popPose();
    }
}
