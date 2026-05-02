package com.github.ysbbbbbb.kaleidoscopecookery.init;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.level.storage.loot.LootTable;

@SuppressWarnings("all")
public final class ModVillager {
    public static final Identifier CHEF_GIFT = Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "gameplay/hero_of_the_village/chef_gift");

    public static final ResourceKey<LootTable> CHEF_GIFT_LOOT_KEY = ResourceKey.create(Registries.LOOT_TABLE, CHEF_GIFT);

    public static final VillagerProfession CHEF_VALUE = new VillagerProfession(Component.translatable("entity.minecraft.villager.chef"),
            poi -> poi.value() == ModPoi.STOVE,
            poi -> poi.value() == ModPoi.STOVE,
            ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_BUTCHER);

    public static final ResourceKey<VillagerProfession> CHEF = ResourceKey.create(Registries.VILLAGER_PROFESSION, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "chef"));

    public static void registerVillagerProfessions() {
        Registry.register(BuiltInRegistries.VILLAGER_PROFESSION, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "chef"), CHEF_VALUE);
    }
}
