package com.github.ysbbbbbb.kaleidoscopecookery.crafting.container;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class SimpleInput implements RecipeInput {
    protected final List<ItemStack> inputs;

    public SimpleInput(List<ItemStack> inputs) {
        this.inputs = inputs;
    }

    @Override
    public @NotNull ItemStack getItem(int index) {
        return this.inputs.get(index);
    }

    @Override
    public int size() {
        return this.inputs.size();
    }

    public List<ItemStack> getInputs() {
        return inputs;
    }

    // 添加新方法：获取非空输入
    public List<ItemStack> getNonEmptyInputs() {
        return inputs.stream()
                .filter(stack -> !stack.isEmpty())
                .collect(Collectors.toList());
    }

    // 添加新方法：获取输入数量（非空）
    public int getNonEmptyCount() {
        return (int) inputs.stream()
                .filter(stack -> !stack.isEmpty())
                .count();
    }
}
