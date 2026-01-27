package com.github.ysbbbbbb.kaleidoscopecookery.particle;

import com.github.ysbbbbbb.kaleidoscopecookery.init.ModParticles;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ScalableParticleOptionsBase;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import org.joml.Vector3fc;
import org.jspecify.annotations.NonNull;

@SuppressWarnings("deprecation")
public class StockpotParticleOptions extends ScalableParticleOptionsBase {

    public static final MapCodec<StockpotParticleOptions> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    ExtraCodecs.VECTOR3F.fieldOf("color").forGetter(StockpotParticleOptions::getColor)
                    , SCALE.fieldOf("scale").forGetter(ScalableParticleOptionsBase::getScale)
            ).apply(instance, StockpotParticleOptions::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, StockpotParticleOptions> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VECTOR3F, StockpotParticleOptions::getColor,
            ByteBufCodecs.FLOAT, StockpotParticleOptions::getScale,
            StockpotParticleOptions::new
    );
    private final Vector3fc color;

    public StockpotParticleOptions(Vector3fc color, float scale) {
        super(scale);
        this.color = color;
    }

    public Vector3fc getColor() {
        return this.color;
    }

    @Override
    public @NonNull ParticleType<StockpotParticleOptions> getType() {
        return ModParticles.STOCKPOT;
    }
}
