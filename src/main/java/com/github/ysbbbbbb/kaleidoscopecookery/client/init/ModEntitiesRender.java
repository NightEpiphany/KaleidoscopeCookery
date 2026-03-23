package com.github.ysbbbbbb.kaleidoscopecookery.client.init;

import com.github.ysbbbbbb.kaleidoscopecookery.client.model.ColdCutHamSlicesModel;
import com.github.ysbbbbbb.kaleidoscopecookery.client.model.ScarecrowModel;
import com.github.ysbbbbbb.kaleidoscopecookery.client.model.StrawHatModel;
import com.github.ysbbbbbb.kaleidoscopecookery.client.render.entity.ScarecrowRender;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

@Environment(EnvType.CLIENT)
public class ModEntitiesRender {
    public static void register() {
        // 注册实体渲染器
        EntityRenderers.register(ModEntities.SIT, NoopRenderer::new);
        EntityRenderers.register(ModEntities.SCARECROW, ScarecrowRender::new);
        EntityRenderers.register(ModEntities.THROWABLE_BAOZI, ThrownItemRenderer::new);

        // 注册模型层定义
        ModelLayerRegistry.registerModelLayer(ScarecrowModel.LAYER_LOCATION, ScarecrowModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(StrawHatModel.LAYER_LOCATION, StrawHatModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(ColdCutHamSlicesModel.LAYER_LOCATION, ColdCutHamSlicesModel::createBodyLayer);
    }
}
