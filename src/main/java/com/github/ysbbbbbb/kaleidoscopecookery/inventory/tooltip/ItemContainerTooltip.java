package com.github.ysbbbbbb.kaleidoscopecookery.inventory.tooltip;

import com.github.ysbbbbbb.kaleidoscopecookery.util.neo.IItemHandler;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

public record ItemContainerTooltip(IItemHandler handler) implements TooltipComponent {
}
