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
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.advancements.Advancement;
import net.minecraft.world.item.crafting.Recipe;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeGenerator extends FabricRecipeProvider {
    public ModRecipeGenerator(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected @NonNull RecipeProvider createRecipeProvider(HolderLookup.@NonNull Provider registries, BootstrapContext<Recipe<?>> recipeOutput, BootstrapContext<Advancement> advancementOutput) {
        RecipeOutput output = new RecipeOutput() {
            @Override public void accept(net.minecraft.resources.ResourceKey<Recipe<?>> key, Recipe<?> recipe, net.minecraft.advancements.AdvancementHolder advancement) { recipeOutput.register(key, recipe); }
            @Override public net.minecraft.advancements.Advancement.Builder advancement() { return net.minecraft.advancements.Advancement.Builder.advancement(); }
            @Override public <S> net.minecraft.core.HolderGetter<S> lookup(net.minecraft.resources.ResourceKey<? extends net.minecraft.core.Registry<? extends S>> key) { return (net.minecraft.core.HolderGetter<S>) registries.lookupOrThrow(key); }
            @Override public <S> java.util.stream.Stream<net.minecraft.core.Holder.Reference<S>> listContextElements(net.minecraft.resources.ResourceKey<? extends net.minecraft.core.Registry<? extends S>> key) { return java.util.stream.Stream.empty(); }
        };
        return new ModRecipeProvider(registries, output) {
            private final List<ModRecipeProvider> providers = List.of(
                    new ChoppingBoardRecipeProvider(registries, output),
                    new DecorationRecipeProvider(registries, output),
                    new FoodBiteRecipeProvider(registries, output),
                    new PotRecipeProvider(registries, output),
                    new ShapedRecipeProvider(registries, output),
                    new ShapelessRecipeProvider(registries, output),
                    new SimpleCookingRecipeProvider(registries, output),
                    new SimplePotRecipeProvider(registries, output),
                    new StockpotRecipeProvider(registries, output),
                    new MillstoneRecipeProvider(registries, output),
                    new SteamerRecipeProvider(registries, output),
                    new TeapotRecipeProvider(registries, output)
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
