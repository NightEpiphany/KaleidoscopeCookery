package com.github.ysbbbbbb.kaleidoscopecookery.client.resources;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.JsonOps;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ItemRenderReplacerReloadListener implements SimpleSynchronousResourceReloadListener {
    public static final ItemRenderReplacer INSTANCE = new ItemRenderReplacer();
    private static final ResourceLocation FILE_PATH = new ResourceLocation(KaleidoscopeCookery.MOD_ID, "models/item_render_replacer.json");
    private static final ResourceLocation FILE_PATH2 = new ResourceLocation("farmersdelight", "models/item_render_replacer.json");
    private static final ResourceLocation ID = new ResourceLocation(KaleidoscopeCookery.MOD_ID, "item_render_replacer");

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        resourceManager.listPacks().forEach((packResources) -> {
            load(packResources, FILE_PATH);
            if (FabricLoader.getInstance().isModLoaded("farmersdelight")) {
                load(packResources, FILE_PATH2);
            }
        });
    }
    private void load(PackResources packResources, ResourceLocation filePath) {
        IoSupplier<InputStream> resource2 = packResources.getResource(PackType.CLIENT_RESOURCES, filePath);
        if (resource2 == null) {
            return;
        }
        try (InputStream inputStream = resource2.get();
             InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            JsonElement jsonElement = JsonParser.parseReader(reader);
            var result = ItemRenderReplacer.CODEC.parse(JsonOps.INSTANCE, jsonElement);
            if (result.result().isPresent()) {
                INSTANCE.addAll(result.result().get());
                KaleidoscopeCookery.LOGGER.info("Successfully loaded item render replacer data {}", filePath);
            } else if (result.error().isPresent()) {
                KaleidoscopeCookery.LOGGER.error("Failed to parse item render replacer data: {}", result.error().get().message());
            }
        } catch (Exception e) {
            KaleidoscopeCookery.LOGGER.error("Failed to load item render replacer resource", e);
        }
    }

    @Override
    public ResourceLocation getFabricId() {
        return ID;
    }
}
