package com.github.ysbbbbbb.kaleidoscopecookery.init;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.block.crop.BaseCropBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.block.crop.ChiliCropBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.block.crop.LettuceCropBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.block.crop.RiceCropBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.block.decoration.ChairBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.block.decoration.CookStoolBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.block.decoration.FruitBasketBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.block.decoration.TableBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.block.drink.EmptyCupBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.block.drink.TeacupBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.block.food.FoodBiteThreeByThreeBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.block.kitchen.*;
import com.github.ysbbbbbb.kaleidoscopecookery.block.misc.*;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.decoration.*;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.food.FoodBiteThreeByThreeBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen.*;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.misc.TrashCanBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.init.registry.TeacupRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;

public final class ModBlocks {
    // Kitchen blocks
    public static final Block STOVE = new StoveBlock();
    public static final Block POT = new PotBlock();
    public static final Block STOCKPOT = new StockpotBlock();
    public static final Block FRUIT_BASKET = new FruitBasketBlock();
    public static final Block CHOPPING_BOARD = new ChoppingBoardBlock();
    public static final Block OIL_BLOCK = new OilBlock();
    public static final Block ENAMEL_BASIN = new EnamelBasinBlock();
    public static final Block KITCHENWARE_RACKS = new KitchenwareRacksBlock();
    public static final Block CHILI_RISTRA = new ChiliRistraBlock();
    public static final Block STRUNG_MUSHROOMS  = new StrungMushroomsBlock();
    public static final Block STRAW_BLOCK = new StrawBlocks();
    public static final Block SHAWARMA_SPIT = new ShawarmaSpitBlock();
    public static final Block MILLSTONE = new MillstoneBlock();
    public static final Block STEAMER = new SteamerBlock();
    public static final Block RECIPE_BLOCK = new RecipeBlock();
    public static final Block OIL_POT = new OilPotBlock();

    // Tea
    public static final Block TEAPOT = new TeapotBlock();
    public static final Block EMPTY_CUP = new EmptyCupBlock();
    public static final Block BARLEY_TEA = new TeacupBlock(TeacupRegistry.TEACUP_DATA_MAP.get(TeacupRegistry.BARLEY_TEA).getMaxCount());
    public static final Block TIEGUANYIN = new TeacupBlock(TeacupRegistry.TEACUP_DATA_MAP.get(TeacupRegistry.TIEGUANYIN).getMaxCount());
    public static final Block BILUOCHUN = new TeacupBlock(TeacupRegistry.TEACUP_DATA_MAP.get(TeacupRegistry.BILUOCHUN).getMaxCount());
    public static final Block OOLONG = new TeacupBlock(TeacupRegistry.TEACUP_DATA_MAP.get(TeacupRegistry.OOLONG).getMaxCount());
    public static final Block SAKURA_FUBUKI = new TeacupBlock(TeacupRegistry.TEACUP_DATA_MAP.get(TeacupRegistry.SAKURA_FUBUKI).getMaxCount());
    public static final Block FLOWER_TEA = new TeacupBlock(TeacupRegistry.TEACUP_DATA_MAP.get(TeacupRegistry.FLOWER_TEA).getMaxCount());



    public static final Block TRASH_CAN = new TrashCanBlock();

    // Crop blocks
    public static final Block TOMATO_CROP = new BaseCropBlock(() -> ModItems.TOMATO, () -> ModItems.TOMATO_SEED);
    public static final Block CHILI_CROP = new ChiliCropBlock();
    public static final Block LETTUCE_CROP = new LettuceCropBlock();
    public static final Block RICE_CROP = new RiceCropBlock();

    // Cook stools
    public static final Block COOK_STOOL_OAK = new CookStoolBlock(SoundType.WOOD);
    public static final Block COOK_STOOL_SPRUCE = new CookStoolBlock(SoundType.WOOD);
    public static final Block COOK_STOOL_ACACIA = new CookStoolBlock(SoundType.WOOD);
    public static final Block COOK_STOOL_BAMBOO = new CookStoolBlock(SoundType.BAMBOO);
    public static final Block COOK_STOOL_BIRCH = new CookStoolBlock(SoundType.WOOD);
    public static final Block COOK_STOOL_CHERRY = new CookStoolBlock(SoundType.CHERRY_WOOD);
    public static final Block COOK_STOOL_CRIMSON = new CookStoolBlock(SoundType.NETHER_WOOD);
    public static final Block COOK_STOOL_DARK_OAK = new CookStoolBlock(SoundType.WOOD);
    public static final Block COOK_STOOL_JUNGLE = new CookStoolBlock(SoundType.WOOD);
    public static final Block COOK_STOOL_MANGROVE = new CookStoolBlock(SoundType.WOOD);
    public static final Block COOK_STOOL_WARPED = new CookStoolBlock(SoundType.NETHER_WOOD);

