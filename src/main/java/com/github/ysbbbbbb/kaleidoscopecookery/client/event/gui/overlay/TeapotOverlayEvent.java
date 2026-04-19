package com.github.ysbbbbbb.kaleidoscopecookery.client.event.gui.overlay;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.api.blockentity.ITeapot;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen.TeapotBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.serializer.TeapotRecipeSerializer;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopecookery.util.fluids.TeaFluidHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.Arrays;
import java.util.function.Function;

@Environment(EnvType.CLIENT)
public final class TeapotOverlayEvent {
    private TeapotOverlayEvent() {
    }

    public static void register() {
        HudElementRegistry.attachElementBefore(
                VanillaHudElements.MISC_OVERLAYS,
                Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "teapot_overlay"),
                TeapotOverlayEvent::render
        );
    }

    private static void render(GuiGraphicsExtractor guiGraphics, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.gameMode == null || minecraft.gameMode.getPlayerMode() == GameType.SPECTATOR) {
            return;
        }
        HitResult hitResult = minecraft.hitResult;
        if (!(hitResult instanceof BlockHitResult blockHitResult) || blockHitResult.getType() != HitResult.Type.BLOCK) {
            return;
        }
        LocalPlayer player = minecraft.player;
        if (player == null) {
            return;
        }

        Level level = player.level();
        BlockPos blockPos = blockHitResult.getBlockPos();
        BlockState blockState = level.getBlockState(blockPos);
        if (!blockState.is(ModBlocks.TEAPOT)) {
            return;
        }
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (!(blockEntity instanceof TeapotBlockEntity teapot)) {
            return;
        }

        Font font = minecraft.font;
        int x = minecraft.getWindow().getGuiScaledWidth() / 2;
        int y = minecraft.getWindow().getGuiScaledHeight() - 72;
        if (minecraft.gui.overlayMessageTime > 0) {
            y -= 12;
        }

        drawWordWrap(guiGraphics, font, teapot.getStatusText(), x, y);
        y += font.lineHeight + 2;

        Component detail = getDetailText(teapot);
        if (!detail.equals(CommonComponents.EMPTY)) {
            drawWordWrap(guiGraphics, font, detail, x, y);
        }
    }

    private static Component getDetailText(TeapotBlockEntity teapot) {
        if (teapot.getStatus() == ITeapot.PUT_INGREDIENT) {
            Component fluidText = teapot.getTeaFluidId().equals(TeapotRecipeSerializer.EMPTY_TEA_FLUID)
                    ? Component.translatable("mco.configure.world.slot.empty")
                    : TeaFluidHelper.getDisplayName(teapot.getTeaFluidId());
            ItemStack input = teapot.getInput();
            Component itemText = input.isEmpty()
                    ? Component.translatable("mco.configure.world.slot.empty")
                    : ComponentUtils.formatList(
                    Arrays.asList(input.getHoverName(), Component.literal("x%d".formatted(input.getCount()))),
                    CommonComponents.space(),
                    Function.identity()
            );
            return Component.translatable("tooltip.kaleidoscope_cookery.teapot.statue.fluid_ingredient", fluidText, itemText);
        }
        if (teapot.getStatus() == ITeapot.FINISHED) {
            ItemStack result = teapot.getResult();
            Component resultText = result.isEmpty()
                    ? Component.translatable("mco.configure.world.slot.empty")
                    : ComponentUtils.formatList(
                    Arrays.asList(result.getHoverName(), Component.literal("x%d".formatted(result.getCount()))),
                    CommonComponents.space(),
                    Function.identity()
            );
            return Component.translatable("tooltip.kaleidoscope_cookery.teapot.statue.result", resultText);
        }
        return CommonComponents.EMPTY;
    }

    private static void drawWordWrap(GuiGraphicsExtractor graphics, Font font, Component text, int centerX, int startY) {
        int y = startY;
        for (FormattedCharSequence sequence : font.split(text, 160)) {
            graphics.text(font, sequence, centerX - font.width(sequence) / 2, y, -1);
            y += font.lineHeight;
        }
    }
}
