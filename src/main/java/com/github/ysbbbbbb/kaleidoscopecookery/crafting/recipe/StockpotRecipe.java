package com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.container.StockpotInput;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModRecipes;
import com.github.ysbbbbbb.kaleidoscopecookery.util.RecipeMatcher;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.List;

import static com.github.ysbbbbbb.kaleidoscopecookery.crafting.serializer.StockpotRecipeSerializer.*;
import static com.github.ysbbbbbb.kaleidoscopecookery.crafting.serializer.StockpotRecipeSerializer.DEFAULT_COOKING_BUBBLE_COLOR;
import static com.github.ysbbbbbb.kaleidoscopecookery.crafting.serializer.StockpotRecipeSerializer.DEFAULT_FINISHED_BUBBLE_COLOR;

public record StockpotRecipe(NonNullList<Ingredient> ingredients,
                             Identifier soupBase, ItemStack result, int time,
                             Ingredient carrier, Identifier cookingTexture, Identifier finishedTexture,
                             int cookingBubbleColor, int finishedBubbleColor) implements BaseRecipe<StockpotInput> {
    public StockpotRecipe(List<Ingredient> ingredients, Identifier soupBase, ItemStack result,
                          int time, Ingredient carrier, Identifier cookingTexture, Identifier finishedTexture,
                          int cookingBubbleColor, int finishedBubbleColor) {
        this(NonNullList.of(Ingredient.of(ItemStack.EMPTY.getItem()), BaseRecipe.fillInputs(ingredients)),
                soupBase, result, time, carrier, cookingTexture, finishedTexture,
                cookingBubbleColor, finishedBubbleColor);
    }

    public StockpotRecipe(NonNullList<Ingredient> ingredients, ItemStack result, int time, ItemStack container) {
        this(ingredients, DEFAULT_SOUP_BASE, result, time, Ingredient.of(container.getItem()),
                DEFAULT_COOKING_TEXTURE, DEFAULT_FINISHED_TEXTURE,
                DEFAULT_COOKING_BUBBLE_COLOR, DEFAULT_FINISHED_BUBBLE_COLOR);
    }

    @Override
    public boolean matches(StockpotInput container, @NonNull Level level) {
        return container.getSoupBase().equals(this.soupBase)
               && RecipeMatcher.findMatches(container.getInputs(), ingredients) != null;
    }


    public @NotNull NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.Provider registryAccess) {
        return this.result;
    }

    @Override
    public @NonNull RecipeSerializer<? extends Recipe<StockpotInput>> getSerializer() {
        return ModRecipes.STOCKPOT_SERIALIZER;
    }

    @Override
    public @NonNull RecipeType<? extends Recipe<StockpotInput>> getType() {
        return ModRecipes.STOCKPOT_RECIPE;
    }

    @Override
    public @NonNull PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public @NonNull RecipeBookCategory recipeBookCategory() {
        return Registry.register(BuiltInRegistries.RECIPE_BOOK_CATEGORY, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "stockpot"), new RecipeBookCategory());
    }
}