    // Chairs
    public static final Block CHAIR_OAK = new ChairBlock(SoundType.WOOD);
    public static final Block CHAIR_SPRUCE = new ChairBlock(SoundType.WOOD);
    public static final Block CHAIR_ACACIA = new ChairBlock(SoundType.WOOD);
    public static final Block CHAIR_BAMBOO = new ChairBlock(SoundType.BAMBOO);
    public static final Block CHAIR_BIRCH = new ChairBlock(SoundType.WOOD);
    public static final Block CHAIR_CHERRY = new ChairBlock(SoundType.CHERRY_WOOD);
    public static final Block CHAIR_CRIMSON = new ChairBlock(SoundType.NETHER_WOOD);
    public static final Block CHAIR_DARK_OAK = new ChairBlock(SoundType.WOOD);
    public static final Block CHAIR_JUNGLE = new ChairBlock(SoundType.WOOD);
    public static final Block CHAIR_MANGROVE = new ChairBlock(SoundType.WOOD);
    public static final Block CHAIR_WARPED = new ChairBlock(SoundType.NETHER_WOOD);

    // Tables
    public static final Block TABLE_OAK = new TableBlock(SoundType.WOOD);
    public static final Block TABLE_SPRUCE = new TableBlock(SoundType.WOOD);
    public static final Block TABLE_ACACIA = new TableBlock(SoundType.WOOD);
    public static final Block TABLE_BAMBOO = new TableBlock(SoundType.BAMBOO);
    public static final Block TABLE_BIRCH = new TableBlock(SoundType.WOOD);
    public static final Block TABLE_CHERRY = new TableBlock(SoundType.CHERRY_WOOD);
    public static final Block TABLE_CRIMSON = new TableBlock(SoundType.NETHER_WOOD);
    public static final Block TABLE_DARK_OAK = new TableBlock(SoundType.WOOD);
    public static final Block TABLE_JUNGLE = new TableBlock(SoundType.WOOD);
    public static final Block TABLE_MANGROVE = new TableBlock(SoundType.WOOD);
    public static final Block TABLE_WARPED = new TableBlock(SoundType.NETHER_WOOD);

    //Feast
    public static final Block COLD_CUT_HAM_SLICES = new FoodBiteThreeByThreeBlock(ModFoods.COLD_CUT_HAM_SLICES_BLOCK, 8, null);

    // Block entities
    public static final BlockEntityType<PotBlockEntity> POT_BE = BlockEntityType.Builder.of(PotBlockEntity::new, POT).build(null);
    public static final BlockEntityType<StockpotBlockEntity> STOCKPOT_BE = BlockEntityType.Builder.of(StockpotBlockEntity::new, STOCKPOT).build(null);
    public static final BlockEntityType<FruitBasketBlockEntity> FRUIT_BASKET_BE = BlockEntityType.Builder.of(FruitBasketBlockEntity::new, FRUIT_BASKET).build(null);
    public static final BlockEntityType<ChoppingBoardBlockEntity> CHOPPING_BOARD_BE = BlockEntityType.Builder.of(ChoppingBoardBlockEntity::new, CHOPPING_BOARD).build(null);
    public static final BlockEntityType<KitchenwareRacksBlockEntity> KITCHENWARE_RACKS_BE = BlockEntityType.Builder.of(KitchenwareRacksBlockEntity::new, KITCHENWARE_RACKS).build(null);
    public static final BlockEntityType<ShawarmaSpitBlockEntity> SHAWARMA_SPIT_BE = BlockEntityType.Builder.of(ShawarmaSpitBlockEntity::new, SHAWARMA_SPIT).build(null);
    public static final BlockEntityType<SteamerBlockEntity> STEAMER_BE = BlockEntityType.Builder.of(SteamerBlockEntity::new, STEAMER).build(null);
    public static final BlockEntityType<MillstoneBlockEntity> MILLSTONE_BE = BlockEntityType.Builder.of(MillstoneBlockEntity::new, MILLSTONE).build(null);
    public static final BlockEntityType<RecipeBlockEntity> RECIPE_BLOCK_BE = BlockEntityType.Builder.of(RecipeBlockEntity::new, RECIPE_BLOCK).build(null);
    public static final BlockEntityType<OilPotBlockEntity> OIL_POT_BE = BlockEntityType.Builder.of(OilPotBlockEntity::new, OIL_POT).build(null);
    public static final BlockEntityType<TeapotBlockEntity> TEAPOT_BE = BlockEntityType.Builder.of(TeapotBlockEntity::new, TEAPOT).build(null);
    public static final BlockEntityType<TrashCanBlockEntity> TRASH_CAN_BE = BlockEntityType.Builder.of(TrashCanBlockEntity::new, TRASH_CAN).build(null);
    public static final BlockEntityType<FoodBiteThreeByThreeBlockEntity> FOOD_BITE_THREE_BY_THREE_BE = BlockEntityType.Builder.of(FoodBiteThreeByThreeBlockEntity::new, COLD_CUT_HAM_SLICES).build(null);

