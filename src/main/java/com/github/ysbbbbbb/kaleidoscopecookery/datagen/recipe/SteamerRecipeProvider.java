package com.github.ysbbbbbb.kaleidoscopecookery.datagen.recipe;

import com.github.ysbbbbbb.kaleidoscopecookery.datagen.builder.SteamerBuilder;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopecookery.init.tag.TagCommon;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Items;


public class SteamerRecipeProvider extends ModRecipeProvider {
    public SteamerRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    public void buildRecipes(RecipeOutput consumer) {
        SteamerBuilder.builder()
                .setIngredient(ModItems.STUFFED_DOUGH_FOOD)
                .setResult(ModItems.BAOZI)
                .save(consumer);

        SteamerBuilder.builder()
                .setIngredient(TagCommon.DOUGH)
                .setResult(ModItems.MANTOU)
                .save(consumer);

        SteamerBuilder.builder()
                .setIngredient(Items.SLIME_BALL)
                .setResult(ModItems.QINGTUAN)
                .save(consumer);

        SteamerBuilder.builder()
                .setIngredient(ModItems.RAW_BAMBOO_TUBE_RICE)
                .setResult(ModItems.BAMBOO_TUBE_RICE)
                .save(consumer);
    }
}
