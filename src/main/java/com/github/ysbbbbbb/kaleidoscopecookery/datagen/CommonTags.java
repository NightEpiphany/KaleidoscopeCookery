package com.github.ysbbbbbb.kaleidoscopecookery.datagen;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class CommonTags {
    private CommonTags() {
    }

    public static final class Items {
        public static final TagKey<Item> ARMORS = itemTag("armors");
        public static final TagKey<Item> COBBLESTONES = itemTag("cobblestones");
        public static final TagKey<Item> EGGS = itemTag("eggs");
        public static final TagKey<Item> FENCES_WOODEN = itemTag("fences/wooden");
        public static final TagKey<Item> GEMS_DIAMOND = itemTag("gems/diamond");
        public static final TagKey<Item> GRAVELS = itemTag("gravels");
        public static final TagKey<Item> INGOTS_COPPER = itemTag("ingots/copper");
        public static final TagKey<Item> INGOTS_GOLD = itemTag("ingots/gold");
        public static final TagKey<Item> INGOTS_IRON = itemTag("ingots/iron");
        public static final TagKey<Item> MUSHROOMS = itemTag("mushrooms");
        public static final TagKey<Item> NUGGETS_IRON = itemTag("nuggets/iron");
        public static final TagKey<Item> SANDS_COLORLESS = itemTag("sands/colorless");
        public static final TagKey<Item> SANDS_RED = itemTag("sands/red");
        public static final TagKey<Item> SEEDS = itemTag("seeds");
        public static final TagKey<Item> TOOLS = itemTag("tools");

        private Items() {
        }
    }

    private static TagKey<Item> itemTag(String path) {
        return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", path));
    }
}
