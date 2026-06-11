package com.github.ysbbbbbb.kaleidoscopecookery.datagen.recipe;

import com.github.ysbbbbbb.kaleidoscopecookery.init.ModRecipes;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public record RiceBowlFinishedRecipe(
        ResourceLocation id,
        Ingredient ingredient,
        Item result
) implements FinishedRecipe {
    public RiceBowlFinishedRecipe(ResourceLocation id, ItemLike ingredient, Item result) {
        this(id, Ingredient.of(ingredient), result);
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public void serializeRecipeData(JsonObject json) {
        json.addProperty("category", CraftingBookCategory.MISC.getSerializedName());
        json.add("ingredient", ingredient.toJson());

        JsonObject resultJson = new JsonObject();
        resultJson.addProperty("item", BuiltInRegistries.ITEM.getKey(result).toString());
        json.add("result", resultJson);
    }

    @Override
    public @NotNull RecipeSerializer<?> getType() {
        return ModRecipes.RICE_BOWL_SERIALIZER;
    }

    @Override
    @Nullable
    public JsonObject serializeAdvancement() {
        return null;
    }

    @Override
    @Nullable
    public ResourceLocation getAdvancementId() {
        return null;
    }
}
