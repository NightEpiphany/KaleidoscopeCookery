package com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv.chopping_board;

import cc.cassian.rrv.api.recipe.ReliableClientRecipe;
import cc.cassian.rrv.api.recipe.ReliableClientRecipeType;
import cc.cassian.rrv.common.recipe.inventory.RecipeViewMenu;
import cc.cassian.rrv.common.recipe.inventory.SlotContent;

import java.util.Collections;
import java.util.List;

public class ChoppingBoardViewRecipe implements ReliableClientRecipe {
    private final SlotContent ingredient;
    private final SlotContent result;
    private final SlotContent tool;

    public ChoppingBoardViewRecipe(ChoppingBoardServerRecipe serverRecipe) {
        this.ingredient = SlotContent.of(serverRecipe.getIngredient());
        this.result = SlotContent.of(serverRecipe.getResult());
        this.tool = SlotContent.of(serverRecipe.getTool());
    }
    @Override
    public ReliableClientRecipeType getViewType() {
        return ChoppingBoardViewType.INSTANCE;
    }

    @Override
    public void bindSlots(RecipeViewMenu.SlotFillContext slotFillContext) {
        slotFillContext.bindSlot(0, this.ingredient);
        slotFillContext.bindSlot(1, this.tool);
        slotFillContext.bindSlot(2, this.result);
    }

    @Override
    public List<SlotContent> getIngredients() {
        return Collections.singletonList(this.ingredient);
    }

    @Override
    public List<SlotContent> getResults() {
        return Collections.singletonList(this.result);
    }
}
