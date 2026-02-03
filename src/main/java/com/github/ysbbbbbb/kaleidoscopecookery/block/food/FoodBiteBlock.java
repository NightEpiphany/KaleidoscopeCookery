package com.github.ysbbbbbb.kaleidoscopecookery.block.food;

import com.github.ysbbbbbb.kaleidoscopecookery.init.registry.FoodBiteAnimateTicks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class FoodBiteBlock extends FoodBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    protected final FoodProperties foodProperties;
    protected final Consumable consumable;
    protected final IntegerProperty bites;
    protected final int maxBites;
    protected final @Nullable FoodBiteAnimateTicks.AnimateTick animateTick;

    protected VoxelShape aabb = FoodBlock.AABB;

    public FoodBiteBlock(BlockBehaviour.Properties properties, FoodProperties foodProperties, Consumable consumable, int maxBites, FoodBiteAnimateTicks.@Nullable AnimateTick animateTick) {
        super(properties);
        this.maxBites = maxBites;
        this.foodProperties = foodProperties;
        this.consumable = consumable;
        this.bites = IntegerProperty.create("bites", 0, maxBites);
        this.animateTick = animateTick;
        // 重置一遍 BlockState，因为在父类 FoodBlock 中已经创建了一个默认的 BlockStateDefinition
        StateDefinition.Builder<Block, BlockState> builder = new StateDefinition.Builder<>(this);
        this.createBitesBlockStateDefinition(builder);
        this.stateDefinition = builder.create(Block::defaultBlockState, BlockState::new);
        this.registerDefaultState(this.stateDefinition.any().setValue(bites, 0).setValue(FACING, Direction.SOUTH));
    }

    public FoodBiteBlock(BlockBehaviour.Properties properties, FoodProperties foodProperties) {
        this(properties, foodProperties, Consumable.builder().build(), 3, null);
    }

    public IntegerProperty getBites() {
        return bites;
    }

    public int getMaxBites() {
        return maxBites;
    }

    @Override
    public void animateTick(@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos, @NonNull RandomSource random) {
        if (animateTick != null) {
            animateTick.animateTick(state, level, pos, random);
        }
    }

    public FoodBiteBlock setAABB(VoxelShape aabb) {
        this.aabb = aabb;
        return this;
    }

    @Override
    public @NotNull InteractionResult useWithoutItem(BlockState state, @NonNull Level level, @NonNull BlockPos pos, @NonNull Player player, @NonNull BlockHitResult hit) {
        int bites = state.getValue(this.bites);
        if (bites >= getMaxBites()) {
            level.destroyBlock(pos, true, player);
            return InteractionResult.SUCCESS;
        }
        if (level.isClientSide()) {
            if (eat(level, pos, state, player).consumesAction()) {
                return InteractionResult.SUCCESS;
            }
        }
        return eat(level, pos, state, player);
    }

    protected InteractionResult eat(Level level, BlockPos pos, BlockState state, Player player) {
        if (!player.canEat(foodProperties.canAlwaysEat())) {
            return InteractionResult.TRY_WITH_EMPTY_HAND;
        }
        player.getFoodData().eat(foodProperties);
        for (ConsumeEffect effect : consumable.onConsumeEffects()) {
            if (!level.isClientSide() && effect instanceof ApplyStatusEffectsConsumeEffect(
                    List<MobEffectInstance> effects, float probability
            ) && level.random.nextFloat() < probability) {
                player.addEffect(new MobEffectInstance(effects.getFirst()));
            }
        }
        level.playSound(null, pos, SoundEvents.GENERIC_EAT.value(), SoundSource.PLAYERS,
                0.5F, level.getRandom().nextFloat() * 0.1F + 0.9F);
        int bites = state.getValue(this.bites);
        level.gameEvent(player, GameEvent.EAT, pos);
        if (bites < getMaxBites()) {
            level.setBlock(pos, state.setValue(this.bites, bites + 1), Block.UPDATE_ALL);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public @NotNull VoxelShape getShape(@NonNull BlockState pState, @NonNull BlockGetter pLevel, @NonNull BlockPos pPos, @NonNull CollisionContext pContext) {
        return this.aabb;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    protected void createBitesBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(bites, FACING);
    }

    @Override
    protected int getAnalogOutputSignal(BlockState blockState, @NonNull Level level, @NonNull BlockPos blockPos, @NonNull Direction direction) {
        int value = blockState.getValue(bites);
        return (3 - value) * 5;
    }

    @Override
    public boolean hasAnalogOutputSignal(@NonNull BlockState state) {
        return true;
    }

    @Override
    protected boolean isPathfindable(@NonNull BlockState state, @NonNull PathComputationType pathComputationType) {
        return false;
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public @NonNull BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public @NonNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }
}
