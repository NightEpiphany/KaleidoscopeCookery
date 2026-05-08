package com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv.teapot;

import cc.cassian.rrv.api.recipe.ReliableClientRecipe;
import cc.cassian.rrv.api.recipe.ReliableClientRecipeType;
import cc.cassian.rrv.common.recipe.inventory.RecipeViewMenu;
import cc.cassian.rrv.common.recipe.inventory.SlotContent;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.TeapotRecipe;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public class TeapotViewRecipe implements ReliableClientRecipe {
    private final Identifier id;
    private final Ingredient ingredient;
    private final int ingredientCount;
    private final ItemStackTemplate result;
    private final SlotContent teaFluid;

    public TeapotViewRecipe(Identifier id, Ingredient ingredient, int ingredientCount, ItemStackTemplate teaFluid, ItemStackTemplate result) {
        this.id = id;
        this.ingredient = ingredient;
        this.ingredientCount = ingredientCount;
        this.result = result;
        this.teaFluid = SlotContent.of(teaFluid);
    }

    @Override
    public ReliableClientRecipeType getType() {
        return TeapotViewType.INSTANCE;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void bindSlots(RecipeViewMenu.SlotFillContext slotFillContext) {
        var checkedIngredient = this.ingredient.items().map(s -> s.value().getDefaultInstance().copyWithCount(this.ingredientCount)).toList();
        var checkedResult = this.result.item().value().getDefaultInstance().copyWithCount(TeapotRecipe.OUTPUT_COUNT);
        slotFillContext.bindSlot(0, this.teaFluid);
        slotFillContext.bindSlot(1, SlotContent.of(checkedIngredient));
        slotFillContext.bindSlot(2, SlotContent.of(checkedResult));
    }

    @Override
    public List<SlotContent> getIngredients() {
        return List.of(this.teaFluid, SlotContent.of(this.ingredient));
    }

    @Override
    public List<SlotContent> getResults() {
        return List.of(SlotContent.of(this.result));
    }
}
