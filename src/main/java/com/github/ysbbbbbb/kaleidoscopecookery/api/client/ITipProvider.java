package com.github.ysbbbbbb.kaleidoscopecookery.api.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;

@Environment(EnvType.CLIENT)
public interface ITipProvider {
    Component getTip();
}
