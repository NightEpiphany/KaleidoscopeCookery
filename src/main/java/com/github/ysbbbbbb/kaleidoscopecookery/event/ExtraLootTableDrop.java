package com.github.ysbbbbbb.kaleidoscopecookery.event;

import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopecookery.init.tag.TagMod;
import com.github.ysbbbbbb.kaleidoscopecookery.loot.AdvanceBlockMatchTool;
import com.github.ysbbbbbb.kaleidoscopecookery.loot.AdvanceEntityMatchTool;
import com.github.ysbbbbbb.kaleidoscopecookery.mixin.accessor.LootTableAccessor;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.UniformContainerBase;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery.LOGGER;

public final class ExtraLootTableDrop {
    private static final Identifier HOGLIN = Identifier.fromNamespaceAndPath("minecraft", "entities/hoglin");
    private static final Identifier PIG = Identifier.fromNamespaceAndPath("minecraft", "entities/pig");
    private static final Identifier PIGLIN = Identifier.fromNamespaceAndPath("minecraft", "entities/piglin");
    private static final Identifier PIGLIN_BRUTE = Identifier.fromNamespaceAndPath("minecraft", "entities/piglin_brute");
    private static final Identifier GRASS = Identifier.fromNamespaceAndPath("minecraft", "blocks/short_grass");
    private static final AtomicBoolean ENABLED = new AtomicBoolean();

    private ExtraLootTableDrop() {
    }

    public static void register() {
        ENABLED.set(true);
    }

    public static void modify(ResourceKey<LootTable> key, LootTable lootTable, RegistryOps<?> registries) {
        if (!ENABLED.get()) {
            return;
        }

        try {
            LootPool.Builder extraPool = createExtraPool(key.identifier(), registries);
            if (extraPool == null) {
                return;
            }

            LootTableAccessor accessor = (LootTableAccessor) lootTable;
            List<LootPool> pools = new ArrayList<>(accessor.kaleidoscopeCookery$getPools());
            pools.add(extraPool.build());
            accessor.kaleidoscopeCookery$setPools(List.copyOf(pools));
            LOGGER.debug("Added Cookery drops to loot table {}", key.identifier());
        } catch (Exception exception) {
            LOGGER.error("Failed to add Cookery drops to loot table {}; keeping the original table", key.identifier(), exception);
        }
    }

    private static LootPool.Builder createExtraPool(Identifier id, RegistryOps<?> registries) {
        if (id.equals(HOGLIN) || id.equals(PIGLIN) || id.equals(PIGLIN_BRUTE)) {
            return createOilPool(2, registries);
        }
        if (id.equals(PIG)) {
            return createOilPool(1, registries);
        }
        if (id.equals(GRASS)) {
            return createSeedPool(registries);
        }
        return null;
    }

    private static LootPool.Builder createOilPool(int rolls, RegistryOps<?> registries) {
        // 仅在玩家主手持厨房刀击杀时掉落油。
        HolderGetter<Item> itemLookup = registries.getter(Registries.ITEM).orElseThrow();
        HolderGetter<Enchantment> enchantmentLookup = registries.getter(Registries.ENCHANTMENT).orElseThrow();
        ItemPredicate hasKnife = ItemPredicate.Builder.item().of(itemLookup, TagMod.KITCHEN_KNIFE).build();
        LootItemCondition.Builder toolMatches = AdvanceEntityMatchTool.toolMatches(EquipmentSlot.MAINHAND, hasKnife);
        var count = SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F));
        var looting = EnchantedCountIncreaseFunction.lootingMultiplier(enchantmentLookup, UniformGenerator.between(0.0F, 1.0F));
        var oil = LootItem.lootTableItem(ModItems.OIL).apply(count).apply(looting);
        var empty = EmptyLootItem.emptyItem();

        return LootPool.lootPool()
                .setRolls(ConstantValue.exactly(rolls))
                .add(oil).add(empty).when(toolMatches);
    }

    private static LootPool.Builder createSeedPool(RegistryOps<?> registries) {
        HolderGetter<Enchantment> enchantmentLookup = registries.getter(Registries.ENCHANTMENT).orElseThrow();
        HolderGetter<Item> itemLookup = registries.getter(Registries.ITEM).orElseThrow();
        // 穿戴草帽破坏矮草丛时有概率掉落作物种子。
        var tomato = createSeedEntry(ModItems.TOMATO_SEED, enchantmentLookup, itemLookup);
        var chili = createSeedEntry(ModItems.CHILI_SEED, enchantmentLookup, itemLookup);
        var lettuce = createSeedEntry(ModItems.LETTUCE_SEED, enchantmentLookup, itemLookup);
        var rice = createSeedEntry(ModItems.WILD_RICE_SEED, enchantmentLookup, itemLookup);
        var empty = EmptyLootItem.emptyItem().setWeight(2);

        return LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(tomato).add(chili)
                .add(lettuce).add(rice)
                .add(empty);
    }

    private static UniformContainerBase.Builder<?> createSeedEntry(
            ItemLike item,
            HolderGetter<Enchantment> enchantments,
            HolderGetter<Item> items
    ) {
        ItemPredicate hasHat = ItemPredicate.Builder.item().of(items, TagMod.STRAW_HAT).build();
        LootItemCondition.Builder hatMatches = AdvanceBlockMatchTool.toolMatches(EquipmentSlot.HEAD, hasHat);
        return LootItem.lootTableItem(item)
                .when(LootItemRandomChanceCondition.randomChance(0.125F)).when(hatMatches)
                .apply(ApplyBonusCount.addUniformBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE), 2));
    }
}
