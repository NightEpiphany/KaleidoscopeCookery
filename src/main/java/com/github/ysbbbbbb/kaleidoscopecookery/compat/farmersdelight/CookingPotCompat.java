package com.github.ysbbbbbb.kaleidoscopecookery.compat.farmersdelight;

import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.StockpotRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.serializer.StockpotRecipeSerializer;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModEvents;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeAccess;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import vectorwing.farmersdelight.common.crafting.CookingPotRecipe;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;
import vectorwing.farmersdelight.refabricated.inventory.ItemStackHandler;
import vectorwing.farmersdelight.refabricated.inventory.RecipeWrapper;

import java.util.List;

public class CookingPotCompat {
    static void getTransformRecipeForJei(Level level, List<RecipeHolder<StockpotRecipe>> recipes) {
        if (level == null) {
            return;
        }
        level.recipeAccess().getSynchronizedRecipes().getAllOfType(ModRecipeTypes.COOKING.get()).forEach(recipe -> recipes.add(transformRecipe(recipe)));
    }

    static RecipeHolder<StockpotRecipe> transformRecipe(RecipeHolder<CookingPotRecipe> holder) {
        CookingPotRecipe cookingPotRecipe = holder.value();
        // 默认全部使用水作为汤底
        StockpotRecipe recipe = new StockpotRecipe(
                (NonNullList<Ingredient>) cookingPotRecipe.input(),
                cookingPotRecipe.result(),
                cookingPotRecipe.getCookTime(),
                cookingPotRecipe.container()
        );
        return new RecipeHolder<>(holder.id(), recipe);
    }


    static void afterStockpotRecipeMatch() {
        ModEvents.STOCKPOT_RECIPE_POST.register(event -> {
            RecipeHolder<StockpotRecipe> rawOutput = event.getRawOutput();
            RecipeAccess recipeManager = event.getLevel().recipeAccess();

            if (rawOutput.id() != StockpotRecipeSerializer.EMPTY_RECIPE_KEY) {
                return;
            }

            // 开始寻找农夫乐事的厨锅配方进行匹配
            List<ItemStack> items = event.getInput().getInputs();
            ItemStackHandler inputs = new ItemStackHandler(items.size());
            for (int i = 0; i < items.size(); i++) {
                inputs.setStackInSlot(i, items.get(i));
            }
            RecipeWrapper wrapper = new RecipeWrapper(inputs);
            recipeManager.getSynchronizedRecipes().getAllMatches(ModRecipeTypes.COOKING.get(), wrapper, event.getLevel()).forEach(recipe -> {
                // 如果找到匹配的农夫乐事厨锅配方，则将其转换为本模组汤锅配方
                event.setOutput(transformRecipe(recipe));
            });
        });
    }
}
