package com.github.ysbbbbbb.kaleidoscopecookery.compat.trinkets.init;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.trinkets.aglets.StrawHatTrinketItem;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.trinkets.aglets.StrawHatTrinketsLootCondition;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopecookery.init.tag.TagMod;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
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
            Registry.register(BuiltInRegistries.LOOT_CONDITION_TYPE, StrawHatTrinketsLootCondition.ID, new LootItemConditionType(new StrawHatTrinketsLootCondition.StrawHatTrinketsLootConditionSerializer()));
            Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeCookery.MOD_ID, "straw_hat_flower"), STRAW_HAT_FLOWER);
            Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeCookery.MOD_ID, "straw_hat"), STRAW_HAT);
            LootTableEvents.MODIFY.register((key, manager, loc, builder, src) -> {
                if (loc.equals(Blocks.GRASS.getLootTable())) {
                    var predicate = ItemPredicate.Builder.item().of(TagMod.STRAW_HAT).build();
                    var enchantments = ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE, 2);
                    var chance = LootItemRandomChanceCondition.randomChance(0.125F);
                    var match = StrawHatTrinketsLootCondition.toolMatches(predicate);
                    builder.withPool(LootPool.lootPool()
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
