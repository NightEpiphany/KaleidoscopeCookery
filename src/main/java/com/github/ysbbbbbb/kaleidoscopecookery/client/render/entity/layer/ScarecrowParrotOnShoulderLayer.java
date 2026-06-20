package com.github.ysbbbbbb.kaleidoscopecookery.client.render.entity.layer;

import com.github.ysbbbbbb.kaleidoscopecookery.client.model.ScarecrowModel;
import com.github.ysbbbbbb.kaleidoscopecookery.client.render.renderstate.ScarecrowEntityRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.animal.parrot.ParrotModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.ParrotRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.ParrotRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.animal.parrot.Parrot;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class ScarecrowParrotOnShoulderLayer extends RenderLayer<ScarecrowEntityRenderState, ScarecrowModel> {
    private final ParrotModel model;

    public ScarecrowParrotOnShoulderLayer(RenderLayerParent<ScarecrowEntityRenderState, ScarecrowModel> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.model = new ParrotModel(modelSet.bakeLayer(ModelLayers.PARROT));
    }



    @Override
    public void submit(@NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, int i, ScarecrowEntityRenderState entityRenderState, float f, float g) {
        if (entityRenderState.entityOnShoulder instanceof Parrot parrot) {
            poseStack.pushPose();
            poseStack.translate(0.325F, -1.675F, 0.0625F);
            Parrot.Variant variant = parrot.getVariant();
            RenderType renderType = this.model.renderType(ParrotRenderer.getVariantTexture(variant));
            poseStack.pushPose();
            poseStack.translate(0.4F, -0.01F, 0.0F);
            ParrotRenderState parrotRenderState = new ParrotRenderState();
            parrotRenderState.pose = ParrotModel.Pose.ON_SHOULDER;
            parrotRenderState.ageInTicks = entityRenderState.ageInTicks;
            parrotRenderState.variant = variant;
            parrotRenderState.walkAnimationPos = entityRenderState.walkAnimationPos;
            parrotRenderState.walkAnimationSpeed = entityRenderState.walkAnimationSpeed;
            parrotRenderState.yRot = f;
            parrotRenderState.xRot = g;
            submitNodeCollector.submitModel(
                    this.model,
                    parrotRenderState,
                    poseStack,
                    renderType,
                    i,
                    OverlayTexture.NO_OVERLAY,
                    0,
                    null
            );
            poseStack.popPose();
            poseStack.popPose();
        }
    }
}
