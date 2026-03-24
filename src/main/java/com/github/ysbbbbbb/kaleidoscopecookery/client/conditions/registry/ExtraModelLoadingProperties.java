package com.github.ysbbbbbb.kaleidoscopecookery.client.conditions.registry;

import com.github.ysbbbbbb.kaleidoscopecookery.client.conditions.prop.ExtraModelLoadingProperty;
import com.mojang.serialization.MapCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;

public class ExtraModelLoadingProperties {
    public static final ExtraCodecs.LateBoundIdMapper<Identifier, MapCodec<? extends ExtraModelLoadingProperty>> ID_MAPPER = new ExtraCodecs.LateBoundIdMapper<>();
    public static final MapCodec<ExtraModelLoadingProperty> MAP_CODEC = ID_MAPPER.codec(Identifier.CODEC)
            .dispatchMap("property", ExtraModelLoadingProperty::type, c -> c);
}
