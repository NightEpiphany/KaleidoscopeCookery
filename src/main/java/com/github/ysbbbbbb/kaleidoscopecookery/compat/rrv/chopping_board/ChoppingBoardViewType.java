package com.github.ysbbbbbb.kaleidoscopecookery.compat.rrv.chopping_board;

import cc.cassian.rrv.api.recipe.ReliableClientRecipeType;
import cc.cassian.rrv.common.recipe.inventory.RecipeViewMenu;
import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class ChoppingBoardViewType implements ReliableClientRecipeType {
    public static final ChoppingBoardViewType INSTANCE = new ChoppingBoardViewType();

    private ChoppingBoardViewType() {
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("emi.category.kaleidoscope_cookery.chopping_board");
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
        return Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "textures/gui/eiv/chopping_board.png");
    }

    @Override
    public int getSlotCount() {
        return 3;
    }

    @Override
    public void placeSlots(RecipeViewMenu.SlotDefinition slotDefinition) {
        slotDefinition.addItemSlot(0, 16, 8);
        slotDefinition.addItemSlot(1, 48, 8);
        slotDefinition.addItemSlot(2, 75, 20);
    }

    @Override
    public Identifier getId() {
        return Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "chopping_board");
    }

    @Override
    public ItemStack getIcon() {
        return ModItems.CHOPPING_BOARD.getDefaultInstance();
    }

    @Override
    public List<ItemStack> getCraftReferences() {
        return List.of(this.getIcon());
    }
}
