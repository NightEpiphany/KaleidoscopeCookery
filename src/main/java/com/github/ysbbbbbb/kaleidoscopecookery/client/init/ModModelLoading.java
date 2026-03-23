package com.github.ysbbbbbb.kaleidoscopecookery.client.init;

import com.github.ysbbbbbb.kaleidoscopecookery.util.ExtraBlockModelLoadingUtil;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ReloadableResourceManager;

@Environment(EnvType.CLIENT)
public class ModModelLoading {
    private static final String MODELS = "models/";
    private static final String MODELS_CHOPPING_BOARD = MODELS + "chopping_board";
    private static final String MODELS_CARPET = MODELS + "block/carpet";
    private static final String JSON = ".json";
    private static final Set<Identifier> REGISTERED_MODELS = ConcurrentHashMap.newKeySet();

    public static void register() {
        REGISTERED_MODELS.clear();
        ModModelKeys.clear();
        registerFromPath(MODELS_CHOPPING_BOARD);
        registerFromPath(MODELS_CARPET);
        ExtraBlockModelLoadingUtil.clearCache();
    }

    public static BlockModel getModel(Identifier modelId) {
        return ExtraBlockModelLoadingUtil.getBlockModel(ModModelKeys.get(modelId));
    }

    public static boolean isRegistered(Identifier modelId) {
        return REGISTERED_MODELS.contains(modelId);
    }

    private static void registerFromPath(String path) {
        try (ReloadableResourceManager resourceManager = new ReloadableResourceManager(PackType.CLIENT_RESOURCES)) {
            resourceManager.listResources(path, id -> id.getPath().endsWith(JSON))
                    .keySet()
                    .stream()
                    .map(ModModelLoading::handleModelId)
                    .forEach(ModModelLoading::registerSingleModel);
        }
    }

    private static void registerSingleModel(Identifier modelId) {
        Identifier key = ModModelKeys.getOrCreate(modelId);
        REGISTERED_MODELS.add(key);
    }

    public static Identifier handleModelId(Identifier input) {
        String namespace = input.getNamespace();
        String path = input.getPath();
        return Identifier.fromNamespaceAndPath(namespace, path.substring(MODELS.length(), path.length() - JSON.length()));
    }
}
