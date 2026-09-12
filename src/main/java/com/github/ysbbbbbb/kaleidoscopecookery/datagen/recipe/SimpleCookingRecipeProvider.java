package com.github.ysbbbbbb.kaleidoscopecookery.datagen.recipe;

import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.crafting.SmokingRecipe;
import net.minecraft.world.level.ItemLike;


public class SimpleCookingRecipeProvider extends ModRecipeProvider {
    public SimpleCookingRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    public void buildRecipes(RecipeOutput consumer) {
        vanillaCookingRecipe(ModItems.RAW_LAMB_CHOPS, ModItems.COOKED_LAMB_CHOPS, 0.35F, consumer);
        vanillaCookingRecipe(ModItems.RAW_COW_OFFAL, ModItems.COOKED_COW_OFFAL, 0.35F, consumer);
        vanillaCookingRecipe(ModItems.RAW_PORK_BELLY, ModItems.COOKED_PORK_BELLY, 0.35F, consumer);
        vanillaCookingRecipe(ModItems.RAW_CUT_SMALL_MEATS, ModItems.COOKED_CUT_SMALL_MEATS, 0.35F, consumer);
        vanillaCookingRecipe(ModItems.RAW_MEATBALL, ModItems.COOKED_MEATBALL, 0.35F, consumer);
        vanillaCookingRecipe(ModItems.STUFFED_DOUGH_FOOD, ModItems.SAMSA, 0.35F, consumer);
    }

    public void vanillaCookingRecipe(ItemLike input, ItemLike output, float experience, RecipeOutput consumer) {
        simpleCookingRecipe("smoking", SmokingRecipe::new, 100, input, output, experience);
        simpleCookingRecipe("campfire_cooking", CampfireCookingRecipe::new, 600, input, output, experience);
        simpleCookingRecipe("smelting", SmeltingRecipe::new, 200, input, output, experience);
    }
}
