package com.github.ysbbbbbb.kaleidoscopecookery.block.food;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;

public class FoodBlock extends Block {
    public static final VoxelShape AABB = Block.box(1, 0, 1, 15, 2, 15);

    public FoodBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public @NonNull VoxelShape getShape(@NonNull BlockState pState, @NonNull BlockGetter pLevel, @NonNull BlockPos pPos, @NonNull CollisionContext pContext) {
        return AABB;
    }
}
