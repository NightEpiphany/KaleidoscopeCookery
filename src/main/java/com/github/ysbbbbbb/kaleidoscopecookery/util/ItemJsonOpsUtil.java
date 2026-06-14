package com.github.ysbbbbbb.kaleidoscopecookery.util;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;

import net.minecraft.core.HolderOwner;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import org.jspecify.annotations.NonNull;

import java.util.Optional;

public class ItemJsonOpsUtil extends RegistryOps<JsonElement> implements HolderOwner<Item> {
    public static final ItemJsonOpsUtil INSTANCE = new ItemJsonOpsUtil();
    private ItemJsonOpsUtil() {
        super(JsonOps.INSTANCE, null);
    }


    @SuppressWarnings("deprecation")
    public static Ingredient ofTag(TagKey<Item> inputTag) {
        return Ingredient.of(HolderSet.emptyNamed(INSTANCE, inputTag));
    }


    @SuppressWarnings("unchecked")
    public <E> @NonNull Optional<HolderOwner<E>> owner(@NonNull ResourceKey<? extends Registry<? extends E>> registryRef) {
        return Optional.of((HolderOwner<E>) this);
    }
}
