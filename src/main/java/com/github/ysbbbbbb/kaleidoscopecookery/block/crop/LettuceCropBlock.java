package com.github.ysbbbbbb.kaleidoscopecookery.block.crop;

import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public class LettuceCropBlock extends BaseCropBlock {
    public LettuceCropBlock(Properties p) {
        super(p, () -> ModItems.LETTUCE, () -> ModItems.LETTUCE_SEED);
    }

    @Override
    public @NotNull InteractionResult useWithoutItem(@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos, @NonNull Player player, @NonNull BlockHitResult hitResult) {
        return InteractionResult.TRY_WITH_EMPTY_HAND;
    }
}
