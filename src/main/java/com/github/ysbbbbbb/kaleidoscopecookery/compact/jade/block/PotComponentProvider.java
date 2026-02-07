package com.github.ysbbbbbb.kaleidoscopecookery.compact.jade.block;

import com.github.ysbbbbbb.kaleidoscopecookery.api.blockentity.IPot;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen.PotBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.compact.jade.ModJadePlugin;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;
import snownee.jade.api.Accessor;
import snownee.jade.api.view.*;

import java.util.List;

public enum PotComponentProvider implements IServerExtensionProvider<ItemStack>, IClientExtensionProvider<ItemStack, ItemView> {
    INSTANCE;

    @Override
    public @NonNull List<ClientViewGroup<ItemView>> getClientGroups(@NonNull Accessor<?> accessor, @NonNull List<ViewGroup<ItemStack>> list) {
        return ClientViewGroup.map(list, ItemView::new, null);
    }

    @Override
    @Nullable
    public List<ViewGroup<ItemStack>> getGroups(Accessor<?> accessor) {
        Object target = accessor.getTarget();
        if (target instanceof PotBlockEntity pot) {
            if (pot.getStatus() < IPot.FINISHED) {
                List<ItemStack> list = pot.getInputs().stream().filter(s -> !s.isEmpty()).toList();
                return List.of(new ViewGroup<>(list));
            }
        }
        return null;
    }

    @Override
    public @NonNull Identifier getUid() {
        return ModJadePlugin.POT;
    }
}
