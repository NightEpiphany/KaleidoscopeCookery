package com.github.ysbbbbbb.kaleidoscopecookery.compat.rei.category.addition;

import com.github.ysbbbbbb.kaleidoscopecookery.compat.create.automation.millstone.MillstoneCompat;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.rei.category.ReiMillstoneRecipeCategory;
import com.zurrtum.create.AllRecipeTypes;
import com.zurrtum.create.content.kinetics.millstone.MillingRecipe;
import me.shedaniel.rei.api.common.registry.display.ServerDisplayRegistry;
import net.minecraft.world.item.crafting.RecipeHolder;

public class MillstoneRecipeDisplayCreateCompat extends ReiMillstoneRecipeCategory.MillstoneRecipeDisplay{
    public MillstoneRecipeDisplayCreateCompat(RecipeHolder<MillingRecipe> holder) {
        super(MillstoneCompat.transformRecipe(holder));
    }

    public static void additionalRecipeDisplay(ServerDisplayRegistry registry) {
        registry.beginRecipeFiller(MillingRecipe.class).filterType(AllRecipeTypes.MILLING).fill(MillstoneRecipeDisplayCreateCompat::new);
    }
}
