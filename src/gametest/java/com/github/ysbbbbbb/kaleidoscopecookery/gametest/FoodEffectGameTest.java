package com.github.ysbbbbbb.kaleidoscopecookery.gametest;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModEffects;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackLinkedSet;

public class FoodEffectGameTest {
    @GameTest
    public void creativeInventoryCanHashEveryModItem(GameTestHelper helper) {
        var stacks = ItemStackLinkedSet.createTypeAndComponentsSet();
        for (var item : BuiltInRegistries.ITEM) {
            if (BuiltInRegistries.ITEM.getKey(item).getNamespace().equals(KaleidoscopeCookery.MOD_ID)) {
                ItemStack stack = item.getDefaultInstance();
                stacks.add(stack);
                helper.assertTrue(stacks.contains(stack.copy()), Component.literal("Cannot look up " + item));
            }
        }
        helper.succeed();
    }

    @GameTest
    public void donkeyBurgerStillAppliesSatiatedShield(GameTestHelper helper) {
        var player = helper.makeMockServerPlayerInLevel();
        ModItems.DONKEY_BURGER.getDefaultInstance().finishUsingItem(helper.getLevel(), player);
        helper.assertTrue(player.hasEffect(ModEffects.SATIATED_SHIELD),
                Component.literal("Donkey burger lost its satiated shield effect"));
        helper.succeed();
    }
}
