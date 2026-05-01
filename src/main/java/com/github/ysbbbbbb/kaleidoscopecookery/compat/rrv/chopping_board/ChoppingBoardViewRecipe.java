package com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv.chopping_board;

import cc.cassian.rrv.api.recipe.ReliableClientRecipe;
import cc.cassian.rrv.api.recipe.ReliableClientRecipeType;
import cc.cassian.rrv.common.recipe.inventory.RecipeViewMenu;
import cc.cassian.rrv.common.recipe.inventory.SlotContent;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public class ChoppingBoardViewRecipe implements ReliableClientRecipe {
    private final Identifier id;
    private final SlotContent ingredient;
    private final SlotContent result;
    private final SlotContent tool;

    public ChoppingBoardViewRecipe(Identifier id, Ingredient ingredient, ItemStackTemplate result) {
        this.id = id;
        this.ingredient = SlotContent.of(ingredient);
        this.result = SlotContent.of(result);
        this.tool = SlotContent.of(Ingredient.of(ModItems.IRON_KITCHEN_KNIFE));
    }

    @Override
    public ReliableClientRecipeType getType() {
        return ChoppingBoardViewType.INSTANCE;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public void bindSlots(RecipeViewMenu.SlotFillContext slotFillContext) {
        slotFillContext.bindSlot(0, this.ingredient);
        slotFillContext.bindSlot(1, this.tool);
        slotFillContext.bindSlot(2, this.result);
    }

    @Override
    public List<SlotContent> getIngredients() {
        return List.of(this.ingredient);
    }

    @Override
    public List<SlotContent> getResults() {
        return List.of(this.result);
    }
}
