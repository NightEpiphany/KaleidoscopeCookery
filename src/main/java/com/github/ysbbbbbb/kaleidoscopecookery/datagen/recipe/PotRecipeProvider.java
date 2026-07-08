package com.github.ysbbbbbb.kaleidoscopecookery.datagen.recipe;

import com.github.ysbbbbbb.kaleidoscopecookery.datagen.builder.FlexPotRecipeBuilder;
import com.github.ysbbbbbb.kaleidoscopecookery.datagen.builder.PotRecipeBuilder;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopecookery.init.tag.TagCommon;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Items;
import com.github.ysbbbbbb.kaleidoscopecookery.datagen.CommonTags;
import net.minecraft.world.item.crafting.Recipe;
import org.jspecify.annotations.NonNull;


public class PotRecipeProvider extends ModRecipeProvider {
    public PotRecipeProvider(@NonNull BootstrapContext<Recipe<?>> recipes, @NonNull BootstrapContext<Advancement> advancements) {
        super(recipes, advancements);
    }

    @Override
    public void buildRecipes(RecipeOutput consumer) {
        PotRecipeBuilder.builder()
                .addInput(TagCommon.CROPS_CHILI_PEPPER, TagCommon.CROPS_CHILI_PEPPER,
                        TagCommon.DOUGH, TagCommon.DOUGH,
                        ModItems.RAW_CUT_SMALL_MEATS, ModItems.RAW_CUT_SMALL_MEATS)
                .setResult(ModItems.DONKEY_BURGER)
                .save(consumer);

        PotRecipeBuilder.builder()
                .addInput(TagCommon.EGGS, TagCommon.EGGS, TagCommon.COOKED_RICE)
                .setBowlCarrier()
                .setResult(ModItems.EGG_FRIED_RICE)
                .save(consumer);

        PotRecipeBuilder.builder()
                .addInput(TagCommon.EGGS, TagCommon.EGGS, TagCommon.EGGS,
                        TagCommon.CROPS_TOMATO, TagCommon.CROPS_TOMATO, TagCommon.CROPS_TOMATO)
                .setBowlCarrier()
                .setResult(ModItems.SCRAMBLE_EGG_WITH_TOMATOES)
                .save(consumer);

        PotRecipeBuilder.builder()
                .addInput(TagCommon.RAW_BEEF, TagCommon.RAW_BEEF, TagCommon.RAW_BEEF,
                        ModItems.RED_CHILI, ModItems.RED_CHILI, ModItems.RED_CHILI)
                .setBowlCarrier()
                .setResult(ModItems.BRAISED_BEEF)
                .save(consumer);

        PotRecipeBuilder.builder()
                .addInput(ModItems.GREEN_CHILI, ModItems.GREEN_CHILI, ModItems.GREEN_CHILI)
                .addInput(TagCommon.RAW_PORK, TagCommon.RAW_PORK, TagCommon.RAW_PORK)
                .setBowlCarrier()
                .setResult(ModItems.STIR_FRIED_PORK_WITH_PEPPERS)
                .save(consumer);

        PotRecipeBuilder.builder()
                .addInput(Items.SUGAR, Items.SUGAR, Items.SUGAR)
                .addInput(TagCommon.RAW_PORK, TagCommon.RAW_PORK, TagCommon.RAW_PORK)
                .setBowlCarrier()
                .setResult(ModItems.SWEET_AND_SOUR_PORK)
                .save(consumer);

        PotRecipeBuilder.builder()
                .addInput(CommonTags.Items.MUSHROOMS, CommonTags.Items.MUSHROOMS, TagCommon.RAW_PORK,
                        TagCommon.RAW_PORK, TagCommon.CROPS_CHILI_PEPPER, TagCommon.CROPS_CHILI_PEPPER)
                .setBowlCarrier()
                .setResult(ModItems.FISH_FLAVORED_SHREDDED_PORK)
                .save(consumer);

        PotRecipeBuilder.builder()
                .addInput(Items.LEATHER, Items.SUGAR, Items.SUGAR, Items.SUGAR, Items.SUGAR)
                .setResult(ModItems.STICKY_CANDY)
                .save(consumer);

        // 妯＄硦閰嶆柟
        FlexPotRecipeBuilder.builder()
                .addInput(TagCommon.CROPS_CHILI_PEPPER, TagCommon.DOUGH, ModItems.RAW_CUT_SMALL_MEATS)
                .setResult(ModItems.DONKEY_BURGER)
                .save(consumer);

        FlexPotRecipeBuilder.builder()
                .addInput(TagCommon.EGGS, TagCommon.COOKED_RICE)
                .setBowlCarrier()
                .setResult(ModItems.EGG_FRIED_RICE)
                .save(consumer);

        FlexPotRecipeBuilder.builder()
                .addInput(TagCommon.EGGS, TagCommon.CROPS_TOMATO)
                .setBowlCarrier()
                .setResult(ModItems.SCRAMBLE_EGG_WITH_TOMATOES)
                .save(consumer);

        FlexPotRecipeBuilder.builder()
                .addInput(TagCommon.RAW_BEEF, TagCommon.CROPS_CHILI_PEPPER)
                .setBowlCarrier()
                .setResult(ModItems.BRAISED_BEEF)
                .save(consumer);

        FlexPotRecipeBuilder.builder()
                .addInput(TagCommon.RAW_PORK, TagCommon.CROPS_CHILI_PEPPER)
                .setBowlCarrier()
                .setResult(ModItems.STIR_FRIED_PORK_WITH_PEPPERS)
                .save(consumer);

        FlexPotRecipeBuilder.builder()
                .addInput(TagCommon.RAW_PORK, Items.SUGAR)
                .setBowlCarrier()
                .setResult(ModItems.SWEET_AND_SOUR_PORK)
                .save(consumer);

        FlexPotRecipeBuilder.builder()
                .addInput(TagCommon.RAW_PORK, TagCommon.CROPS_CHILI_PEPPER, CommonTags.Items.MUSHROOMS)
                .setBowlCarrier()
                .setResult(ModItems.FISH_FLAVORED_SHREDDED_PORK)
                .save(consumer);

        FlexPotRecipeBuilder.builder()
                .addInput(Items.LEATHER, Items.SUGAR)
                .setResult(ModItems.STICKY_CANDY)
                .save(consumer);
    }
}
