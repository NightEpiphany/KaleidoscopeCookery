package com.github.ysbbbbbb.kaleidoscopecookery.compact.jade.block;

import com.github.ysbbbbbb.kaleidoscopecookery.api.blockentity.IStockpot;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen.StockpotBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.compact.jade.ModJadePlugin;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;
import snownee.jade.api.Accessor;
import snownee.jade.api.view.*;

import java.util.List;

public enum StockpotComponentProvider implements IServerExtensionProvider<ItemStack>, IClientExtensionProvider<ItemStack, ItemView> {
    INSTANCE;

    @Override
    public @NonNull List<ClientViewGroup<ItemView>> getClientGroups(@NonNull Accessor<?> accessor, @NonNull List<ViewGroup<ItemStack>> list) {
        return ClientViewGroup.map(list, ItemView::new, null);
    }

    @Override
    @Nullable
    public List<ViewGroup<ItemStack>> getGroups(Accessor<?> accessor) {
        Object target = accessor.getTarget();
        if (target instanceof StockpotBlockEntity stockpot) {
            if (stockpot.getStatus() < IStockpot.FINISHED) {
                List<ItemStack> list = stockpot.getInputs().stream().filter(s -> !s.isEmpty()).toList();
                return List.of(new ViewGroup<>(list));
            }
        }
        return null;
    }

    @Override
    public @NonNull Identifier getUid() {
        return ModJadePlugin.STOCKPOT;
    }
}
