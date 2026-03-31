package com.github.ysbbbbbb.kaleidoscopecookery.compat.jei.category;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.api.recipe.soupbase.ISoupBase;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.StockpotRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.soupbase.SoupBaseManager;
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
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

@SuppressWarnings("deprecation")
public class StockpotRecipeCategory implements IRecipeCategory<RecipeHolder<StockpotRecipe>> {
    public static final IRecipeHolderType<StockpotRecipe> TYPE = IRecipeType.create(ModRecipes.STOCKPOT_RECIPE);
    private static final Identifier BG = Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "textures/gui/jei/stockpot.png");
    private static final MutableComponent TITLE = Component.translatable("block.kaleidoscope_cookery.stockpot");
    public static final int WIDTH = 176;
    public static final int HEIGHT = 102;
    private final IDrawable bgDraw;
    private final IDrawable iconDraw;
    private final IDrawable slotDraw;
    private final IGuiHelper guiHelper;

    public StockpotRecipeCategory(IGuiHelper guiHelper) {
        this.bgDraw = guiHelper.createDrawable(BG, 0, 0, WIDTH, HEIGHT);
        this.iconDraw = guiHelper.createDrawableItemStack(ModItems.STOCKPOT.getDefaultInstance());
        this.slotDraw = guiHelper.getSlotDrawable();
        this.guiHelper = guiHelper;
    }

    @Override
    public void draw(RecipeHolder<StockpotRecipe> recipe, @NonNull IRecipeSlotsView recipeSlotsView, @NonNull GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        this.bgDraw.draw(guiGraphics);
        if (recipe.value().carrier().items().findFirst().isPresent()) {
            Item carrier = recipe.value().carrier().items().findFirst().get().value();
            guiHelper.createDrawableItemLike(carrier).draw(guiGraphics, 133, 18);
        }
    }

    @Override
    public void setRecipe(@NonNull IRecipeLayoutBuilder builder, RecipeHolder<StockpotRecipe> holder, @NonNull IFocusGroup focuses) {
        StockpotRecipe recipe = holder.value();
        NonNullList<Ingredient> inputs = recipe.getIngredients();
        ItemStack output = recipe.result().create();
        for (int i = 0; i < inputs.size(); i++) {
            int xOffset = (i % 3) * 18 + 15;
            int yOffset = (i / 3) * 18 + 25;
            builder.addSlot(RecipeIngredientRole.INPUT, xOffset, yOffset).add(inputs.get(i)).setBackground(slotDraw, -1, -1);
        }
        ISoupBase soupBase = SoupBaseManager.getSoupBase(recipe.soupBase());
        if (soupBase == null) {
            throw new RuntimeException("No soup found for " + recipe.soupBase());
        }
        ItemStack displayStack = soupBase.getDisplayStack();
        if (!displayStack.isEmpty()) {
            builder.addSlot(RecipeIngredientRole.INPUT, 72, 61).add(Ingredient.of(displayStack.getItem()));
        }
        builder.addSlot(RecipeIngredientRole.OUTPUT, 143, 60).add(output);
    }

    @Override
    public @NotNull IRecipeHolderType<StockpotRecipe> getRecipeType() {
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
