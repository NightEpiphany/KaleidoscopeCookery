package com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.container.TeapotInput;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

public record TeapotRecipe(Identifier teaFluid,
                           Ingredient ingredient, int ingredientCount,
                           int time, ItemStack result
) implements BaseRecipe<TeapotInput> {
    /**
     * 配方强制输出 12 个
     */
    public static final int OUTPUT_COUNT = 12;

    @Override
    public boolean matches(TeapotInput container, @NonNull Level level) {
        ItemStack stack = container.getItemStack();
        Identifier fluid = container.getTeaFluid();
        return teaFluid.equals(fluid) && ingredient.test(stack) && stack.getCount() >= ingredientCount;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return this.result;
    }

    @Override
    public @NonNull ItemStack assemble(@NonNull TeapotInput container, HolderLookup.@NonNull Provider registryAccess) {
        return getResultItem(registryAccess).copyWithCount(OUTPUT_COUNT);
    }

    @Override
    public @NonNull RecipeSerializer<? extends Recipe<TeapotInput>> getSerializer() {
        return ModRecipes.TEAPOT_SERIALIZER;
    }



    @Override
    public @NonNull RecipeType<? extends Recipe<TeapotInput>> getType() {
        return ModRecipes.TEAPOT_RECIPE;
    }

    @Override
    public @NonNull PlacementInfo placementInfo() {
        return PlacementInfo.create(this.ingredient);
    }

    @Override
    public @NonNull RecipeBookCategory recipeBookCategory() {
        return Registry.register(BuiltInRegistries.RECIPE_BOOK_CATEGORY, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "teapot"), new RecipeBookCategory());
    }
}
