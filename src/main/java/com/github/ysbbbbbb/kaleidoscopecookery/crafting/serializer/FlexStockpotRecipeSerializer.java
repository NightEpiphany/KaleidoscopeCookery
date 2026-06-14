package com.github.ysbbbbbb.kaleidoscopecookery.crafting.serializer;

import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.FlexStockpotRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.util.StreamCodecUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import static com.github.ysbbbbbb.kaleidoscopecookery.crafting.serializer.StockpotRecipeSerializer.*;

public class FlexStockpotRecipeSerializer {
    private static final MapCodec<FlexStockpotRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(recipe -> recipe.ingredients().stream().toList()),
            Identifier.CODEC.optionalFieldOf("soup_base", DEFAULT_SOUP_BASE).forGetter(FlexStockpotRecipe::soupBase),
            ItemStackTemplate.CODEC.fieldOf("result").forGetter(FlexStockpotRecipe::result),
            Codec.INT.optionalFieldOf("time", DEFAULT_TIME).forGetter(FlexStockpotRecipe::time),
            Ingredient.CODEC.optionalFieldOf("carrier", DEFAULT_CARRIER).forGetter(FlexStockpotRecipe::carrier),
            Identifier.CODEC.optionalFieldOf("cooking_texture", DEFAULT_COOKING_TEXTURE).forGetter(FlexStockpotRecipe::cookingTexture),
            Identifier.CODEC.optionalFieldOf("finished_texture", DEFAULT_FINISHED_TEXTURE).forGetter(FlexStockpotRecipe::finishedTexture),
            Codec.INT.optionalFieldOf("cooking_bubble_color", DEFAULT_COOKING_BUBBLE_COLOR).forGetter(FlexStockpotRecipe::cookingBubbleColor),
            Codec.INT.optionalFieldOf("finished_bubble_color", DEFAULT_FINISHED_BUBBLE_COLOR).forGetter(FlexStockpotRecipe::finishedBubbleColor)
    ).apply(instance, FlexStockpotRecipe::new));

    private static final StreamCodec<RegistryFriendlyByteBuf, FlexStockpotRecipe> STREAM_CODEC = StreamCodecUtil.composite(
            Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), FlexStockpotRecipe::getIngredients,
            Identifier.STREAM_CODEC, FlexStockpotRecipe::soupBase,
            ItemStackTemplate.STREAM_CODEC, FlexStockpotRecipe::result,
            ByteBufCodecs.INT, FlexStockpotRecipe::time,
            Ingredient.CONTENTS_STREAM_CODEC, FlexStockpotRecipe::carrier,
            Identifier.STREAM_CODEC, FlexStockpotRecipe::cookingTexture,
            Identifier.STREAM_CODEC, FlexStockpotRecipe::finishedTexture,
            ByteBufCodecs.INT, FlexStockpotRecipe::cookingBubbleColor,
            ByteBufCodecs.INT, FlexStockpotRecipe::finishedBubbleColor,
            FlexStockpotRecipe::new);

    public static @NotNull MapCodec<FlexStockpotRecipe> codec() {
        return CODEC;
    }

    public static @NotNull StreamCodec<RegistryFriendlyByteBuf, FlexStockpotRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
