package com.github.ysbbbbbb.kaleidoscopecookery.compat.jei;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.jei.category.*;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.jei.recipes.ModRecipesLibrary;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.fabricmc.fabric.api.recipe.v1.sync.RecipeSynchronization;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

@JeiPlugin
public class ModJeiPlugin implements IModPlugin {
    private static final Identifier UID = Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "jei");

    @Override
    public void registerCategories(@NonNull IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new PotRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new ChoppingBoardRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new StockpotRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new MillstoneRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new SteamerRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(@NonNull IRecipeRegistration registration) {
        registration.addRecipes(PotRecipeCategory.TYPE, ModRecipesLibrary.INSTANCE.potRecipes());
        registration.addRecipes(ChoppingBoardRecipeCategory.TYPE, ModRecipesLibrary.INSTANCE.choppingBoardRecipes());
        registration.addRecipes(StockpotRecipeCategory.TYPE, ModRecipesLibrary.INSTANCE.stockpotRecipes());
        registration.addRecipes(MillstoneRecipeCategory.TYPE, ModRecipesLibrary.INSTANCE.millstoneRecipes());
        registration.addRecipes(SteamerRecipeCategory.TYPE, ModRecipesLibrary.INSTANCE.steamerRecipes());
    }

    @Override
    public void registerRecipeCatalysts(@NonNull IRecipeCatalystRegistration registration) {
        registration.addCraftingStation(PotRecipeCategory.TYPE, ModItems.POT.getDefaultInstance());
        registration.addCraftingStation(ChoppingBoardRecipeCategory.TYPE, ModItems.CHOPPING_BOARD.getDefaultInstance());
        registration.addCraftingStation(StockpotRecipeCategory.TYPE, ModItems.STOCKPOT.getDefaultInstance());
        registration.addCraftingStation(MillstoneRecipeCategory.TYPE, ModItems.MILLSTONE.getDefaultInstance());
        registration.addCraftingStation(SteamerRecipeCategory.TYPE, ModItems.STEAMER.getDefaultInstance());
    }

    public static void syncRecipes() {
        RecipeSynchronization.synchronizeRecipeSerializer(ModRecipes.POT_SERIALIZER);
        RecipeSynchronization.synchronizeRecipeSerializer(ModRecipes.CHOPPING_BOARD_SERIALIZER);
        RecipeSynchronization.synchronizeRecipeSerializer(ModRecipes.STOCKPOT_SERIALIZER);
        RecipeSynchronization.synchronizeRecipeSerializer(ModRecipes.STEAMER_SERIALIZER);
        RecipeSynchronization.synchronizeRecipeSerializer(ModRecipes.MILLSTONE_SERIALIZER);
    }

    @Override
    public @NotNull Identifier getPluginUid() {
        return UID;
    }
}
