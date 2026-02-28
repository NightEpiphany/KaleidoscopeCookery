package com.github.ysbbbbbb.kaleidoscopecookery.compat.jade.block;

import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.decoration.RecipeBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.jade.ModJadePlugin;
import com.github.ysbbbbbb.kaleidoscopecookery.item.RecipeItem;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jspecify.annotations.NonNull;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.JadeUI;

public enum RecipeBlockComponentProvider implements IBlockComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(@NonNull ITooltip tooltip, BlockAccessor accessor, @NonNull IPluginConfig pluginConfig) {
        BlockEntity blockEntity = accessor.getBlockEntity();
        if (!(blockEntity instanceof RecipeBlockEntity recipeBlock)) {
            return;
        }
        ItemStack stackInSlot = recipeBlock.getItems().getStackInSlot(0);
        if (stackInSlot.isEmpty()) {
            return;
        }
        RecipeItem.RecipeRecord recipe = RecipeItem.getRecipe(stackInSlot);
        if (recipe == null) {
            return;
        }
        ItemStack output = recipe.output();
        tooltip.add(output.getHoverName());

        boolean isFirst = true;
        for (ItemStack stack : recipe.input()) {
            if (isFirst) {
                tooltip.add(JadeUI.item(stack));
            } else {
                tooltip.append(JadeUI.item(stack));
            }
            isFirst = false;
        }
        tooltip.append(JadeUI.item(output));
    }

    @Override
    public @NonNull Identifier getUid() {
        return ModJadePlugin.RECIPE_BLOCK;
    }
}
