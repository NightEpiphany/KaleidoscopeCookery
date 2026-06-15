package com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv.stockpot;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class FlexStockpotViewType extends StockpotViewType {
    public static final FlexStockpotViewType INSTANCE = new FlexStockpotViewType();

    private FlexStockpotViewType() {
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("emi.category.kaleidoscope_cookery.flex_stockpot");
    }

    @Override
    public Identifier getId() {
        return Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "flex_stockpot");
    }
}
