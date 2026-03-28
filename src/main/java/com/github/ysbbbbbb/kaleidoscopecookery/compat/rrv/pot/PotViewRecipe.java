package com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv.pot;

import cc.cassian.rrv.api.recipe.ReliableClientRecipe;
import cc.cassian.rrv.api.recipe.ReliableClientRecipeType;
import cc.cassian.rrv.common.recipe.inventory.RecipeViewMenu;
import cc.cassian.rrv.common.recipe.inventory.SlotContent;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class PotViewRecipe implements ReliableClientRecipe {
    private final List<Ingredient> ingredients;
    private final Ingredient carrier;
    private final ItemStack result;

    public PotViewRecipe(PotServerRecipe recipe) {
        this.ingredients = recipe.getIngredients();
        this.carrier = recipe.getCarrier();
        this.result = recipe.getResult();
    }

    @Override
    public ReliableClientRecipeType getViewType() {
        return PotViewType.INSTANCE;
    }

    @Override
    public void bindSlots(RecipeViewMenu.SlotFillContext slotFillContext) {
        for (int i = 0; i < Math.min(9, this.ingredients.size()); i++) {
            slotFillContext.bindSlot(i, SlotContent.of(this.ingredients.get(i)));
        }
        slotFillContext.bindSlot(9, SlotContent.of(this.carrier));
        slotFillContext.bindSlot(10, SlotContent.of(this.result));
        slotFillContext.bindSlot(11, SlotContent.of(Ingredient.of(ModItems.KITCHEN_SHOVEL)));
    }

    @Override
    public List<SlotContent> getIngredients() {
        List<SlotContent> contents = new ArrayList<>();
        this.ingredients.forEach(ingredient -> contents.add(SlotContent.of(ingredient)));
        contents.add(SlotContent.of(this.carrier));
        return contents;
    }

    @Override
    public List<SlotContent> getResults() {
        return List.of(SlotContent.of(this.result));
    }
}
