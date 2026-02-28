package com.github.ysbbbbbb.kaleidoscopecookery.compat.jei.category;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.MillstoneRecipe;
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
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public class MillstoneRecipeCategory implements IRecipeCategory<RecipeHolder<MillstoneRecipe>> {
    public static final IRecipeHolderType<MillstoneRecipe> TYPE = IRecipeType.create(ModRecipes.MILLSTONE_RECIPE);

    private static final Identifier BG = Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "textures/gui/jei/millstone.png");
    private static final MutableComponent TITLE = Component.translatable("block.kaleidoscope_cookery.millstone");

    public static final int WIDTH = 176;
    public static final int HEIGHT = 95;

    private final IDrawable bgDraw;
    private final IDrawable iconDraw;

    public MillstoneRecipeCategory(IGuiHelper guiHelper) {
        this.bgDraw = guiHelper.createDrawable(BG, 0, 0, WIDTH, HEIGHT);
        this.iconDraw = guiHelper.createDrawableItemLike(ModItems.MILLSTONE);
    }

    @Override
    public void draw(RecipeHolder<MillstoneRecipe> recipe, @NonNull IRecipeSlotsView recipeSlotsView, @NonNull GuiGraphics guiGraphics, double mouseX, double mouseY) {
        this.bgDraw.draw(guiGraphics);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<MillstoneRecipe> holder, @NonNull IFocusGroup focuses) {
        MillstoneRecipe recipe = holder.value();
        Ingredient input = recipe.getIngredient();
        ItemStack output = recipe.getResult();

        builder.addSlot(RecipeIngredientRole.INPUT, 69, 39).add(input).setStandardSlotBackground();
        builder.addSlot(RecipeIngredientRole.OUTPUT, 146, 47).add(output);

    }


    @Override
    public @NonNull IRecipeType<RecipeHolder<MillstoneRecipe>> getRecipeType() {
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
