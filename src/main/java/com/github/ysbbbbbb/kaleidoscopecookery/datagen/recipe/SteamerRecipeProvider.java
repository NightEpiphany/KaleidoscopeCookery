package com.github.ysbbbbbb.kaleidoscopecookery.datagen.recipe;

import com.github.ysbbbbbb.kaleidoscopecookery.datagen.builder.SteamerBuilder;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopecookery.init.tag.TagCommon;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import org.jspecify.annotations.NonNull;


public class SteamerRecipeProvider extends ModRecipeProvider {
    public SteamerRecipeProvider(@NonNull BootstrapContext<Recipe<?>> recipes, @NonNull BootstrapContext<Advancement> advancements) {
        super(recipes, advancements);
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
