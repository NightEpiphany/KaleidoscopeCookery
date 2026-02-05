package com.github.ysbbbbbb.kaleidoscopecookery;

import com.github.ysbbbbbb.kaleidoscopecookery.client.init.*;
import net.fabricmc.api.ClientModInitializer;

public class KaleidoscopeCookeryClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModModelLoading.register();
        ClientRegistry.init();
        ModClientTooltip.register();
        ModEntitiesRender.register();
        ModParticleFactoryRegistry.register();
        ModBlockRenderLayerMap.register();
    }
}
