package com.github.ysbbbbbb.kaleidoscopecookery.inventory.tooltip;

import com.github.ysbbbbbb.kaleidoscopecookery.util.forge.IItemHandler;
import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

public record ItemContainerTooltip(IItemHandler handler) implements TooltipComponent {
}
