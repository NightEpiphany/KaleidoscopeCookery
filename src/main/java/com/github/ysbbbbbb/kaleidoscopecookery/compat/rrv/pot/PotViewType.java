package com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv.pot;

import cc.cassian.rrv.api.recipe.ReliableClientRecipeType;
import cc.cassian.rrv.common.recipe.inventory.RecipeViewMenu;
import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public class PotViewType implements ReliableClientRecipeType {

    public static final PotViewType INSTANCE = new PotViewType();

    private PotViewType() {
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("emi.category.kaleidoscope_cookery.pot");
    }

    @Override
    public int getDisplayWidth() {
        return 165;
    }

    @Override
    public int getDisplayHeight() {
        return 70;
    }

    @Override
    public @Nullable Identifier getGuiTexture() {
        return Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "textures/gui/eiv/pot.png");
    }

    @Override
    public int getSlotCount() {
        return 12;
    }

    @Override
    public void placeSlots(RecipeViewMenu.SlotDefinition slotDefinition) {
        for (int i = 0; i < 9; i++) {
            slotDefinition.addItemSlot(i, 4 + 18 * (i % 3), 8 + 18 * (i / 3)); // ingredients
        }
        slotDefinition.addItemSlot(9, 143, 8); // carrier
        slotDefinition.addItemSlot(10, 127, 39); // result
        slotDefinition.addItemSlot(11, 105, 3); // tool
    }

    @Override
    public Identifier getId() {
        return Identifier.withDefaultNamespace("kaleidoscope_pot");
    }

    @Override
    public ItemStack getIcon() {
        return ModItems.POT.getDefaultInstance();
    }
}
