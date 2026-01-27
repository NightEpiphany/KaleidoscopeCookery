package com.github.ysbbbbbb.kaleidoscopecookery.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

public class WithTooltipsItem extends Item {
    private final String key;

    public WithTooltipsItem(Properties properties, String name) {
        super(properties);
        this.key = "tooltip.kaleidoscope_cookery." + name;
    }

    @Override
    public void appendHoverText(@NonNull ItemStack stack, @NonNull TooltipContext tooltip, @NonNull TooltipDisplay tooltipDisplay, @NonNull Consumer<Component> consumer, @NonNull TooltipFlag tooltipFlag) {
        consumer.accept(Component.translatable(key).withStyle(ChatFormatting.GRAY));
    }
}
