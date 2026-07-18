package com.github.ysbbbbbb.kaleidoscopecookery.block.misc;

import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class ChiliRistraBlock extends Block implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty IS_HEAD = BooleanProperty.create("is_head");
    public static final BooleanProperty SHEARED = BooleanProperty.create("sheared");

    private static final VoxelShape AABB_HEAD = Block.box(4, 2, 4, 12, 16, 12);
    private static final VoxelShape AABB_BODY = Block.box(3.5, 0, 3.5, 12.5, 16, 12.5);

    public ChiliRistraBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_RED)
                .noCollission()
                .instabreak()
                .sound(SoundType.GRASS)
                .pushReaction(PushReaction.DESTROY));
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(IS_HEAD, true)
                .setValue(WATERLOGGED, false)
                .setValue(SHEARED, false));
    }

    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        if (hand != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }
        ItemStack mainHandItem = player.getMainHandItem();
        if (!mainHandItem.isEmpty() && !mainHandItem.is(ModItems.RED_CHILI)) {
            return InteractionResult.PASS;
        }
        if (state.getValue(SHEARED)) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
        } else {
            level.setBlock(pos, state.setValue(SHEARED, true), Block.UPDATE_ALL);
        }
        ItemStack redChili = new ItemStack(ModItems.RED_CHILI, 3);
        if (mainHandItem.isEmpty()) {
            player.setItemInHand(InteractionHand.MAIN_HAND, redChili);
        } else {
            player.getInventory().placeItemBackInInventory(redChili);
        }
        level.playSound(null, pos,
                SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES,
                SoundSource.BLOCKS, 1.0F,
                0.8F + level.random.nextFloat() * 0.4F);
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    new BlockParticleOption(ParticleTypes.BLOCK, state),
                    pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    20,
                    0.25, 0.25, 0.25,
                    0.05);
        }
        return super.use(state, level, pos, player, hand, hitResult);
    }

    @Override
    public void entityInside(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
        if (!level.isClientSide && entity instanceof Mob mob && mob.getMobType() == MobType.UNDEAD) {
            mob.hurt(level.damageSources().magic(), 2.0F);
        }
    }

    @Override
    public @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor levelAccessor, @NotNull BlockPos currentPos, @NotNull BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            levelAccessor.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }
        if (direction == Direction.DOWN.getOpposite() && !state.canSurvive(levelAccessor, currentPos)) {
            levelAccessor.scheduleTick(currentPos, this, 1);
        }
        if (direction == Direction.DOWN) {
            return state.setValue(IS_HEAD, !neighborState.is(this));
        }
        return super.updateShape(state, direction, neighborState, levelAccessor, currentPos, neighborPos);
    }

    @Override
    public boolean canSurvive(@NotNull BlockState state, LevelReader levelReader, BlockPos pos) {
        BlockPos belowPos = pos.relative(Direction.DOWN.getOpposite());
        BlockState belowState = levelReader.getBlockState(belowPos);
        return belowState.is(this) || belowState.isFaceSturdy(levelReader, belowPos, Direction.DOWN);
    }

    @Override
    public void tick(BlockState state, @NotNull ServerLevel serverLevel, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (!state.canSurvive(serverLevel, pos)) {
            serverLevel.destroyBlock(pos, true);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(IS_HEAD, SHEARED, WATERLOGGED);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter blockGetter, @NotNull BlockPos pos, @NotNull CollisionContext collisionContext) {
        return state.getValue(IS_HEAD) ? AABB_HEAD : AABB_BODY;
    }
}
