package com.github.ysbbbbbb.kaleidoscopecookery.datagen;

import com.github.ysbbbbbb.kaleidoscopecookery.datagen.recipe.ChoppingBoardRecipeProvider;
import com.github.ysbbbbbb.kaleidoscopecookery.datagen.recipe.DecorationRecipeProvider;
import com.github.ysbbbbbb.kaleidoscopecookery.datagen.recipe.FoodBiteRecipeProvider;
import com.github.ysbbbbbb.kaleidoscopecookery.datagen.recipe.MillstoneRecipeProvider;
import com.github.ysbbbbbb.kaleidoscopecookery.datagen.recipe.ModRecipeProvider;
import com.github.ysbbbbbb.kaleidoscopecookery.datagen.recipe.PotRecipeProvider;
import com.github.ysbbbbbb.kaleidoscopecookery.datagen.recipe.ShapedRecipeProvider;
import com.github.ysbbbbbb.kaleidoscopecookery.datagen.recipe.ShapelessRecipeProvider;
import com.github.ysbbbbbb.kaleidoscopecookery.datagen.recipe.SimpleCookingRecipeProvider;
import com.github.ysbbbbbb.kaleidoscopecookery.datagen.recipe.SimplePotRecipeProvider;
import com.github.ysbbbbbb.kaleidoscopecookery.datagen.recipe.SteamerRecipeProvider;
import com.github.ysbbbbbb.kaleidoscopecookery.datagen.recipe.StockpotRecipeProvider;
import com.github.ysbbbbbb.kaleidoscopecookery.datagen.recipe.TeapotRecipeProvider;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.crafting.Recipe;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeGenerator extends FabricRecipeProvider {
    public ModRecipeGenerator(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected @NonNull RecipeProvider createRecipeProvider(HolderLookup.@NonNull Provider registries, @NonNull BootstrapContext<Recipe<?>> recipes, @NonNull BootstrapContext<Advancement> advancements) {
        return new ModRecipeProvider(recipes, advancements) {
            private final List<ModRecipeProvider> providers = List.of(
                    new ChoppingBoardRecipeProvider(recipes, advancements),
                    new DecorationRecipeProvider(recipes, advancements),
                    new FoodBiteRecipeProvider(recipes, advancements),
                    new PotRecipeProvider(recipes, advancements),
                    new ShapedRecipeProvider(recipes, advancements),
                    new ShapelessRecipeProvider(recipes, advancements),
                    new SimpleCookingRecipeProvider(recipes, advancements),
                    new SimplePotRecipeProvider(recipes, advancements),
                    new StockpotRecipeProvider(recipes, advancements),
                    new MillstoneRecipeProvider(recipes, advancements),
                    new SteamerRecipeProvider(recipes, advancements),
                    new TeapotRecipeProvider(recipes, advancements)
            );

            @Override
            public void buildRecipes(RecipeOutput consumer) {
                netheriteSmithing(ModItems.DIAMOND_KITCHEN_KNIFE, RecipeCategory.TOOLS, ModItems.NETHERITE_KITCHEN_KNIFE);
                for (ModRecipeProvider provider : providers) {
                    provider.buildRecipes(consumer);
                }
            }
        };
    }

    @Override
    public @NonNull String getName() {
        return "recipe";
    }
}
