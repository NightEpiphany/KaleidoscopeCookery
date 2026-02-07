package com.github.ysbbbbbb.kaleidoscopecookery.compact.jade.block;

import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen.KitchenwareRacksBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.compact.jade.ModJadePlugin;
import com.google.common.collect.Lists;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;
import snownee.jade.api.Accessor;
import snownee.jade.api.view.*;

import java.util.List;

public enum KitchenwareRackComponentProvider implements IServerExtensionProvider<ItemStack>, IClientExtensionProvider<ItemStack, ItemView> {
    INSTANCE;

    @Override
    public @NonNull List<ClientViewGroup<ItemView>> getClientGroups(@NonNull Accessor<?> accessor, @NonNull List<ViewGroup<ItemStack>> list) {
        return ClientViewGroup.map(list, ItemView::new, null);
    }

    @Override
    @Nullable
    public List<ViewGroup<ItemStack>> getGroups(Accessor<?> accessor) {
        Object target = accessor.getTarget();
        if (target instanceof KitchenwareRacksBlockEntity kitchenwareRacks) {
            List<ItemStack> list = Lists.newArrayList();
            if (!kitchenwareRacks.getItemLeft().isEmpty()) {
                list.add(kitchenwareRacks.getItemLeft());
            }
            if (!kitchenwareRacks.getItemRight().isEmpty()) {
                list.add(kitchenwareRacks.getItemRight());
            }
            return List.of(new ViewGroup<>(list));
        }
        return null;
    }

    @Override
    public @NonNull Identifier getUid() {
        return ModJadePlugin.KITCHENWARE_RACK;
    }
}
