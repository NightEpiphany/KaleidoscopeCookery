package com.github.ysbbbbbb.kaleidoscopecookery.item;

import com.github.ysbbbbbb.kaleidoscopecookery.api.event.SickleHarvestEvent;
import com.github.ysbbbbbb.kaleidoscopecookery.block.crop.RiceCropBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModEvents;
import com.github.ysbbbbbb.kaleidoscopecookery.init.tag.TagMod;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

public class SickleItem extends Item {

    public SickleItem(Properties properties) {
        super(properties);
    }

    public SickleItem() {
        this(new Properties().stacksTo(1));
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        // 生成挥动音效和粒子
        Player player = context.getPlayer();
        if (player == null) {
            return super.useOn(context);
        }
        Level level = context.getLevel();
        if (!(level instanceof ServerLevel serverLevel)) {
            return InteractionResult.SUCCESS;
        }

        BlockPos pos = context.getClickedPos();
        ItemStack stack = context.getItemInHand();
        int breakCount = 0;
        // 搜索方块的 5x5x2 范围内的可收割作物、草丛、灌木等并收割
        for (int x = -2; x <= 2; x++) {
            for (int y = 0; y <= 1; y++) {
                for (int z = -2; z <= 2; z++) {
                    if (harvest(pos, x, y, z, level, player, stack)) {
                        breakCount++;
                    }
                }
            }
        }

        serverLevel.playSound(null,
                player.getX(), player.getY(), player.getZ(),
                SoundEvents.PLAYER_ATTACK_SWEEP, player.getSoundSource(),
                1.0F, 1.0F);
        double d = -Mth.sin(player.getYRot() * (float) (Math.PI / 180.0));
        double e = Mth.cos(player.getYRot() * (float) (Math.PI / 180.0));
        if (player.level() instanceof ServerLevel) {
            ((ServerLevel)player.level()).sendParticles(ParticleTypes.SWEEP_ATTACK, player.getX() + d, player.getY(0.5), player.getZ() + e, 0, d, 0.0, e, 0.0);
        }
        stack.hurtAndBreak(breakCount, player, EquipmentSlot.MAINHAND);
        player.getCooldowns().addCooldown(stack, 10);
        return InteractionResult.SUCCESS;
    }

    private boolean harvest(BlockPos pos, int x, int y, int z, Level level, Player player, ItemStack stack) {
        BlockPos newPos = pos.offset(x, y, z);
        if (!level.mayInteract(player, newPos)) {
            return false;
        }
        BlockState blockState = level.getBlockState(newPos);
        if (blockState.isAir()) {
            return false;
        }
        // 黑名单
        if (blockState.is(TagMod.SICKLE_HARVEST_BLACKLIST)) {
            return false;
        }

        Block block = blockState.getBlock();
        // 触发事件
        SickleHarvestEvent event = new SickleHarvestEvent(player, stack, newPos, blockState);
        ModEvents.SICKLE_HARVEST.invoker().onSickleHarvest(event);
        if (event.isCanceled()) {
            return event.isCostDurability();
        }

        // 如果是作物，那么检查是否成熟
        if (block instanceof CropBlock cropBlock) {
            // 水稻特判
            if (block instanceof RiceCropBlock) {
                int position = blockState.getValue(RiceCropBlock.LOCATION);
                newPos = newPos.below(position);
                blockState = level.getBlockState(newPos);
            }
            if (cropBlock.isMaxAge(blockState)) {
                // 成熟则收割
                cropBlock.playerDestroy(level, player, newPos, blockState, null, ItemStack.EMPTY);
                BlockState stateForAge = cropBlock.getStateForAge(0);
                // 同步水属性状态
                BooleanProperty waterlogged = BlockStateProperties.WATERLOGGED;
                if (stateForAge.hasProperty(waterlogged)) {
                    stateForAge = stateForAge.setValue(waterlogged, blockState.getValue(waterlogged));
                }
                level.setBlock(newPos, stateForAge, Block.UPDATE_ALL);
                level.levelEvent(null, LevelEvent.PARTICLES_DESTROY_BLOCK, newPos, Block.getId(blockState));
                return true;
            }
            return false;
        }

        // 如果是灌木，直接破坏
        if (block instanceof BushBlock) {
            if (player instanceof ServerPlayer serverPlayer) {
                serverPlayer.gameMode.destroyBlock(newPos);
                level.levelEvent(null, LevelEvent.PARTICLES_DESTROY_BLOCK, newPos, Block.getId(blockState));
                return true;
            }
        }
        return false;
    }

    @Override
    public void appendHoverText(@NonNull ItemStack stack, @NonNull TooltipContext tooltip, @NonNull TooltipDisplay tooltipDisplay, @NonNull Consumer<Component> consumer, @NonNull TooltipFlag tooltipFlag) {
        consumer.accept(Component.translatable("tooltip.kaleidoscope_cookery.sickle").withStyle(ChatFormatting.GRAY));
    }
}
