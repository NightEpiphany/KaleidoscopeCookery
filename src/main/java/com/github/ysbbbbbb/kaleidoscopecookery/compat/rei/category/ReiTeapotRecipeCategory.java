package com.github.ysbbbbbb.kaleidoscopecookery.compat.rei.category;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.rei.ReiUtil;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.TeapotRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.compat.GuiGraphics;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReiTeapotRecipeCategory implements DisplayCategory<ReiTeapotRecipeCategory.TeapotRecipeDisplay> {
    public static final CategoryIdentifier<TeapotRecipeDisplay> ID = CategoryIdentifier.of(KaleidoscopeCookery.MOD_ID, "plugin/teapot");
    private static final Identifier BG = Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "textures/gui/jei/teapot.png");
    private static final MutableComponent TITLE = Component.translatable("block.kaleidoscope_cookery.teapot");
    public static final int WIDTH = 176;
    public static final int HEIGHT = 88;

    @Override
    public CategoryIdentifier<TeapotRecipeDisplay> getCategoryIdentifier() {
        return ID;
    }

    @Override
    public List<Widget> setupDisplay(TeapotRecipeDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();
        int startX = bounds.x;
        int startY = bounds.y + 4;
        Component brewTime = Component.translatable("jei.kaleidoscope_cookery.teapot.time", display.brewTime / 20);

        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createTexturedWidget(BG, startX, startY, 0, 0, WIDTH, HEIGHT));
        widgets.add(Widgets.withTranslate(Widgets.createDrawableWidget((guiGraphics, mouseX, mouseY, v) -> drawCenteredString(guiGraphics, brewTime)), startX, startY));
        widgets.add(Widgets.createSlot(new Point(startX + 65, startY + 3))
                .entries(display.getInputEntries().get(0))
                .markInput());
        widgets.add(Widgets.createSlot(new Point(startX + 83, startY + 3))
                .entries(display.getInputEntries().get(1))
                .markInput());
        widgets.add(Widgets.createSlot(new Point(startX + 128, startY + 30))
                .entries(display.getOutputEntries().getFirst())
                .backgroundEnabled(false)
                .markOutput());

        return widgets;
    }

    private void drawCenteredString(GuiGraphics guiGraphics, Component text) {
        Font font = Minecraft.getInstance().font;
        guiGraphics.drawString(font, text, 88 - font.width(text) / 2, 70, 0x555555, false);
    }

    @Override
    public int getDisplayWidth(TeapotRecipeDisplay display) {
        return WIDTH;
    }

    @Override
    public int getDisplayHeight() {
        return HEIGHT;
    }

    @Override
    public Component getTitle() {
        return TITLE;
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModItems.TEAPOT);
    }

    public static void registerCategories(CategoryRegistry registry) {
        registry.add(new ReiTeapotRecipeCategory());
        registry.addWorkstations(ReiTeapotRecipeCategory.ID, ReiUtil.ofItem(ModItems.TEAPOT));
    }

//    public static void registerDisplays(DisplayRegistry registry) {
//        registry.getRecipeManager().getAllRecipesFor(ModRecipes.TEAPOT_RECIPE)
//                .forEach(r -> {
//                    Fluid fluid = BuiltInRegistries.FLUID.get(r.value().teaFluid());
//                    Item bucket = fluid.getBucket();
//                    List<EntryIngredient> fluidInput = ReiUtil.ofItems(bucket);
//                    List<EntryIngredient> inputs = List.of(EntryIngredient.of(Arrays.stream(r.value().ingredient().getItems())
//                            .map(stack -> EntryStacks.of(stack.copyWithCount(r.value().ingredientCount())))
//                            .toList()));
//                    List<EntryIngredient> output = ReiUtil.ofItemStacks(r.value().result().copyWithCount(TeapotRecipe.OUTPUT_COUNT));
//
//                    registry.add(new TeapotRecipeDisplay(r.id(), fluidInput, inputs, output, r.value().time()));
//                });
//    }

    public static class TeapotRecipeDisplay extends BasicDisplay {
        public final int brewTime;
        protected final List<EntryIngredient> fluidInput;

        public static final DisplaySerializer<TeapotRecipeDisplay> SERIALIZER = DisplaySerializer.of(
                RecordCodecBuilder.mapCodec(inst -> inst.group(
                        Identifier.CODEC.fieldOf("location").forGetter(r -> r.getDisplayLocation().orElse(Identifier.withDefaultNamespace("air"))),
                        EntryIngredient.codec().listOf().fieldOf("tea_fluid").forGetter(TeapotRecipeDisplay::getFluidInput),
                        EntryIngredient.codec().listOf().fieldOf("inputs").forGetter(TeapotRecipeDisplay::getInputEntries),
                        EntryIngredient.codec().listOf().fieldOf("outputs").forGetter(TeapotRecipeDisplay::getOutputEntries),
                        Codec.INT.fieldOf("brew_time").forGetter(TeapotRecipeDisplay::getBrewTime)

                ).apply(inst, TeapotRecipeDisplay::new)),
                StreamCodec.composite(
                        Identifier.STREAM_CODEC, r -> r.getDisplayLocation().orElse(Identifier.withDefaultNamespace("air")),
                        EntryIngredient.streamCodec().apply(ByteBufCodecs.list()), TeapotRecipeDisplay::getFluidInput,
                        EntryIngredient.streamCodec().apply(ByteBufCodecs.list()), TeapotRecipeDisplay::getInputEntries,
                        EntryIngredient.streamCodec().apply(ByteBufCodecs.list()), TeapotRecipeDisplay::getOutputEntries,
                        ByteBufCodecs.INT, TeapotRecipeDisplay::getBrewTime,
                        TeapotRecipeDisplay::new
                ));

        public TeapotRecipeDisplay(Identifier location,
                                   List<EntryIngredient> fluidInput,
                                   List<EntryIngredient> inputs,
                                   List<EntryIngredient> outputs, int brewTime) {
            super(List.of(fluidInput.getFirst(), inputs.getFirst()), outputs, Optional.of(location));
            this.fluidInput = fluidInput;
            this.brewTime = brewTime;
        }

        public TeapotRecipeDisplay(RecipeHolder<TeapotRecipe> holder) {
            this(holder.id().identifier(), ReiUtil.ofIngredients(getBucket(holder.value().teaFluid())), ReiUtil.ofIngredients(holder.value().ingredient()), ReiUtil.ofItemStacks(holder.value().result().create()), holder.value().time());
        }

        private static Ingredient getBucket(Identifier id) {
            Fluid f = BuiltInRegistries.FLUID.getValue(id);
            return Ingredient.of(f.getBucket());
        }

        public List<EntryIngredient> getFluidInput() {
            return fluidInput;
        }

        public int getBrewTime() {
            return brewTime;
        }

        @Override
        public CategoryIdentifier<?> getCategoryIdentifier() {
            return ID;
        }

        @Override
        public @Nullable DisplaySerializer<? extends Display> getSerializer() {
            return SERIALIZER;
        }
    }
}
