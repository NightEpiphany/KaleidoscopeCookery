package com.github.ysbbbbbb.kaleidoscopecookery.item;

import com.github.ysbbbbbb.kaleidoscopecookery.init.ModArmorMaterials;
import com.github.ysbbbbbb.kaleidoscopecookery.util.PortHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.equipment.ArmorType;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

public class StrawHatItem extends Item {

    private final boolean hasFlower;

    public StrawHatItem(boolean hasFlower, Properties properties) {
        super(properties.stacksTo(1).humanoidArmor(ModArmorMaterials.FARMER, ArmorType.HELMET));
        this.hasFlower = hasFlower;
    }

    public boolean hasFlower() {
        return hasFlower;
    }

    @Override
    public void appendHoverText(@NonNull ItemStack itemStack, @NonNull TooltipContext tooltipContext, @NonNull TooltipDisplay tooltipDisplay, @NonNull Consumer<Component> consumer, @NonNull TooltipFlag tooltipFlag) {
        consumer.accept(Component.translatable("tooltip.kaleidoscope_cookery.straw_hat").withStyle(ChatFormatting.GRAY));
    }
}
