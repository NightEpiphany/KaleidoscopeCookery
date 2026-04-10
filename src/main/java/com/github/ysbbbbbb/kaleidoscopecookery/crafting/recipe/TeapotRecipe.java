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

public record TeapotRecipe(ResourceLocation id, Ingredient ingredient, int ingredientCount,
                           ResourceLocation baseTeaFluid, ResourceLocation resultTeaFluid,
                           int time) implements BaseRecipe<TeapotContainer> {

    @Override
    public boolean matches(TeapotContainer container, @NotNull Level level) {
        ItemStack itemStack = container.getItemStack();
        return ingredient.test(itemStack) && itemStack.getCount() >= ingredientCount && baseTeaFluid.equals(container.getTeaFluid());
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
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
