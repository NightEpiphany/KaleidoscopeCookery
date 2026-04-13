package com.github.ysbbbbbb.kaleidoscopecookery.event;

import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopecookery.item.TeapotItem;
import com.github.ysbbbbbb.kaleidoscopecookery.item.TransmutationLunchBagItem;
import com.github.ysbbbbbb.kaleidoscopecookery.network.message.ThrowBaoziMessage;
import io.github.fabricators_of_create.porting_lib.entity.events.PlayerInteractionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import static io.github.fabricators_of_create.porting_lib.entity.events.PlayerInteractionEvents.LEFT_CLICK_EMPTY;

public class LeftClickEvent {
    public static void register() {
        LEFT_CLICK_EMPTY.register(LeftClickEvent::onHandleBaoZi);
        LEFT_CLICK_EMPTY.register(LeftClickEvent::onHandleTeaPot);
    }

    private static void onHandleTeaPot(PlayerInteractionEvents.LeftClickEmpty event) {
        Player player = event.getEntity();
        if (player.isSecondaryUseActive() && event.getHand() == InteractionHand.MAIN_HAND) {
            ItemStack mainHandItem = player.getMainHandItem();
            if (mainHandItem.is(ModItems.TEAPOT)) {
                TeapotItem.clearAll(mainHandItem, event.getEntity());
                event.setCanceled(true);
            }
        }
    }

    //肉包打狗，AUV，地道！
    private static void onHandleBaoZi(PlayerInteractionEvents.LeftClickEmpty event) {
        Player player = event.getPlayer();
        InteractionHand hand = event.getHand();
        if (player == null) return;
        ItemStack mainHandItem = player.getMainHandItem();
        if (player.isSecondaryUseActive()
                && hand == InteractionHand.MAIN_HAND
                && mainHandItem.is(ModItems.BAOZI)
                && ClientPlayNetworking.canSend(ThrowBaoziMessage.TYPE)
        ) {
            ClientPlayNetworking.send(new ThrowBaoziMessage());
        }
        if (player.isSecondaryUseActive() && mainHandItem.is(ModItems.TRANSMUTATION_LUNCH_BAG)) {
            // 摆动动作时，取出物品
            if (TransmutationLunchBagItem.dropContents(mainHandItem, player)
            && mainHandItem.getItem() instanceof TransmutationLunchBagItem t) {
                t.playDropContentsSound(player);
            }
        }
    }
}