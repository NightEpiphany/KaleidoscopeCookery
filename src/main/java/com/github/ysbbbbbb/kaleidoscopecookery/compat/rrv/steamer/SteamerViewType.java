package com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv.steamer;

import cc.cassian.rrv.api.recipe.ReliableClientRecipeType;
import cc.cassian.rrv.common.recipe.inventory.RecipeViewMenu;
import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public class SteamerViewType implements ReliableClientRecipeType {

    public static final SteamerViewType INSTANCE = new SteamerViewType();

    private SteamerViewType() {
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("emi.category.kaleidoscope_cookery.steamer");
    }

    @Override
    public int getDisplayWidth() {
        return 118;
    }

    @Override
    public int getDisplayHeight() {
        return 58;
    }

    @Override
    public @Nullable Identifier getGuiTexture() {
        return Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "textures/gui/eiv/steamer.png");
    }

    @Override
    public int getSlotCount() {
        return 2;
    }

    @Override
    public void placeSlots(RecipeViewMenu.SlotDefinition slotDefinition) {
        slotDefinition.addItemSlot(0, 21, 12);
        slotDefinition.addItemSlot(1, 86, 22);
    }

    @Override
    public Identifier getId() {
        return Identifier.withDefaultNamespace("kaleidoscope_steamer");
    }

    @Override
    public ItemStack getIcon() {
        return ModItems.STEAMER.getDefaultInstance();
    }
}
