package com.github.ysbbbbbb.kaleidoscopecookery.compat.create.automation.arm.point;

import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.decoration.FruitBasketBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.util.ItemUtils;
import com.github.ysbbbbbb.kaleidoscopecookery.util.neo.ItemStackHandler;
import com.zurrtum.create.content.kinetics.mechanicalArm.ArmBlockEntity;
import com.zurrtum.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import com.zurrtum.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class FruitBasketPoint extends ArmInteractionPoint {
    public FruitBasketPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
        super(type, level, pos, state);
    }

    @Override
    protected Vec3 getInteractionPositionVector() {
        return Vec3.atLowerCornerOf(this.pos).add(0.5F, 0.5125F, 0.5F);
    }

    @Override
    public int getSlotCount(ArmBlockEntity armBlockEntity) {
        return 8;
    }

    @Override
    public ItemStack insert(ArmBlockEntity armBlockEntity, ItemStack stack, boolean simulate) {
        BlockEntity blockEntity = this.level.getBlockEntity(this.pos);
        if(blockEntity instanceof FruitBasketBlockEntity entity) {
            if (entity.getItems().stream().noneMatch(s -> s.is(stack.getItem()))) return stack;
            ItemStackHandler handler = new ItemStackHandler(entity.getItems());
            if (!simulate) return ItemUtils.insertItemStacked(handler,stack.copy(),false);
            else return ItemUtils.insertItemStacked(handler,stack.copy(),true);
        }

        return stack;
    }
}
