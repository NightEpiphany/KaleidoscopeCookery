package com.github.ysbbbbbb.kaleidoscopecookery.item;

import com.github.ysbbbbbb.kaleidoscopecookery.util.PortHelper;
import com.google.common.collect.Lists;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.function.Consumer;

public class FoodWithEffectsItem extends Item {
    private final List<MobEffectInstance> effectInstances = Lists.newArrayList();

    public FoodWithEffectsItem(Properties p, FoodProperties properties, Consumable consumable) {
        super(p.food(properties));
        consumable.onConsumeEffects().forEach(consumeEffect ->  {
            if (consumeEffect instanceof ApplyStatusEffectsConsumeEffect(List<MobEffectInstance> effects, float probability)) {
                effectInstances.addAll(effects);
            }
        });
    }

    @Override
    public void appendHoverText(ItemStack stack, @NonNull TooltipContext tooltip, @NonNull TooltipDisplay tooltipDisplay, @NonNull Consumer<Component> consumer, @NonNull TooltipFlag tooltipFlag) {
        Identifier id = BuiltInRegistries.ITEM.getKey(stack.getItem());
        String key = "tooltip.%s.%s.maxim".formatted(id.getNamespace(), id.getPath());
        MutableComponent full = Component.translatable(key).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC);
        // 先拿到纯文本，再按 \n 切
        String text = full.getString();
        for (String line : text.split("\n")) {
            if (!line.isEmpty()) {
                consumer.accept(Component.literal(line).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
            } else {
                consumer.accept(CommonComponents.EMPTY);
            }
            if (!this.effectInstances.isEmpty()) {
                consumer.accept(CommonComponents.space());
                PotionContents.addPotionTooltip(this.effectInstances, consumer, 1.0F, tooltip.tickRate());
            }
        }
    }
}
