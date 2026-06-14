package com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.container.SimpleInput;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.output.RandomOutput;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModRecipes;
import com.google.common.base.Preconditions;
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

public record MillstoneRecipe(Ingredient ingredient, List<RandomOutput> results) implements BaseRecipe<SimpleInput> {
    public MillstoneRecipe {
        Preconditions.checkArgument(!results.isEmpty(), "Millstone recipe must have at least one output");
        Preconditions.checkArgument(results.size() <= 4, "Millstone recipe can have at most 4 outputs");
    }

    @Override
    public boolean matches(SimpleInput input, @NonNull Level level) {
        return this.ingredient.test(input.getItem(0));
    }

    @Override
    public Ingredient ingredient() {
        return ingredient;
    }


    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        ingredients.add(this.ingredient);
        return ingredients;
    }

    @Override
    public @NonNull ItemStack assemble(@NonNull SimpleInput input) {
        return this.results.getFirst().stack().create();
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public @NonNull String group() {
        return "millstone";
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.Provider registryAccess) {
        return this.results.getFirst().stack().create();
    }

    @Override
    public @NonNull RecipeSerializer<? extends Recipe<SimpleInput>> getSerializer() {
        return ModRecipes.MILLSTONE_SERIALIZER;
    }

    @Override
    public @NonNull RecipeType<? extends Recipe<SimpleInput>> getType() {
        return ModRecipes.MILLSTONE_RECIPE;
    }

    @Override
    public @NonNull PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public @NonNull RecipeBookCategory recipeBookCategory() {
        return Registry.register(BuiltInRegistries.RECIPE_BOOK_CATEGORY,
                Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "millstone"),
                new RecipeBookCategory());
    }
}
