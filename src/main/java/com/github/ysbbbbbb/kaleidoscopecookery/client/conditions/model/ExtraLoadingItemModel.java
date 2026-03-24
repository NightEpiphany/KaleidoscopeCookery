package com.github.ysbbbbbb.kaleidoscopecookery.client.conditions.model;

import com.github.ysbbbbbb.kaleidoscopecookery.client.conditions.prop.ExtraModelLoadingProperty;
import com.github.ysbbbbbb.kaleidoscopecookery.client.conditions.registry.ExtraModelLoadingProperties;
import com.mojang.math.Transformation;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.*;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4fc;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.*;

@Environment(EnvType.CLIENT)
public class ExtraLoadingItemModel implements ItemModel {
    private final ExtraModelLoadingProperty property;
    private final Map<String, ItemModel> models;
    private final ItemModel fallback;

    public ExtraLoadingItemModel(ExtraModelLoadingProperty property, Map<String, ItemModel> models, ItemModel fallback) {
        this.property = property;
        this.models = models;
        this.fallback = fallback;
    }

    @Override
    public void update(@NonNull ItemStackRenderState output, @NonNull ItemStack item, @NonNull ItemModelResolver resolver, @NonNull ItemDisplayContext displayContext, @Nullable ClientLevel level, @Nullable ItemOwner owner, int seed) {
        output.appendModelIdentityElement(this);
        this.models.getOrDefault(this.property.get(item, level, owner == null ? null : owner.asLivingEntity(), seed, displayContext), this.fallback)
                .update(output, item, resolver, displayContext, level, owner, seed);
    }

    @Environment(EnvType.CLIENT)
    public record Entry(String name, ItemModel.Unbaked model) {
        public static final Codec<ExtraLoadingItemModel.Entry> CODEC = RecordCodecBuilder.create(
                i -> i.group(
                                Codec.STRING.fieldOf("name").forGetter(ExtraLoadingItemModel.Entry::name),
                                ItemModels.CODEC.fieldOf("model").forGetter(ExtraLoadingItemModel.Entry::model)
                        )
                        .apply(i, ExtraLoadingItemModel.Entry::new)
        );
    }

    @Environment(EnvType.CLIENT)
    public record Unbaked(
            Optional<Transformation> transformation,
            ExtraModelLoadingProperty property,
            List<ExtraLoadingItemModel.Entry> entries,
            Optional<ItemModel.Unbaked> fallback
    ) implements ItemModel.Unbaked {
        public static final MapCodec<ExtraLoadingItemModel.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(
                i -> i.group(
                                Transformation.EXTENDED_CODEC.optionalFieldOf("transformation").forGetter(ExtraLoadingItemModel.Unbaked::transformation),
                                ExtraModelLoadingProperties.MAP_CODEC.forGetter(ExtraLoadingItemModel.Unbaked::property),
                                ExtraLoadingItemModel.Entry.CODEC.listOf().fieldOf("entries").forGetter(ExtraLoadingItemModel.Unbaked::entries),
                                ItemModels.CODEC.optionalFieldOf("fallback").forGetter(ExtraLoadingItemModel.Unbaked::fallback)
                        )
                        .apply(i, ExtraLoadingItemModel.Unbaked::new)
        );


        @Override
        public @NonNull MapCodec<? extends ItemModel.Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public @NonNull ItemModel bake(@NonNull BakingContext context, @NonNull Matrix4fc transformation) {
            Matrix4fc childTransform = Transformation.compose(transformation, this.transformation);
            Map<String, ItemModel> modelMap = new HashMap<>();
            List<ExtraLoadingItemModel.Entry> mutableEntries = new ArrayList<>(this.entries);

            for (Entry entry : mutableEntries) {
                modelMap.put(entry.name, entry.model.bake(context, childTransform));
            }
            ItemModel bakedFallback = this.fallback.map(m -> m.bake(context, childTransform)).orElseGet(() -> context.missingItemModel(childTransform));
            return new ExtraLoadingItemModel(this.property, modelMap, bakedFallback);
        }

        @Override
        public void resolveDependencies(@NonNull Resolver resolver) {
            this.fallback.ifPresent(m -> m.resolveDependencies(resolver));
            this.entries.forEach(entry -> entry.model.resolveDependencies(resolver));
        }
    }
}
