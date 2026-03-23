package com.github.ysbbbbbb.kaleidoscopecookery.init;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.loot.AdvanceBlockMatchTool;
import com.github.ysbbbbbb.kaleidoscopecookery.loot.AdvanceEntityMatchTool;
import com.github.ysbbbbbb.kaleidoscopecookery.loot.ChefRecipeTradeFunction;
import com.github.ysbbbbbb.kaleidoscopecookery.loot.RecipeRandomlyFunction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

public final class ModLootModifier {
    public static void registerLootModifiers() {
        Registry.register(BuiltInRegistries.LOOT_CONDITION_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "advance_entity_match_tool"), AdvanceEntityMatchTool.CODEC);
        Registry.register(BuiltInRegistries.LOOT_CONDITION_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "advance_block_match_tool"), AdvanceBlockMatchTool.CODEC);
        Registry.register(BuiltInRegistries.LOOT_FUNCTION_TYPE, RecipeRandomlyFunction.ID, RecipeRandomlyFunction.CODEC);
        Registry.register(BuiltInRegistries.LOOT_FUNCTION_TYPE, ChefRecipeTradeFunction.ID, ChefRecipeTradeFunction.CODEC);
    }
}
