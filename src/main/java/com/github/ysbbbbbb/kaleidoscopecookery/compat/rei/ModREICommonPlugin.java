package com.github.ysbbbbbb.kaleidoscopecookery.compat.rei;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.rei.category.*;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.*;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModRecipes;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.plugins.REICommonPlugin;
import me.shedaniel.rei.api.common.registry.display.ServerDisplayRegistry;
import net.minecraft.resources.Identifier;

public class ModREICommonPlugin implements REICommonPlugin {
    private static final Identifier MILLSTONE_ID = Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "plugin/millstone");
    private static final Identifier CHOPPING_BOARD_ID = Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "plugin/chopping_board");
    private static final Identifier STOCKPOT_ID = Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "plugin/stockpot");
    private static final Identifier POT_ID = Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "plugin/pot");
    private static final Identifier STEAMER_ID = Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "plugin/steamer");

    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        registerSerializerIfNeeded(registry);
    }

    @Override
    public void registerDisplays(ServerDisplayRegistry registry) {
       registerSerializerIfNeeded(DisplaySerializerRegistry.getInstance());
       registry.beginRecipeFiller(MillstoneRecipe.class).filterType(ModRecipes.MILLSTONE_RECIPE).fill(ReiMillstoneRecipeCategory.MillstoneRecipeDisplay::new);
       registry.beginRecipeFiller(ChoppingBoardRecipe.class).filterType(ModRecipes.CHOPPING_BOARD_RECIPE).fill(ReiChoppingBoardRecipeCategory.ChoppingBoardRecipeDisplay::new);
       registry.beginRecipeFiller(SteamerRecipe.class).filterType(ModRecipes.STEAMER_RECIPE).fill(ReiSteamerRecipeCategory.SteamerRecipeDisplay::new);
       registry.beginRecipeFiller(StockpotRecipe.class).filterType(ModRecipes.STOCKPOT_RECIPE).fill(ReiStockpotRecipeCategory.StockpotRecipeDisplay::new);
       registry.beginRecipeFiller(PotRecipe.class).filterType(ModRecipes.POT_RECIPE).fill(ReiPotRecipeCategory.PotRecipeDisplay::new);
    }

    private static void registerSerializerIfNeeded(DisplaySerializerRegistry registry) {
        if (!registry.isRegistered(ReiMillstoneRecipeCategory.MillstoneRecipeDisplay.SERIALIZER)) {
            registry.register(MILLSTONE_ID, ReiMillstoneRecipeCategory.MillstoneRecipeDisplay.SERIALIZER);
        }
        if (!registry.isRegistered(ReiSteamerRecipeCategory.SteamerRecipeDisplay.SERIALIZER)) {
            registry.register(STEAMER_ID, ReiSteamerRecipeCategory.SteamerRecipeDisplay.SERIALIZER);
        }
        if (!registry.isRegistered(ReiChoppingBoardRecipeCategory.ChoppingBoardRecipeDisplay.SERIALIZER)) {
            registry.register(CHOPPING_BOARD_ID, ReiChoppingBoardRecipeCategory.ChoppingBoardRecipeDisplay.SERIALIZER);
        }
        if (!registry.isRegistered(ReiStockpotRecipeCategory.StockpotRecipeDisplay.SERIALIZER)) {
            registry.register(STOCKPOT_ID, ReiStockpotRecipeCategory.StockpotRecipeDisplay.SERIALIZER);
        }
        if (!registry.isRegistered(ReiPotRecipeCategory.PotRecipeDisplay.SERIALIZER)) {
            registry.register(POT_ID, ReiPotRecipeCategory.PotRecipeDisplay.SERIALIZER);
        }
    }
}
