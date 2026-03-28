package com.github.ysbbbbbb.kaleidoscopecookery.event;

import com.github.ysbbbbbb.kaleidoscopecookery.api.blockentity.IKaleidoscopeBlockEntity;
import net.fabricmc.fabric.api.event.player.ItemEvents;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;

public class PlaceIngredientEvent {
    public static void register() {
        ItemEvents.USE_ON.register(call -> {
            if (call.getItemInHand().getItem() instanceof BlockItem && call.getClickedFace() == Direction.UP) {
                if (call.getLevel().getBlockEntity(call.getClickedPos()) instanceof IKaleidoscopeBlockEntity) {
                    return InteractionResult.FAIL;
                }
            }
            return null;
        });
    }
}
