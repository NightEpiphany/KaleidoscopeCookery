package com.github.ysbbbbbb.kaleidoscopecookery.init;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.*;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.serializer.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.NotNull;

public final class ModRecipes {
    public static final RecipeSerializer<PotRecipe> POT_SERIALIZER = new PotRecipeSerializer();
    public static final RecipeSerializer<FlexPotRecipe> FLEX_POT_SERIALIZER = new FlexPotRecipeSerializer();
    public static final RecipeSerializer<ChoppingBoardRecipe> CHOPPING_BOARD_SERIALIZER = new ChoppingBoardRecipeSerializer();
    public static final RecipeSerializer<StockpotRecipe> STOCKPOT_SERIALIZER = new StockpotRecipeSerializer();
    public static final RecipeSerializer<FlexStockpotRecipe> FLEX_STOCKPOT_SERIALIZER = new FlexStockpotRecipeSerializer();
    public static final RecipeSerializer<MillstoneRecipe> MILLSTONE_SERIALIZER = new MillstoneRecipeSerializer();
    public static final RecipeSerializer<SteamerRecipe> STEAMER_SERIALIZER = new SteamerRecipeSerializer();
    public static final RecipeSerializer<TeapotRecipe> TEAPOT_SERIALIZER = new TeapotRecipeSerializer();
    public static final RecipeSerializer<RiceBowlRecipe> RICE_BOWL_SERIALIZER = new RiceBowlRecipeSerializer();
    public static final RecipeSerializer<BambooTrayRecipe> BAMBOO_TRAY_SERIALIZER = new BambooTrayRecipeSerializer();

    public static final RecipeType<PotRecipe> POT_RECIPE = simple(new ResourceLocation(KaleidoscopeCookery.MOD_ID, "pot"));
    public static final RecipeType<FlexPotRecipe> FLEX_POT_RECIPE = simple(new ResourceLocation(KaleidoscopeCookery.MOD_ID, "flex_pot"));
    public static final RecipeType<ChoppingBoardRecipe> CHOPPING_BOARD_RECIPE = simple(new ResourceLocation(KaleidoscopeCookery.MOD_ID, "chopping_board"));
    public static final RecipeType<StockpotRecipe> STOCKPOT_RECIPE = simple(new ResourceLocation(KaleidoscopeCookery.MOD_ID, "stockpot"));
    public static final RecipeType<FlexStockpotRecipe> FLEX_STOCKPOT_RECIPE = simple(new ResourceLocation(KaleidoscopeCookery.MOD_ID, "flex_stockpot"));
    public static final RecipeType<MillstoneRecipe> MILLSTONE_RECIPE = simple(new ResourceLocation(KaleidoscopeCookery.MOD_ID, "millstone"));
    public static final RecipeType<SteamerRecipe> STEAMER_RECIPE = simple(new ResourceLocation(KaleidoscopeCookery.MOD_ID, "steamer"));
    public static final RecipeType<TeapotRecipe> TEAPOT_RECIPE = simple(new ResourceLocation(KaleidoscopeCookery.MOD_ID, "teapot"));
    public static final RecipeType<BambooTrayRecipe> BAMBOO_TRAY_RECIPE = simple(new ResourceLocation(KaleidoscopeCookery.MOD_ID, "bamboo_tray"));

    public static void registerRecipes() {
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, new ResourceLocation(KaleidoscopeCookery.MOD_ID, "pot"), POT_SERIALIZER);
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, new ResourceLocation(KaleidoscopeCookery.MOD_ID, "flex_pot"), FLEX_POT_SERIALIZER);
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, new ResourceLocation(KaleidoscopeCookery.MOD_ID, "chopping_board"), CHOPPING_BOARD_SERIALIZER);
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, new ResourceLocation(KaleidoscopeCookery.MOD_ID, "stockpot"), STOCKPOT_SERIALIZER);
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, new ResourceLocation(KaleidoscopeCookery.MOD_ID, "flex_stockpot"), FLEX_STOCKPOT_SERIALIZER);
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, new ResourceLocation(KaleidoscopeCookery.MOD_ID, "millstone"), MILLSTONE_SERIALIZER);
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, new ResourceLocation(KaleidoscopeCookery.MOD_ID, "steamer"), STEAMER_SERIALIZER);
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, new ResourceLocation(KaleidoscopeCookery.MOD_ID, "teapot"), TEAPOT_SERIALIZER);
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, new ResourceLocation(KaleidoscopeCookery.MOD_ID, "bamboo_tray"), BAMBOO_TRAY_SERIALIZER);
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, new ResourceLocation(KaleidoscopeCookery.MOD_ID, "rice_bowl"), RICE_BOWL_SERIALIZER);

        Registry.register(BuiltInRegistries.RECIPE_TYPE, new ResourceLocation(KaleidoscopeCookery.MOD_ID, "pot"), POT_RECIPE);
        Registry.register(BuiltInRegistries.RECIPE_TYPE, new ResourceLocation(KaleidoscopeCookery.MOD_ID, "flex_pot"), FLEX_POT_RECIPE);
        Registry.register(BuiltInRegistries.RECIPE_TYPE, new ResourceLocation(KaleidoscopeCookery.MOD_ID, "chopping_board"), CHOPPING_BOARD_RECIPE);
        Registry.register(BuiltInRegistries.RECIPE_TYPE, new ResourceLocation(KaleidoscopeCookery.MOD_ID, "stockpot"), STOCKPOT_RECIPE);
        Registry.register(BuiltInRegistries.RECIPE_TYPE, new ResourceLocation(KaleidoscopeCookery.MOD_ID, "flex_stockpot"), FLEX_STOCKPOT_RECIPE);
        Registry.register(BuiltInRegistries.RECIPE_TYPE, new ResourceLocation(KaleidoscopeCookery.MOD_ID, "millstone"), MILLSTONE_RECIPE);
        Registry.register(BuiltInRegistries.RECIPE_TYPE, new ResourceLocation(KaleidoscopeCookery.MOD_ID, "steamer"), STEAMER_RECIPE);
        Registry.register(BuiltInRegistries.RECIPE_TYPE, new ResourceLocation(KaleidoscopeCookery.MOD_ID, "teapot"), TEAPOT_RECIPE);
        Registry.register(BuiltInRegistries.RECIPE_TYPE, new ResourceLocation(KaleidoscopeCookery.MOD_ID, "bamboo_tray"), BAMBOO_TRAY_RECIPE);
    }

    private static <T extends Recipe<?>> RecipeType<T> simple(final ResourceLocation id) {
        return new RecipeType<>() {
            @Override
            public String toString() {
                return id.toString();
            }
        };
    }
}
