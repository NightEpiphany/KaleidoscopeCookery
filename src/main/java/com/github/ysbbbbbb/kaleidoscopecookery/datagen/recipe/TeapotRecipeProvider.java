package com.github.ysbbbbbb.kaleidoscopecookery.datagen.recipe;

import com.github.ysbbbbbb.kaleidoscopecookery.datagen.builder.TeapotBuilder;
import com.github.ysbbbbbb.kaleidoscopecookery.init.registry.TeacupRegistry;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.BlockItemTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.material.Fluids;
import org.jspecify.annotations.NonNull;


public class TeapotRecipeProvider extends ModRecipeProvider {
    public TeapotRecipeProvider(@NonNull BootstrapContext<Recipe<?>> recipes, @NonNull BootstrapContext<Advancement> advancements) {
        super(recipes, advancements);
    }

    @Override
    public void buildRecipes(RecipeOutput consumer) {
        TeapotBuilder.builder()
                .setTeaFluid(Fluids.WATER)
                .setIngredient(BlockItemTags.SMALL_FLOWERS.item())
                .setResult(TeacupRegistry.getItem(TeacupRegistry.FLOWER_TEA))
                .setTime(240)
                .save(consumer);

        TeapotBuilder.builder()
                .setTeaFluid(Fluids.WATER)
                .setIngredient(Items.WHEAT_SEEDS)
                .setResult(TeacupRegistry.getItem(TeacupRegistry.BARLEY_TEA))
                .setTime(240)
                .save(consumer);

        TeapotBuilder.builder()
                .setTeaFluid(Fluids.WATER)
                .setIngredient(Items.IRON_NUGGET)
                .setResult(TeacupRegistry.getItem(TeacupRegistry.TIEGUANYIN))
                .setTime(240)
                .save(consumer);

        TeapotBuilder.builder()
                .setTeaFluid(Fluids.WATER)
                .setIngredient(Items.NAUTILUS_SHELL)
                .setResult(TeacupRegistry.getItem(TeacupRegistry.BILUOCHUN))
                .setTime(240)
                .save(consumer);

        TeapotBuilder.builder()
                .setTeaFluid(Fluids.WATER)
                .setIngredient(Items.DRAGON_BREATH)
                .setResult(TeacupRegistry.getItem(TeacupRegistry.OOLONG))
                .setTime(240)
                .save(consumer);

        TeapotBuilder.builder()
                .setTeaFluid(Fluids.WATER)
                .setIngredient(Items.PINK_PETALS)
                .setResult(TeacupRegistry.getItem(TeacupRegistry.SAKURA_FUBUKI))
                .setTime(240)
                .save(consumer);
    }
}
