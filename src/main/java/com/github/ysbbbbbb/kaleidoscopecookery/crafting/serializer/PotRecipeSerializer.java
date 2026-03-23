package com.github.ysbbbbbb.kaleidoscopecookery.crafting.serializer;

import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.PotRecipe;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jspecify.annotations.NonNull;

public class PotRecipeSerializer {
    private static final MapCodec<PotRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.INT.optionalFieldOf("time", 200).forGetter(PotRecipe::time),
                    Codec.INT.optionalFieldOf("stir_fry_count", 3).forGetter(PotRecipe::stirFryCount),
                    Ingredient.CODEC.optionalFieldOf("carrier", Ingredient.of(Items.BOWL)).forGetter(PotRecipe::carrier),
                    Ingredient.CODEC.listOf().fieldOf("ingredients").xmap(
                            list -> list,
                            list -> list.stream().filter(i -> !i.isEmpty()).toList()
                    ).forGetter(recipe -> recipe.ingredients().stream().toList()),
                    ItemStackTemplate.CODEC.fieldOf("result").forGetter(PotRecipe::result)
            ).apply(instance, PotRecipe::new)
    );

    private static final StreamCodec<RegistryFriendlyByteBuf, PotRecipe> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, PotRecipe::time,
            ByteBufCodecs.INT, PotRecipe::stirFryCount,
            Ingredient.CONTENTS_STREAM_CODEC, PotRecipe::carrier,
            Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), PotRecipe::ingredients,
            ItemStackTemplate.STREAM_CODEC, PotRecipe::result,
            PotRecipe::new);


    public static @NonNull MapCodec<PotRecipe> codec() {
        return CODEC;
    }
    public static @NonNull StreamCodec<RegistryFriendlyByteBuf, PotRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
