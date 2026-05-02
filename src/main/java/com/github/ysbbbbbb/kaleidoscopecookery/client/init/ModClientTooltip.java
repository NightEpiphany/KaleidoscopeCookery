package com.github.ysbbbbbb.kaleidoscopecookery.client.init;

import com.github.ysbbbbbb.kaleidoscopecookery.client.tooltip.ClientItemContainerTooltip;
import com.github.ysbbbbbb.kaleidoscopecookery.client.tooltip.ClientRecipeItemTooltip;
import com.github.ysbbbbbb.kaleidoscopecookery.inventory.tooltip.ItemContainerTooltip;
import com.github.ysbbbbbb.kaleidoscopecookery.inventory.tooltip.RecipeItemTooltip;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ClientTooltipComponentCallback;

@Environment(EnvType.CLIENT)
public final class ModClientTooltip {
    public static void register() {
        ClientTooltipComponentCallback.EVENT.register(tooltipData -> {
            if (tooltipData instanceof ItemContainerTooltip itemContainerTooltip) {
                return new ClientItemContainerTooltip(itemContainerTooltip);
            }
            if (tooltipData instanceof RecipeItemTooltip recipeItemTooltip) {
                return new ClientRecipeItemTooltip(recipeItemTooltip);
            }
            return null;
        });
    }
}
