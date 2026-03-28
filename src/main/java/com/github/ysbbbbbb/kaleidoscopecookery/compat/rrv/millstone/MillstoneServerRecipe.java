package com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv.millstone;

import cc.cassian.rrv.api.TagUtil;
import cc.cassian.rrv.api.recipe.ReliableServerRecipe;
import cc.cassian.rrv.api.recipe.ReliableServerRecipeType;
import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;

@SuppressWarnings("deprecation")
public class MillstoneServerRecipe implements ReliableServerRecipe {
    public static final ReliableServerRecipeType<MillstoneServerRecipe> TYPE = ReliableServerRecipeType.register(
            Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "millstone"),
            () -> new MillstoneServerRecipe(null, null)
    );
    private ItemStackTemplate result;
    private Ingredient ingredient;

    public MillstoneServerRecipe(ItemStackTemplate result, Ingredient ingredient) {
        this.result = result;
        this.ingredient = ingredient;
    }

    public ItemStackTemplate getResult() {
        return this.result;
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }

    @Override
    public void writeToTag(CompoundTag tag) {
        tag.put("ingredient", TagUtil.writeIngredient(this.ingredient));
        tag.put("result", TagUtil.encodeItemStackOnServer(this.result));
    }

    @Override
    public void loadFromTag(CompoundTag tag) {
        this.ingredient = TagUtil.readIngredient(tag.getCompound("ingredient").orElseGet(CompoundTag::new));
        this.result = ItemStackTemplate.fromNonEmptyStack(TagUtil.decodeItemStackOnServer(tag.getCompound("result").orElseGet(CompoundTag::new)));
    }

    @Override
    public ReliableServerRecipeType<? extends ReliableServerRecipe> getRecipeType() {
        return TYPE;
    }
}
