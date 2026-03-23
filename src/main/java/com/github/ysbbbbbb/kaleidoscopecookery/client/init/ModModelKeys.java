package com.github.ysbbbbbb.kaleidoscopecookery.client.init;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class ModModelKeys {
    private static final Map<Identifier, Identifier> MODEL_KEYS = new ConcurrentHashMap<>();

    private ModModelKeys() {}

    public static Identifier getOrCreate(Identifier modelId) {
        return MODEL_KEYS.computeIfAbsent(modelId, id -> id);
    }

    public static Identifier get(Identifier modelId) {
        return MODEL_KEYS.getOrDefault(modelId, modelId);
    }

    public static void clear() {
        MODEL_KEYS.clear();
    }
}
