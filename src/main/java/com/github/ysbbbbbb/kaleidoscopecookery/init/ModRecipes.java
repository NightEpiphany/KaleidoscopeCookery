package com.github.ysbbbbbb.kaleidoscopecookery.init;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.*;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.serializer.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class ModRecipes {
    public static final RecipeSerializer<PotRecipe> POT_SERIALIZER = new RecipeSerializer<>(PotRecipeSerializer.codec(), PotRecipeSerializer.streamCodec());
    public static final RecipeSerializer<ChoppingBoardRecipe> CHOPPING_BOARD_SERIALIZER = new RecipeSerializer<>(ChoppingBoardRecipeSerializer.codec(), ChoppingBoardRecipeSerializer.streamCodec());
    public static final RecipeSerializer<StockpotRecipe> STOCKPOT_SERIALIZER = new RecipeSerializer<>(StockpotRecipeSerializer.codec(), StockpotRecipeSerializer.streamCodec());
    public static final RecipeSerializer<SteamerRecipe> STEAMER_SERIALIZER = new RecipeSerializer<>(SteamerRecipeSerializer.codec(), SteamerRecipeSerializer.streamCodec());
    public static final RecipeSerializer<MillstoneRecipe> MILLSTONE_SERIALIZER = new RecipeSerializer<>(MillstoneRecipeSerializer.codec(), MillstoneRecipeSerializer.streamCodec());

    public static final RecipeType<PotRecipe> POT_RECIPE = simple(Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "pot"));
    public static final RecipeType<ChoppingBoardRecipe> CHOPPING_BOARD_RECIPE = simple(Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "chopping_board"));
    public static final RecipeType<StockpotRecipe> STOCKPOT_RECIPE = simple(Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "stockpot"));
    public static final RecipeType<SteamerRecipe> STEAMER_RECIPE = simple(Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "steamer"));
    public static final RecipeType<MillstoneRecipe> MILLSTONE_RECIPE = simple(Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "millstone"));

    public static void registerRecipes() {
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "pot"), POT_SERIALIZER);
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "chopping_board"), CHOPPING_BOARD_SERIALIZER);
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "stockpot"), STOCKPOT_SERIALIZER);
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "steamer"), STEAMER_SERIALIZER);
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "millstone"), MILLSTONE_SERIALIZER);

        Registry.register(BuiltInRegistries.RECIPE_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "pot"), POT_RECIPE);
        Registry.register(BuiltInRegistries.RECIPE_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "chopping_board"), CHOPPING_BOARD_RECIPE);
        Registry.register(BuiltInRegistries.RECIPE_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "stockpot"), STOCKPOT_RECIPE);
        Registry.register(BuiltInRegistries.RECIPE_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "steamer"), STEAMER_RECIPE);
        Registry.register(BuiltInRegistries.RECIPE_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "millstone"), MILLSTONE_RECIPE);
    }

    private static <T extends Recipe<?>> RecipeType<T> simple(final Identifier id) {
        return new RecipeType<>() {
            @Override
            public String toString() {
                return id.toString();
            }
        };
    }
}
