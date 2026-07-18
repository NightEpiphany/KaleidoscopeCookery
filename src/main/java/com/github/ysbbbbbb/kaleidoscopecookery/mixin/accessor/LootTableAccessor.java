package com.github.ysbbbbbb.kaleidoscopecookery.mixin.accessor;

import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(LootTable.class)
public interface LootTableAccessor {
    @Accessor("pools")
    List<LootPool> kaleidoscopeCookery$getPools();

    @Mutable
    @Accessor("pools")
    void kaleidoscopeCookery$setPools(List<LootPool> pools);
}
