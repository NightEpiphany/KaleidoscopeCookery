package com.github.ysbbbbbb.kaleidoscopecookery.client.init;

import com.github.ysbbbbbb.kaleidoscopecookery.client.particle.CookingParticle;
import com.github.ysbbbbbb.kaleidoscopecookery.client.particle.StockpotParticle;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModParticles;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;

@Environment(EnvType.CLIENT)
public class ModParticleFactoryRegistry {
    public static void register() {
        ParticleProviderRegistry.getInstance().register(ModParticles.COOKING, CookingParticle.Provider::new);
        ParticleProviderRegistry.getInstance().register(ModParticles.STOCKPOT, StockpotParticle.Provider::new);
    }
}
