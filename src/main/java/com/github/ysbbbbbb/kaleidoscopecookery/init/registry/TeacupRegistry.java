package com.github.ysbbbbbb.kaleidoscopecookery.init.registry;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModEffects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;

import org.jetbrains.annotations.Nullable;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class TeacupRegistry {
    public static final Map<Identifier, TeacupData> TEACUP_DATA_MAP = Maps.newLinkedHashMap();

    public static final Identifier BARLEY_TEA = id("barley_tea");
    public static final Identifier TIEGUANYIN = id("tieguanyin");
    public static final Identifier BILUOCHUN = id("biluochun");
    public static final Identifier OOLONG = id("oolong");
    public static final Identifier SAKURA_FUBUKI = id("sakura_fubuki");
    public static final Identifier FLOWER_TEA = id("flower_tea");

    static {
        bootstrap();
    }

    public static void init() {
        // 类加载时已完成注册，保留该入口仅用于兼容现有初始化流程。
    }

    private static void bootstrap() {
        registerTeacupData(BARLEY_TEA, TeacupData.create(4).addEffect(() ->
                new MobEffectInstance(ModEffects.VITALITY, 8 * 60 * 20))
        );

        registerTeacupData(TIEGUANYIN, TeacupData.create(4).addEffect(() ->
                new MobEffectInstance(ModEffects.INSTANT_SMELTING, 2 * 60 * 20))
        );

        registerTeacupData(BILUOCHUN, TeacupData.create(4).addEffect(() ->
                new MobEffectInstance(ModEffects.PROJECTILE_DODGE, 2 * 60 * 20))
        );

        registerTeacupData(OOLONG, TeacupData.create(4)
                .addEffect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING, 6 * 60 * 20))
                .addEffect(() -> new MobEffectInstance(MobEffects.JUMP_BOOST, 6 * 60 * 20))
        );

        registerTeacupData(SAKURA_FUBUKI, TeacupData.create(4).addEffect(() ->
                new MobEffectInstance(ModEffects.HINDER, 6 * 60 * 20))
        );

        registerTeacupData(FLOWER_TEA, TeacupData.create(4).addEffect(() ->
                new MobEffectInstance(MobEffects.REGENERATION, 20 * 20))
        );
    }

    private static Identifier id(String name) {
        return Identifier.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, name);
    }

    public static Identifier registerTeacupData(Identifier id, TeacupData data) {
        TEACUP_DATA_MAP.put(id, data);
        return id;
    }

    public static Identifier registerTeacupData(String name, TeacupData data) {
        return registerTeacupData(id(name), data);
    }

    public static Item getItem(Identifier name) {
        return BuiltInRegistries.ITEM.getValue(name);
    }

    public static Block getBlock(Identifier name) {
        return BuiltInRegistries.BLOCK.getValue(name);
    }

    public static final class TeacupData {
        private final int maxCount;
        private final List<Pair<Supplier<MobEffectInstance>, Float>> effects = Lists.newArrayList();
        private @Nullable VoxelShape aabb = null;

        private TeacupData(int maxCount) {
            this.maxCount = maxCount;
        }

        public static TeacupData create(int maxCount) {
            return new TeacupData(maxCount);
        }

        public TeacupData addEffect(Supplier<MobEffectInstance> effect, float probability) {
            this.effects.add(Pair.of(effect, probability));
            return this;
        }

        public TeacupData addEffect(Supplier<MobEffectInstance> effect) {
            return addEffect(effect, 1.0f);
        }

        public TeacupData setAABB(VoxelShape aabb) {
            this.aabb = aabb;
            return this;
        }

        public int getMaxCount() {
            return maxCount;
        }

        @Nullable
        public VoxelShape getAABB() {
            return aabb;
        }

        public List<Pair<Supplier<MobEffectInstance>, Float>> getEffects() {
            return effects;
        }
    }
}
