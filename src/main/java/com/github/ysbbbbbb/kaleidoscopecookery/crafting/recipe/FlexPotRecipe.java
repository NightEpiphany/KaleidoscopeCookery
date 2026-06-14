package com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.container.SimpleInput;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModRecipes;
import com.github.ysbbbbbb.kaleidoscopecookery.util.RecipeMatcher;
import com.google.common.collect.Sets;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Set;

public record FlexPotRecipe(int time, int stirFryCount, Ingredient carrier,
                            NonNullList<Ingredient> ingredients,
                            ItemStackTemplate result) implements BaseRecipe<SimpleInput> {
    public FlexPotRecipe(int time, int stirFryCount, Ingredient carrier,
                         List<Ingredient> ingredients, ItemStackTemplate result) {
        this(time, stirFryCount, carrier, createNonNullList(ingredients), result);
    }

    private static NonNullList<Ingredient> createNonNullList(List<Ingredient> ingredients) {
        NonNullList<Ingredient> list = NonNullList.create();
        list.addAll(ingredients);
        return list;
    }

    @Override
    public boolean matches(SimpleInput simpleInput, @NonNull Level level) {
        List<Ingredient> recipeIngredients = this.ingredients.stream()
                .filter(ingredient -> !ingredient.isEmpty())
                .toList();
        NonNullList<ItemStack> merged = NonNullList.create();
        Set<Item> record = Sets.newHashSet();
        for (int i = 0; i < simpleInput.size(); i++) {
            ItemStack stack = simpleInput.getItem(i);
            Item item = stack.getItem();
            if (!stack.isEmpty() && record.add(item)) {
                merged.add(stack);
            }
        }
        return RecipeMatcher.findMatches(merged, recipeIngredients) != null;
    }

    @Override
    public @NonNull ItemStack assemble(@NonNull SimpleInput input) {
        return this.result.create();
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public @NonNull String group() {
        return "flex_pot";
    }

    @Override
    public @NonNull RecipeSerializer<? extends Recipe<SimpleInput>> getSerializer() {
        return ModRecipes.FLEX_POT_SERIALIZER;
    }

    public @NotNull NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public @NonNull RecipeType<? extends Recipe<SimpleInput>> getType() {
        return ModRecipes.FLEX_POT_RECIPE;
    }

    @Override
    public @NonNull PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.Provider registryAccess) {
        return this.result.create();
    }

    @Override
    public @NonNull RecipeBookCategory recipeBookCategory() {
        return Registry.register(BuiltInRegistries.RECIPE_BOOK_CATEGORY,
                Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "flex_pot"),
                new RecipeBookCategory());
    }
}
