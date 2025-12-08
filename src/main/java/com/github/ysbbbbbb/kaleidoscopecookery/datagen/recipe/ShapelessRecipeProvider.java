package com.github.ysbbbbbb.kaleidoscopecookery.datagen.recipe;

import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopecookery.init.tag.TagCommon;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class ShapelessRecipeProvider extends ModRecipeProvider {
    public ShapelessRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    public void buildRecipes(RecipeOutput consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModItems.RICE_PANICLE, 9)
                .requires(ModItems.STRAW_BLOCK)
                .unlockedBy("has_rice_panicle", has(ModItems.RICE_PANICLE))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModItems.OIL, 9)
                .requires(ModItems.OIL_BLOCK)
                .unlockedBy("has_ingot_iron", has(Items.IRON_INGOT))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.CHILI_SEED, 1)
                .requires(ModItems.GREEN_CHILI)
                .unlockedBy("has_chili", has(ModItems.GREEN_CHILI))
                .save(consumer, "chili_seed_from_green_chili");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.CHILI_SEED, 1)
                .requires(ModItems.RED_CHILI)
                .unlockedBy("has_chili", has(ModItems.RED_CHILI))
                .save(consumer, "chili_seed_from_red_chili");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.TOMATO_SEED, 1)
                .requires(ModItems.TOMATO)
                .unlockedBy("has_tomato", has(ModItems.TOMATO))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.STUFFED_DOUGH_FOOD, 1)
                .requires(TagCommon.RAW_MEATS)
                .requires(TagCommon.VEGETABLES)
                .requires(TagCommon.DOUGH)
                .unlockedBy("has_dough", has(TagCommon.DOUGH))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RECIPE_ITEM, 1)
                .requires(ModItems.RECIPE_ITEM)
                .unlockedBy("has_recipe_item", has(ModItems.RECIPE_ITEM))
                .save(consumer, "reset_recipe_item");

        for (int i = 0; i < 8; i++) {
            int count = i + 1;
            String name = "flour_from_" + count + "_wheat";
            ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.RAW_DOUGH, count)
                    .requires(Items.WATER_BUCKET)
                    .requires(ModItems.FLOUR, count)
                    .unlockedBy("has_wheat", has(Items.WHEAT))
                    .save(consumer, name);
        }
    }
}
