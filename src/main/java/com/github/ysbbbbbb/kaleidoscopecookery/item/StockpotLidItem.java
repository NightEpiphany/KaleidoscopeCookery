package com.github.ysbbbbbb.kaleidoscopecookery.item;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public class StockpotLidItem extends ShieldItem {
    public static final ResourceLocation USING_PROPERTY = new ResourceLocation(KaleidoscopeCookery.MOD_ID, "using");
    private static final int NORMAL = 0;
    private static final int USING = 1;

    public StockpotLidItem() {
        super(new Item.Properties().durability(120));
    }

    @SuppressWarnings("unused")
    @Environment(EnvType.CLIENT)
    public static float getTexture(ItemStack stack, Level level, @Nullable LivingEntity entity, int seed) {
        if (entity != null && entity.isUsingItem() && entity.getUseItem() == stack) {
            return USING;
        }
        return NORMAL;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, List<Component> tooltip, @NotNull TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.kaleidoscope_cookery.stockpot_lid").withStyle(ChatFormatting.GRAY));
    }
}
