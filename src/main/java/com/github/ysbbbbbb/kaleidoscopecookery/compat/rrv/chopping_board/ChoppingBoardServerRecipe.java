package com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv.chopping_board;

import cc.cassian.rrv.api.TagUtil;
import cc.cassian.rrv.api.recipe.ReliableServerRecipe;
import cc.cassian.rrv.api.recipe.ReliableServerRecipeType;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import vectorwing.farmersdelight.FarmersDelight;

@SuppressWarnings("deprecation")
public class ChoppingBoardServerRecipe implements ReliableServerRecipe {
    public static final ReliableServerRecipeType<ChoppingBoardServerRecipe> TYPE = ReliableServerRecipeType.register(
            Identifier.fromNamespaceAndPath(FarmersDelight.MODID, "chopping"),
            () -> new ChoppingBoardServerRecipe(  null, null)
    );
    private Ingredient ingredient;
    private ItemStackTemplate result;
    private Ingredient tool;

    public ChoppingBoardServerRecipe(ItemStackTemplate result, Ingredient ingredient) {
        this.tool = Ingredient.of(ModItems.IRON_KITCHEN_KNIFE);
        this.result = result;
        this.ingredient = ingredient;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public ItemStackTemplate getResult() {
        return result;
    }

    public Ingredient getTool() {
        return tool;
    }

    @Override
    public void writeToTag(CompoundTag tag) {
        tag.put("ingredient", TagUtil.writeIngredient(this.ingredient));
        tag.put("result", TagUtil.encodeItemStackOnServer(this.result));
        tag.put("tool", TagUtil.writeIngredient(this.tool));
    }

    @Override
    public void loadFromTag(CompoundTag tag) {
        this.ingredient = TagUtil.readIngredient(tag.getCompound("ingredient").orElseGet(CompoundTag::new));
        this.result = ItemStackTemplate.fromNonEmptyStack(TagUtil.decodeItemStackOnServer(tag.getCompound("result").orElseGet(CompoundTag::new)));
        this.tool = TagUtil.readIngredient(tag.getCompound("tool").orElseGet(CompoundTag::new));
    }

    @Override
    public ReliableServerRecipeType<? extends ReliableServerRecipe> getRecipeType() {
        return TYPE;
    }
}
