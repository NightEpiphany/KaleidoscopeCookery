package com.github.ysbbbbbb.kaleidoscopecookery.init;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConsumableInitializationTest {
    @BeforeAll
    static void bootstrap() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
    }

    @Test
    void foodLoadedBeforeExplicitEffectRegistrationKeepsValidEffects() throws ReflectiveOperationException {
        // This ordering used to capture null holders permanently in the food components.
        Consumable burger = ModConsumables.DONKEY_BURGER;
        ModEffects.registerEffects();

        for (var field : ModConsumables.class.getFields()) {
            Consumable consumable = (Consumable) field.get(null);
            for (var effect : consumable.onConsumeEffects()) {
                if (effect instanceof ApplyStatusEffectsConsumeEffect statusEffects) {
                    for (var instance : statusEffects.effects()) {
                        assertNotNull(instance.getEffect(), field.getName());
                        assertTrue(instance.getEffect().isBound(), field.getName());
                    }
                }
            }
            assertDoesNotThrow(consumable::hashCode, field.getName());
        }

        var effect = (ApplyStatusEffectsConsumeEffect) burger.onConsumeEffects().getFirst();
        assertSame(ModEffects.SATIATED_SHIELD, effect.effects().getFirst().getEffect());
        assertEquals(45 * 20, effect.effects().getFirst().getDuration());
    }
}
