package com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv.pot;

import cc.cassian.rrv.api.recipe.ReliableClientRecipe;
import cc.cassian.rrv.api.recipe.ReliableClientRecipeType;
import cc.cassian.rrv.common.recipe.inventory.RecipeViewMenu;
import cc.cassian.rrv.common.recipe.inventory.SlotContent;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class PotViewRecipe implements ReliableClientRecipe {
    private final Identifier id;
    private final List<SlotContent> ingredients;
    private final SlotContent carrier;
    private final SlotContent result;
    private final SlotContent tool;

    public PotViewRecipe(Identifier id, List<Ingredient> ingredients, Ingredient carrier, ItemStackTemplate result) {
        this.id = id;
        this.ingredients = ingredients.stream()
                .filter(ingredient -> !ingredient.isEmpty())
                .limit(9)
                .map(SlotContent::of)
                .toList();
        this.carrier = SlotContent.of(carrier);
        this.result = SlotContent.of(result);
        this.tool = SlotContent.of(Ingredient.of(ModItems.KITCHEN_SHOVEL));
    }

    @Override
    public ReliableClientRecipeType getType() {
        return PotViewType.INSTANCE;
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
        slotFillContext.bindSlot(9, this.carrier);
        slotFillContext.bindSlot(10, this.result);
        slotFillContext.bindSlot(11, this.tool);
    }

    @Override
    public List<SlotContent> getIngredients() {
        List<SlotContent> contents = new ArrayList<>(this.ingredients);
        contents.add(this.carrier);
        return contents;
    }

    @Override
    public List<SlotContent> getResults() {
        return List.of(this.result);
    }
}
