package com.github.ysbbbbbb.kaleidoscopecookery.client.render.item;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.client.model.StrawHatModel;
import com.github.ysbbbbbb.kaleidoscopecookery.item.StrawHatItem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class StrawHatArmorRenderer implements ArmorRenderer {
    private static final Identifier NORMAL = Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "textures/models/armor/straw_hat.png");
    private static final Identifier FLOWER = Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "textures/models/armor/straw_hat_flower.png");
    private StrawHatModel cachedModel = null;


    @Override
    public void render(@NonNull PoseStack matrices, @NonNull SubmitNodeCollector orderedRenderCommandQueue, @NonNull ItemStack stack, @NonNull HumanoidRenderState bipedEntityRenderState, @NonNull EquipmentSlot slot, int light, @NonNull HumanoidModel<HumanoidRenderState> modelPart) {
        if (cachedModel == null) {
            cachedModel = new StrawHatModel(Minecraft.getInstance().getEntityModels().bakeLayer(StrawHatModel.LAYER_LOCATION));
        }
        matrices.pushPose();
        matrices.scale(1.275f, 1.275f, 1.275f);
        Identifier texture = getArmorTexture(stack);
        ArmorRenderer.submitTransformCopyingModel(
                modelPart,
                bipedEntityRenderState,
                cachedModel,
                new HumanoidRenderState(),
                false,
                orderedRenderCommandQueue,
                matrices,
                RenderTypes.entityCutoutNoCull(texture),
                bipedEntityRenderState.lightCoords,
                OverlayTexture.NO_OVERLAY,
                0,
                null
        );
        matrices.popPose();
    }

    public Identifier getArmorTexture(ItemStack stack) {
        if (stack.getItem() instanceof StrawHatItem hatItem && hatItem.hasFlower()) {
            return FLOWER;
        }
        return NORMAL;
    }
}
