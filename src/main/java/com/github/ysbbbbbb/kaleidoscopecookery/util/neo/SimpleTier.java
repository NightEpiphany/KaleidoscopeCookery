package com.github.ysbbbbbb.kaleidoscopecookery.util.neo;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

//From Neoforge
public record SimpleTier(
        TagKey<Block> incorrectBlocksForDrops,
        int uses,
        float speed,
        float attackDamageBonus,
        int enchantmentValue,
        Supplier<Ingredient> repairIngredient
) implements Tier {

    public int getUses() {
        return this.uses;
    }

    public float getSpeed() {
        return this.speed;
    }

    public float getAttackDamageBonus() {
        return this.attackDamageBonus;
    }

    public @NotNull TagKey<Block> getIncorrectBlocksForDrops() {
        return this.incorrectBlocksForDrops;
    }

    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    public @NotNull Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    public @NotNull String toString() {
        String var10000 = String.valueOf(this.incorrectBlocksForDrops);
        return "SimpleTier[incorrectBlocksForDrops=" + var10000 + ", uses=" + this.uses + ", speed=" + this.speed + ", attackDamageBonus=" + this.attackDamageBonus + ", enchantmentValue=" + this.enchantmentValue + ", repairIngredient=" + String.valueOf(this.repairIngredient) + "]";
    }
}
