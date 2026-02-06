package com.github.ysbbbbbb.kaleidoscopecookery.compact.jei.category;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.PotRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModRecipes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeHolderType;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public class PotRecipeCategory implements IRecipeCategory<RecipeHolder<PotRecipe>> {
    public static final IRecipeHolderType<PotRecipe> TYPE = IRecipeType.create(ModRecipes.POT_RECIPE);
    private static final Identifier BG = Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "textures/gui/jei/pot.png");
    private static final MutableComponent TITLE = Component.translatable("block.kaleidoscope_cookery.pot");
    public static final int WIDTH = 176;
    public static final int HEIGHT = 102;
    private final IDrawable bgDraw;
    private final IDrawable slotDraw;
    private final IDrawable iconDraw;

    public PotRecipeCategory(IGuiHelper guiHelper) {
        this.slotDraw = guiHelper.getSlotDrawable();
        this.bgDraw = guiHelper.createDrawable(BG, 0, 0, WIDTH, HEIGHT);
        this.iconDraw = guiHelper.createDrawableItemStack(ModItems.POT.getDefaultInstance());
    }

    @Override
    public void draw(RecipeHolder<PotRecipe> recipe, @NonNull IRecipeSlotsView recipeSlotsView, @NonNull GuiGraphics guiGraphics, double mouseX, double mouseY) {
        this.bgDraw.draw(guiGraphics);
        Component stirFryCount = Component.translatable("jei.kaleidoscope_cookery.pot.stir_fry_count", recipe.value().stirFryCount());
        drawCenteredString(guiGraphics, stirFryCount, WIDTH / 2, 85);
    }

    private void drawCenteredString(GuiGraphics guiGraphics, Component text, int centerX, int y) {
        Font font = Minecraft.getInstance().font;
        FormattedCharSequence sequence = text.getVisualOrderText();
        guiGraphics.drawString(font, sequence, centerX - font.width(sequence) / 2, y, 0x555555, false);
    }

    @Override
    public void setRecipe(@NonNull IRecipeLayoutBuilder builder, RecipeHolder<PotRecipe> recipe, @NonNull IFocusGroup focuses) {
        NonNullList<Ingredient> inputs = recipe.value().getIngredients();
        ItemStack output = recipe.value().result();
        for (int i = 0; i < inputs.size(); i++) {
            int xOffset = (i % 3) * 18 + 15;
            int yOffset = (i / 3) * 18 + 24;
            builder.addSlot(RecipeIngredientRole.INPUT, xOffset, yOffset).add(inputs.get(i)).setBackground(slotDraw, -1, -1);
        }
        if (recipe.value().carrier().isPresent()) {
            builder.addSlot(RecipeIngredientRole.INPUT, 133, 18).add(recipe.value().carrier().get());
        }
        builder.addSlot(RecipeIngredientRole.OUTPUT, 143, 60).add(output).setBackground(slotDraw, -1, -1);
    }

    @Override
    public @NonNull IRecipeType<RecipeHolder<PotRecipe>> getRecipeType() {
        return TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return TITLE;
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    @Nullable
    public IDrawable getIcon() {
        return iconDraw;
    }
}
