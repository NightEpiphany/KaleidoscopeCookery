package com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv.pot;

import cc.cassian.rrv.api.TagUtil;
import cc.cassian.rrv.api.recipe.ReliableServerRecipe;
import cc.cassian.rrv.api.recipe.ReliableServerRecipeType;
import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class PotServerRecipe implements ReliableServerRecipe {
    public static final ReliableServerRecipeType<PotServerRecipe> TYPE = ReliableServerRecipeType.register(
            Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "pot"),
            () -> new PotServerRecipe(null, List.of(), null)
    );
    private ItemStackTemplate result;
    private List<Ingredient> ingredients;
    private Ingredient carrier;

    public PotServerRecipe(ItemStackTemplate result, List<Ingredient> ingredients, Ingredient carrier) {
        this.result = result;
        this.ingredients = ingredients;
        this.carrier = carrier;
    }

    public ItemStackTemplate getResult() {
        return this.result;
    }

    public List<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public Ingredient getCarrier() {
        return this.carrier;
    }

    @Override
    public void writeToTag(CompoundTag tag) {
        tag.put("result", TagUtil.encodeItemStackOnServer(this.result));
        tag.put("carrier", TagUtil.writeIngredient(this.carrier));
        for (int i = 0; i < this.ingredients.size(); i++) {
            tag.put("ingredient_" + i, TagUtil.writeIngredient(this.ingredients.get(i)));
        }
    }

    @Override
    public void loadFromTag(CompoundTag tag) {
        this.result = ItemStackTemplate.fromNonEmptyStack(TagUtil.decodeItemStackOnServer(tag.getCompound("result").orElseGet(CompoundTag::new)));
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
