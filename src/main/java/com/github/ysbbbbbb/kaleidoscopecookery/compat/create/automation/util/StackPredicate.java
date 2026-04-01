package com.github.ysbbbbbb.kaleidoscopecookery.compat.create.automation.util;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Predicate;

public class StackPredicate {
    private final Predicate<ItemStack> predicate;

    public StackPredicate(Predicate<ItemStack> predicate) { this.predicate = predicate; }

    public StackPredicate(Item item) { this.predicate = (stack)-> stack.is(item); }

    public StackPredicate(ItemStack itemStack) { this.predicate = (stack)-> ItemStack.isSameItemSameComponents(stack,itemStack); }

    public StackPredicate(Ingredient ingredient) { this.predicate = ingredient; }

    public StackPredicate(TagKey<Item> key) { this.predicate = (s)-> s.is(key); }

    public static StackPredicate of(Predicate<ItemStack> predicate) { return new StackPredicate(predicate); }

    public static StackPredicate of(Item item) { return new StackPredicate(item); }

    public static StackPredicate of(ItemStack itemStack) { return new StackPredicate(itemStack); }

    public static StackPredicate of(Ingredient ingredient) { return new StackPredicate(ingredient); }

    public static StackPredicate of(TagKey<Item> key) { return new StackPredicate(key); }

    public StackPredicate copy(){
        return new StackPredicate(predicate);
    }

    public boolean test(ItemStack stack){
        return predicate.test(stack);
    }
}
