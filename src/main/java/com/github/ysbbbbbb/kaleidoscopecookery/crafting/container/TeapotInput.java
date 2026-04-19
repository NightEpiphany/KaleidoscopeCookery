package com.github.ysbbbbbb.kaleidoscopecookery.crafting.container;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class TeapotInput extends SimpleInput {
    private final Identifier teaFluid;

    public TeapotInput(ItemStack itemStack, Identifier teaFluid) {
        super(List.of(itemStack));
        this.teaFluid = teaFluid;
    }

    public Identifier getTeaFluid() {
        return this.teaFluid;
    }

    public ItemStack getItemStack() {
        return this.getItem(0);
    }
}