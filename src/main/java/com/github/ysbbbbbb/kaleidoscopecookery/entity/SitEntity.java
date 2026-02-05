package com.github.ysbbbbbb.kaleidoscopecookery.entity;

import com.github.ysbbbbbb.kaleidoscopecookery.init.ModEntities;
import com.github.ysbbbbbb.kaleidoscopecookery.util.SitUtil;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

public class SitEntity extends Entity {
    public SitEntity(EntityType<? extends SitEntity> type, Level level) {
        super(type, level);
    }

    public SitEntity(Level level) {
        super(ModEntities.SIT, level);
        noPhysics = true;
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
    protected void defineSynchedData(@NonNull Builder builder) {}

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
}
