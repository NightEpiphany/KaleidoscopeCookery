package com.github.ysbbbbbb.kaleidoscopecookery.event;

import com.github.ysbbbbbb.kaleidoscopecookery.entity.ScarecrowEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class ScarecrowFarmlandTrampleEvent {

    public static void register() {
        ModEvents.FARMLAND_TRAMPLE.register(event -> {
            LevelAccessor level = event.getLevel();
            BlockPos pos = event.getPos();
            if (level instanceof ServerLevel serverLevel) {
                List<ScarecrowEntity> entities = serverLevel.getEntitiesOfClass(ScarecrowEntity.class, new AABB(pos).inflate(16));
                if (!entities.isEmpty()) {
                    event.setCanceled(true);
                }
            }
        });

    }
}
