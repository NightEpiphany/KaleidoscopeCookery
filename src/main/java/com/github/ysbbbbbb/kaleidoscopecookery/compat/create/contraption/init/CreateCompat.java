package com.github.ysbbbbbb.kaleidoscopecookery.compat.create.contraption.init;

import com.github.ysbbbbbb.kaleidoscopecookery.compat.create.contraption.millstone.MillstoneCompat;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.MillstoneRecipe;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.level.Level;

import java.util.List;

public class CreateCompat {
    public static final String ID = "create";
    public static boolean IS_LOADED = false;

    public static void init() {
        if (FabricLoader.getInstance().isModLoaded("create")) {
            IS_LOADED = true;
            MillstoneCompat.register();
        }
    }

    public static void getTransformRecipeForSearch(Level level, List<MillstoneRecipe> recipes) {
        if (IS_LOADED) {
            MillstoneCompat.getTransformRecipeForSearch(level, recipes);
        }
    }
}