    public static final BlockEntityType<ChairBlockEntity> CHAIR_BE = BlockEntityType.Builder.of(ChairBlockEntity::new,
            CHAIR_OAK, CHAIR_SPRUCE, CHAIR_ACACIA, CHAIR_BAMBOO,
            CHAIR_BIRCH, CHAIR_CHERRY, CHAIR_CRIMSON, CHAIR_DARK_OAK,
            CHAIR_JUNGLE, CHAIR_MANGROVE, CHAIR_WARPED
    ).build(null);

    public static final BlockEntityType<TableBlockEntity> TABLE_BE = BlockEntityType.Builder.of(TableBlockEntity::new,
            TABLE_OAK, TABLE_SPRUCE, TABLE_ACACIA, TABLE_BAMBOO,
            TABLE_BIRCH, TABLE_CHERRY, TABLE_CRIMSON, TABLE_DARK_OAK,
            TABLE_JUNGLE, TABLE_MANGROVE, TABLE_WARPED
    ).build(null);

    public static void registerBlocks() {
        // Kitchen blocks
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "stove"), STOVE);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "pot"), POT);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "stockpot"), STOCKPOT);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "fruit_basket"), FRUIT_BASKET);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "chopping_board"), CHOPPING_BOARD);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "oil_block"), OIL_BLOCK);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "enamel_basin"), ENAMEL_BASIN);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "kitchenware_racks"), KITCHENWARE_RACKS);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "chili_ristra"), CHILI_RISTRA);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "strung_mushrooms"), STRUNG_MUSHROOMS);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "straw_block"), STRAW_BLOCK);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "shawarma_spit"), SHAWARMA_SPIT);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "steamer"), STEAMER);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "millstone"), MILLSTONE);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "recipe_block"), RECIPE_BLOCK);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "oil_pot"), OIL_POT);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "teapot"), TEAPOT);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "empty_cup"), EMPTY_CUP);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "barley_tea"), BARLEY_TEA);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "tieguanyin"), TIEGUANYIN);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "biluochun"), BILUOCHUN);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "oolong"), OOLONG);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "sakura_fubuki"), SAKURA_FUBUKI);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "flower_tea"), FLOWER_TEA);



        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "trash_can"), TRASH_CAN);

        // Crop blocks
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "tomato_crop"), TOMATO_CROP);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "chili_crop"), CHILI_CROP);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "lettuce_crop"), LETTUCE_CROP);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "rice_crop"), RICE_CROP);

        // Cook stools
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "cook_stool_oak"), COOK_STOOL_OAK);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "cook_stool_spruce"), COOK_STOOL_SPRUCE);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "cook_stool_acacia"), COOK_STOOL_ACACIA);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "cook_stool_bamboo"), COOK_STOOL_BAMBOO);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "cook_stool_birch"), COOK_STOOL_BIRCH);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "cook_stool_cherry"), COOK_STOOL_CHERRY);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "cook_stool_crimson"), COOK_STOOL_CRIMSON);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "cook_stool_dark_oak"), COOK_STOOL_DARK_OAK);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "cook_stool_jungle"), COOK_STOOL_JUNGLE);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "cook_stool_mangrove"), COOK_STOOL_MANGROVE);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "cook_stool_warped"), COOK_STOOL_WARPED);

        // Chairs
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "chair_oak"), CHAIR_OAK);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "chair_spruce"), CHAIR_SPRUCE);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "chair_acacia"), CHAIR_ACACIA);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "chair_bamboo"), CHAIR_BAMBOO);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "chair_birch"), CHAIR_BIRCH);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "chair_cherry"), CHAIR_CHERRY);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "chair_crimson"), CHAIR_CRIMSON);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "chair_dark_oak"), CHAIR_DARK_OAK);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "chair_jungle"), CHAIR_JUNGLE);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "chair_mangrove"), CHAIR_MANGROVE);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "chair_warped"), CHAIR_WARPED);

        // Tables
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "table_oak"), TABLE_OAK);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "table_spruce"), TABLE_SPRUCE);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "table_acacia"), TABLE_ACACIA);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "table_bamboo"), TABLE_BAMBOO);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "table_birch"), TABLE_BIRCH);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "table_cherry"), TABLE_CHERRY);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "table_crimson"), TABLE_CRIMSON);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "table_dark_oak"), TABLE_DARK_OAK);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "table_jungle"), TABLE_JUNGLE);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "table_mangrove"), TABLE_MANGROVE);
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "table_warped"), TABLE_WARPED);

        // Feast
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "cold_cut_ham_slices"), COLD_CUT_HAM_SLICES);

        // Block entities
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "pot"), POT_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "stockpot"), STOCKPOT_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "fruit_basket"), FRUIT_BASKET_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "chopping_board"), CHOPPING_BOARD_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "kitchenware_racks"), KITCHENWARE_RACKS_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "shawarma_spit"), SHAWARMA_SPIT_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "chair"), CHAIR_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "table"), TABLE_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "steamer"), STEAMER_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "millstone"), MILLSTONE_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "recipe_book"), RECIPE_BLOCK_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "oil_pot"), OIL_POT_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "teapot"), TEAPOT_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "trash_can"), TRASH_CAN_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "food_bite_three_by_three"), FOOD_BITE_THREE_BY_THREE_BE);
    }
}
