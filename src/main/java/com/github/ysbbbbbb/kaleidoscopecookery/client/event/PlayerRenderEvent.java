package com.github.ysbbbbbb.kaleidoscopecookery.client.event;

import com.github.ysbbbbbb.kaleidoscopecookery.entity.SitEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.entity.player.Player;

@Deprecated
@Environment(EnvType.CLIENT)
public final class PlayerRenderEvent {
    private PlayerRenderEvent() {
    }

    public static boolean shouldCancel(Player player) {
        return player.getVehicle() instanceof SitEntity sitEntity
                && sitEntity.getSitType() == SitEntity.TRASH_CAN;
    }
}
