package com.github.ysbbbbbb.kaleidoscopecookery.compat.jei.recipes;

import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.*;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModRecipes;
import net.fabricmc.fabric.api.recipe.v1.sync.SynchronizedRecipes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;

public class ModRecipesLibrary {
    public static final ModRecipesLibrary INSTANCE = new ModRecipesLibrary();

    private final SynchronizedRecipes synchronizedRecipes;

    private ModRecipesLibrary() {
        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel level = minecraft.level;

        if (level != null) {
            synchronizedRecipes = level.recipeAccess().getSynchronizedRecipes();
        } else {
            throw new NullPointerException("minecraft world must not be null.");
        }

    }

    public List<RecipeHolder<ChoppingBoardRecipe>> choppingBoardRecipes() {
        return List.copyOf(synchronizedRecipes.getAllOfType(ModRecipes.CHOPPING_BOARD_RECIPE));
    }

    public List<RecipeHolder<MillstoneRecipe>> millstoneRecipes() {
        return List.copyOf(synchronizedRecipes.getAllOfType(ModRecipes.MILLSTONE_RECIPE));
    }

    public List<RecipeHolder<PotRecipe>> potRecipes() {
        return List.copyOf(synchronizedRecipes.getAllOfType(ModRecipes.POT_RECIPE));
    }

    public List<RecipeHolder<SteamerRecipe>> steamerRecipes() {
        return List.copyOf(synchronizedRecipes.getAllOfType(ModRecipes.STEAMER_RECIPE));
    }

    public List<RecipeHolder<StockpotRecipe>> stockpotRecipes() {
        return List.copyOf(synchronizedRecipes.getAllOfType(ModRecipes.STOCKPOT_RECIPE));
    }
}
