package com.github.ysbbbbbb.kaleidoscopecookery.init;

import com.github.ysbbbbbb.kaleidoscopecookery.api.event.ActionEventCallback;
import com.github.ysbbbbbb.kaleidoscopecookery.event.SpecialRecipeItemEvent;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

// 所有的自定义事件
public class ModEvents {

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

    public static final Event<ActionEventCallback.FarmlandTrample> FARMLAND_TRAMPLE =
            EventFactory.createArrayBacked(ActionEventCallback.FarmlandTrample.class, call -> (event) -> {
                for (ActionEventCallback.FarmlandTrample listener : call) {
                    listener.onFarmlandTrample(event);
                }
            });

    public static final Event<ActionEventCallback.StockMatchRecipePre> STOCKPOT_RECIPE_PRE =
            EventFactory.createArrayBacked(ActionEventCallback.StockMatchRecipePre.class, call -> (event) -> {
                for (ActionEventCallback.StockMatchRecipePre listener : call) {
                    listener.onStockMatchRecipePre(event);
                }
            });

    public static final Event<ActionEventCallback.StockMatchRecipePost> STOCKPOT_RECIPE_POST =
            EventFactory.createArrayBacked(ActionEventCallback.StockMatchRecipePost.class, call -> (event) -> {
                for (ActionEventCallback.StockMatchRecipePost listener : call) {
                    listener.onStockMatchRecipePost(event);
                }
            });

    public static final Event<ActionEventCallback.SickleHarvest> SICKLE_HARVEST =
            EventFactory.createArrayBacked(ActionEventCallback.SickleHarvest.class, call -> (event) -> {
                for (ActionEventCallback.SickleHarvest listener : call) {
                    listener.onSickleHarvest(event);
                }
            });

    public static void init() {
        SpecialRecipeItemEvent.onCheckItemEvent();
        SpecialRecipeItemEvent.onDeductItemEvent();
    }
}
