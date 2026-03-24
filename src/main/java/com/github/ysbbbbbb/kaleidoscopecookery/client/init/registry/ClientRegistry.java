package com.github.ysbbbbbb.kaleidoscopecookery.client.init.registry;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.client.conditions.*;
import com.github.ysbbbbbb.kaleidoscopecookery.client.event.FlatulenceEvent;
import com.github.ysbbbbbb.kaleidoscopecookery.client.event.PotOverlayEvent;
import com.github.ysbbbbbb.kaleidoscopecookery.client.model.MillstoneModel;
import com.github.ysbbbbbb.kaleidoscopecookery.client.render.block.*;
import com.github.ysbbbbbb.kaleidoscopecookery.client.render.item.StrawHatArmorRenderer;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.create.ponder.init.PonderCompat;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperties;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class ClientRegistry {
    public static void init() {
        // 注册盔甲渲染器
        ArmorRenderer.register(new StrawHatArmorRenderer(), ModItems.STRAW_HAT, ModItems.STRAW_HAT_FLOWER);

        registerItemProperties();
        registerClientEvents();
        registerBlockEntityRenderers();
        modCompatClient();
    }

    private static void registerItemProperties() {
        ConditionalItemModelProperties.ID_MAPPER.put(Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "kitchen_shovel/has_oil"), KitchenShovelCondition.MAP_CODEC);
        ConditionalItemModelProperties.ID_MAPPER.put(Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "stockpot_lid/using"), StockpotLidCondition.MAP_CODEC);
        ConditionalItemModelProperties.ID_MAPPER.put(Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "steamer/has_item"), SteamerCondition.MAP_CODEC);
        ConditionalItemModelProperties.ID_MAPPER.put(Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "recipe_item/has_recipe"), RecipeItemCondition.MAP_CODEC);
        ConditionalItemModelProperties.ID_MAPPER.put(Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "oil_pot/has_oil"), OilPotBlockCondition.MAP_CODEC);
        ConditionalItemModelProperties.ID_MAPPER.put(Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "transmutation_lunch_bag/has_food"), TransmutationLunchBagItemCondition.MAP_CODEC);
        ConditionalItemModelProperties.ID_MAPPER.put(Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "ingredient/special_render"), SpecialRenderCondition.MAP_CODEC);
    }

    private static void registerClientEvents() {
        FlatulenceEvent.register();
        PotOverlayEvent.register();
    }

    private static void registerBlockEntityRenderers() {
        BlockEntityRenderers.register(ModBlocks.POT_BE, PotBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.FRUIT_BASKET_BE, FruitBasketBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.CHOPPING_BOARD_BE, ChoppingBoardBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.STOCKPOT_BE, StockpotBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.KITCHENWARE_RACKS_BE, KitchenwareRacksBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.CHAIR_BE, ChairBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.TABLE_BE, TableBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.SHAWARMA_SPIT_BE, ShawarmaSpitBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.MILLSTONE_BE, MillstoneBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.RECIPE_BLOCK_BE, RecipeBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.STEAMER_BE, SteamerBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.FOOD_BITE_THREE_BY_THREE_BE, FoodBiteThreeByThreeBlockEntityRender::new);

        EntityModelLayerRegistry.registerModelLayer(MillstoneModel.LAYER_LOCATION, MillstoneModel::createBodyLayer);
    }

    private static void modCompatClient() {
        PonderCompat.init();
    }
}
