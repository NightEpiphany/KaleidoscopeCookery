package com.github.ysbbbbbb.kaleidoscopecookery.compat.trinkets.renderer;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.client.model.StrawHatModel;
import com.github.ysbbbbbb.kaleidoscopecookery.item.StrawHatItem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
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
    public void render(ItemStack itemStack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel, PoseStack poseStack, MultiBufferSource vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (!(contextModel instanceof HumanoidModel<?> humanoidModel)) return;
        if (!(itemStack.getItem() instanceof StrawHatItem)) return;
        if (cachedModel == null) {
            cachedModel = new StrawHatModel(StrawHatModel.createBodyLayer().bakeRoot());
        }
        ModelPart head = cachedModel.getHead();
        head.copyFrom(humanoidModel.head);
        poseStack.pushPose();
        var texture = hasFlower ?
                                new ResourceLocation(KaleidoscopeCookery.MOD_ID, "textures/models/armor/straw_hat_flower.png")
                               :
                                new ResourceLocation(KaleidoscopeCookery.MOD_ID, "textures/models/armor/straw_hat.png");
        ArmorRenderer.renderPart(poseStack, vertexConsumers, light, itemStack, cachedModel, texture);
        poseStack.popPose();
    }
}
