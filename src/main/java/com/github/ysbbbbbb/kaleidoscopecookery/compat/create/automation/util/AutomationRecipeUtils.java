package com.github.ysbbbbbb.kaleidoscopecookery.compat.create.automation.util;

import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.FlexPotRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.FlexStockpotRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.PotRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.StockpotRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModRecipes;
import com.github.ysbbbbbb.kaleidoscopecookery.item.RecipeItem;
import com.google.common.collect.Sets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    public static Optional<RecipeHolder<FlexPotRecipe>> findFlexPotRecipe(ServerLevel level, RecipeItem.RecipeRecord record) {
        if (!record.type().equals(RecipeItem.POT)) {
            return Optional.empty();
        }
        for (RecipeHolder<?> rawHolder : level.recipeAccess().getRecipes()) {
            if (!rawHolder.value().getType().equals(ModRecipes.FLEX_POT_RECIPE)) {
                continue;
            }
            @SuppressWarnings("unchecked")
            RecipeHolder<FlexPotRecipe> holder = (RecipeHolder<FlexPotRecipe>) rawHolder;
            FlexPotRecipe recipe = holder.value();
            if (!ItemStack.isSameItem(recipe.result().create(), record.output()) || recipe.result().count() != record.output().getCount()) {
                continue;
            }
            List<StackPredicate> ingredients = recipe.ingredients().stream()
                    .filter(ingredient -> !ingredient.isEmpty())
                    .map(StackPredicate::new)
                    .toList();
            if (matchesFlexShapeless(record.input(), ingredients)) {
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

    public static Optional<RecipeHolder<FlexStockpotRecipe>> findFlexStockpotRecipe(ServerLevel level, RecipeItem.RecipeRecord record) {
        if (!record.type().equals(RecipeItem.STOCKPOT)) {
            return Optional.empty();
        }
        for (RecipeHolder<?> rawHolder : level.recipeAccess().getRecipes()) {
            if (!rawHolder.value().getType().equals(ModRecipes.FLEX_STOCKPOT_RECIPE)) {
                continue;
            }
            @SuppressWarnings("unchecked")
            RecipeHolder<FlexStockpotRecipe> holder = (RecipeHolder<FlexStockpotRecipe>) rawHolder;
            FlexStockpotRecipe recipe = holder.value();
            if (!ItemStack.isSameItem(recipe.result().create(), record.output()) || recipe.result().count() != record.output().getCount()) {
                continue;
            }
            List<StackPredicate> ingredients = recipe.ingredients().stream()
                    .filter(ingredient -> !ingredient.isEmpty())
                    .map(StackPredicate::new)
                    .toList();
            if (matchesFlexShapeless(record.input(), ingredients)) {
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

    private static boolean matchesFlexShapeless(List<ItemStack> itemStacks, List<StackPredicate> ingredients) {
        List<ItemStack> uniqueItems = new ArrayList<>();
        Set<Item> seen = Sets.newHashSet();
        for (ItemStack stack : itemStacks) {
            if (!stack.isEmpty() && seen.add(stack.getItem())) {
                uniqueItems.add(stack);
            }
        }
        return matchesShapeless(uniqueItems, ingredients);
    }
}
