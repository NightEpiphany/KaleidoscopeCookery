package com.github.ysbbbbbb.kaleidoscopecookery.client.event;

import com.github.ysbbbbbb.kaleidoscopecookery.api.event.client.RenderPlayerEvent;
import com.github.ysbbbbbb.kaleidoscopecookery.entity.SitEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.entity.player.Player;

@Environment(EnvType.CLIENT)
public class PlayerRenderEvent {

    public static void onPlayerRender(RenderPlayerEvent.Pre event) {
        Player player = event.getPlayer();
        // 如果玩家坐在垃圾桶实体上，取消玩家的渲染
        if (player.getVehicle() instanceof SitEntity sitEntity && sitEntity.getSitType() == SitEntity.TRASH_CAN) {
            event.setCanceled(true);
        }
    }
}
