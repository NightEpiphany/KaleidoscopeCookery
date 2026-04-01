package com.github.ysbbbbbb.kaleidoscopecookery.compat.create.automation.arm.type;

import com.github.ysbbbbbb.kaleidoscopecookery.compat.create.automation.arm.point.TablePoint;
import com.github.ysbbbbbb.kaleidoscopecookery.init.tag.TagMod;
import com.zurrtum.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import com.zurrtum.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class TableType extends ArmInteractionPointType {
    @Override
    public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
        return state.is(TagMod.TABLE);
    }

    @Override
    public ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
        return new TablePoint(this, level, pos, state);
    }
}
