package com.github.ysbbbbbb.kaleidoscopecookery.crafting.container;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class StockpotInput extends SimpleInput {
    private final Identifier soupBase;

    public StockpotInput(List<ItemStack> items, Identifier soupBase) {
        super(items);
        this.soupBase = soupBase;
    }

    public Identifier getSoupBase() {
        return soupBase;
    }
}
