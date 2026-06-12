package com.github.ysbbbbbb.kaleidoscopecookery.api.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;

public interface ICustomEatEffect {
    FoodProperties modifyFoodProperties(ItemStack stack);
}
