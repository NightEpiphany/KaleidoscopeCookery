package com.github.ysbbbbbb.kaleidoscopecookery.compact.jade.block;

import com.github.ysbbbbbb.kaleidoscopecookery.block.kitchen.MillstoneBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.block.kitchen.NinePart;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen.MillstoneBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.compact.jade.ModJadePlugin;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jspecify.annotations.NonNull;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.JadeUI;
import snownee.jade.api.view.ProgressView;

public enum MillstoneComponentProvider implements IBlockComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(@NonNull ITooltip tooltip, BlockAccessor accessor, @NonNull IPluginConfig pluginConfig) {
        NinePart part = accessor.getBlockState().getValue(MillstoneBlock.PART);
        BlockPos pos = accessor.getPosition();
        BlockPos centerPos = pos.subtract(new Vec3i(part.getPosX(), 0, part.getPosY()));
        BlockEntity te = accessor.getLevel().getBlockEntity(centerPos);
        if (!(te instanceof MillstoneBlockEntity millstone)) {
            return;
        }
        if (millstone.getInput().isEmpty() && millstone.getOutput().isEmpty()) {
            return;
        }
        tooltip.add(JadeUI.item(millstone.getInput()));
        tooltip.append(JadeUI.progress(ProgressView.read(new ProgressView.Data(millstone.getProgressPercent()))));
        tooltip.append(JadeUI.item(millstone.getOutput()));
    }

    @Override
    public @NonNull Identifier getUid() {
        return ModJadePlugin.MILLSTONE;
    }
}
