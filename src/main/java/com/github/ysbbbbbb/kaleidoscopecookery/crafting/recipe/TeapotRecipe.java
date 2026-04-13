package com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe;

import com.github.ysbbbbbb.kaleidoscopecookery.crafting.container.TeapotContainer;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModRecipes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public record TeapotRecipe(ResourceLocation id, ResourceLocation teaFluid,
                           Ingredient ingredient, int ingredientCount,
                           int time, ItemStack result
) implements BaseRecipe<TeapotContainer> {
    /**
     * 配方强制输出 12 个
     */
    public static final int OUTPUT_COUNT = 12;

    @Override
    public boolean matches(TeapotContainer container, @NotNull Level level) {
        ItemStack stack = container.getItemStack();
        ResourceLocation fluid = container.getTeaFluid();
        return teaFluid.equals(fluid) && ingredient.test(stack) && stack.getCount() >= ingredientCount;
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess registryAccess) {
        return this.result;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull TeapotContainer container, @NotNull RegistryAccess registryAccess) {
        return getResultItem(registryAccess).copyWithCount(OUTPUT_COUNT);
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipes.TEAPOT_SERIALIZER;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipes.TEAPOT_RECIPE;
    }
}
