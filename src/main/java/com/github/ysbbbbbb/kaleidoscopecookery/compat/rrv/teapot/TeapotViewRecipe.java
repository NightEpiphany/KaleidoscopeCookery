package com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv.teapot;

import cc.cassian.rrv.api.recipe.ReliableClientRecipe;
import cc.cassian.rrv.api.recipe.ReliableClientRecipeType;
import cc.cassian.rrv.common.recipe.inventory.RecipeViewMenu;
import cc.cassian.rrv.common.recipe.inventory.SlotContent;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.TeapotRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public class TeapotViewRecipe implements ReliableClientRecipe {
    private final Ingredient ingredient;
    private final ItemStack result;
    private final ItemStack teaFluid;
    private final int ingredientCount;

    public TeapotViewRecipe(TeapotServerRecipe recipe) {
        this.ingredient = recipe.getIngredient();
        this.result = recipe.getResult();
        this.teaFluid = recipe.getTeaFluid();
        this.ingredientCount = recipe.getIngredientCount();
    }

    @Override
    public ReliableClientRecipeType getViewType() {
        return TeapotViewType.INSTANCE;
    }

    @Override
    public void bindSlots(RecipeViewMenu.SlotFillContext slotFillContext) {
        var checkedIngredient = this.ingredient.items().map(s -> s.value().getDefaultInstance().copyWithCount(this.ingredientCount)).toList();
        slotFillContext.bindSlot(0, SlotContent.of(this.teaFluid));
        slotFillContext.bindSlot(1, SlotContent.of(checkedIngredient));
        slotFillContext.bindSlot(2, SlotContent.of(this.result.copyWithCount(TeapotRecipe.OUTPUT_COUNT)));
    }

    @Override
    public List<SlotContent> getIngredients() {
        return List.of(SlotContent.of(this.teaFluid), SlotContent.of(this.ingredient));
    }

    @Override
    public List<SlotContent> getResults() {
        return List.of(SlotContent.of(this.result));
    }
}
