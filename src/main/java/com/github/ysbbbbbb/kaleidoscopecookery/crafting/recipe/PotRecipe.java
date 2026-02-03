package com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.container.SimpleInput;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModRecipes;
import com.github.ysbbbbbb.kaleidoscopecookery.util.RecipeMatcher;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Optional;

public record PotRecipe(int time, int stirFryCount, Optional<Ingredient> carrier,
                        NonNullList<Ingredient> ingredients, ItemStack result) implements BaseRecipe<SimpleInput> {
    public PotRecipe(int time, int stirFryCount, Optional<Ingredient> carrier,
                     List<Ingredient> ingredients, ItemStack result) {
        this(time, stirFryCount, carrier, NonNullList.of(Ingredient.of(Items.BARRIER),
                BaseRecipe.fillInputs(ingredients)), result);
    }



    @Override
    public boolean matches(SimpleInput simpleInput, @NonNull Level level) {
        return RecipeMatcher.findMatches(simpleInput.getInputs(), ingredients) != null;
    }

    @Override
    public @NonNull RecipeSerializer<? extends Recipe<SimpleInput>> getSerializer() {
        return ModRecipes.POT_SERIALIZER;
    }


    public @NotNull NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public @NonNull RecipeType<? extends Recipe<SimpleInput>> getType() {
        return ModRecipes.POT_RECIPE;
    }

    @Override
    public @NonNull PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public @NonNull RecipeBookCategory recipeBookCategory() {
        return Registry.register(BuiltInRegistries.RECIPE_BOOK_CATEGORY, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "pot"), new RecipeBookCategory());
    }


    @Override
    public ItemStack getResultItem(HolderLookup.Provider registryAccess) {
        return this.result;
    }

}
