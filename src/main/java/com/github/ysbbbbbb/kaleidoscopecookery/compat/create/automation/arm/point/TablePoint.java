package com.github.ysbbbbbb.kaleidoscopecookery.compat.create.automation.arm.point;

import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.decoration.TableBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.init.tag.TagMod;
import com.github.ysbbbbbb.kaleidoscopecookery.util.ItemUtils;
import com.github.ysbbbbbb.kaleidoscopecookery.util.neo.ItemStackHandler;
import com.zurrtum.create.content.kinetics.mechanicalArm.ArmBlockEntity;
import com.zurrtum.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import com.zurrtum.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;


public class TablePoint extends ArmInteractionPoint {
    public TablePoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
        super(type, level, pos, state);
    }

    @Override
    public int getSlotCount(ArmBlockEntity armBlockEntity) {
        return 4;
    }

    @Override
    public ItemStack insert(ArmBlockEntity armBlockEntity, ItemStack stack, boolean simulate) {
        if (!stack.is(TagMod.RESULT)) return stack;

        if (level.getBlockEntity(pos) instanceof TableBlockEntity table) {
            ItemStackHandler tableItems = new ItemStackHandler(table.getItems());
            if (!simulate) return ItemUtils.insertItemStacked(tableItems,stack,false);
            else return ItemUtils.insertItemStacked(tableItems,stack,true);
        }

        return stack;
    }

    public ItemStack extract(ArmBlockEntity armBlockEntity, int slot, int amount, boolean simulate) {
        return ItemStack.EMPTY;
    }
}
