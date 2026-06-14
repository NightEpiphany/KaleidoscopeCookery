package com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv;

import cc.cassian.rrv.api.ReliableRecipeViewerPlugin;
import cc.cassian.rrv.common.recipe.ServerRecipeManager;
import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModRecipes;

public class ModRRVCommonPlugin implements ReliableRecipeViewerPlugin {
    @Override
    public void onIntegrationInitialize() {
        KaleidoscopeCookery.LOGGER.info("Registering RRV recipe synchronization");
        ServerRecipeManager.INSTANCE.synchronizeRecipeType(ModRecipes.POT_SERIALIZER, ModRecipes.POT_RECIPE);
        ServerRecipeManager.INSTANCE.synchronizeRecipeType(ModRecipes.FLEX_POT_SERIALIZER, ModRecipes.FLEX_POT_RECIPE);
        ServerRecipeManager.INSTANCE.synchronizeRecipeType(ModRecipes.CHOPPING_BOARD_SERIALIZER, ModRecipes.CHOPPING_BOARD_RECIPE);
        ServerRecipeManager.INSTANCE.synchronizeRecipeType(ModRecipes.STOCKPOT_SERIALIZER, ModRecipes.STOCKPOT_RECIPE);
        ServerRecipeManager.INSTANCE.synchronizeRecipeType(ModRecipes.FLEX_STOCKPOT_SERIALIZER, ModRecipes.FLEX_STOCKPOT_RECIPE);
        ServerRecipeManager.INSTANCE.synchronizeRecipeType(ModRecipes.STEAMER_SERIALIZER, ModRecipes.STEAMER_RECIPE);
        ServerRecipeManager.INSTANCE.synchronizeRecipeType(ModRecipes.MILLSTONE_SERIALIZER, ModRecipes.MILLSTONE_RECIPE);
        ServerRecipeManager.INSTANCE.synchronizeRecipeType(ModRecipes.TEAPOT_SERIALIZER, ModRecipes.TEAPOT_RECIPE);
    }
}
