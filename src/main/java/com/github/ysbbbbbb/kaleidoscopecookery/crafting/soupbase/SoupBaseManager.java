package com.github.ysbbbbbb.kaleidoscopecookery.crafting.soupbase;

import com.github.ysbbbbbb.kaleidoscopecookery.api.recipe.soupbase.ISoupBase;
import com.google.common.collect.Maps;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

import java.util.Map;

public class SoupBaseManager {
    private static final Map<Identifier, ISoupBase> ALL_SOUP_BASES = Maps.newLinkedHashMap();

    public static void registerSoupBase(ISoupBase soupBase) {
        if (ALL_SOUP_BASES.containsKey(soupBase.getName())) {
            throw new IllegalArgumentException("Soup base with name " + soupBase.getName() + " already exists!");
        }
        ALL_SOUP_BASES.put(soupBase.getName(), soupBase);
    }

    public static void registerFluidSoupBase(Identifier name, Item bucketItem, int bubbleColor) {
        registerSoupBase(new FluidSoupBase(name, bucketItem, bubbleColor));
    }

    public static void registerMobSoupBase(Identifier name, Item bucketItem, int bubbleColor) {
        registerSoupBase(new MobSoupBase(name, bucketItem, bubbleColor));
    }

    public static void registerMobSoupBase(Identifier name, Item mobBucketItem) {
        registerSoupBase(new MobSoupBase(name, mobBucketItem));
    }

    public static ISoupBase getSoupBase(Identifier name) {
        return ALL_SOUP_BASES.get(name);
    }

    public static boolean containsSoupBase(Identifier name) {
        return ALL_SOUP_BASES.containsKey(name);
    }

    public static Map<Identifier, ISoupBase> getAllSoupBases() {
        return ALL_SOUP_BASES;
    }
}
