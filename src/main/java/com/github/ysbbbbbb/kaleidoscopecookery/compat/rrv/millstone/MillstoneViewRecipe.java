package com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv.millstone;

import cc.cassian.rrv.api.recipe.ReliableClientRecipe;
import cc.cassian.rrv.api.recipe.ReliableClientRecipeType;
import cc.cassian.rrv.common.recipe.inventory.RecipeViewMenu;
import cc.cassian.rrv.common.recipe.inventory.SlotContent;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.output.RandomOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public class MillstoneViewRecipe implements ReliableClientRecipe {
    private final Identifier id;
    private final SlotContent ingredient;
    private final List<SlotContent> results;

    public MillstoneViewRecipe(Identifier id, Ingredient ingredient, List<RandomOutput> results) {
        this.id = id;
        this.ingredient = SlotContent.of(ingredient);
        this.results = results.stream()
                .filter(output -> !output.isEmpty())
                .limit(4)
                .map(RandomOutput::stack)
                .map(SlotContent::of)
                .toList();
    }

    @Override
    public ReliableClientRecipeType getType() {
        return MillstoneViewType.INSTANCE;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public void bindSlots(RecipeViewMenu.SlotFillContext slotFillContext) {
        slotFillContext.bindSlot(0, this.ingredient);
        for (int i = 0; i < this.results.size(); i++) {
            slotFillContext.bindSlot(i + 1, this.results.get(i));
        }
    }

    @Override
    public List<SlotContent> getIngredients() {
        return List.of(this.ingredient);
    }

    @Override
    public List<SlotContent> getResults() {
        return this.results;
    }
}
