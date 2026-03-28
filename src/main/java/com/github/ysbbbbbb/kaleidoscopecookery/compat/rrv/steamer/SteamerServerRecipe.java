package com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv.steamer;

import cc.cassian.rrv.api.TagUtil;
import cc.cassian.rrv.api.recipe.ReliableServerRecipe;
import cc.cassian.rrv.api.recipe.ReliableServerRecipeType;
import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;


public class SteamerServerRecipe implements ReliableServerRecipe {
    public static final ReliableServerRecipeType<SteamerServerRecipe> TYPE = ReliableServerRecipeType.register(
            Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "steamer"),
            () -> new SteamerServerRecipe(null, null)
    );
    private ItemStack result;
    private Ingredient ingredient;

    public SteamerServerRecipe(ItemStack result, Ingredient ingredient) {
        this.result = result;
        this.ingredient = ingredient;
    }

    public ItemStack getResult() {
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
        this.result = TagUtil.decodeItemStackOnServer(tag.getCompound("result").orElseGet(CompoundTag::new));
    }

    @Override
    public ReliableServerRecipeType<? extends ReliableServerRecipe> getRecipeType() {
        return TYPE;
    }
}
