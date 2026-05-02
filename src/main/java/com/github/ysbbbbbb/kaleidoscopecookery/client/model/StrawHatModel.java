package com.github.ysbbbbbb.kaleidoscopecookery.client.model;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class StrawHatModel extends EntityModel<HumanoidRenderState> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "straw_hat"), "main");
    private final ModelPart head;

    public StrawHatModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition bone = head.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(-10, 17).addBox(-7.5F, -4.0F, -7.5F, 15.0F, 0.0F, 15.0F, new CubeDeformation(0.01F))
                .texOffs(30, 0).addBox(-4.0F, -8.0F, -4.5F, 8.0F, 4.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(0, 41).addBox(-4.0F, -7.25F, -4.5F, 8.0F, 4.0F, 9.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.3F, 0.5F, 0.0F, -0.0873F, 0.0F, -0.0873F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(@NonNull HumanoidRenderState object) {
        super.setupAnim(object);
    }


    public ModelPart getHead() {
        return head;
    }
}
