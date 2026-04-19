package com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv.teapot;

import cc.cassian.rrv.api.recipe.ReliableClientRecipeType;
import cc.cassian.rrv.common.recipe.inventory.RecipeViewMenu;
import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public class TeapotViewType implements ReliableClientRecipeType {
    public static final TeapotViewType INSTANCE = new TeapotViewType();

    private TeapotViewType() {
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("emi.category.kaleidoscope_cookery.teapot");
    }

    @Override
    public int getDisplayWidth() {
        return 118;
    }

    @Override
    public int getDisplayHeight() {
        return 76;
    }

    @Override
    public @Nullable Identifier getGuiTexture() {
        return Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "textures/gui/eiv/teapot.png");
    }

    @Override
    public int getSlotCount() {
        return 3;
    }

    @Override
    public void placeSlots(RecipeViewMenu.SlotDefinition slotDefinition) {
        slotDefinition.addItemSlot(0, 7, 55); // fluid bucket
        slotDefinition.addItemSlot(1, 62, 6); // ingredient
        slotDefinition.addItemSlot(2, 81, 30); // result
    }

    @Override
    public Identifier getId() {
        return Identifier.withDefaultNamespace("kaleidoscope_teapot");
    }

    @Override
    public ItemStack getIcon() {
        return ModItems.TEAPOT.getDefaultInstance();
    }
}
