package com.github.ysbbbbbb.kaleidoscopecookery.compat.rei.category;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.api.recipe.soupbase.ISoupBase;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.farmersdelight.FarmersDelightCompat;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.rei.ReiUtil;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.StockpotRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.soupbase.SoupBaseManager;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopecookery.util.recipes.ModRecipesLibrary;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReiStockpotRecipeCategory implements DisplayCategory<ReiStockpotRecipeCategory.StockpotRecipeDisplay> {
    public static final CategoryIdentifier<StockpotRecipeDisplay> ID = CategoryIdentifier.of(KaleidoscopeCookery.MOD_ID, "plugin/stockpot");
    private static final Identifier BG = Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "textures/gui/jei/stockpot.png");
    private static final MutableComponent TITLE = Component.translatable("block.kaleidoscope_cookery.stockpot");
    public static final int WIDTH = 176;
    public static final int HEIGHT = 102;

    @Override
    public CategoryIdentifier<StockpotRecipeDisplay> getCategoryIdentifier() {
        return ID;
    }

    @Override
    public List<Widget> setupDisplay(StockpotRecipeDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();
        int startX = bounds.x;
        int startY = bounds.y;

        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createTexturedWidget(BG, startX, startY, 0, 0, WIDTH, HEIGHT));

        if (!display.soupBase.isEmpty()) {
            widgets.add(Widgets.createSlot(new Point(startX + 72, startY + 61))
                    .entries(display.soupBase)
                    .disableBackground()
                    .markInput());
        }
        List<EntryIngredient> inputs = display.getInputEntries();
        for (int i = 0; i < inputs.size(); i++) {
            int xOffset = (i % 3) * 18 + 15;
            int yOffset = (i / 3) * 18 + 25;
            widgets.add(Widgets.createSlot(new Point(startX + xOffset, startY + yOffset))
                    .entries(inputs.get(i))
                    .disableBackground()
                    .markInput());
        }
        if (!display.carrier.isEmpty()) {
            widgets.add(Widgets.createSlot(new Point(startX + 133, startY + 18))
                    .entries(display.carrier)
                    .disableBackground()
                    .markInput());
        }
        widgets.add(Widgets.createSlot(new Point(startX + 143, startY + 60))
                .entries(display.getOutputEntries().getFirst())
                .disableBackground()
                .markOutput());

        return widgets;
    }

    @Override
    public int getDisplayWidth(StockpotRecipeDisplay display) {
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
        return EntryStacks.of(ModItems.STOCKPOT);
    }

    public static void registerCategories(CategoryRegistry registry) {
        registry.add(new ReiStockpotRecipeCategory());
        registry.addWorkstations(ReiStockpotRecipeCategory.ID,
                ReiUtil.ofItem(ModItems.STOCKPOT),
                ReiUtil.ofItem(ModItems.STOCKPOT_LID)
        );
    }

    public static void registerDisplays(DisplayRegistry registry) {
        List<RecipeHolder<StockpotRecipe>> list = new ArrayList<>(ModRecipesLibrary.INSTANCE.stockpotRecipes());
        FarmersDelightCompat.getTransformRecipeForJei(Minecraft.getInstance().level, list);

        list.forEach(r -> {
            List<EntryIngredient> inputs = ReiUtil.ofIngredients(r.value().getIngredients());
            List<EntryIngredient> output = ReiUtil.ofItemStacks(r.value().getResultItem(RegistryAccess.EMPTY));
            EntryIngredient carrier = r.value().carrier().isEmpty() ? EntryIngredient.empty() : ReiUtil.ofIngredient(r.value().carrier());

            ISoupBase soupBase = SoupBaseManager.getSoupBase(r.value().soupBase());
            if (soupBase == null) {
                throw new RuntimeException("No soup found for " + r.value().soupBase());
            }
            EntryIngredient soupBaseEntry = ReiUtil.ofItemStack(soupBase.getDisplayStack());

            registry.add(new StockpotRecipeDisplay(r.id().registry(), inputs, output, carrier, soupBaseEntry));
        });
    }

    public static class StockpotRecipeDisplay extends BasicDisplay {
        public final EntryIngredient carrier;
        public final EntryIngredient soupBase;

        public static final DisplaySerializer<StockpotRecipeDisplay> SERIALIZER = DisplaySerializer.of(
                RecordCodecBuilder.mapCodec(inst -> inst.group(
                        Identifier.CODEC.fieldOf("location").forGetter(r -> r.getDisplayLocation().orElse(Identifier.withDefaultNamespace("air"))),
                        EntryIngredient.codec().listOf().fieldOf("inputs").forGetter(StockpotRecipeDisplay::getInputEntries),
                        EntryIngredient.codec().listOf().fieldOf("outputs").forGetter(StockpotRecipeDisplay::getOutputEntries),
                        EntryIngredient.codec().fieldOf("carrier").forGetter(StockpotRecipeDisplay::getCarrier),
                        EntryIngredient.codec().fieldOf("soup_base").forGetter(StockpotRecipeDisplay::getSoupBase)
                ).apply(inst, StockpotRecipeDisplay::new)),
                StreamCodec.composite(
                        Identifier.STREAM_CODEC, r -> r.getDisplayLocation().orElse(Identifier.withDefaultNamespace("air")),
                        EntryIngredient.streamCodec().apply(ByteBufCodecs.list()), StockpotRecipeDisplay::getInputEntries,
                        EntryIngredient.streamCodec().apply(ByteBufCodecs.list()), StockpotRecipeDisplay::getOutputEntries,
                        EntryIngredient.streamCodec(), StockpotRecipeDisplay::getCarrier,
                        EntryIngredient.streamCodec(), StockpotRecipeDisplay::getSoupBase,
                        StockpotRecipeDisplay::new
                ));

        public StockpotRecipeDisplay(RecipeHolder<StockpotRecipe> holder) {
            this(holder.id().identifier(), EntryIngredients.ofIngredients(holder.value().ingredients()), ReiUtil.ofItemStacks(holder.value().getResultItem(RegistryAccess.EMPTY)),
                    ReiUtil.ofIngredient(holder.value().carrier()), ReiUtil.ofItemStack(SoupBaseManager.getSoupBase(holder.value().soupBase()).getDisplayStack()));
        }

        public StockpotRecipeDisplay(Identifier location, List<EntryIngredient> inputs, List<EntryIngredient> outputs, EntryIngredient carrier, EntryIngredient soupBase) {
            super(inputs, outputs, Optional.of(location));
            this.carrier = carrier;
            this.soupBase = soupBase;
        }

        public EntryIngredient getCarrier() {
            return carrier;
        }

        public EntryIngredient getSoupBase() {
            return soupBase;
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
