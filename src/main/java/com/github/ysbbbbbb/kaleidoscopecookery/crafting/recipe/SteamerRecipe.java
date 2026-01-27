package com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModRecipes;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

public class SteamerRecipe extends SingleItemRecipe {
    private final int cookTick;

    public SteamerRecipe(Ingredient ingredient, ItemStack result, int cookTick) {
        super("steamer", ingredient, result);
        this.cookTick = Math.max(cookTick, 1);
    }

    @Override
    public @NonNull RecipeSerializer<? extends SingleItemRecipe> getSerializer() {
        return ModRecipes.STEAMER_SERIALIZER;
    }

    @Override
    public @NonNull RecipeType<? extends SingleItemRecipe> getType() {
        return ModRecipes.STEAMER_RECIPE;
    }

    @Override
    public @NonNull RecipeBookCategory recipeBookCategory() {
        return Registry.register(BuiltInRegistries.RECIPE_BOOK_CATEGORY, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "steamer"), new RecipeBookCategory());
    }

    @Override
    public boolean matches(SingleRecipeInput inv, @NonNull Level level) {
        return this.input().test(inv.getItem(0));
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public Ingredient getIngredient() {
        return this.input();
    }

    public ItemStack getResult() {
        return this.result();
    }

    public int getCookTick() {
        return cookTick;
    }
}
