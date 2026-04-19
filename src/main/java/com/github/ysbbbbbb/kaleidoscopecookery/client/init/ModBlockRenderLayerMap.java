package com.github.ysbbbbbb.kaleidoscopecookery.client.init;

import com.github.ysbbbbbb.kaleidoscopecookery.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopecookery.init.registry.FoodBiteRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;

@Environment(EnvType.CLIENT)
public class ModBlockRenderLayerMap {
    public static void register() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(),
                ModBlocks.POT,
                ModBlocks.KITCHENWARE_RACKS,
                ModBlocks.SHAWARMA_SPIT,
                ModBlocks.CHILI_CROP,
                ModBlocks.TOMATO_CROP,
                ModBlocks.LETTUCE_CROP,
                ModBlocks.RICE_CROP,
                ModBlocks.CHILI_RISTRA,
                ModBlocks.STEAMER,
                ModBlocks.STOCKPOT,
                ModBlocks.STRUNG_MUSHROOMS,
                ModBlocks.BARLEY_TEA,
                ModBlocks.FLOWER_TEA,
                ModBlocks.TIEGUANYIN,
                ModBlocks.BILUOCHUN,
                ModBlocks.OOLONG,
                ModBlocks.SAKURA_FUBUKI,
                ModBlocks.EMPTY_CUP,
                ModBlocks.TEAPOT
        );

        FoodBiteRegistry.FOOD_DATA_MAP.keySet().forEach(id -> {
            Block block = FoodBiteRegistry.getBlock(id);
            BlockRenderLayerMap.INSTANCE.putBlock(block, RenderType.cutout());
        });
    }
}
