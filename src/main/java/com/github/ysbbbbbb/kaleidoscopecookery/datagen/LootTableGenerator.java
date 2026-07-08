package com.github.ysbbbbbb.kaleidoscopecookery.datagen;

import com.github.ysbbbbbb.kaleidoscopecookery.datagen.lootable.BlockLootTables;
import com.github.ysbbbbbb.kaleidoscopecookery.datagen.lootable.ChestLootTables;
import com.github.ysbbbbbb.kaleidoscopecookery.datagen.lootable.EntityLootTables;
import com.github.ysbbbbbb.kaleidoscopecookery.datagen.lootable.GiftLootTables;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class LootTableGenerator extends LootTableProvider {
    public LootTableGenerator(FabricPackOutput pack, CompletableFuture<HolderLookup.Provider> registries)   {
        super(Set.of(), List.of(
                new SubProviderEntry(BlockLootTables::new, LootContextParamSets.BLOCK),
                new SubProviderEntry(EntityLootTables::new, LootContextParamSets.ENTITY),
                new SubProviderEntry(ChestLootTables::new, LootContextParamSets.CHEST),
                new SubProviderEntry(GiftLootTables::new, LootContextParamSets.GIFT)
        ));
    }
}
