package com.github.ysbbbbbb.kaleidoscopecookery.compat.rei;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.rei.category.ReiMillstoneRecipeCategory;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.rei.category.ReiPotRecipeCategory;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.rei.category.ReiStockpotRecipeCategory;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.*;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModRecipes;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.plugins.REICommonPlugin;
import me.shedaniel.rei.api.common.registry.display.ServerDisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.plugin.common.displays.crafting.DefaultCustomDisplay;
import net.minecraft.resources.Identifier;

import java.util.Collections;
import java.util.Optional;

public class ModREICommonPlugin implements REICommonPlugin {

    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        registry.register(Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "plugin/millstone"), ReiMillstoneRecipeCategory.MillstoneRecipeDisplay.SERIALIZER);
        registry.register(Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "plugin/chopping_board"), DefaultCustomDisplay.SERIALIZER);
        registry.register(Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "plugin/steamer"), DefaultCustomDisplay.SERIALIZER);
        registry.register(Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "plugin/stockpot"), ReiStockpotRecipeCategory.StockpotRecipeDisplay.SERIALIZER);
        registry.register(Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "plugin/pot"), ReiPotRecipeCategory.PotRecipeDisplay.SERIALIZER);
    }

    @Override
    public void registerDisplays(ServerDisplayRegistry registry) {
       registry.beginRecipeFiller(MillstoneRecipe.class).filterType(ModRecipes.MILLSTONE_RECIPE).fill(ReiMillstoneRecipeCategory.MillstoneRecipeDisplay::new);
       registry.beginRecipeFiller(ChoppingBoardRecipe.class).filterType(ModRecipes.CHOPPING_BOARD_RECIPE).fill(i -> new DefaultCustomDisplay(EntryIngredients.ofIngredients(Collections.singletonList(i.value().getIngredient())), ReiUtil.ofItemStacks(i.value().getResult()), Optional.of(i.id().identifier())));
       registry.beginRecipeFiller(SteamerRecipe.class).filterType(ModRecipes.STEAMER_RECIPE).fill(i -> new DefaultCustomDisplay(EntryIngredients.ofIngredients(Collections.singletonList(i.value().getIngredient())), ReiUtil.ofItemStacks(i.value().getResult()), Optional.of(i.id().identifier())));
       registry.beginRecipeFiller(StockpotRecipe.class).filterType(ModRecipes.STOCKPOT_RECIPE).fill(ReiStockpotRecipeCategory.StockpotRecipeDisplay::new);
       registry.beginRecipeFiller(PotRecipe.class).filterType(ModRecipes.POT_RECIPE).fill(ReiPotRecipeCategory.PotRecipeDisplay::new);
    }
}
