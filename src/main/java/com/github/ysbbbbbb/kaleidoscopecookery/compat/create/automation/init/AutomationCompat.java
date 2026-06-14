package com.github.ysbbbbbb.kaleidoscopecookery.compat.create.automation.init;

import com.github.ysbbbbbb.kaleidoscopecookery.compat.create.automation.millstone.MillstoneCompat;
import com.github.ysbbbbbb.kaleidoscopecookery.config.GeneralConfig;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.MillstoneRecipe;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;

import java.util.List;

public class AutomationCompat {
    public static final String ID = "create";

    public static boolean AUTOMATION_LOADED = false;

    public static void init() {
        if (FabricLoader.getInstance().isModLoaded(ID) && GeneralConfig.CREATE_AUTOMATION_ENABLED.get()) {
            AUTOMATION_LOADED = true;
            KitchenAutomationPlugin.init();
            MillstoneCompat.register();
        }
    }

    public static void getTransformRecipeForSearch(Level level, List<RecipeHolder<MillstoneRecipe>> recipes) {
        if (AUTOMATION_LOADED) {
            MillstoneCompat.getTransformRecipeForSearch(level, recipes);
        }
    }
}
