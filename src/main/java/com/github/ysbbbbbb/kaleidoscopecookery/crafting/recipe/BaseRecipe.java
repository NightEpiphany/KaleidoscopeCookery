package com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.List;

public interface BaseRecipe<C extends RecipeInput> extends Recipe<C> {
    int RECIPES_SIZE = 9;

    static Ingredient[] fillInputs(List<Ingredient> inputs) {
        Ingredient[] newInputs = new Ingredient[RECIPES_SIZE];
        for (int i = 0; i < RECIPES_SIZE; i++) {
            if (i < inputs.size()) {
                newInputs[i] = inputs.get(i);
            } else {
                newInputs[i] = Ingredient.of(Items.BARRIER);
            }
        }
        return newInputs;
    }

    ItemStack getResultItem(HolderLookup.Provider registries);

    @Override
    default @NotNull ItemStack assemble(C container, HolderLookup.@NonNull Provider registryAccess) {
        return getResultItem(registryAccess).copy();
    }

    @Override
    default boolean isSpecial() {
        return true;
    }

    default boolean canCraftInDimensions(int width, int height) {
        return false;
    }

}
