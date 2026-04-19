package com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv.teapot;

import cc.cassian.rrv.api.TagUtil;
import cc.cassian.rrv.api.recipe.ReliableServerRecipe;
import cc.cassian.rrv.api.recipe.ReliableServerRecipeType;
import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;

@SuppressWarnings("deprecation")
public class TeapotServerRecipe implements ReliableServerRecipe {
    public static final ReliableServerRecipeType<TeapotServerRecipe> TYPE = ReliableServerRecipeType.register(
            Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "teapot"),
            () -> new TeapotServerRecipe(null, null, null, 0)
    );

    private ItemStackTemplate result;
    private Ingredient ingredient;
    private ItemStackTemplate teaFluid;
    private int ingredientCount;

    public TeapotServerRecipe(ItemStackTemplate result, Ingredient ingredient, ItemStackTemplate teaFluid, int ingredientCount) {
        this.result = result;
        this.ingredient = ingredient;
        this.teaFluid = teaFluid;
        this.ingredientCount = ingredientCount;
    }

    public ItemStackTemplate getResult() {
        return this.result;
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }

    public ItemStackTemplate getTeaFluid() {
        return this.teaFluid;
    }

    public int getIngredientCount() {
        return this.ingredientCount;
    }

    @Override
    public void writeToTag(CompoundTag tag) {
        tag.put("result", TagUtil.encodeItemStackOnServer(this.result));
        tag.put("ingredient", TagUtil.writeIngredient(this.ingredient));
        tag.put("tea_fluid", TagUtil.encodeItemStackOnServer(this.teaFluid));
        tag.putInt("ingredient_count", this.ingredientCount);
    }

    @Override
    public void loadFromTag(CompoundTag tag) {
        this.result = ItemStackTemplate.fromNonEmptyStack(TagUtil.decodeItemStackOnServer(tag.getCompound("result").orElseGet(CompoundTag::new)));
        this.ingredient = TagUtil.readIngredient(tag.getCompound("ingredient").orElseGet(CompoundTag::new));
        this.teaFluid = ItemStackTemplate.fromNonEmptyStack(TagUtil.decodeItemStackOnServer(tag.getCompound("tea_fluid").orElseGet(CompoundTag::new)));
        this.ingredientCount = tag.getInt("ingredient_count").orElse(0);
    }

    @Override
    public ReliableServerRecipeType<? extends ReliableServerRecipe> getRecipeType() {
        return TYPE;
    }
}
