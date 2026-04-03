package com.github.ysbbbbbb.kaleidoscopecookery.compat.create.automation.util;

import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.minecraft.server.level.ServerLevel;

import java.util.UUID;

public final class AutomationArmPlayer {
    private static final GameProfile POT_PROFILE = new GameProfile(UUID.fromString("8f245611-32df-4dbc-a88d-3f5a8c0bf4c9"), "KC-Pot-Arm");
    private static final GameProfile STOCKPOT_PROFILE = new GameProfile(UUID.fromString("fe60d0d8-88df-4552-a2c8-f096043232e9"), "KC-Stockpot-Arm");

    private AutomationArmPlayer() {
    }

    public static FakePlayer pot(ServerLevel level) {
        FakePlayer fakePlayer = FakePlayer.get(level, POT_PROFILE);
        fakePlayer.getInventory().clearContent();
        return fakePlayer;
    }

    public static FakePlayer stockpot(ServerLevel level) {
        FakePlayer fakePlayer = FakePlayer.get(level, STOCKPOT_PROFILE);
        fakePlayer.getInventory().clearContent();
        return fakePlayer;
    }

    public static void clear(FakePlayer fakePlayer) {
        fakePlayer.getInventory().clearContent();
    }
}
