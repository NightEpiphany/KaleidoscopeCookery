package com.github.ysbbbbbb.kaleidoscopecookery.compat.trinkets.aglets;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.init.tag.TagMod;
import com.github.ysbbbbbb.kaleidoscopecookery.loot.AdvanceBlockMatchTool;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;

public class StrawHatTrinketsLootCondition implements LootItemCondition {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "straw_hat_trinkets_loot_condition");

    private final ItemPredicate predicate;

    public static final MapCodec<StrawHatTrinketsLootCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ItemPredicate.CODEC.fieldOf("predicate").forGetter(tool -> tool.predicate)
    ).apply(instance, StrawHatTrinketsLootCondition::new));

    public StrawHatTrinketsLootCondition(ItemPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public @NotNull Set<LootContextParam<?>> getReferencedContextParams() {
        return ImmutableSet.of(LootContextParams.LAST_DAMAGE_PLAYER);
    }

    @Override
    public @NotNull LootItemConditionType getType() {
        return new LootItemConditionType(StrawHatTrinketsLootCondition.CODEC);
    }

    @Override
    public boolean test(LootContext lootContext) {
        if (lootContext.hasParam(LootContextParams.THIS_ENTITY)) {
            Entity entity = lootContext.getParam(LootContextParams.THIS_ENTITY);
            if (entity instanceof LivingEntity livingEntity) {
                Optional<TrinketComponent> optionalComponent = TrinketsApi.getTrinketComponent(livingEntity);
                if (optionalComponent.isPresent()) {
                    TrinketComponent component = optionalComponent.get();
                    return component.isEquipped(itemStack -> itemStack.is(TagMod.STRAW_HAT));
                }
            }
        }
        return false;
    }

    public static Builder toolMatches(ItemPredicate builder) {
        return () -> new StrawHatTrinketsLootCondition(builder);
    }
}
