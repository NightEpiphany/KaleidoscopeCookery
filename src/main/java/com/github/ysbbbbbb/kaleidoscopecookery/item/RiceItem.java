package com.github.ysbbbbbb.kaleidoscopecookery.item;

import com.github.ysbbbbbb.kaleidoscopecookery.init.ModBlocks;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

public class RiceItem extends BlockItem {
    public RiceItem(Item.Properties  settings) {
        super(ModBlocks.RICE_CROP, settings);
    }

    // 留空不显示

    @Override
    public void appendHoverText(@NonNull ItemStack itemStack, @NonNull TooltipContext tooltipContext, @NonNull TooltipDisplay tooltipDisplay, @NonNull Consumer<Component> consumer, @NonNull TooltipFlag tooltipFlag) {

    }
}
