package com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv.pot;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class FlexPotViewType extends PotViewType {
    public static final FlexPotViewType INSTANCE = new FlexPotViewType();

    private FlexPotViewType() {
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("emi.category.kaleidoscope_cookery.flex_pot");
    }

    @Override
    public Identifier getId() {
        return Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "flex_pot");
    }
}
