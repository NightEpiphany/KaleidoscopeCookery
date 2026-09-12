package com.github.ysbbbbbb.kaleidoscopecookery.datagen.recipe;

import com.github.ysbbbbbb.kaleidoscopecookery.datagen.builder.MillstoneRecipeBuilder;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Items;
import com.github.ysbbbbbb.kaleidoscopecookery.datagen.CommonTags;


public class MillstoneRecipeProvider extends ModRecipeProvider {
    public MillstoneRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    public void buildRecipes(RecipeOutput consumer) {
        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.ALLIUM)
                .setResult(Items.DYE.magenta(), 2)
                .save(consumer, "magenta_dye_from_allium");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.AMETHYST_BLOCK)
                .setResult(Items.AMETHYST_SHARD, 3)
                .save(consumer, "amethyst_shard_from_amethyst_block");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.AZURE_BLUET)
                .setResult(Items.DYE.lightGray(), 2)
                .save(consumer, "light_gray_dye_from_azure_bluet");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.BASALT)
                .setResult(Items.POLISHED_BASALT, 1)
                .save(consumer, "polished_basalt_from_basalt");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.BEETROOT)
                .setResult(Items.DYE.red(), 2)
                .save(consumer, "red_dye_from_beetroot");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.WOOL.black())
                .setResult(Items.STRING, 3)
                .save(consumer, "string_from_black_wool");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.BLACKSTONE)
                .setResult(Items.POLISHED_BLACKSTONE, 1)
                .save(consumer, "polished_blackstone_from_blackstone");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.BLUE_ORCHID)
                .setResult(Items.DYE.lightBlue(), 2)
                .save(consumer, "light_blue_dye_from_blue_orchid");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.WOOL.blue())
                .setResult(Items.STRING, 3)
                .save(consumer, "string_from_blue_wool");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.BONE)
                .setResult(Items.BONE_MEAL, 5)
                .save(consumer, "bone_meal_from_bone");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.WOOL.brown())
                .setResult(Items.STRING, 3)
                .save(consumer, "string_from_brown_wool");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.CACTUS)
                .setResult(Items.DYE.green(), 2)
                .save(consumer, "green_dye_from_cactus");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.CHISELED_QUARTZ_BLOCK)
                .setResult(Items.QUARTZ, 3)
                .save(consumer, "quartz_from_chiseled_quartz_block");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.COAL_ORE)
                .setResult(Items.COAL, 3)
                .save(consumer, "coal_from_coal_ore");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.COBBLED_DEEPSLATE)
                .setResult(Items.POLISHED_DEEPSLATE, 1)
                .save(consumer, "polished_deepslate_from_cobbled_deepslate");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.COBBLESTONE)
                .setResult(Items.SMOOTH_STONE, 1)
                .save(consumer, "smooth_stone_from_cobblestone");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.COPPER_ORE)
                .setResult(Items.RAW_COPPER, 5)
                .save(consumer, "raw_copper_from_copper_ore");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.CORNFLOWER)
                .setResult(Items.DYE.blue(), 2)
                .save(consumer, "blue_dye_from_cornflower");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.WOOL.cyan())
                .setResult(Items.STRING, 3)
                .save(consumer, "string_from_cyan_wool");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.DANDELION)
                .setResult(Items.DYE.yellow(), 2)
                .save(consumer, "yellow_dye_from_dandelion");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.DEEPSLATE)
                .setResult(Items.POLISHED_DEEPSLATE, 1)
                .save(consumer, "polished_deepslate_from_deepslate");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.DEEPSLATE_COAL_ORE)
                .setResult(Items.COAL, 3)
                .save(consumer, "coal_from_deepslate_coal_ore");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.DEEPSLATE_COPPER_ORE)
                .setResult(Items.RAW_COPPER, 5)
                .save(consumer, "raw_copper_from_deepslate_copper_ore");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.DEEPSLATE_DIAMOND_ORE)
                .setResult(Items.DIAMOND, 2)
                .save(consumer, "diamond_from_deepslate_diamond_ore");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.DEEPSLATE_EMERALD_ORE)
                .setResult(Items.EMERALD, 2)
                .save(consumer, "emerald_from_deepslate_emerald_ore");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.DEEPSLATE_GOLD_ORE)
                .setResult(Items.RAW_GOLD, 3)
                .save(consumer, "raw_gold_from_deepslate_gold_ore");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.DEEPSLATE_IRON_ORE)
                .setResult(Items.RAW_IRON, 3)
                .save(consumer, "raw_iron_from_deepslate_iron_ore");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.DEEPSLATE_LAPIS_ORE)
                .setResult(Items.LAPIS_LAZULI, 7)
                .save(consumer, "lapis_lazuli_from_deepslate_lapis_ore");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.DEEPSLATE_REDSTONE_ORE)
                .setResult(Items.REDSTONE, 7)
                .save(consumer, "redstone_from_deepslate_redstone_ore");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.DIAMOND_ORE)
                .setResult(Items.DIAMOND, 2)
                .save(consumer, "diamond_from_diamond_ore");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.DIORITE)
                .setResult(Items.POLISHED_DIORITE, 1)
                .save(consumer, "polished_diorite_from_diorite");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.EMERALD_ORE)
                .setResult(Items.EMERALD, 2)
                .save(consumer, "emerald_from_emerald_ore");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.FLINT)
                .setResult(Items.GUNPOWDER, 1)
                .save(consumer, "gunpowder_from_flint");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.GILDED_BLACKSTONE)
                .setResult(Items.GOLD_NUGGET, 3)
                .save(consumer, "gold_nugget_from_gilded_blackstone");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.GOLD_ORE)
                .setResult(Items.RAW_GOLD, 3)
                .save(consumer, "raw_gold_from_gold_ore");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.GRANITE)
                .setResult(Items.POLISHED_GRANITE, 1)
                .save(consumer, "polished_granite_from_granite");

        MillstoneRecipeBuilder.builder()
                .setIngredient(CommonTags.Items.GRAVELS)
                .setResult(Items.SAND, 1)
                .save(consumer, "sand_from_gravel");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.WOOL.gray())
                .setResult(Items.STRING, 3)
                .save(consumer, "string_from_gray_wool");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.WOOL.green())
                .setResult(Items.STRING, 3)
                .save(consumer, "string_from_green_wool");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.IRON_ORE)
                .setResult(Items.RAW_IRON, 3)
                .save(consumer, "raw_iron_from_iron_ore");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.LAPIS_ORE)
                .setResult(Items.LAPIS_LAZULI, 7)
                .save(consumer, "lapis_lazuli_from_lapis_ore");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.LARGE_FERN)
                .setResult(Items.DYE.green(), 3)
                .save(consumer, "green_dye_from_large_fern");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.WOOL.lightBlue())
                .setResult(Items.STRING, 3)
                .save(consumer, "string_from_light_blue_wool");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.WOOL.lightGray())
                .setResult(Items.STRING, 3)
                .save(consumer, "string_from_light_gray_wool");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.LILAC)
                .setResult(Items.DYE.magenta(), 3)
                .save(consumer, "magenta_dye_from_lilac");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.LILY_OF_THE_VALLEY)
                .setResult(Items.DYE.white(), 2)
                .save(consumer, "white_dye_from_lily_of_the_valley");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.WOOL.lime())
                .setResult(Items.STRING, 3)
                .save(consumer, "string_from_lime_wool");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.WOOL.magenta())
                .setResult(Items.STRING, 3)
                .save(consumer, "string_from_magenta_wool");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.NETHER_GOLD_ORE)
                .setResult(Items.GOLD_NUGGET, 5)
                .save(consumer, "gold_nugget_from_nether_gold_ore");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.NETHER_QUARTZ_ORE)
                .setResult(Items.QUARTZ, 3)
                .save(consumer, "quartz_from_nether_quartz_ore");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.ORANGE_TULIP)
                .setResult(Items.DYE.orange(), 2)
                .save(consumer, "orange_dye_from_orange_tulip");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.WOOL.orange())
                .setResult(Items.STRING, 3)
                .save(consumer, "string_from_orange_wool");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.OXEYE_DAISY)
                .setResult(Items.DYE.lightGray(), 2)
                .save(consumer, "light_gray_dye_from_oxeye_daisy");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.PEONY)
                .setResult(Items.DYE.pink(), 3)
                .save(consumer, "pink_dye_from_peony");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.PINK_PETALS)
                .setResult(Items.DYE.pink(), 2)
                .save(consumer, "pink_dye_from_pink_petals");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.PINK_TULIP)
                .setResult(Items.DYE.pink(), 2)
                .save(consumer, "pink_dye_from_pink_tulip");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.WOOL.pink())
                .setResult(Items.STRING, 3)
                .save(consumer, "string_from_pink_wool");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.PITCHER_PLANT)
                .setResult(Items.DYE.cyan(), 2)
                .save(consumer, "cyan_dye_from_pitcher_plant");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.POPPY)
                .setResult(Items.DYE.red(), 2)
                .save(consumer, "red_dye_from_poppy");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.WOOL.purple())
                .setResult(Items.STRING, 3)
                .save(consumer, "string_from_purple_wool");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.QUARTZ_BLOCK)
                .setResult(Items.QUARTZ, 3)
                .save(consumer, "quartz_from_quartz_block");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.QUARTZ_BRICKS)
                .setResult(Items.QUARTZ, 3)
                .save(consumer, "quartz_from_quartz_bricks");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.QUARTZ_PILLAR)
                .setResult(Items.QUARTZ, 3)
                .save(consumer, "quartz_from_quartz_pillar");

        MillstoneRecipeBuilder.builder()
                .setIngredient(CommonTags.Items.SANDS_RED)
                .setResult(Items.SMOOTH_RED_SANDSTONE, 1)
                .save(consumer, "smooth_red_sandstone_from_red_sand");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.RED_SANDSTONE)
                .setResult(Items.SMOOTH_RED_SANDSTONE, 1)
                .save(consumer, "smooth_red_sandstone_from_red_sandstone");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.RED_TULIP)
                .setResult(Items.DYE.red(), 2)
                .save(consumer, "red_dye_from_red_tulip");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.WOOL.red())
                .setResult(Items.STRING, 3)
                .save(consumer, "string_from_red_wool");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.REDSTONE_ORE)
                .setResult(Items.REDSTONE, 7)
                .save(consumer, "redstone_from_redstone_ore");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.ROSE_BUSH)
                .setResult(Items.DYE.red(), 3)
                .save(consumer, "red_dye_from_rose_bush");

        MillstoneRecipeBuilder.builder()
                .setIngredient(CommonTags.Items.SANDS_COLORLESS)
                .setResult(Items.SMOOTH_SANDSTONE, 1)
                .save(consumer, "smooth_sandstone_from_sand");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.SANDSTONE)
                .setResult(Items.SMOOTH_SANDSTONE, 1)
                .save(consumer, "smooth_sandstone_from_sandstone");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.FERN)
                .setResult(Items.DYE.green(), 1)
                .save(consumer, "green_dye_from_fern");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.SMOOTH_BASALT)
                .setResult(Items.POLISHED_BASALT, 1)
                .save(consumer, "polished_basalt_from_smooth_basalt");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.ANDESITE)
                .setResult(Items.POLISHED_ANDESITE, 1)
                .save(consumer, "polished_andesite_from_andesite");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.SMOOTH_QUARTZ)
                .setResult(Items.QUARTZ, 3)
                .save(consumer, "quartz_from_smooth_quartz");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.STONE)
                .setResult(Items.COBBLESTONE, 1)
                .save(consumer, "cobblestone_from_stone");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.SUNFLOWER)
                .setResult(Items.DYE.yellow(), 3)
                .save(consumer, "yellow_dye_from_sunflower");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.TORCHFLOWER)
                .setResult(Items.DYE.orange(), 2)
                .save(consumer, "orange_dye_from_torchflower");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.WHITE_TULIP)
                .setResult(Items.DYE.white(), 2)
                .save(consumer, "white_dye_from_white_tulip");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.WOOL.white())
                .setResult(Items.STRING, 3)
                .save(consumer, "string_from_white_wool");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.WITHER_ROSE)
                .setResult(Items.DYE.black(), 2)
                .save(consumer, "black_dye_from_wither_rose");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.WOOL.yellow())
                .setResult(Items.STRING, 3)
                .save(consumer, "string_from_yellow_wool");

        MillstoneRecipeBuilder.builder()
                .setIngredient(Items.WHEAT)
                .setResult(ModItems.FLOUR)
                .save(consumer, "flour_from_wheat");

        MillstoneRecipeBuilder.builder()
                .setIngredient(CommonTags.Items.SEEDS)
                .setResult(ModItems.OIL)
                .save(consumer, "oil_from_seeds");

        MillstoneRecipeBuilder.builder()
                .setIngredient(ModItems.RICE_PANICLE)
                .setResult(ModItems.RICE_SEED, 3)
                .save(consumer, "rice_from_rice_panicle");
    }
}