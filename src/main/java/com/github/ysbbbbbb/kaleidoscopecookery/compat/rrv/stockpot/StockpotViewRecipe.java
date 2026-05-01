package com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv.stockpot;

import cc.cassian.rrv.api.recipe.ReliableClientRecipe;
import cc.cassian.rrv.api.recipe.ReliableClientRecipeType;
import cc.cassian.rrv.common.recipe.inventory.RecipeViewMenu;
import cc.cassian.rrv.common.recipe.inventory.SlotContent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class StockpotViewRecipe implements ReliableClientRecipe {
    private final Identifier id;
    private final List<SlotContent> ingredients;
    private final SlotContent soupBase;
    private final SlotContent carrier;
    private final SlotContent result;

    public StockpotViewRecipe(Identifier id, List<Ingredient> ingredients, ItemStackTemplate soupBase, Ingredient carrier, ItemStackTemplate result) {
        this.id = id;
        this.ingredients = ingredients.stream()
                .filter(ingredient -> !ingredient.isEmpty())
                .limit(9)
                .map(SlotContent::of)
                .toList();
        this.soupBase = SlotContent.of(soupBase);
        this.carrier = SlotContent.of(carrier);
        this.result = SlotContent.of(result);
    }

    @Override
    public ReliableClientRecipeType getType() {
        return StockpotViewType.INSTANCE;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public void bindSlots(RecipeViewMenu.SlotFillContext slotFillContext) {
        for (int i = 0; i < this.ingredients.size(); i++) {
            slotFillContext.bindSlot(i, this.ingredients.get(i));
        }
        slotFillContext.bindSlot(9, this.soupBase);
        slotFillContext.bindSlot(10, this.carrier);
        slotFillContext.bindSlot(11, this.result);
    }

    @Override
    public List<SlotContent> getIngredients() {
        List<SlotContent> contents = new ArrayList<>(this.ingredients);
        contents.add(this.soupBase);
        contents.add(this.carrier);
        return contents;
    }

    @Override
    public List<SlotContent> getResults() {
        return List.of(this.result);
    }
}
