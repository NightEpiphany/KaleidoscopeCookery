package com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv.millstone;

import cc.cassian.rrv.api.recipe.ReliableClientRecipe;
import cc.cassian.rrv.api.recipe.ReliableClientRecipeType;
import cc.cassian.rrv.common.recipe.inventory.RecipeViewMenu;
import cc.cassian.rrv.common.recipe.inventory.SlotContent;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public class MillstoneViewRecipe implements ReliableClientRecipe {
    private final Ingredient ingredient;
    private final ItemStackTemplate result;

    public MillstoneViewRecipe(MillstoneServerRecipe recipe) {
        this.ingredient = recipe.getIngredient();
        this.result = recipe.getResult();
    }

    @Override
    public ReliableClientRecipeType getViewType() {
        return MillstoneViewType.INSTANCE;
    }

    @Override
    public void bindSlots(RecipeViewMenu.SlotFillContext slotFillContext) {
        slotFillContext.bindSlot(0, SlotContent.of(this.ingredient));
        slotFillContext.bindSlot(1, SlotContent.of(this.result));
    }

    @Override
    public List<SlotContent> getIngredients() {
        return List.of(SlotContent.of(this.ingredient));
    }

    @Override
    public List<SlotContent> getResults() {
        return List.of(SlotContent.of(this.result));
    }
}
