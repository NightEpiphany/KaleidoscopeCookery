package com.github.ysbbbbbb.kaleidoscopecookery.client.particle;

import com.github.ysbbbbbb.kaleidoscopecookery.particle.StockpotParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.RandomSource;
import org.joml.Vector3f;
import org.jspecify.annotations.NonNull;

public class StockpotParticle extends SingleQuadParticle {
    private final SpriteSet spriteSet;

    protected StockpotParticle(ClientLevel level, double posX, double posY, double posZ,
                                SpriteSet spriteSet, TextureAtlasSprite textureAtlasSprite) {
        super(level, posX, posY, posZ, textureAtlasSprite);
        this.friction = 0.96F;
        this.spriteSet = spriteSet;
        this.scale(1.0F);
        this.hasPhysics = false;
        this.setSpriteFromAge(spriteSet);
    }

    @Override
    protected int getLightCoords(float a) {
        return super.getLightCoords(a);
    }

    @Override
    protected @NonNull Layer getLayer() {
        return SingleQuadParticle.Layer.TRANSLUCENT;
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.spriteSet);
    }

    public record Provider(SpriteSet spriteSet) implements ParticleProvider<StockpotParticleOptions> {

        @Override
        public @NonNull Particle createParticle(StockpotParticleOptions particleOptions, @NonNull ClientLevel clientLevel, double d, double e, double f, double g, double h, double i, @NonNull RandomSource randomSource) {
            StockpotParticle particle = new StockpotParticle(clientLevel, d, e, f, this.spriteSet, this.spriteSet.get(randomSource));
            Vector3f color = (Vector3f) particleOptions.getColor();
            float scale = particleOptions.getScale() - 0.1f + clientLevel.getRandom().nextFloat() * 0.2f;
            particle.setAlpha(1);
            particle.setColor(color.x, color.y, color.z);
            particle.setSize(scale, scale);
            particle.setParticleSpeed(g, h, i);
            particle.setLifetime(clientLevel.getRandom().nextInt(4) + 6);
            return particle;
        }
    }
}
