package com.github.ysbbbbbb.kaleidoscopecookery.client.init;

import net.fabricmc.fabric.api.client.model.loading.v1.ExtraModelKey;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.resources.Identifier;

import java.util.HashMap;
import java.util.Map;

//获取全局模型注册键
public class ModModelKeys {

    private static final Map<Identifier, ExtraModelKey<BlockStateModel>> KEYS = new HashMap<>();

    public static ExtraModelKey<BlockStateModel> getOrCreate(Identifier id) {
        return KEYS.computeIfAbsent(id, key ->
                ExtraModelKey.create(key::toString)
        );
    }

    public static ExtraModelKey<BlockStateModel> get(Identifier id) {
        return KEYS.get(id);
    }
}
