package com.github.ysbbbbbb.kaleidoscopecookery.client.init;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.model.loading.v1.SimpleUnbakedExtraModel;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;

@Environment(EnvType.CLIENT)
public final class ModModelLoading {
    private static final String MODELS = "models/";
    private static final String MODELS_CHOPPING_BOARD = MODELS + "chopping_board";
    private static final String MODELS_CARPET = MODELS + "block/carpet";
    private static final String JSON = ".json";

    public static void register() {
        ModelLoadingPlugin.register(context -> {
            ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();

            resourceManager.listResources(MODELS_CHOPPING_BOARD, id -> id.getPath().endsWith(JSON))
                    .keySet().stream().map(ModModelLoading::handleModelId).forEach(ids -> context.addModel(ModModelKeys.getOrCreate(ids), SimpleUnbakedExtraModel.blockStateModel(ids)));

            resourceManager.listResources(MODELS_CARPET, id -> id.getPath().endsWith(JSON))
                    .keySet().stream().map(ModModelLoading::handleModelId).forEach(ids -> context.addModel(ModModelKeys.getOrCreate(ids), SimpleUnbakedExtraModel.blockStateModel(ids)));
        });
    }

    public static Identifier handleModelId(Identifier input) {
        String namespace = input.getNamespace();
        String path = input.getPath();
        return Identifier.fromNamespaceAndPath(namespace, path.substring(MODELS.length(), path.length() - JSON.length()));
    }
}
