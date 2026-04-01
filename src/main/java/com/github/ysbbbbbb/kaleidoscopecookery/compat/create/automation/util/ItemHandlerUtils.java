package com.github.ysbbbbbb.kaleidoscopecookery.compat.create.automation.util;

import com.github.ysbbbbbb.kaleidoscopecookery.util.ItemUtils;
import com.github.ysbbbbbb.kaleidoscopecookery.util.neo.IItemHandler;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemHandlerUtils {
    public static List<StackPredicate> getRequired(List<StackPredicate> required, IItemHandler handler) {
        List<StackPredicate> remain = new ArrayList<>(required);
        List<ItemStack> stacks = new ArrayList<>();

        for (int i = 0;i < handler.getSlots();i++) {
            ItemStack itemStack = handler.getStackInSlot(i);
            for (int j = 0;j < itemStack.getCount();j++)
                stacks.add(itemStack.copyWithCount(1));
        }

        for (ItemStack itemStack : stacks)
            for (int i = 0;i < remain.size();i++)
                if (remain.get(i).test(itemStack)){
                    remain.remove(remain.get(i));
                    break;
                }

        return remain;
    }

    public static List<StackPredicate> getRequired(List<StackPredicate> required, List<ItemStack> slots) {
        List<StackPredicate> remain = new ArrayList<>(required);
        List<ItemStack> stacks = new ArrayList<>();

        for (ItemStack itemStack : slots)
            for (int i = 0;i < itemStack.getCount();i++)
                stacks.add(itemStack.copyWithCount(1));

        for (ItemStack itemStack : stacks)
            for (int i = 0;i < remain.size();i++)
                if (remain.get(i).test(itemStack)){
                    remain.remove(remain.get(i));
                    break;
                }

        return remain;
    }

    public static void tryTakeFrom(IItemHandler from, IItemHandler to, StackPredicate predicate, int count) {
        for (int i = 0;i < from.getSlots() && count > 0;i++){
            ItemStack stack = from.getStackInSlot(i);
            if (predicate.test(stack)){
                if (stack.getCount() > count){
                    ItemStack toInsert = stack.copyWithCount(count);
                    toInsert = ItemUtils.insertItemStacked(to,toInsert,false);
                    stack.split(count - toInsert.getCount());
                    break;
                } else {
                    ItemStack toInsert = stack.copyWithCount(stack.getCount());
                    toInsert = ItemUtils.insertItemStacked(to,toInsert,false);
                    count -= stack.getCount() - toInsert.getCount();
                    stack.split(stack.getCount() - toInsert.getCount());
                }
            }
        }
    }

    public static List<ItemStack> toStacks(IItemHandler itemHandler) {
        List<ItemStack> ans = new ArrayList<>();
        for (int i = 0;i < itemHandler.getSlots();i++)
            if (!itemHandler.getStackInSlot(i).isEmpty())
                ans.add(itemHandler.getStackInSlot(i));

        return ans;
    }
}
