package com.github.ysbbbbbb.kaleidoscopecookery;

import com.github.ysbbbbbb.kaleidoscopecookery.server.KaleidoscopeCookeryServerPatch;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.SERVER)
public final class KaleidoscopeCookeryServer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        KaleidoscopeCookeryServerPatch.patch();
    }
}
