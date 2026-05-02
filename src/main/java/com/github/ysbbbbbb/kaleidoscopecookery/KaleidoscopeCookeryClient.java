package com.github.ysbbbbbb.kaleidoscopecookery;

import com.github.ysbbbbbb.kaleidoscopecookery.client.init.*;
import com.github.ysbbbbbb.kaleidoscopecookery.client.init.registry.ClientRegistry;
import com.github.ysbbbbbb.kaleidoscopecookery.config.ClientConfig;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraftforge.fml.config.ModConfig;

import static com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery.MOD_ID;

@Environment(EnvType.CLIENT)
public final class KaleidoscopeCookeryClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ForgeConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.CLIENT, ClientConfig.init());
        ClientRegistry.init();
        ModModelLoading.register();
        ModClientTooltip.register();
        ModEntitiesRender.register();
        ModParticleFactoryRegistry.register();
        ModBlockRenderLayerMap.register();
    }
}
