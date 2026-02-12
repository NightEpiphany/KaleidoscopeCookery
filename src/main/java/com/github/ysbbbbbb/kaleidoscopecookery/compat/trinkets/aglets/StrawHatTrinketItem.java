package com.github.ysbbbbbb.kaleidoscopecookery.compat.trinkets.aglets;

import com.github.ysbbbbbb.kaleidoscopecookery.item.StrawHatItem;
import dev.emi.trinkets.TrinketSlot;
import dev.emi.trinkets.api.*;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;

public class StrawHatTrinketItem extends StrawHatItem implements Trinket {

    public StrawHatTrinketItem(boolean hasFlower) {
        super(hasFlower);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, Player user, @NotNull InteractionHand hand) {
        ItemStack stack = user.getItemInHand(hand);
        if (equipItem(user, stack)) {
            return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
        }
        return super.use(world, user, hand);
    }

    public static boolean equipItem(Player user, ItemStack stack) {
        return equipItem((LivingEntity) user, stack);
    }

    public static boolean equipItem(LivingEntity user, ItemStack stack) {
        var optional = TrinketsApi.getTrinketComponent(user);
        if (optional.isPresent()) {
            TrinketComponent comp = optional.get();
            for (var group : comp.getInventory().values()) {
                for (TrinketInventory inv : group.values()) {
                    for (int i = 0; i < inv.getContainerSize(); i++) {
                        if (inv.getItem(i).isEmpty()) {
                            SlotReference ref = new SlotReference(inv, i);
                            if (TrinketSlot.canInsert(stack, ref, user)) {
                                ItemStack newStack = stack.copy();
                                inv.setItem(i, newStack);
                                SoundEvent soundEvent = stack.getItem() instanceof Equipable eq ? eq.getEquipSound() : null;
                                if (!stack.isEmpty() && soundEvent != null) {
                                    user.gameEvent(GameEvent.EQUIP);
                                    user.playSound(soundEvent, 1.0F, 1.0F);
                                }
                                stack.setCount(0);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
