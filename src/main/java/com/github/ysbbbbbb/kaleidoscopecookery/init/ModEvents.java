package com.github.ysbbbbbb.kaleidoscopecookery.init;

import com.github.ysbbbbbb.kaleidoscopecookery.api.event.ActionEventCallback;
import com.github.ysbbbbbb.kaleidoscopecookery.event.SpecialRecipeItemEvent;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

// 所有的自定义事件
public class ModEvents {

    @Deprecated
    public static final Event<ActionEventCallback.MillstoneFinish> MILLSTONE_FINISH =
            EventFactory.createArrayBacked(ActionEventCallback.MillstoneFinish.class, call -> action -> {
                for (ActionEventCallback.MillstoneFinish listener : call) {
                    listener.onMillstoneFinish(action);
                }
            });
    @Deprecated
    public static final Event<ActionEventCallback.MillstoneTakeItem> MILLSTONE_TAKE_ITEM =
            EventFactory.createArrayBacked(ActionEventCallback.MillstoneTakeItem.class, call -> action -> {
                for (ActionEventCallback.MillstoneTakeItem listener : call) {
                    listener.onMillstoneTakeItem(action);
                }
            });

    public static final Event<ActionEventCallback.CheckSpecialItem> CHECK_SPECIAL_ITEM =
            EventFactory.createArrayBacked(ActionEventCallback.CheckSpecialItem.class, call -> action -> {
                for (ActionEventCallback.CheckSpecialItem listener : call) {
                    listener.onCheckItemEvent(action);
                }
            });

    public static final Event<ActionEventCallback.DeductSpecialItem> DEDUCT_SPECIAL_ITEM =
            EventFactory.createArrayBacked(ActionEventCallback.DeductSpecialItem.class, call -> action -> {
                for (ActionEventCallback.DeductSpecialItem listener : call) {
                    listener.onDeductItemEvent(action);
                }
            });



    public static final Event<ActionEventCallback.LivingEntityHurt> LIVING_ENTITY_HURT =
            EventFactory.createArrayBacked(ActionEventCallback.LivingEntityHurt.class, call -> action -> {
                for (ActionEventCallback.LivingEntityHurt listener : call) {
                    listener.onLivingEntityHurt(action);
                }
            });

    public static final Event<ActionEventCallback.PlayerLeftClick> PLAYER_LEFT_CLICK =
            EventFactory.createArrayBacked(ActionEventCallback.PlayerLeftClick.class, call -> (player, hand) -> {
                for (ActionEventCallback.PlayerLeftClick listener : call) {
                    listener.onPlayerLeftClick(player, hand);
                }
            });

    public static void init() {
        SpecialRecipeItemEvent.onCheckItemEvent();
        SpecialRecipeItemEvent.onDeductItemEvent();
    }
}
