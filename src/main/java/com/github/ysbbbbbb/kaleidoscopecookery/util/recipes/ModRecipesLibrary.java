package com.github.ysbbbbbb.kaleidoscopecookery.util.recipes;

import com.github.ysbbbbbb.kaleidoscopecookery.compat.create.automation.init.AutomationCompat;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.*;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModRecipes;
import net.fabricmc.fabric.api.recipe.v1.sync.SynchronizedRecipes;
import net.fabricmc.loader.api.FabricLoader;
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
        if (FabricLoader.getInstance().isModLoaded("create")) {
            Minecraft minecraft = Minecraft.getInstance();
            ClientLevel level = minecraft.level;
            AutomationCompat.getTransformRecipeForSearch(level, List.copyOf(synchronizedRecipes.getAllOfType(ModRecipes.MILLSTONE_RECIPE)));
        }
        return List.copyOf(synchronizedRecipes.getAllOfType(ModRecipes.MILLSTONE_RECIPE));
    }

    public List<RecipeHolder<PotRecipe>> potRecipes() {
        return List.copyOf(synchronizedRecipes.getAllOfType(ModRecipes.POT_RECIPE));
    }

    public List<RecipeHolder<FlexPotRecipe>> flexPotRecipes() {
        return List.copyOf(synchronizedRecipes.getAllOfType(ModRecipes.FLEX_POT_RECIPE));
    }

    public List<RecipeHolder<SteamerRecipe>> steamerRecipes() {
        return List.copyOf(synchronizedRecipes.getAllOfType(ModRecipes.STEAMER_RECIPE));
    }

    public List<RecipeHolder<StockpotRecipe>> stockpotRecipes() {
        return List.copyOf(synchronizedRecipes.getAllOfType(ModRecipes.STOCKPOT_RECIPE));
    }

    public List<RecipeHolder<FlexStockpotRecipe>> flexStockpotRecipes() {
        return List.copyOf(synchronizedRecipes.getAllOfType(ModRecipes.FLEX_STOCKPOT_RECIPE));
    }

    public List<RecipeHolder<TeapotRecipe>> teapotRecipes() {
        return List.copyOf(synchronizedRecipes.getAllOfType(ModRecipes.TEAPOT_RECIPE));
    }
}
