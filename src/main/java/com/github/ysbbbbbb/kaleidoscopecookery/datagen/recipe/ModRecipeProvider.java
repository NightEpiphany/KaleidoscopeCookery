package com.github.ysbbbbbb.kaleidoscopecookery.datagen.recipe;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;

import java.util.Arrays;

public abstract class ModRecipeProvider extends RecipeProvider {
    protected ModRecipeProvider(BootstrapContext<Recipe<?>> registries, BootstrapContext<Advancement> output) {
        super(registries, output);
    }

    @Override
    public final void buildRecipes() {
        this.buildRecipes(this.output);
    }

    public abstract void buildRecipes(RecipeOutput consumer);

    public Identifier modLoc(String path) {
        return Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, path);
    }

    public String getRecipeIdWithCount(ItemLike itemLike, int count) {
        return RecipeBuilder.getDefaultRecipeId((ItemInstance) itemLike.asItem()).identifier().getPath() + "_" + count;
    }

    public ItemLike[] getItemsWithCount(ItemLike itemLike, int count) {
        ItemLike[] items = new ItemLike[count];
        Arrays.fill(items, itemLike);
        return items;
    }

    public TagKey<Item>[] getItemsWithCount(TagKey<Item> itemLike, int count) {
        TagKey<Item>[] items = new TagKey[count];
        Arrays.fill(items, itemLike);
        return items;
    }
}
