package com.github.ysbbbbbb.kaleidoscopecookery;

import com.github.ysbbbbbb.kaleidoscopecookery.client.init.*;
import com.github.ysbbbbbb.kaleidoscopecookery.client.init.registry.ClientRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.pack.PackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class KaleidoscopeCookeryClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientRegistry.init();
        ModClientTooltip.register();
        ModEntitiesRender.register();
        ModParticleFactoryRegistry.register();
        ModBlockRenderLayerMap.register();

        FabricLoader
                .getInstance()
                .getModContainer(KaleidoscopeCookery.MOD_ID)
                .ifPresent(container ->
                        ResourceLoader.registerBuiltinPack(
                                Identifier.withDefaultNamespace("kaleidoscope_eating_animation"),
                                container,
                                Component.translatable("resourcePack.kaleidoscope_eating_animation"),
                                PackActivationType.NORMAL
                        ));
    }
}
