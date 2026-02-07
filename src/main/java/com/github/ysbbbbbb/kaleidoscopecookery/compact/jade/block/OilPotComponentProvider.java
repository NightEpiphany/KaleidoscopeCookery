package com.github.ysbbbbbb.kaleidoscopecookery.compact.jade.block;

import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen.OilPotBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.compact.jade.ModJadePlugin;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;
import snownee.jade.api.Accessor;
import snownee.jade.api.view.*;

import java.util.Collections;
import java.util.List;

public enum OilPotComponentProvider implements IServerExtensionProvider<ItemStack>, IClientExtensionProvider<ItemStack, ItemView> {
    INSTANCE;

    @Override
    public @NonNull List<ClientViewGroup<ItemView>> getClientGroups(@NonNull Accessor<?> accessor, @NonNull List<ViewGroup<ItemStack>> list) {
        return ClientViewGroup.map(list, ItemView::new, null);
    }

    @Override
    @Nullable
    public List<ViewGroup<ItemStack>> getGroups(Accessor<?> accessor) {
        Object target = accessor.getTarget();
        if (target instanceof OilPotBlockEntity oilPot) {
            int oilCount = oilPot.getOilCount();
            if (oilCount > 0) {
                ItemStack stack = new ItemStack(ModItems.OIL, oilCount);
                return List.of(new ViewGroup<>(Collections.singletonList(stack)));
            }
        }
        return null;
    }

    @Override
    public @NonNull Identifier getUid() {
        return ModJadePlugin.OIL_POT;
    }
}
