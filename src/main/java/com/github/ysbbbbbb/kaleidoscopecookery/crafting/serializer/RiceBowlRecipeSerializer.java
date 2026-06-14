package com.github.ysbbbbbb.kaleidoscopecookery.crafting.serializer;

import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.RiceBowlRecipe;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class RiceBowlRecipeSerializer {
    public static final MapCodec<RiceBowlRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(RiceBowlRecipe::category),
                    Ingredient.CODEC.fieldOf("ingredient").forGetter(RiceBowlRecipe::getIngredient),
                    ItemStackTemplate.CODEC.fieldOf("result").forGetter(RiceBowlRecipe::getResult)
            ).apply(instance, RiceBowlRecipe::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, RiceBowlRecipe> STREAM_CODEC = StreamCodec.composite(
            CraftingBookCategory.STREAM_CODEC, RiceBowlRecipe::category,
            Ingredient.CONTENTS_STREAM_CODEC, RiceBowlRecipe::getIngredient,
            ItemStackTemplate.STREAM_CODEC, RiceBowlRecipe::getResult,
            RiceBowlRecipe::new
    );

    public static @NotNull MapCodec<RiceBowlRecipe> codec() {
        return CODEC;
    }

    public static @NotNull StreamCodec<RegistryFriendlyByteBuf, RiceBowlRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
