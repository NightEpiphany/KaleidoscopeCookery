package com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv;

import cc.cassian.rrv.api.ReliableRecipeViewerPlugin;
import cc.cassian.rrv.api.recipe.ItemView;
import cc.cassian.rrv.common.recipe.ServerRecipeManager;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv.chopping_board.ChoppingBoardViewRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv.chopping_board.ChoppingBoardServerRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv.millstone.MillstoneServerRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv.millstone.MillstoneViewRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv.pot.PotServerRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv.pot.PotViewRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv.steamer.SteamerServerRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv.steamer.SteamerViewRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv.stockpot.StockpotServerRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv.stockpot.StockpotViewRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv.teapot.TeapotServerRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv.teapot.TeapotViewRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModRecipes;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.soupbase.SoupBaseManager;
import com.github.ysbbbbbb.kaleidoscopecookery.util.fluids.TeaFluidHelper;
import net.minecraft.world.item.ItemStackTemplate;

import java.util.Collections;

public class ModRRVPlugin implements ReliableRecipeViewerPlugin {
    @Override
    public void onIntegrationInitialize() {
        ItemView.addServerRecipeProvider(list -> {
            ServerRecipeManager.INSTANCE.getRecipesForType(ModRecipes.CHOPPING_BOARD_RECIPE).forEach(recipe -> list.add(new ChoppingBoardServerRecipe(recipe.getResult(), recipe.getIngredient())));
            ServerRecipeManager.INSTANCE.getRecipesForType(ModRecipes.MILLSTONE_RECIPE).forEach(recipe -> list.add(new MillstoneServerRecipe(recipe.getResult(), recipe.getIngredient())));
            ServerRecipeManager.INSTANCE.getRecipesForType(ModRecipes.POT_RECIPE).forEach(recipe -> list.add(new PotServerRecipe(recipe.result(), recipe.ingredients(), recipe.carrier())));
            ServerRecipeManager.INSTANCE.getRecipesForType(ModRecipes.STEAMER_RECIPE).forEach(recipe -> list.add(new SteamerServerRecipe(recipe.getResult(), recipe.getIngredient())));
            ServerRecipeManager.INSTANCE.getRecipesForType(ModRecipes.STOCKPOT_RECIPE).forEach(recipe -> {
                ItemStackTemplate soupBase = ItemStackTemplate.fromNonEmptyStack(SoupBaseManager.getSoupBase(recipe.soupBase()).getDisplayStack());
                list.add(new StockpotServerRecipe(recipe.result(), recipe.ingredients(), soupBase, recipe.carrier()));
            });
            ServerRecipeManager.INSTANCE.getRecipesForType(ModRecipes.TEAPOT_RECIPE).forEach(recipe -> {
                ItemStackTemplate teaFluid = ItemStackTemplate.fromNonEmptyStack(TeaFluidHelper.getFilledContainer(recipe.teaFluid()));
                list.add(new TeapotServerRecipe(recipe.result(), recipe.ingredient(), teaFluid, recipe.ingredientCount()));
            });
        });

        ItemView.addClientRecipeWrapper(ChoppingBoardServerRecipe.TYPE, i -> Collections.singletonList(new ChoppingBoardViewRecipe(i)));
        ItemView.addClientRecipeWrapper(MillstoneServerRecipe.TYPE, i -> Collections.singletonList(new MillstoneViewRecipe(i)));
        ItemView.addClientRecipeWrapper(PotServerRecipe.TYPE, i -> Collections.singletonList(new PotViewRecipe(i)));
        ItemView.addClientRecipeWrapper(SteamerServerRecipe.TYPE, i -> Collections.singletonList(new SteamerViewRecipe(i)));
        ItemView.addClientRecipeWrapper(StockpotServerRecipe.TYPE, i -> Collections.singletonList(new StockpotViewRecipe(i)));
        ItemView.addClientRecipeWrapper(TeapotServerRecipe.TYPE, i -> Collections.singletonList(new TeapotViewRecipe(i)));
    }
}
