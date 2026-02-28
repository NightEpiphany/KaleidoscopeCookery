package com.github.ysbbbbbb.kaleidoscopecookery.compat.jade.block;

import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen.ChoppingBoardBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.jade.ModJadePlugin;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.Element;
import snownee.jade.api.ui.IDisplayHelper;
import snownee.jade.api.ui.JadeUI;

public enum ChoppingBoardComponentProvider implements IBlockComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(@NonNull ITooltip tooltip, BlockAccessor accessor, @NonNull IPluginConfig pluginConfig) {
        if (!(accessor.getBlockEntity() instanceof ChoppingBoardBlockEntity choppingBoard)) {
            return;
        }
        ItemStack cutStack = choppingBoard.getCurrentCutStack();
        if (cutStack.isEmpty()) {
            return;
        }
        Element icon = JadeUI.smallItem(cutStack);
        MutableComponent stackName = IDisplayHelper.get().stripColor(cutStack.getHoverName());
        MutableComponent info = Component.translatable("jade.kaleidoscope_cookery.chopping_board.cut_count",
                choppingBoard.getCurrentCutCount(), choppingBoard.getMaxCutCount());
        tooltip.add(icon);
        tooltip.append(JadeUI.spacer(2, 1));
        tooltip.append(stackName);
        tooltip.add(info);
    }


    @Override
    public @NonNull Identifier getUid() {
        return ModJadePlugin.CHOPPING_BOARD;
    }
}
