package com.github.ysbbbbbb.kaleidoscopecookery.client.event;

import com.github.ysbbbbbb.kaleidoscopecookery.entity.SitEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

@Environment(EnvType.CLIENT)
public final class CameraEvent {
    private CameraEvent() {
    }

    public static float lockPitch(float originalPitch) {
        CameraType cameraType = Minecraft.getInstance().options.getCameraType();
        if (!cameraType.isFirstPerson()) {
            return originalPitch;
        }
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return originalPitch;
        }
        if (player.getVehicle() instanceof SitEntity sitEntity && sitEntity.getSitType() == SitEntity.TRASH_CAN) {
            return 0;
        }
        return originalPitch;
    }
}
