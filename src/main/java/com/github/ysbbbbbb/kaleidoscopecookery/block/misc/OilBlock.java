package com.github.ysbbbbbb.kaleidoscopecookery.block.misc;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;

public class OilBlock extends Block {
    public OilBlock(BlockBehaviour.Properties p) {
        super(p);
    }

    @Deprecated
    private static boolean never(BlockState state, BlockGetter blockGetter, BlockPos pos, EntityType<?> entityType) {
        return false;
    }

    // TODO: 缺少粘液块不能带动

    @Override
    public void animateTick(@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos, RandomSource random) {
        if (random.nextInt(50) != 0) {
            return;
        }
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 1;
        double z = pos.getZ() + 0.5;

        level.addParticle(ParticleTypes.WAX_OFF,
                x + random.nextDouble() / 3 * (random.nextBoolean() ? 1 : -1),
                y + random.nextDouble() / 3,
                z + random.nextDouble() / 3 * (random.nextBoolean() ? 1 : -1),
                0.3, 0.1, 0.3);
    }
}


