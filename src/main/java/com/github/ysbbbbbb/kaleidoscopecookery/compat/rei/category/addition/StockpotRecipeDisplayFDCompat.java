package com.github.ysbbbbbb.kaleidoscopecookery.compat.rei.category.addition;

import com.github.ysbbbbbb.kaleidoscopecookery.compat.farmersdelight.CookingPotCompat;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.rei.category.ReiStockpotRecipeCategory;
import me.shedaniel.rei.api.common.registry.display.ServerDisplayRegistry;
import net.minecraft.world.item.crafting.RecipeHolder;
import vectorwing.farmersdelight.common.crafting.CookingPotRecipe;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;

public class StockpotRecipeDisplayFDCompat extends ReiStockpotRecipeCategory.StockpotRecipeDisplay {
    public StockpotRecipeDisplayFDCompat(RecipeHolder<CookingPotRecipe> holder) {
        super(CookingPotCompat.transformRecipe(holder));
    }

    public static void additionalRecipeDisplay(ServerDisplayRegistry registry) {
        registry.beginRecipeFiller(CookingPotRecipe.class).filterType(ModRecipeTypes.COOKING.get()).fill(StockpotRecipeDisplayFDCompat::new);
    }
}
