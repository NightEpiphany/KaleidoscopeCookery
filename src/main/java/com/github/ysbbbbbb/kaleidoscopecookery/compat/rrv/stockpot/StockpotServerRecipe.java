package com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv.stockpot;

import cc.cassian.rrv.api.TagUtil;
import cc.cassian.rrv.api.recipe.ReliableServerRecipe;
import cc.cassian.rrv.api.recipe.ReliableServerRecipeType;
import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class StockpotServerRecipe implements ReliableServerRecipe {
    public static final ReliableServerRecipeType<StockpotServerRecipe> TYPE = ReliableServerRecipeType.register(
            Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "stockpot"),
            () -> new StockpotServerRecipe(null, List.of(), null, null)
    );
    private ItemStack result;
    private List<Ingredient> ingredients;
    private ItemStack soupBase;
    private Ingredient carrier;

    public StockpotServerRecipe(ItemStack result, List<Ingredient> ingredients, ItemStack soupBase, Ingredient carrier) {
        this.result = result;
        this.ingredients = ingredients;
        this.soupBase = soupBase;
        this.carrier = carrier;
    }

    public ItemStack getResult() {
        return this.result;
    }

    public List<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public ItemStack getSoupBase() {
        return this.soupBase;
    }

    public Ingredient getCarrier() {
        return this.carrier;
    }

    @Override
    public void writeToTag(CompoundTag tag) {
        tag.put("result", TagUtil.encodeItemStackOnServer(this.result));
        tag.put("soup_base", TagUtil.encodeItemStackOnServer(this.soupBase));
        tag.put("carrier", TagUtil.writeIngredient(this.carrier));
        for (int i = 0; i < this.ingredients.size(); i++) {
            tag.put("ingredient_" + i, TagUtil.writeIngredient(this.ingredients.get(i)));
        }
    }

    @Override
    public void loadFromTag(CompoundTag tag) {
        this.result = TagUtil.decodeItemStackOnServer(tag.getCompound("result").orElseGet(CompoundTag::new));
        this.soupBase = TagUtil.decodeItemStackOnServer(tag.getCompound("soup_base").orElseGet(CompoundTag::new));
        this.carrier = TagUtil.readIngredient(tag.getCompound("carrier").orElseGet(CompoundTag::new));
        this.ingredients = new ArrayList<>();
        for (int i = 0; ; i++) {
            var ingredientTag = tag.getCompound("ingredient_" + i);
            if (ingredientTag.isEmpty()) {
                break;
            }
            this.ingredients.add(TagUtil.readIngredient(ingredientTag.get()));
        }
    }

    @Override
    public ReliableServerRecipeType<? extends ReliableServerRecipe> getRecipeType() {
        return TYPE;
    }
}
