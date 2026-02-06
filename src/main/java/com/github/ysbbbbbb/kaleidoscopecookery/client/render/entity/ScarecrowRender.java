package com.github.ysbbbbbb.kaleidoscopecookery.client.render.entity;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.client.model.ScarecrowModel;
import com.github.ysbbbbbb.kaleidoscopecookery.client.render.entity.layer.ScarecrowHandLayer;
import com.github.ysbbbbbb.kaleidoscopecookery.client.render.entity.layer.ScarecrowParrotOnShoulderLayer;
import com.github.ysbbbbbb.kaleidoscopecookery.client.renderstates.ScarecrowEntityRenderState;
import com.github.ysbbbbbb.kaleidoscopecookery.entity.ScarecrowEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class ScarecrowRender extends LivingEntityRenderer<ScarecrowEntity, ScarecrowEntityRenderState, ScarecrowModel> {

    public static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "textures/entity/scarecrow.png");

    private final ItemModelResolver itemModelResolver;

    public ScarecrowRender(EntityRendererProvider.Context context) {
        super(context, new ScarecrowModel(context.bakeLayer(ScarecrowModel.LAYER_LOCATION)), 0);
        this.itemModelResolver = context.getItemModelResolver();
        this.addLayer(new ScarecrowHandLayer(this, context.getBlockRenderDispatcher()));
        this.addLayer(new CustomHeadLayer<>(this, context.getModelSet(), Minecraft.getInstance().playerSkinRenderCache()));
        this.addLayer(new ScarecrowParrotOnShoulderLayer(this, context.getModelSet()));
    }

    @Override
    public void extractRenderState(ScarecrowEntity livingEntity, ScarecrowEntityRenderState livingEntityRenderState, float f) {
        super.extractRenderState(livingEntity, livingEntityRenderState, f);
        livingEntityRenderState.headItem = livingEntity.getItemBySlot(EquipmentSlot.HEAD);
        livingEntityRenderState.entityOnShoulder = livingEntity.getShoulderEntity();
        livingEntityRenderState.partialTicks = f;
        livingEntityRenderState.lastHit = livingEntity.lastHit;
        livingEntityRenderState.leftHandItemStack = livingEntity.getItemBySlot(EquipmentSlot.OFFHAND);
        livingEntityRenderState.rightHandItemStack = livingEntity.getItemBySlot(EquipmentSlot.MAINHAND);
        this.itemModelResolver.updateForLiving(livingEntityRenderState.leftHandItemState, livingEntityRenderState.leftHandItemStack, ItemDisplayContext.THIRD_PERSON_LEFT_HAND, livingEntity);
        this.itemModelResolver.updateForLiving(livingEntityRenderState.rightHandItemState, livingEntityRenderState.rightHandItemStack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, livingEntity);
        livingEntityRenderState.time = livingEntity.level().getGameTime();
    }

    @Override
    protected void setupRotations(ScarecrowEntityRenderState livingEntityRenderState, PoseStack poseStack, float f, float g) {
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - f));
        float time = (float) (livingEntityRenderState.time - livingEntityRenderState.lastHit) + livingEntityRenderState.partialTicks;
        if (time < 5.0F) {
            poseStack.mulPose(Axis.YP.rotationDegrees(Mth.sin(time / 1.5F * Mth.PI) * 3.0F));
        }
    }

    @Override
    public ScarecrowEntityRenderState createRenderState() {
        return new ScarecrowEntityRenderState();
    }


    @Override
    public @NonNull Identifier getTextureLocation(ScarecrowEntityRenderState livingEntityRenderState) {
        return TEXTURE;
    }
}
