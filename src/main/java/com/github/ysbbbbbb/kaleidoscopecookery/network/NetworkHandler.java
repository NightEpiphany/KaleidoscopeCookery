package com.github.ysbbbbbb.kaleidoscopecookery.network;

import com.github.ysbbbbbb.kaleidoscopecookery.network.message.FlatulenceMessage;
import com.github.ysbbbbbb.kaleidoscopecookery.network.message.ThrowBaoziMessage;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class NetworkHandler {
    public static void init() {
        PayloadTypeRegistry.serverboundPlay().register(FlatulenceMessage.TYPE, FlatulenceMessage.STREAM_CODEC);
        PayloadTypeRegistry.serverboundPlay().register(ThrowBaoziMessage.TYPE, ThrowBaoziMessage.STREAM_CODEC);

        ServerPlayNetworking.registerGlobalReceiver(FlatulenceMessage.TYPE, new FlatulenceMessage());
        ServerPlayNetworking.registerGlobalReceiver(ThrowBaoziMessage.TYPE, new ThrowBaoziMessage());
    }
}
