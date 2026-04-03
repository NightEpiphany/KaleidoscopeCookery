package com.github.ysbbbbbb.kaleidoscopecookery.compat.create.automation.arm.type;

import com.github.ysbbbbbb.kaleidoscopecookery.block.kitchen.ShawarmaSpitBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.create.automation.arm.point.ShawarmaSpitPoint;
import com.zurrtum.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import com.zurrtum.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;

public class ShawarmaSpitType extends ArmInteractionPointType {
    @Override
    public boolean canCreatePoint(@NonNull Level level, @NonNull BlockPos pos, BlockState state) {
        return state.getBlock() instanceof ShawarmaSpitBlock;
    }

    @Override
    public ArmInteractionPoint createPoint(@NonNull Level level, @NonNull BlockPos pos, @NonNull BlockState state) {
        return new ShawarmaSpitPoint(this, level, pos, state);
    }
}
