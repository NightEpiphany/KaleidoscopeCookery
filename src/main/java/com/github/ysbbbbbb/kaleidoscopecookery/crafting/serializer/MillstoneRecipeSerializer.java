package com.github.ysbbbbbb.kaleidoscopecookery.crafting.serializer;

import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.MillstoneRecipe;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class MillstoneRecipeSerializer {
    private static final MapCodec<MillstoneRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Ingredient.CODEC.fieldOf("ingredient").forGetter(MillstoneRecipe::getIngredient),
                    ItemStackTemplate.CODEC.fieldOf("result").forGetter(MillstoneRecipe::getResult)
            ).apply(instance, MillstoneRecipe::new)
    );

    private static final StreamCodec<RegistryFriendlyByteBuf, MillstoneRecipe> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC, MillstoneRecipe::getIngredient,
            ItemStackTemplate.STREAM_CODEC, MillstoneRecipe::getResult,
            MillstoneRecipe::new);


    public static @NotNull MapCodec<MillstoneRecipe> codec() {
        return CODEC;
    }

    public static StreamCodec<RegistryFriendlyByteBuf, MillstoneRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
