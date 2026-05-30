package com.github.ysbbbbbb.kaleidoscopecookery;

import com.github.ysbbbbbb.kaleidoscopecookery.client.init.*;
import com.github.ysbbbbbb.kaleidoscopecookery.client.init.registry.ClientRegistry;
import com.github.ysbbbbbb.kaleidoscopecookery.config.ClientConfig;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.fabric.impl.resource.loader.FabricResource;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.config.ModConfig;

import static com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery.MOD_ID;

@Environment(EnvType.CLIENT)
public final class KaleidoscopeCookeryClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        NeoForgeConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.CLIENT, ClientConfig.init());
        ClientRegistry.init();
        ModModelLoading.register();
        ModClientTooltip.register();
        ModEntitiesRender.register();
        ModParticleFactoryRegistry.register();
        ModBlockRenderLayerMap.register();

        FabricLoader
                .getInstance()
                .getModContainer(MOD_ID)
                .ifPresent(container -> ResourceManagerHelper.registerBuiltinResourcePack(
                        ResourceLocation.withDefaultNamespace("kaleidoscope_classic_texture"),
                        container,
                        ResourcePackActivationType.NORMAL
                ));
    }
}
