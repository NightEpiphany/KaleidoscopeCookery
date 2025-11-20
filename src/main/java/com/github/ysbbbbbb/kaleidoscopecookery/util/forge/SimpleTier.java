package com.github.ysbbbbbb.kaleidoscopecookery.util.forge;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

//From forge
public record SimpleTier(
        int level,
        int uses,
        float speed,
        float attackDamageBonus,
        int enchantmentValue,
        Supplier<Ingredient> repairIngredient,
        TagKey<Block> tagKey
) implements Tier {

    public int getUses() {
        return this.uses;
    }

    @Override
    public @Nullable TagKey<Block> getTag() {
        return Tier.super.getTag();
    }

    public float getSpeed() {
        return this.speed;
    }

    public float getAttackDamageBonus() {
        return this.attackDamageBonus;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    public @NotNull Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
