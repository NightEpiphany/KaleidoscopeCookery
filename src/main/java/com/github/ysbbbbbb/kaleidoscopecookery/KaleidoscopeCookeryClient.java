package com.github.ysbbbbbb.kaleidoscopecookery;

import com.github.ysbbbbbb.kaleidoscopecookery.client.init.*;
import com.github.ysbbbbbb.kaleidoscopecookery.client.init.registry.ClientRegistry;
import com.github.ysbbbbbb.kaleidoscopecookery.config.ClientConfig;
import com.github.ysbbbbbb.kaleidoscopecookery.config.ConfigGetter;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.pack.PackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import static com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery.MOD_ID;

@Environment(EnvType.CLIENT)
public final class KaleidoscopeCookeryClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        if (FabricLoader.getInstance().isModLoaded(ConfigGetter.ID)
                && FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT)
            ClientConfig.init();
        ClientRegistry.init();
        ModClientTooltip.register();
        ModEntitiesRender.register();
        ModParticleFactoryRegistry.register();

        FabricLoader
                .getInstance()
                .getModContainer(MOD_ID)
                .ifPresent(container -> {
                            ResourceLoader.registerBuiltinPack(
                                    Identifier.withDefaultNamespace("kaleidoscope_eating_animation"),
                                    container,
                                    Component.translatable("resourcePack.kaleidoscope_eating_animation"),
                                    PackActivationType.NORMAL
                            );
                            ResourceLoader.registerBuiltinPack(
                                    Identifier.withDefaultNamespace("kaleidoscope_classic_texture"),
                                    container,
                                    Component.translatable("resourcePack.kaleidoscope_classic_texture"),
                                    PackActivationType.NORMAL
                            );
                        }
                );
    }
}
