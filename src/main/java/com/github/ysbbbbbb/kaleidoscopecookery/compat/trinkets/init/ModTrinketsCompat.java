package com.github.ysbbbbbb.kaleidoscopecookery.compat.trinkets.init;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.trinkets.aglets.StrawHatTrinketItem;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.trinkets.aglets.StrawHatTrinketsLootCondition;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopecookery.init.tag.TagMod;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.Contract;

import static com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems.STRAW_HAT;
import static com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems.STRAW_HAT_FLOWER;

public class ModTrinketsCompat {

    @Contract(pure = true)
    static void init() {
            STRAW_HAT_FLOWER = new StrawHatTrinketItem(true);
            STRAW_HAT = new StrawHatTrinketItem(false);
            Registry.register(BuiltInRegistries.LOOT_CONDITION_TYPE, StrawHatTrinketsLootCondition.ID, new LootItemConditionType(StrawHatTrinketsLootCondition.CODEC));
            Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "straw_hat_flower"), STRAW_HAT_FLOWER);
            Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "straw_hat"), STRAW_HAT);
            LootTableEvents.MODIFY.register((key, tableBuilder, source, wrapperLookup) -> {
                ResourceLocation id = key.location();
                HolderLookup.RegistryLookup<Enchantment> enchantment = wrapperLookup.lookupOrThrow(Registries.ENCHANTMENT);
                if (id.equals(ResourceLocation.withDefaultNamespace("blocks/short_grass"))) {
                    var predicate = ItemPredicate.Builder.item().of(TagMod.STRAW_HAT).build();
                    var enchantments = ApplyBonusCount.addUniformBonusCount(enchantment.getOrThrow(Enchantments.FORTUNE), 2);
                    var chance = LootItemRandomChanceCondition.randomChance(0.125F);
                    var match = StrawHatTrinketsLootCondition.toolMatches(predicate);
                    tableBuilder.withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(ModItems.TOMATO_SEED)
                                    .when(chance).when(match)
                                    .apply(enchantments))
                            .add(LootItem.lootTableItem(ModItems.CHILI_SEED)
                                    .when(chance).when(match)
                                    .apply(enchantments))
                            .add(LootItem.lootTableItem(ModItems.LETTUCE_SEED)
                                    .when(chance).when(match)
                                    .apply(enchantments))
                            .add(LootItem.lootTableItem(ModItems.WILD_RICE_SEED)
                                    .when(chance).when(match)
                                    .apply(enchantments))
                            .add(EmptyLootItem.emptyItem().setWeight(2)));
                }
            });
    }
}
