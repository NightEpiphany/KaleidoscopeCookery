package com.github.ysbbbbbb.kaleidoscopecookery.compat.rei;

import com.github.ysbbbbbb.kaleidoscopecookery.compat.rei.category.*;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;

public class ModREIClientPlugin implements REIClientPlugin {

    @Override
    public void registerCategories(CategoryRegistry registry) {
        ReiChoppingBoardRecipeCategory.registerCategories(registry);
        ReiMillstoneRecipeCategory.registerCategories(registry);
        ReiPotRecipeCategory.registerCategories(registry);
        ReiFlexPotRecipeCategory.registerCategories(registry);
        ReiStockpotRecipeCategory.registerCategories(registry);
        ReiFlexStockpotRecipeCategory.registerCategories(registry);
        ReiSteamerRecipeCategory.registerCategories(registry);
        ReiTeapotRecipeCategory.registerCategories(registry);
    }
}
