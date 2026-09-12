package com.github.ysbbbbbb.kaleidoscopecookery.datagen.lootable;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopecookery.loot.AdvanceEntityMatchTool;
import com.google.common.collect.Sets;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;
import org.jspecify.annotations.NonNull;

import java.util.Set;
import java.util.function.BiConsumer;

public class EntityLootTables extends EntityLootSubProvider {
    public final Set<EntityType<?>> knownEntities = Sets.newHashSet();

    public EntityLootTables(LootTableSubProvider.Context output) {
        super(FeatureFlags.REGISTRY.allFlags(), output);
    }

    public void generate(@NonNull BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {

        ItemPredicate hasKnife = ItemPredicate.Builder.item().build();
        LootItemCondition.Builder toolMatches = AdvanceEntityMatchTool.toolMatches(EquipmentSlot.MAINHAND, hasKnife);
        var count = SetItemCountFunction.setCount(ContextIntProviders.between(1, 2));
        var looting = EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 1.0F));
        var oil = LootItem.lootTableItem(ModItems.OIL).apply(count).apply(looting);

        LootTable.Builder lessOil = LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1)).add(oil).when(toolMatches));
        LootTable.Builder moreOil = LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(2)).add(oil).when(toolMatches));

        output.accept(modLoc("pig"), lessOil);
        output.accept(modLoc("zombified_piglin"), lessOil);
        output.accept(modLoc("piglin"), moreOil);
        output.accept(modLoc("piglin_brute"), moreOil);
        output.accept(modLoc("hoglin"), moreOil);
        output.accept(modLoc("zoglin"), moreOil);
    }

    @Override public void generate() { generate(this.output::accept); }



    public ResourceKey<LootTable> modLoc(String name) {
        return ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, name));
    }
}


