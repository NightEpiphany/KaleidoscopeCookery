package com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModRecipes;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

public class ChoppingBoardRecipe extends SingleItemRecipe {
    private final int cutCount;
    private final Identifier modelId;

    public ChoppingBoardRecipe(Ingredient ingredient, ItemStackTemplate result, int cutCount, Identifier modelId) {
        super(BaseRecipe.NO_INFO, ingredient, result);
        this.cutCount = Math.max(cutCount, 1);
        this.modelId = modelId;
    }

    @Override
    public @NonNull RecipeSerializer<? extends SingleItemRecipe> getSerializer() {
        return ModRecipes.CHOPPING_BOARD_SERIALIZER;
    }

    @Override
    public @NonNull RecipeType<? extends SingleItemRecipe> getType() {
        return ModRecipes.CHOPPING_BOARD_RECIPE;
    }

    @Override
    public @NonNull RecipeBookCategory recipeBookCategory() {
        return Registry.register(BuiltInRegistries.RECIPE_BOOK_CATEGORY, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "chopping_board"), new RecipeBookCategory());
    }

    @Override
    public boolean matches(SingleRecipeInput inv, @NonNull Level level) {
        return this.input().test(inv.getItem(0));
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public @NonNull String group() {
        return "chopping_board";
    }

    public Ingredient getIngredient() {
        return this.input();
    }

    public ItemStackTemplate getResult() {
        return this.result();
    }

    public int getCutCount() {
        return cutCount;
    }

    public Identifier getModelId() {
        return modelId;
    }
}
