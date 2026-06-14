package com.github.ysbbbbbb.kaleidoscopecookery.api.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;

public interface ICustomEatEffect {
    FoodProperties modifyFoodProperties(ItemStack stack);
    Consumable modifyConsumables(ItemStack stack);
}
