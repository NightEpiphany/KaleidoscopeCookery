package com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv;

import cc.cassian.rrv.api.ReliableRecipeViewerClientPlugin;
import cc.cassian.rrv.api.recipe.ItemView;
import cc.cassian.rrv.api.recipe.ReliableClientRecipe;
import cc.cassian.rrv.client.recipe.ClientRecipeManager;
import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv.chopping_board.ChoppingBoardViewRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv.millstone.MillstoneViewRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv.pot.PotViewRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv.steamer.SteamerViewRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv.stockpot.StockpotViewRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv.teapot.TeapotViewRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.soupbase.SoupBaseManager;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModRecipes;
import com.github.ysbbbbbb.kaleidoscopecookery.util.fluids.TeaFluidHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;

import java.util.List;

public class ModRRVPlugin implements ReliableRecipeViewerClientPlugin {
    @Override
    public void onIntegrationInitialize() {
        KaleidoscopeCookery.LOGGER.info("Initializing RRV integration");
        ItemView.addClientRecipeProvider(recipeList -> {
            addChoppingBoardRecipes(recipeList);
            addMillstoneRecipes(recipeList);
            addPotRecipes(recipeList);
            addSteamerRecipes(recipeList);
            addStockpotRecipes(recipeList);
            addTeapotRecipes(recipeList);
        });
    }

    private static void addChoppingBoardRecipes(List<ReliableClientRecipe> recipeList) {
        ClientRecipeManager.INSTANCE.getRecipesForType(ModRecipes.CHOPPING_BOARD_RECIPE).forEach(holder -> {
            var recipe = holder.value();
            recipeList.add(new ChoppingBoardViewRecipe(holder.id().identifier(), recipe.getIngredient(), recipe.getResult()));
        });
    }

    private static void addMillstoneRecipes(List<ReliableClientRecipe> recipeList) {
        ClientRecipeManager.INSTANCE.getRecipesForType(ModRecipes.MILLSTONE_RECIPE).forEach(holder -> {
            var recipe = holder.value();
            recipeList.add(new MillstoneViewRecipe(holder.id().identifier(), recipe.getIngredient(), recipe.getResult()));
        });
    }

    private static void addPotRecipes(List<ReliableClientRecipe> recipeList) {
        ClientRecipeManager.INSTANCE.getRecipesForType(ModRecipes.POT_RECIPE).forEach(holder -> {
            var recipe = holder.value();
            recipeList.add(new PotViewRecipe(holder.id().identifier(), recipe.ingredients(), recipe.carrier(), recipe.result()));
        });
    }

    private static void addSteamerRecipes(List<ReliableClientRecipe> recipeList) {
        ClientRecipeManager.INSTANCE.getRecipesForType(ModRecipes.STEAMER_RECIPE).forEach(holder -> {
            var recipe = holder.value();
            recipeList.add(new SteamerViewRecipe(holder.id().identifier(), recipe.getIngredient(), recipe.getResult()));
        });
    }

    private static void addStockpotRecipes(List<ReliableClientRecipe> recipeList) {
        ClientRecipeManager.INSTANCE.getRecipesForType(ModRecipes.STOCKPOT_RECIPE).forEach(holder -> {
            var recipe = holder.value();
            var soupBase = SoupBaseManager.getSoupBase(recipe.soupBase());
            if (soupBase == null) {
                KaleidoscopeCookery.LOGGER.warn("Skipping RRV stockpot recipe {} because soup base {} is not registered", holder.id().identifier(), recipe.soupBase());
                return;
            }

            ItemStack displayStack = soupBase.getDisplayStack();
            if (displayStack.isEmpty()) {
                KaleidoscopeCookery.LOGGER.warn("Skipping RRV stockpot recipe {} because soup base {} has no display stack", holder.id().identifier(), recipe.soupBase());
                return;
            }

            recipeList.add(new StockpotViewRecipe(
                    holder.id().identifier(),
                    recipe.ingredients(),
                    ItemStackTemplate.fromNonEmptyStack(displayStack),
                    recipe.carrier(),
                    recipe.result()
            ));
        });
    }

    private static void addTeapotRecipes(List<ReliableClientRecipe> recipeList) {
        ClientRecipeManager.INSTANCE.getRecipesForType(ModRecipes.TEAPOT_RECIPE).forEach(holder -> {
            var recipe = holder.value();
            ItemStack teaFluid = TeaFluidHelper.getFilledContainer(recipe.teaFluid());
            if (teaFluid.isEmpty()) {
                KaleidoscopeCookery.LOGGER.warn("Skipping RRV teapot recipe {} because tea fluid {} has no display container", holder.id().identifier(), recipe.teaFluid());
                return;
            }

            recipeList.add(new TeapotViewRecipe(
                    holder.id().identifier(),
                    recipe.ingredient(),
                    ItemStackTemplate.fromNonEmptyStack(teaFluid),
                    recipe.result()
            ));
        });
    }
}
