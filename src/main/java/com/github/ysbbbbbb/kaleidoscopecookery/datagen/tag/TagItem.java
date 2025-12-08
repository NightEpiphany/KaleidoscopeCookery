package com.github.ysbbbbbb.kaleidoscopecookery.datagen.tag;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopecookery.init.tag.TagCommon;
import com.github.ysbbbbbb.kaleidoscopecookery.init.tag.TagMod;
import com.github.ysbbbbbb.kaleidoscopecookery.item.BowlFoodBlockItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

import static com.github.ysbbbbbb.kaleidoscopecookery.init.tag.TagCommon.FD_KNIVES;
import static com.github.ysbbbbbb.kaleidoscopecookery.init.tag.TagMod.*;
import static net.minecraft.tags.ItemTags.VILLAGER_PLANTABLE_SEEDS;
import static net.minecraft.world.item.Items.*;

public class TagItem extends ItemTagsProvider {

    public static final TagKey<Item> POT_INGREDIENT = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "pot_ingredient"));



    public TagItem(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags) {
        super(output, lookupProvider, blockTags);
    }

    @Override
    @SuppressWarnings("all")
    protected void addTags(HolderLookup.Provider provider) {
        // 模组 tag
        tag(OIL).add(ModItems.OIL);
        tag(LIT_STOVE).add(FLINT_AND_STEEL, FIRE_CHARGE);
        tag(STRAW_HAT).add(ModItems.STRAW_HAT,
                ModItems.STRAW_HAT_FLOWER);

        tag(KITCHEN_KNIFE).add(ModItems.IRON_KITCHEN_KNIFE,
                        ModItems.GOLD_KITCHEN_KNIFE,
                        ModItems.DIAMOND_KITCHEN_KNIFE,
                        ModItems.NETHERITE_KITCHEN_KNIFE)
                .addOptionalTag(ResourceLocation.parse("farmersdelight:tools/knives"));

        tag(KITCHEN_SHOVEL).add(ModItems.KITCHEN_SHOVEL);

        // 农夫乐事
        tag(FD_KNIVES).add(ModItems.IRON_KITCHEN_KNIFE,
                ModItems.GOLD_KITCHEN_KNIFE,
                ModItems.DIAMOND_KITCHEN_KNIFE,
                ModItems.NETHERITE_KITCHEN_KNIFE);

        tag(FARMER_ARMOR).add(ModItems.FARMER_CHEST_PLATE,
                        ModItems.FARMER_LEGGINGS,
                        ModItems.FARMER_BOOTS)
                .addTag(STRAW_HAT);
        this.tag(TagMod.STRAW_BALE).add(HAY_BLOCK, ModItems.STRAW_BLOCK);

        this.tag(COOKERY_MOD_SEEDS).add(
                ModItems.TOMATO_SEED, ModItems.CHILI_SEED,
                ModItems.WILD_RICE_SEED, ModItems.LETTUCE_SEED
        );

        this.tag(INGREDIENT_CONTAINER).add(BUCKET, BOWL, GLASS_BOTTLE);
        this.tag(GLASS_BOTTLE_CONTAINER).add(HONEY_BOTTLE);
        this.tag(BUCKET_CONTAINER).add(WATER_BUCKET, LAVA_BUCKET, MILK_BUCKET,
                SALMON_BUCKET, COD_BUCKET, TROPICAL_FISH_BUCKET, PUFFERFISH_BUCKET,
                AXOLOTL_BUCKET, TADPOLE_BUCKET, POWDER_SNOW_BUCKET);

        // 给食物分类，简单分为食物和可放置食物
        IntrinsicTagAppender<Item> meal = this.tag(MEALS);
        IntrinsicTagAppender<Item> feasts = this.tag(FEASTS);
        BuiltInRegistries.ITEM.keySet().stream()
                .filter(id -> id.getNamespace().equals(KaleidoscopeCookery.MOD_ID))
                .forEach(id -> {
                    Item item = BuiltInRegistries.ITEM.get(id);
                    if (item == null) {
                        return;
                    }
                    ItemStack stack = item.getDefaultInstance();
                    if (item.getDefaultInstance().get(DataComponents.FOOD) != null) {
                        meal.add(item);
                        if (item instanceof BowlFoodBlockItem) {
                            feasts.add(item);
                        }
                    }
                });

        this.addModItems();

        // 原版兼容
        tag(ItemTags.SHOVELS).add(ModItems.KITCHEN_SHOVEL);
        tag(ItemTags.SWORDS).addTag(KITCHEN_KNIFE).add(ModItems.SICKLE);
        tag(EXTINGUISH_STOVE).addTag(ItemTags.SHOVELS);
        tag(VILLAGER_PLANTABLE_SEEDS).add(ModItems.TOMATO_SEED,
                ModItems.CHILI_SEED, ModItems.LETTUCE_SEED
        );
        tag(VILLAGER_PLANTABLE_SEEDS).add(
                ModItems.CHILI_SEED,
                ModItems.TOMATO_SEED,
                ModItems.LETTUCE_SEED,
                ModItems.WILD_RICE_SEED,
                ModItems.RICE_SEED
        );

        // 社区兼容
        tag(TagCommon.CROPS_CHILI_PEPPER).add(ModItems.RED_CHILI, ModItems.GREEN_CHILI);
        tag(TagCommon.CROPS_TOMATO).add(ModItems.TOMATO);
        tag(TagCommon.CROPS_LETTUCE).add(ModItems.LETTUCE);
        tag(TagCommon.CROPS_RICE).add(ModItems.RICE_SEED);
        tag(TagCommon.CROPS).addTag(TagCommon.CROPS_CHILI_PEPPER)
                .addTag(TagCommon.CROPS_TOMATO)
                .addTag(TagCommon.CROPS_LETTUCE)
                .addTag(TagCommon.CROPS_RICE);

        tag(TagCommon.VEGETABLES_CHILI_PEPPER).add(ModItems.RED_CHILI, ModItems.GREEN_CHILI);
        tag(TagCommon.VEGETABLES_TOMATO).add(ModItems.TOMATO);
        tag(TagCommon.VEGETABLES_LETTUCE).add(ModItems.LETTUCE);
        tag(TagCommon.VEGETABLES).addTag(TagCommon.VEGETABLES_CHILI_PEPPER)
                .addTag(TagCommon.VEGETABLES_TOMATO)
                .addTag(TagCommon.VEGETABLES_LETTUCE);

        tag(TagCommon.SEEDS_CHILI_PEPPER).add(ModItems.CHILI_SEED);
        tag(TagCommon.SEEDS_TOMATO).add(ModItems.TOMATO_SEED);
        tag(TagCommon.SEEDS_LETTUCE).add(ModItems.LETTUCE_SEED);
        tag(TagCommon.SEEDS_RICE).add(ModItems.RICE_SEED);

        tag(TagCommon.GRAIN_RICE).add(ModItems.RICE_SEED);

        tag(TagCommon.COOKED_BEEF).add(ModItems.COOKED_COW_OFFAL, COOKED_BEEF);
        tag(TagCommon.COOKED_PORK).add(ModItems.COOKED_PORK_BELLY, COOKED_PORKCHOP);
        tag(TagCommon.COOKED_MUTTON).add(ModItems.COOKED_LAMB_CHOPS, COOKED_MUTTON);
        tag(TagCommon.COOKED_EGGS).add(ModItems.FRIED_EGG);
        tag(TagCommon.COOKED_RICE).add(ModItems.COOKED_RICE);

        tag(TagCommon.RAW_BEEF).add(ModItems.RAW_COW_OFFAL, BEEF);
        tag(TagCommon.RAW_CHICKEN).add(CHICKEN);
        tag(TagCommon.RAW_PORK).add(ModItems.RAW_PORK_BELLY, PORKCHOP);
        tag(TagCommon.RAW_MUTTON).add(ModItems.RAW_LAMB_CHOPS, MUTTON);
        tag(TagCommon.RAW_FISHES_TROPICAL).add(ModItems.SASHIMI);
        tag(TagCommon.RAW_FISHES_COD).add(COD);
        tag(TagCommon.RAW_FISHES_SALMON).add(SALMON);

        tag(TagCommon.RAW_MEATS).addTag(TagCommon.RAW_BEEF)
                .addTag(TagCommon.RAW_CHICKEN)
                .addTag(TagCommon.RAW_PORK)
                .addTag(TagCommon.RAW_MUTTON)
                .addTag(TagCommon.RAW_FISHES_COD)
                .addTag(TagCommon.RAW_FISHES_SALMON)
                .addTag(TagCommon.RAW_FISHES_TROPICAL)
                .add(ModItems.RAW_DONKEY_MEAT)
                .add(ModItems.RAW_CUT_SMALL_MEATS);

        tag(TagCommon.FLOUR).add(ModItems.FLOUR);
        tag(TagCommon.DOUGHS);
        tag(TagCommon.FOODS_DOUGH);

        // 均衡饮食兼容
        tag(TagCommon.GRAINS).add(ModItems.RICE_SEED, ModItems.RICE_PANICLE);
        tag(TagCommon.PROTEINS).add(ModItems.CATERPILLAR, ModItems.RAW_CUT_SMALL_MEATS, ModItems.RAW_DONKEY_MEAT, ModItems.COOKED_DONKEY_MEAT);
        tag(TagCommon.DIET_VEGETABLES).addTag(TagCommon.VEGETABLES);

        // 兼容静谧四季
        tag(TagCommon.SPRING_CROPS).add(ModItems.LETTUCE_SEED);
        tag(TagCommon.SUMMER_CROPS).add(ModItems.TOMATO_SEED, ModItems.CHILI_SEED, ModItems.RICE_SEED, ModItems.WILD_RICE_SEED);
        tag(TagCommon.AUTUMN_CROPS).add(ModItems.TOMATO_SEED, ModItems.CHILI_SEED, ModItems.LETTUCE_SEED, ModItems.RICE_SEED, ModItems.WILD_RICE_SEED);
    }

    private void addModItems() {
        IntrinsicTagAppender<Item> modTags = tag(COOKERY_MOD_ITEMS);
        for (Item item : BuiltInRegistries.ITEM) {
            ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(item);
            if (itemId.getNamespace().equals(KaleidoscopeCookery.MOD_ID)) {
                modTags.add(item);
            }
        }
    }
}
