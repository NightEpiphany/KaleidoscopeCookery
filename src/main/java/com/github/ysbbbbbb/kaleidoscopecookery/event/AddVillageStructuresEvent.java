package com.github.ysbbbbbb.kaleidoscopecookery.event;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

import java.util.List;

public class AddVillageStructuresEvent {
    private static final ResourceKey<StructureProcessorList> CROP_REPLACE_PROCESSOR_LIST_KEY = ResourceKey.create(
            Registries.PROCESSOR_LIST, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "crop_replace"));

    private static final Identifier PLAINS = Identifier.parse("minecraft:village/plains/houses");
    private static final Identifier SNOWY = Identifier.parse("minecraft:village/snowy/houses");
    private static final Identifier SAVANNA = Identifier.parse("minecraft:village/savanna/houses");
    private static final Identifier DESERT = Identifier.parse("minecraft:village/desert/houses");
    private static final Identifier TAIGA = Identifier.parse("minecraft:village/taiga/houses");

    public static void register() {
        // 服务器启动时添加建筑，注意只需要在初始化时执行一遍。在服务端不能为此消耗额外内存。
        ServerLifecycleEvents.SERVER_STARTING.register(minecraftServer -> {
            var registryAccess = minecraftServer.registryAccess();

            addBuildingToPool(registryAccess, PLAINS, "village/houses/plains_kitchen", 4);
            addBuildingToPool(registryAccess, SNOWY, "village/houses/snowy_kitchen", 4);
            addBuildingToPool(registryAccess, SAVANNA, "village/houses/savanna_kitchen", 4);
            addBuildingToPool(registryAccess, DESERT, "village/houses/desert_kitchen", 4);
            addBuildingToPool(registryAccess, TAIGA, "village/houses/taiga_kitchen", 4);
        });
    }

    /**
     * 参考自：<a href="https://gist.github.com/TelepathicGrunt/4fdbc445ebcbcbeb43ac748f4b18f342">GitHub TelepathicGrunt</a>
     */
    public static void addBuildingToPool(RegistryAccess registryAccess, Identifier poolId, String structId, int weight) {

        try {
            var templatePools = registryAccess.lookup(Registries.TEMPLATE_POOL);
            if (templatePools.isEmpty()) {
                KaleidoscopeCookery.LOGGER.warn("Template pools registry is empty for pool: {}", poolId);
                return;
            }
            var processorLists = registryAccess.lookup(Registries.PROCESSOR_LIST);
            if (processorLists.isEmpty()) {
                KaleidoscopeCookery.LOGGER.warn("Processor lists registry is empty for pool: {}", poolId);
                return;
            }
            templatePools.flatMap(pools -> pools.get(poolId)).ifPresent(e -> {
                StructureTemplatePool pool = e.value();
                Holder<StructureProcessorList> holder = processorLists.get().getOrThrow(CROP_REPLACE_PROCESSOR_LIST_KEY);
                Identifier structLocation = Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, structId);
                SinglePoolElement piece = SinglePoolElement.legacy(structLocation.toString(), holder).apply(StructureTemplatePool.Projection.RIGID);

                // 添加到 templates 列表
                for (int i = 0; i < weight; i++) {
                    pool.templates.add(piece);
                }


                List<Pair<StructurePoolElement, Integer>> newRawTemplates = Lists.newArrayList(pool.rawTemplates);
                newRawTemplates.add(Pair.of(piece, weight));

                pool.rawTemplates = newRawTemplates;
            });


        } catch (Exception e) {
            KaleidoscopeCookery.LOGGER.error("Failed to add village structure to pool {}: {}", poolId, e.getMessage());
            throw new RuntimeException("Failed to add village structure", e);
        }
    }
}
