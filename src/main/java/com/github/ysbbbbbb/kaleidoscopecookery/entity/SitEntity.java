package com.github.ysbbbbbb.kaleidoscopecookery.entity;

import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.misc.TrashCanBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModEntities;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModSounds;
import com.github.ysbbbbbb.kaleidoscopecookery.init.tag.TagMod;
import com.github.ysbbbbbb.kaleidoscopecookery.util.SitUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

public class SitEntity extends Entity {

    public static final int DEFAULT = 0;
    public static final int TRASH_CAN = 1;
    private static final EntityDataAccessor<Integer> SIT_TYPE = SynchedEntityData.defineId(SitEntity.class, EntityDataSerializers.INT);
    private int passengerTick = 0;
    public SitEntity(EntityType<? extends SitEntity> type, Level level) {
        super(type, level);
    }

    public SitEntity(Level level, BlockPos pos) {
        this(ModEntities.SIT, level);
        this.setPos(pos.getX() + 0.5, pos.getY() + 0.4375, pos.getZ() + 0.5);
        noPhysics = true;
    }

    public SitEntity(Level worldIn, BlockPos pos, double y) {
        this(worldIn, pos);
        this.setPos(pos.getX() + 0.5, pos.getY() + y, pos.getZ() + 0.5);
    }

    public SitEntity(Level worldIn, BlockPos pos, double y, int sitType) {
        this(worldIn, pos, y);
        this.setSitType(sitType);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            this.checkBelowWorld();
            this.checkPassengers();

            // 每秒检查一次所处位置是否有方块，没有就删除实体
            if (this.tickCount % 20 == 0) {
                BlockState blockState = this.level().getBlockState(this.blockPosition());
                if (!blockState.is(TagMod.SITTABLE)) {
                    this.discard();
                }
            }
        }
    }

    @Override
    protected void removePassenger(@NonNull Entity passenger) {
        // 玩家脱离骑乘垃圾桶实体，此时停止动画，并播放声音
        if (this.getSitType() == SitEntity.TRASH_CAN && passenger instanceof Player player) {
            // 获取垃圾桶
            BlockPos blockPos = this.blockPosition();
            if (level().getBlockEntity(blockPos) instanceof TrashCanBlockEntity trashCan) {
                trashCan.player1State.stop();
                trashCan.player2State.stop();
                player.playSound(ModSounds.TRASH_CAN);
            }
        }

        super.removePassenger(passenger);
    }

    private void checkPassengers() {
        if (this.getPassengers().isEmpty()) {
            passengerTick++;
        } else {
            passengerTick = 0;
        }
        if (passengerTick > 10) {
            this.discard();
        }
    }

    @Override
    public @NonNull Vec3 getDismountLocationForPassenger(@NonNull LivingEntity passenger) {
        if (passenger instanceof Player player) {
            Vec3 resetPosition = SitUtil.getPreviousPlayerPosition(player, this);

            if (resetPosition != null) {
                discard();
                return resetPosition;
            }
        }

        discard();
        return super.getDismountLocationForPassenger(passenger);
    }

    @Override
    public void remove(@NonNull RemovalReason reason) {
        super.remove(reason);
        SitUtil.removeSitEntity(level(), blockPosition());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(SIT_TYPE, DEFAULT);
    }

    @Override
    public void readAdditionalSaveData(@NonNull ValueInput nbt) {}

    @Override
    public void addAdditionalSaveData(@NonNull ValueOutput nbt) {}

    @Override
    public @NonNull Packet<ClientGamePacketListener> getAddEntityPacket(@NonNull ServerEntity serverEntity) {
        return new ClientboundAddEntityPacket(this, serverEntity);
    }

    @Override
    public boolean hurtServer(@NonNull ServerLevel level, @NonNull DamageSource source, float amount) {
        return false;
    }

    @Override
    public boolean shouldRender(double x, double y, double z) {
        return false;
    }

    public int getSitType() {
        return this.entityData.get(SIT_TYPE);
    }

    public void setSitType(int sitType) {
        this.entityData.set(SIT_TYPE, sitType);
    }
}
