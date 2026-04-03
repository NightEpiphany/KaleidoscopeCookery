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
import org.jspecify.annotations.NonNull;

public class TablePoint extends ArmInteractionPoint {
    public TablePoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
        super(type, level, pos, state);
    }

    @Override
    public int getSlotCount(@NonNull ArmBlockEntity armBlockEntity) {
        return 4;
    }

    @Override
    public @NonNull ItemStack insert(@NonNull ArmBlockEntity armBlockEntity, ItemStack stack, boolean simulate) {
        if (!(stack.is(TagMod.FEASTS) || stack.is(TagMod.MEALS))) return stack;

        if (level.getBlockEntity(pos) instanceof TableBlockEntity table) {
            ItemStackHandler tableItems = new ItemStackHandler(table.getItems());
            if (!simulate) return ItemUtils.insertItemStacked(tableItems,stack,false);
            else return ItemUtils.insertItemStacked(tableItems,stack,true);
        }

        return stack;
    }

    public @NonNull ItemStack extract(@NonNull ArmBlockEntity armBlockEntity, int slot, int amount, boolean simulate) {
        return ItemStack.EMPTY;
    }
}
