package com.github.ysbbbbbb.kaleidoscopecookery.compat.create.automation.init;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.create.automation.arm.type.*;
import com.zurrtum.create.api.registry.CreateRegistries;
import com.zurrtum.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;

@SuppressWarnings("unused")
public class InitArmInteractionPointTypes {
    public static final ArmInteractionPointType STOCKPOT_POINT = Registry.register(CreateRegistries.ARM_INTERACTION_POINT_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "stockpot"), new StockPotType());

    public static final ArmInteractionPointType POT_POINT = Registry.register(CreateRegistries.ARM_INTERACTION_POINT_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "pot"), new PotType());

    public static final ArmInteractionPointType STEAMER_POINT = Registry.register(CreateRegistries.ARM_INTERACTION_POINT_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "steamer"), new SteamerType());

    public static final ArmInteractionPointType FRUIT_BASKET_POINT = Registry.register(CreateRegistries.ARM_INTERACTION_POINT_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "fruit_basket"), new FruitBasketType());

    public static final ArmInteractionPointType SHAWARMA_SPIT_POINT = Registry.register(CreateRegistries.ARM_INTERACTION_POINT_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "shawarma_spit"), new ShawarmaSpitType());

    public static final ArmInteractionPointType TABLE_POINT = Registry.register(CreateRegistries.ARM_INTERACTION_POINT_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "table"), new TableType());

    public static void init() {
    }
}
