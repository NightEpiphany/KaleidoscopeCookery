package com.github.ysbbbbbb.kaleidoscopecookery.block.crop;

import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.function.Supplier;

public class BaseCropBlock extends CropBlock {
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D)
    };
    protected final Supplier<Item> result;
    protected final Supplier<Item> seed;

    public BaseCropBlock(Properties p, Supplier<Item> result, Supplier<Item> seed) {
        super(p);
        this.result = result;
        this.seed = seed;
    }


    @Override
    protected @NonNull InteractionResult useItemOn(@NonNull ItemStack itemStack, @NonNull BlockState blockState, @NonNull Level level, @NonNull BlockPos blockPos, @NonNull Player player, @NonNull InteractionHand interactionHand, @NonNull BlockHitResult blockHitResult) {
        ItemStack itemInHand = player.getItemInHand(interactionHand);
        if (itemInHand.is(ModItems.SICKLE)) {
            return InteractionResult.TRY_WITH_EMPTY_HAND;
        }
        if (blockState.getValue(AGE) >= this.getMaxAge()) {
            Block.popResource(level, blockPos, this.result.get().getDefaultInstance());
            this.onUseBreakCrop(level, blockPos);
            return InteractionResult.SUCCESS;
        }
        return super.useItemOn(itemStack, blockState, level, blockPos, player, interactionHand, blockHitResult);
    }

    protected void onUseBreakCrop(Level level, BlockPos pos) {
        // 默认右键收割后为第五阶段
        int ageAfterUse = 5;
        level.playSound(null,
                pos.getX() + 0.5,
                pos.getY() + 0.5,
                pos.getZ() + 0.5,
                SoundEvents.CROP_BREAK,
                SoundSource.BLOCKS,
                1.0F, 1.0F);
        level.setBlock(pos, this.getStateForAge(ageAfterUse), Block.UPDATE_CLIENTS);
    }

    @Override
    protected @NotNull ItemLike getBaseSeedId() {
        return this.seed.get();
    }

    @Override
    public @NotNull VoxelShape getShape(@NonNull BlockState state, @NonNull BlockGetter blockGetter, @NonNull BlockPos pos, @NonNull CollisionContext collisionContext) {
        return SHAPE_BY_AGE[this.getAge(state)];
    }

}