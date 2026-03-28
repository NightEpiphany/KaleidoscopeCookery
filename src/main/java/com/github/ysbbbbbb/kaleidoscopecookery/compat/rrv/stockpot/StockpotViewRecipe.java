package com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv.stockpot;

import cc.cassian.rrv.api.recipe.ReliableClientRecipe;
import cc.cassian.rrv.api.recipe.ReliableClientRecipeType;
import cc.cassian.rrv.common.recipe.inventory.RecipeViewMenu;
import cc.cassian.rrv.common.recipe.inventory.SlotContent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class StockpotViewRecipe implements ReliableClientRecipe {
    private final List<Ingredient> ingredients;
    private final ItemStack soupBase;
    private final Ingredient carrier;
    private final ItemStack result;

    public StockpotViewRecipe(StockpotServerRecipe recipe) {
        this.ingredients = recipe.getIngredients();
        this.soupBase = recipe.getSoupBase();
        this.carrier = recipe.getCarrier();
        this.result = recipe.getResult();
    }

    @Override
    public ReliableClientRecipeType getViewType() {
        return StockpotViewType.INSTANCE;
    }

    @Override
    public void bindSlots(RecipeViewMenu.SlotFillContext slotFillContext) {
        for (int i = 0; i < Math.min(9, this.ingredients.size()); i++) {
            slotFillContext.bindSlot(i, SlotContent.of(this.ingredients.get(i)));
        }
        slotFillContext.bindSlot(9, SlotContent.of(this.soupBase));
        slotFillContext.bindSlot(10, SlotContent.of(this.carrier));
        slotFillContext.bindSlot(11, SlotContent.of(this.result));
    }

    @Override
    public List<SlotContent> getIngredients() {
        List<SlotContent> contents = new ArrayList<>();
        this.ingredients.forEach(ingredient -> contents.add(SlotContent.of(ingredient)));
        contents.add(SlotContent.of(this.soupBase));
        contents.add(SlotContent.of(this.carrier));
        return contents;
    }

    @Override
    public List<SlotContent> getResults() {
        return List.of(SlotContent.of(this.result));
    }
}
