package com.github.ysbbbbbb.kaleidoscopecookery.client.tooltip;

import com.github.ysbbbbbb.kaleidoscopecookery.inventory.tooltip.ItemContainerTooltip;
import com.github.ysbbbbbb.kaleidoscopecookery.util.PortHelper;
import com.github.ysbbbbbb.kaleidoscopecookery.util.neo.IItemHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class ClientItemContainerTooltip implements ClientTooltipComponent {
    private final NonNullList<ItemStack> items = NonNullList.create();
    private @Nullable MutableComponent emptyTip = null;

    public ClientItemContainerTooltip(ItemContainerTooltip containerTooltip) {
        IItemHandler handler = containerTooltip.handler();
        for (int i = 0; i < handler.getSlots(); i++) {
            ItemStack stack = handler.getStackInSlot(i);
            if (!stack.isEmpty()) {
                this.items.add(stack);
            }
        }
        if (items.isEmpty()) {
            this.emptyTip = Component.translatable("tooltip.kaleidoscope_cookery.item_container.empty");
        }
    }

    @Override
    public int getHeight(@NonNull Font font) {
        if (emptyTip != null) {
            return 10;
        }
        return 20;
    }

    @Override
    public int getWidth(@NonNull Font font) {
        if (emptyTip != null) {
            return font.width(emptyTip);
        }
        return items.size() * 20;
    }

    @Override
    public void extractImage(@NonNull Font font, int pX, int pY, int a, int b, @NonNull GuiGraphicsExtractor guiGraphics) {
        if (emptyTip != null) {
            guiGraphics.text(font, emptyTip, pX, pY, PortHelper.DEFAULT_COLOR);
        } else {
            int i = 0;
            for (ItemStack stack : this.items) {
                int xOffset = pX + i * 20;
                guiGraphics.fakeItem(stack, xOffset, pY);
                guiGraphics.itemDecorations(font, stack, xOffset, pY);
                i++;
            }
        }
    }
}
