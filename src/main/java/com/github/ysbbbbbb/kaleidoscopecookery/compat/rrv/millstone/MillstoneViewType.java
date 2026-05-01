package com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv.millstone;

import cc.cassian.rrv.api.recipe.ReliableClientRecipeType;
import cc.cassian.rrv.common.recipe.inventory.RecipeViewMenu;
import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class MillstoneViewType implements ReliableClientRecipeType {
    public static final MillstoneViewType INSTANCE = new MillstoneViewType();

    private MillstoneViewType() {
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("emi.category.kaleidoscope_cookery.millstone");
    }

    @Override
    public int getDisplayWidth() {
        return 118;
    }

    @Override
    public int getDisplayHeight() {
        return 80;
    }

    @Override
    public @Nullable Identifier getGuiTexture() {
        return Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "textures/gui/eiv/millstone.png");
    }

    @Override
    public int getSlotCount() {
        return 2;
    }

    @Override
    public void placeSlots(RecipeViewMenu.SlotDefinition slotDefinition) {
        slotDefinition.addItemSlot(0, 16, 21);
        slotDefinition.addItemSlot(1, 91, 30);
    }

    @Override
    public Identifier getId() {
        return Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "millstone");
    }

    @Override
    public ItemStack getIcon() {
        return ModItems.MILLSTONE.getDefaultInstance();
    }

    @Override
    public List<ItemStack> getCraftReferences() {
        return List.of(this.getIcon());
    }
}
