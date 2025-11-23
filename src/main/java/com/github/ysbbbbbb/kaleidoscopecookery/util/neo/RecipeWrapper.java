package com.github.ysbbbbbb.kaleidoscopecookery.util.neo;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import org.jetbrains.annotations.NotNull;

public class RecipeWrapper implements RecipeInput {
    protected final IItemHandler inv;

    public RecipeWrapper(IItemHandler inv) {
        this.inv = inv;
    }

    public int size() {
        return this.inv.getSlots();
    }

    public @NotNull ItemStack getItem(int slot) {
        return this.inv.getStackInSlot(slot);
    }
}
