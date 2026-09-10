package com.github.ysbbbbbb.kaleidoscopecookery.util.forge;

import com.github.ysbbbbbb.kaleidoscopecookery.api.event.CropGrowEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public final class Hooks {

    public static boolean onCropsGrowPre(Level level, BlockPos pos, BlockState state, boolean def) {
        CropGrowEvent.Pre ev = new CropGrowEvent.Pre(level, pos, state);
        ev.post();
        return ev.getResult() == CropGrowEvent.Pre.Result.GROW || ev.getResult() == CropGrowEvent.Pre.Result.DEFAULT && def;
    }

    public static void onCropsGrowPost(Level level, BlockPos pos, BlockState state) {
        var ev = new CropGrowEvent.Post(level, pos, state, level.getBlockState(pos));
        ev.post();
    }
}
