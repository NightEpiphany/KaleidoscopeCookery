package com.github.ysbbbbbb.kaleidoscopecookery.compat.jei.category;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.output.RandomOutput;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.MillstoneRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModRecipes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotRichTooltipCallback;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeHolderType;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.text.DecimalFormat;
import java.util.List;

public class MillstoneRecipeCategory implements IRecipeCategory<RecipeHolder<MillstoneRecipe>> {
    public static final IRecipeHolderType<MillstoneRecipe> TYPE = IRecipeType.create(ModRecipes.MILLSTONE_RECIPE);

    private static final DecimalFormat FORMAT = new DecimalFormat("0.##%");
    private static final Identifier BG = Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "textures/gui/jei/millstone.png");
    private static final MutableComponent TITLE = Component.translatable("block.kaleidoscope_cookery.millstone");

    public static final int WIDTH = 196;
    public static final int HEIGHT = 95;

    private final IDrawable bgDraw;
    private final IDrawable iconDraw;

    public MillstoneRecipeCategory(IGuiHelper guiHelper) {
        this.bgDraw = guiHelper.createDrawable(BG, 0, 0, WIDTH, HEIGHT);
        this.iconDraw = guiHelper.createDrawableItemLike(ModItems.MILLSTONE);
    }

    @Override
    public void draw(@NonNull RecipeHolder<MillstoneRecipe> recipe, @NonNull IRecipeSlotsView recipeSlotsView, @NonNull GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        this.bgDraw.draw(guiGraphics);
    }

    public static IRecipeSlotRichTooltipCallback addChanceTooltip(RandomOutput output) {
        return (view, tooltip) -> {
            float chance = output.chance();
            if (chance != 1.0F) {
                tooltip.add(Component.translatable("tooltip.kaleidoscope_cookery.chance", FORMAT.format(chance))
                        .withStyle(ChatFormatting.GOLD));
            }
        };
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<MillstoneRecipe> holder, @NonNull IFocusGroup focuses) {
        MillstoneRecipe recipe = holder.value();
        Ingredient input = recipe.ingredient();
        List<RandomOutput> outputs = recipe.results();

        builder.addSlot(RecipeIngredientRole.INPUT, 69, 39)
                .add(input)
                .setStandardSlotBackground();

        RandomOutput output = outputs.getFirst();
        builder.addSlot(RecipeIngredientRole.OUTPUT, 150, 47)
                .add(output.stack())
                .setOutputSlotBackground()
                .addRichTooltipCallback(addChanceTooltip(output));

        for (int i = 1; i < outputs.size(); i++) {
            RandomOutput randomOutput = outputs.get(i);
            int x = switch (i) {
                case 2 -> 128;
                case 3 -> 172;
                default -> 150;
            };
            builder.addSlot(RecipeIngredientRole.OUTPUT, x, 20)
                    .add(randomOutput.stack())
                    .setStandardSlotBackground()
                    .addRichTooltipCallback(addChanceTooltip(randomOutput));
        }
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
