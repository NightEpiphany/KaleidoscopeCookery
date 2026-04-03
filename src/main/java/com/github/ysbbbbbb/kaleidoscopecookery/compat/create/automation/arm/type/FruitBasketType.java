package com.github.ysbbbbbb.kaleidoscopecookery.compat.create.automation.arm.type;

import com.github.ysbbbbbb.kaleidoscopecookery.block.decoration.FruitBasketBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.create.automation.arm.point.FruitBasketPoint;
import com.zurrtum.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import com.zurrtum.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;

public class FruitBasketType extends ArmInteractionPointType {
    @Override
    public boolean canCreatePoint(@NonNull Level level, @NonNull BlockPos pos, BlockState state) {
        return state.getBlock() instanceof FruitBasketBlock;
    }

    @Override
    public ArmInteractionPoint createPoint(@NonNull Level level, @NonNull BlockPos pos, @NonNull BlockState state) {
        return new FruitBasketPoint(this, level, pos, state);
    }
}
