package com.github.ysbbbbbb.kaleidoscopecookery.client.resources;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackCompatibility;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraft.world.flag.FeatureFlagSet;

import java.util.Collections;
import java.util.Optional;
import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class LegacyPackRepositorySource implements RepositorySource {
    private static final String LEGACY_PACK_DIR_NAME = "kaleidoscope_classic_texture";
    private static final String PACK_NAME = "kaleidoscope_cookery_legacy_resources_pack";
    private final Pack legacyPack;

    public LegacyPackRepositorySource() {
        Pack.ResourcesSupplier supplier = getLegacyPack();
        MutableComponent title = Component.translatable("pack.kaleidoscope_cookery.legacy_resources_pack.title");
        MutableComponent desc = Component.translatable("pack.kaleidoscope_cookery.legacy_resources_pack.desc");
        PackLocationInfo info = new PackLocationInfo(PACK_NAME, title, PackSource.BUILT_IN, Optional.empty());
        Pack.Metadata metadata = new Pack.Metadata(desc, PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), Collections.emptyList());
        PackSelectionConfig config = new PackSelectionConfig(false, Pack.Position.TOP, false);
        this.legacyPack = new Pack(info, supplier, metadata, config);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private PathPackResources.PathResourcesSupplier getLegacyPack() {
        return new PathPackResources.PathResourcesSupplier(
                FabricLoader.getInstance()
                        .getModContainer(KaleidoscopeCookery.MOD_ID)
                        .get().findPath("resourcepacks/" + LEGACY_PACK_DIR_NAME).get()
        );
    }

    @Override
    public void loadPacks(Consumer<Pack> consumer) {
        consumer.accept(this.legacyPack);
    }
}
