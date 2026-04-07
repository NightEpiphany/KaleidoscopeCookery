package com.github.ysbbbbbb.kaleidoscopecookery.init.registry;

import com.github.ysbbbbbb.kaleidoscopecookery.block.dispenser.OilPotDispenseBehavior;
import com.github.ysbbbbbb.kaleidoscopecookery.block.food.FoodBiteBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.block.food.FoodBiteOneByTwoBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.create.automation.init.AutomationCompat;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.farmersdelight.FarmersDelightCompat;
import com.github.ysbbbbbb.kaleidoscopecookery.event.*;
import com.github.ysbbbbbb.kaleidoscopecookery.event.effect.*;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopecookery.item.BowlFoodBlockItem;
import com.github.ysbbbbbb.kaleidoscopecookery.util.PortHelper;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class CommonRegistry {

    public static void init() {
        addComposter();
        registerFoodBiteBlocks();
        registerServerEvents();
        addDispenserBehavior();
        modCompat();
        fuelRegister();
    }

    public static void fuelRegister() {
        FuelRegistryEvents.BUILD.register((registry, i) -> {
            registry.add(ModItems.OIL, i.baseSmeltTime() * 8);
        });
    }

    public static void registerServerEvents() {
        PlaceIngredientEvent.register();
        SatiatedShieldEvent.register();
        FlatulenceServerEvent.register();
        PreservationEvent.register();
        HinderEvent.register();
        ProjectileDodgeEvent.register();
        VitalityEvent.register();
        ArmorEffectHandler.register();
        AddVillageStructuresEvent.register();
        ScarecrowFarmlandTrampleEvent.register();
        EntityJoinWorldEvent.register();
        SickleHarvestNetherWartEvent.register();
        HoeUseEvent.register();
        RightClickEvent.register();
        LeftClickEvent.register();
        ExtraLootTableDrop.register();
        PlayerSitEvent.register();
    }

    private static void registerFoodBiteBlocks() {
        FoodBiteRegistry.init();

        FoodBiteRegistry.FOOD_DATA_MAP.forEach((resourceLocation, data) -> {
                FoodBiteBlock biteBlock = getFoodBiteBlock(data, resourceLocation.getPath());
                Registry.register(BuiltInRegistries.BLOCK, resourceLocation, biteBlock);

                Block block = BuiltInRegistries.BLOCK.getValue(resourceLocation);
                // 选取第一个掉落物作为 usingConvertsTo
                ItemLike first = data.getLootItems().getFirst();
            BowlFoodBlockItem register = Registry.register(BuiltInRegistries.ITEM, resourceLocation, new BowlFoodBlockItem(block, data.itemFood(), data.itemConsumable(), first, resourceLocation.getPath()));
            FoodBiteRegistry.FOOD_ITEM_MAP.put(resourceLocation, register);
        });
    }

    private static @NotNull FoodBiteBlock getFoodBiteBlock(FoodBiteRegistry.FoodData data, String name) {
        FoodBiteBlock biteBlock;
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of()
                .forceSolidOn()
                .instabreak()
                .mapColor(MapColor.WOOD)
                .sound(SoundType.WOOD)
                .pushReaction(PushReaction.DESTROY)
                .noOcclusion();

        if (data.blockType() == FoodBiteRegistry.BlockType.ONE_BY_TWO) {
            biteBlock = new FoodBiteOneByTwoBlock(properties.setId(PortHelper.createBlockId(name)), data.blockFood(), data.blockConsumable(), data.maxBites(), data.animateTick());
        } else {
            biteBlock = new FoodBiteBlock(properties.setId(PortHelper.createBlockId(name)), data.blockFood(), data.blockConsumable(), data.maxBites(), data.animateTick());
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
        FarmersDelightCompat.init();
        AutomationCompat.init();
    }

    private static void addDispenserBehavior() {
        DispenserBlock.registerBehavior(ModItems.OIL_POT, new OilPotDispenseBehavior());
    }
}
