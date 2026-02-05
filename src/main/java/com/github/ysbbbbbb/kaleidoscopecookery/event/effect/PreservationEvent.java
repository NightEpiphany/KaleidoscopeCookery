package com.github.ysbbbbbb.kaleidoscopecookery.event.effect;

import com.github.ysbbbbbb.kaleidoscopecookery.init.ModEffects;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.level.Level;

import java.util.List;

import static net.minecraft.world.effect.MobEffectCategory.HARMFUL;

public class PreservationEvent {
    public static void register() {
        UseItemCallback.EVENT.register(PreservationEvent::onUseItem);
    }

    private static InteractionResult onUseItem(Player player, Level world, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.has(DataComponents.FOOD) && player.hasEffect(ModEffects.PRESERVATION)) {
            Consumable consumable = stack.get(DataComponents.CONSUMABLE);
            if (consumable == null) {
                return InteractionResult.TRY_WITH_EMPTY_HAND;
            }
            for (var effectPair : consumable.onConsumeEffects()) {
                if (effectPair instanceof ApplyStatusEffectsConsumeEffect(
                        List<MobEffectInstance> effects, float probability
                )) {
                    effects.forEach(mobEffectHolder -> {
                        if (mobEffectHolder.getEffect().value().getCategory() == HARMFUL) {
                            player.removeEffect(mobEffectHolder.getEffect());
                        }
                    });
                }
            }
        }
        return InteractionResult.PASS;
    }
}
