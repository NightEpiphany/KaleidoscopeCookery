package com.github.ysbbbbbb.kaleidoscopecookery.init;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.entity.ScarecrowEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.entity.SitEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.entity.ThrowableBaoziEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntities {
    public static final EntityType<SitEntity> SIT = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "sit"),
            EntityType.Builder.<SitEntity>of(SitEntity::new, MobCategory.MISC).sized(0.001F, 0.001F).build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "sit")))
    );
    public static final EntityType<ScarecrowEntity> SCARECROW = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "scarecrow"),
            EntityType.Builder.<ScarecrowEntity>of(ScarecrowEntity::new, MobCategory.MISC).sized(0.5F, 2.375f)
                    .clientTrackingRange(10).build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "scarecrow")))
    );
    public static final EntityType<ThrowableBaoziEntity> THROWABLE_BAOZI = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "throwable_baozi"),
            EntityType.Builder.<ThrowableBaoziEntity>of(ThrowableBaoziEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4)
                    .updateInterval(10).build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "throwable_baozi")))
    );

    public static void registerEntities() {

        // Register entity attributes
        FabricDefaultAttributeRegistry.register(SCARECROW, ScarecrowEntity.createAttributes());
    }
}
