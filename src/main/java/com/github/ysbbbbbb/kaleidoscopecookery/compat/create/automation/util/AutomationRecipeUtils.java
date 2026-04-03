package com.github.ysbbbbbb.kaleidoscopecookery.compat.create.automation.util;

import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.PotRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.StockpotRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModRecipes;
import com.github.ysbbbbbb.kaleidoscopecookery.item.RecipeItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class AutomationRecipeUtils {
    private AutomationRecipeUtils() {
    }

    public static Optional<RecipeHolder<PotRecipe>> findPotRecipe(ServerLevel level, RecipeItem.RecipeRecord record) {
        if (!record.type().equals(RecipeItem.POT)) {
            return Optional.empty();
        }
        for (RecipeHolder<?> rawHolder : level.recipeAccess().getRecipes()) {
            if (!rawHolder.value().getType().equals(ModRecipes.POT_RECIPE)) {
                continue;
            }
            @SuppressWarnings("unchecked")
            RecipeHolder<PotRecipe> holder = (RecipeHolder<PotRecipe>) rawHolder;
            PotRecipe recipe = holder.value();
            if (!ItemStack.isSameItemSameComponents(recipe.result().create(), record.output()) || recipe.result().count() != record.output().getCount()) {
                continue;
            }
            List<StackPredicate> ingredients = recipe.ingredients().stream()
                    .filter(ingredient -> !ingredient.isEmpty())
                    .map(StackPredicate::new)
                    .toList();
            if (matchesShapeless(record.input(), ingredients)) {
                return Optional.of(holder);
            }
        }
        return Optional.empty();
    }

    public static Optional<RecipeHolder<StockpotRecipe>> findStockpotRecipe(ServerLevel level, RecipeItem.RecipeRecord record) {
        if (!record.type().equals(RecipeItem.STOCKPOT)) {
            return Optional.empty();
        }
        for (RecipeHolder<?> rawHolder : level.recipeAccess().getRecipes()) {
            if (!rawHolder.value().getType().equals(ModRecipes.STOCKPOT_RECIPE)) {
                continue;
            }
            @SuppressWarnings("unchecked")
            RecipeHolder<StockpotRecipe> holder = (RecipeHolder<StockpotRecipe>) rawHolder;
            StockpotRecipe recipe = holder.value();
            if (!ItemStack.isSameItemSameComponents(recipe.result().create(), record.output()) || recipe.result().count() != record.output().getCount()) {
                continue;
            }
            List<StackPredicate> ingredients = recipe.ingredients().stream()
                    .filter(ingredient -> !ingredient.isEmpty())
                    .map(StackPredicate::new)
                    .toList();
            if (matchesShapeless(record.input(), ingredients)) {
                return Optional.of(holder);
            }
        }
        return Optional.empty();
    }

    private static boolean matchesShapeless(List<ItemStack> itemStacks, List<StackPredicate> ingredients) {
        List<ItemStack> nonEmptyItems = itemStacks.stream()
                .filter(stack -> !stack.isEmpty())
                .toList();
        List<StackPredicate> remainingIngredients = new ArrayList<>(ingredients);
        if (nonEmptyItems.size() != remainingIngredients.size()) {
            return false;
        }
        for (ItemStack stack : nonEmptyItems) {
            boolean foundMatch = false;
            for (int i = 0; i < remainingIngredients.size(); i++) {
                if (remainingIngredients.get(i).test(stack)) {
                    remainingIngredients.remove(i);
                    foundMatch = true;
                    break;
                }
            }
            if (!foundMatch) {
                return false;
            }
        }
        return remainingIngredients.isEmpty();
    }
}
