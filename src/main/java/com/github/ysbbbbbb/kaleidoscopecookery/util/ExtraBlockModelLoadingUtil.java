package com.github.ysbbbbbb.kaleidoscopecookery.util;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.dispatch.BlockModelRotation;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.block.dispatch.SingleVariant;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.BlockStateModelWrapper;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelDebugName;
import net.minecraft.client.resources.model.ModelDiscovery;
import net.minecraft.client.resources.model.ResolvedModel;
import net.minecraft.client.resources.model.SimpleModelWrapper;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.client.resources.model.cuboid.CuboidModel;
import net.minecraft.client.resources.model.cuboid.MissingCuboidModel;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.client.resources.model.sprite.MaterialBaker;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3fc;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class ExtraBlockModelLoadingUtil {
    public static void preLoad() {}
    public static final ReloadableResourceManager RESOURCE_MANAGER = new ReloadableResourceManager(PackType.CLIENT_RESOURCES);
    private static final FileToIdConverter MODEL_LISTER = FileToIdConverter.json("models");
    private static final Matrix4fc IDENTITY = new Matrix4f();
    private static final Map<Identifier, BlockModel> MODEL_CACHE = new ConcurrentHashMap<>();
    private static volatile Object cacheGeneration;
    private static final ModelBaker.Interner NO_OP_INTERNER = new ModelBaker.Interner() {
        @Override
        public @NonNull Vector3fc vector(@NonNull Vector3fc vector) {
            return vector;
        }

        @Override
        public BakedQuad.@NonNull MaterialInfo materialInfo(BakedQuad.@NonNull MaterialInfo material) {
            return material;
        }
    };

    private ExtraBlockModelLoadingUtil() {}

    public static BlockModel getBlockModel(Identifier modelId) {
        Minecraft minecraft = Minecraft.getInstance();
        ModelManager modelManager = minecraft.getModelManager();
        refreshCacheGeneration(modelManager);
        if (modelId == null) {
            return createMissingBlockModel(modelManager);
        }
        return MODEL_CACHE.computeIfAbsent(modelId, id -> bakeBlockModel(modelManager, id));
    }

    public static void clearCache() {
        MODEL_CACHE.clear();
    }

    private static void refreshCacheGeneration(ModelManager modelManager) {
        Object currentGeneration = modelManager.getBlockStateModelSet();
        if (cacheGeneration != currentGeneration) {
            synchronized (ExtraBlockModelLoadingUtil.class) {
                if (cacheGeneration != currentGeneration) {
                    MODEL_CACHE.clear();
                    cacheGeneration = currentGeneration;
                }
            }
        }
    }

    private static BlockModel bakeBlockModel(ModelManager modelManager, Identifier modelId) {
        try {
            Map<Identifier, UnbakedModel> unbakedModels = new HashMap<>();
            loadUnbakedModel(RESOURCE_MANAGER, modelId, unbakedModels, new HashSet<>());
            if (!unbakedModels.containsKey(modelId)) {
                return createMissingBlockModel(modelManager);
            }
            ModelDiscovery discovery = new ModelDiscovery(unbakedModels, MissingCuboidModel.missingModel());
            discovery.addRoot(new SingleVariant.Unbaked(new Variant(modelId)));
            Map<Identifier, ResolvedModel> resolvedModels = discovery.resolve();
            RuntimeModelBaker baker = new RuntimeModelBaker(modelManager, resolvedModels, discovery.missingModel());
            BlockStateModelPart modelPart = SimpleModelWrapper.bake(baker, modelId, BlockModelRotation.IDENTITY);
            BlockStateModel stateModel = new SingleVariant(modelPart);
            return new BlockStateModelWrapper(stateModel, List.of(), IDENTITY);
        } catch (Exception ignored) {
            return createMissingBlockModel(modelManager);
        }
    }

    private static void loadUnbakedModel(
        ResourceManager resourceManager,
        Identifier modelId,
        Map<Identifier, UnbakedModel> loadedModels,
        Set<Identifier> visiting
    ) throws IOException {
        if (loadedModels.containsKey(modelId) || !visiting.add(modelId)) {
            return;
        }
        try {
            Identifier modelFileId = MODEL_LISTER.idToFile(modelId);
            Optional<Resource> resource = resourceManager.getResource(modelFileId);
            if (resource.isEmpty()) {
                return;
            }
            UnbakedModel model;
            try (Reader reader = resource.get().openAsReader()) {
                model = CuboidModel.fromStream(reader);
            }
            loadedModels.put(modelId, model);
            Identifier parentId = model.parent();
            if (parentId != null && !loadedModels.containsKey(parentId)) {
                loadUnbakedModel(resourceManager, parentId, loadedModels, visiting);
            }
        } finally {
            visiting.remove(modelId);
        }
    }

    private static BlockModel createMissingBlockModel(ModelManager modelManager) {
        return new BlockStateModelWrapper(modelManager.getBlockStateModelSet().missingModel(), List.of(), IDENTITY);
    }

    private static final class RuntimeModelBaker implements ModelBaker {
        private final Map<Identifier, ResolvedModel> resolvedModels;
        private final ResolvedModel missingModel;
        private final MaterialBaker materialBaker;
        private final Map<ModelBaker.SharedOperationKey<Object>, Object> operationCache = new ConcurrentHashMap<>();
        private final BlockStateModelPart missingModelPart;

        private RuntimeModelBaker(ModelManager modelManager, Map<Identifier, ResolvedModel> resolvedModels, ResolvedModel missingModel) {
            this.resolvedModels = resolvedModels;
            this.missingModel = missingModel;
            this.materialBaker = new RuntimeMaterialBaker(modelManager);
            this.missingModelPart = SimpleModelWrapper.bake(this, MissingCuboidModel.LOCATION, BlockModelRotation.IDENTITY);
        }

        @Override
        public @NonNull ResolvedModel getModel(@NonNull Identifier location) {
            return this.resolvedModels.getOrDefault(location, this.missingModel);
        }

        @Override
        public @NonNull BlockStateModelPart missingBlockModelPart() {
            return this.missingModelPart;
        }

        @Override
        public @NonNull MaterialBaker materials() {
            return this.materialBaker;
        }

        @Override
        public ModelBaker.@NonNull Interner interner() {
            return NO_OP_INTERNER;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T compute(ModelBaker.@NonNull SharedOperationKey<T> key) {
            return (T) this.operationCache.computeIfAbsent((ModelBaker.SharedOperationKey<Object>) key, k -> k.compute(this));
        }
    }

    private static final class RuntimeMaterialBaker implements MaterialBaker {
        private final TextureAtlas blockAtlas;

        @SuppressWarnings("deprecation")
        private RuntimeMaterialBaker(ModelManager modelManager) {
            this.blockAtlas = modelManager.atlasManager.getAtlasOrThrow(TextureAtlas.LOCATION_BLOCKS);
        }

        @Override
        public Material.@NonNull Baked get(Material material, @NonNull ModelDebugName name) {
            return new Material.Baked(resolveSprite(material.sprite()), material.forceTranslucent());
        }

        @Override
        public Material.@NonNull Baked reportMissingReference(@NonNull String reference, @NonNull ModelDebugName name) {
            return new Material.Baked(this.blockAtlas.missingSprite(), false);
        }

        private TextureAtlasSprite resolveSprite(Identifier spriteId) {
            return this.blockAtlas.getSprite(spriteId);
        }
    }
}
