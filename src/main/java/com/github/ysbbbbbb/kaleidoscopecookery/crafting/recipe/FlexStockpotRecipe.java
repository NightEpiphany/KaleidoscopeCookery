package com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.container.StockpotInput;
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

import static com.github.ysbbbbbb.kaleidoscopecookery.crafting.serializer.StockpotRecipeSerializer.*;

public record FlexStockpotRecipe(NonNullList<Ingredient> ingredients,
                                 Identifier soupBase,
                                 ItemStackTemplate result,
                                 int time,
                                 Ingredient carrier,
                                 Identifier cookingTexture,
                                 Identifier finishedTexture,
                                 int cookingBubbleColor,
                                 int finishedBubbleColor) implements BaseRecipe<StockpotInput> {
    public FlexStockpotRecipe(List<Ingredient> ingredients, Identifier soupBase, ItemStackTemplate result,
                              int time, Ingredient carrier, Identifier cookingTexture, Identifier finishedTexture,
                              int cookingBubbleColor, int finishedBubbleColor) {
        this(createNonNullList(ingredients),
                soupBase, result, time, carrier, cookingTexture, finishedTexture,
                cookingBubbleColor, finishedBubbleColor);
    }

    private static NonNullList<Ingredient> createNonNullList(List<Ingredient> ingredients) {
        NonNullList<Ingredient> list = NonNullList.create();
        list.addAll(ingredients);
        return list;
    }

    public FlexStockpotRecipe(NonNullList<Ingredient> ingredients, ItemStackTemplate result, int time, ItemStack container) {
        this(ingredients, DEFAULT_SOUP_BASE, result, time, Ingredient.of(container.getItem()),
                DEFAULT_COOKING_TEXTURE, DEFAULT_FINISHED_TEXTURE,
                DEFAULT_COOKING_BUBBLE_COLOR, DEFAULT_FINISHED_BUBBLE_COLOR);
    }

    @Override
    public boolean matches(StockpotInput container, @NonNull Level level) {
        if (!container.getSoupBase().equals(this.soupBase)) {
            return false;
        }

        List<Ingredient> recipeIngredients = this.ingredients.stream()
                .filter(ingredient -> !ingredient.isEmpty())
                .toList();
        NonNullList<ItemStack> merged = NonNullList.create();
        Set<Item> record = Sets.newHashSet();
        for (int i = 0; i < container.size(); i++) {
            ItemStack stack = container.getItem(i);
            Item item = stack.getItem();
            if (!stack.isEmpty() && record.add(item)) {
                merged.add(stack);
            }
        }
        return RecipeMatcher.findMatches(merged, recipeIngredients) != null;
    }

    @Override
    public @NonNull ItemStack assemble(@NonNull StockpotInput input) {
        return this.result.create();
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public @NonNull String group() {
        return "flex_stockpot";
    }

    public @NotNull NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.Provider registryAccess) {
        return this.result.create();
    }

    @Override
    public @NonNull RecipeSerializer<? extends Recipe<StockpotInput>> getSerializer() {
        return ModRecipes.FLEX_STOCKPOT_SERIALIZER;
    }

    @Override
    public @NonNull RecipeType<? extends Recipe<StockpotInput>> getType() {
        return ModRecipes.FLEX_STOCKPOT_RECIPE;
    }

    @Override
    public @NonNull PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public @NonNull RecipeBookCategory recipeBookCategory() {
        return Registry.register(BuiltInRegistries.RECIPE_BOOK_CATEGORY,
                Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "flex_stockpot"),
                new RecipeBookCategory());
    }
}
