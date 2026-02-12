package com.github.ysbbbbbb.kaleidoscopecookery.init.registry;

import com.github.ysbbbbbb.kaleidoscopecookery.block.dispenser.OilPotDispenseBehavior;
import com.github.ysbbbbbb.kaleidoscopecookery.block.food.FoodBiteBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.block.food.FoodBiteOneByTwoBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.farmersdelight.FarmersDelightCompat;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.trinkets.init.ModTrinketsCompat;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.trinkets.init.TrinketsCompatServer;
import com.github.ysbbbbbb.kaleidoscopecookery.event.*;
import com.github.ysbbbbbb.kaleidoscopecookery.event.effect.FlatulenceServerEvent;
import com.github.ysbbbbbb.kaleidoscopecookery.event.effect.PreservationEvent;
import com.github.ysbbbbbb.kaleidoscopecookery.event.effect.SatiatedShieldEvent;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopecookery.item.BowlFoodBlockItem;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class CommonRegistry {
    public static void init() {
        modCompat();
        addComposter();
        registerFoodBiteBlocks();
        registerServerEvents();
        addDispenserBehavior();
    }

    public static void registerServerEvents() {
        SatiatedShieldEvent.register();
        FlatulenceServerEvent.register();
        PreservationEvent.register();
        ArmorEffectHandler.register();
        AddVillageStructuresEvent.register();
        ScarecrowFarmlandTrampleEvent.register();
        EntityJoinWorldEvent.register();
        SickleHarvestNetherWartEvent.register();
        HoeUseEvent.register();
        RightClickEvent.register();
        LeftClickEvent.register();
        ExtraLootTableDrop.register();
    }

    private static void registerFoodBiteBlocks() {
        FoodBiteRegistry.init();

        FoodBiteRegistry.FOOD_DATA_MAP.forEach((resourceLocation, data) -> {
                FoodBiteBlock biteBlock = getFoodBiteBlock(data);
                Registry.register(BuiltInRegistries.BLOCK, resourceLocation, biteBlock);

                Block block = BuiltInRegistries.BLOCK.get(resourceLocation);
                // 选取第一个掉落物作为 usingConvertsTo
                ItemLike first = data.getLootItems().getFirst();
                Registry.register(BuiltInRegistries.ITEM, resourceLocation, new BowlFoodBlockItem(block, data.itemFood(), first));
        });
    }

    private static @NotNull FoodBiteBlock getFoodBiteBlock(FoodBiteRegistry.FoodData data) {
        FoodBiteBlock biteBlock;
        if (data.blockType() == FoodBiteRegistry.BlockType.ONE_BY_TWO) {
            biteBlock = new FoodBiteOneByTwoBlock(data.blockFood(), data.maxBites(), data.animateTick());
        } else {
            biteBlock = new FoodBiteBlock(data.blockFood(), data.maxBites(), data.animateTick());
        }

        VoxelShape aabb = data.getAABB();
        if (aabb != null) {
            biteBlock.setAABB(aabb);
        }
        return biteBlock;
    }

    private static void addComposter() {
        CompostingChanceRegistry.INSTANCE.add(ModItems.TOMATO_SEED, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(ModItems.CHILI_SEED, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(ModItems.LETTUCE_SEED, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(ModItems.WILD_RICE_SEED, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(ModItems.RICE_SEED, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(ModItems.TOMATO, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(ModItems.RED_CHILI, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(ModItems.GREEN_CHILI, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(ModItems.LETTUCE, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(ModItems.RICE_PANICLE, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(ModItems.CATERPILLAR, 1.0F);
    }

    private static void modCompat() {
        TrinketsCompatServer.init();
        FarmersDelightCompat.init();
    }

    private static void addDispenserBehavior() {
        DispenserBlock.registerBehavior(ModItems.OIL_POT, new OilPotDispenseBehavior());
    }
}
