package com.github.ysbbbbbb.kaleidoscopecookery.item;

import com.github.ysbbbbbb.kaleidoscopecookery.init.ModFoods;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

public class ChiliItem extends Item {
    private final int damage;

    public ChiliItem(int damage) {
        super(new Properties().food(ModFoods.CHILI));
        this.damage = damage;
    }

    @Override
    public @NonNull ItemStack finishUsingItem(@NonNull ItemStack stack, Level level, LivingEntity entity) {
        entity.hurt(level.damageSources().magic(), this.damage);
        return super.finishUsingItem(stack, level, entity);
    }
}
