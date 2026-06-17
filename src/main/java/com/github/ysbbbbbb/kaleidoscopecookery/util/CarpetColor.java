package com.github.ysbbbbbb.kaleidoscopecookery.util;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

public final class CarpetColor {
    public static Item getCarpetByColor(DyeColor color) {
        return switch (color) {
            case WHITE -> Items.CARPET.white();
            case ORANGE -> Items.CARPET.orange();
            case MAGENTA -> Items.CARPET.magenta();
            case LIGHT_BLUE -> Items.CARPET.lightBlue();
            case YELLOW -> Items.CARPET.yellow();
            case LIME -> Items.CARPET.lime();
            case PINK -> Items.CARPET.pink();
            case GRAY -> Items.CARPET.gray();
            case LIGHT_GRAY -> Items.CARPET.lightGray();
            case CYAN -> Items.CARPET.cyan();
            case PURPLE -> Items.CARPET.purple();
            case BLUE -> Items.CARPET.blue();
            case BROWN -> Items.CARPET.brown();
            case GREEN -> Items.CARPET.green();
            case RED -> Items.CARPET.red();
            case BLACK -> Items.CARPET.black();
        };
    }

    @Nullable
    public static DyeColor getColorByCarpet(Item item) {
        if (item == Items.CARPET.white()) {
            return DyeColor.WHITE;
        }
        if (item == Items.CARPET.orange()) {
            return DyeColor.ORANGE;
        }
        if (item == Items.CARPET.magenta()) {
            return DyeColor.MAGENTA;
        }
        if (item == Items.CARPET.lightBlue()) {
            return DyeColor.LIGHT_BLUE;
        }
        if (item == Items.CARPET.yellow()) {
            return DyeColor.YELLOW;
        }
        if (item == Items.CARPET.lime()) {
            return DyeColor.LIME;
        }
        if (item == Items.CARPET.pink()) {
            return DyeColor.PINK;
        }
        if (item == Items.CARPET.gray()) {
            return DyeColor.GRAY;
        }
        if (item == Items.CARPET.lightGray()) {
            return DyeColor.LIGHT_GRAY;
        }
        if (item == Items.CARPET.cyan()) {
            return DyeColor.CYAN;
        }
        if (item == Items.CARPET.purple()) {
            return DyeColor.PURPLE;
        }
        if (item == Items.CARPET.blue()) {
            return DyeColor.BLUE;
        }
        if (item == Items.CARPET.brown()) {
            return DyeColor.BROWN;
        }
        if (item == Items.CARPET.green()) {
            return DyeColor.GREEN;
        }
        if (item == Items.CARPET.red()) {
            return DyeColor.RED;
        }
        if (item == Items.CARPET.black()) {
            return DyeColor.BLACK;
        }
        return null;
    }
}
