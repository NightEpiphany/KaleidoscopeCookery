package com.github.ysbbbbbb.kaleidoscopecookery.compact.create.ponder.init;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import com.zurrtum.create.client.ponder.api.registration.PonderTagRegistrationHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

import java.util.Objects;

@Environment(EnvType.CLIENT)
public class KitchenBlockPonderTag {
    public static final Identifier KITCHEN_BLOCKS = Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "kitchen_blocks");

    public static void register(PonderTagRegistrationHelper<Identifier> helper) {
        helper.registerTag(KITCHEN_BLOCKS).addToIndex()
                .item(ModItems.KITCHEN_SHOVEL, true, false)
                .title("")
                .description("")
                .register();

        helper.addToTag(KITCHEN_BLOCKS)
                .add(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(ModItems.STOCKPOT)))
                .add(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(ModItems.POT)))
                .add(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(ModItems.RECIPE_ITEM)))
                .add(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(ModItems.STEAMER)))
                .add(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(ModItems.ENAMEL_BASIN)))
                .add(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(ModItems.MILLSTONE)))
                .add(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(ModItems.SHAWARMA_SPIT)));
    }
}
