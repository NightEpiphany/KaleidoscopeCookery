package com.github.ysbbbbbb.kaleidoscopecookery.compat.trinkets.aglets;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.init.tag.TagMod;
import com.github.ysbbbbbb.kaleidoscopecookery.loot.AdvanceEntityMatchTool;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;

public class StrawHatTrinketsLootCondition implements LootItemCondition {

    public static final ResourceLocation ID = new ResourceLocation(KaleidoscopeCookery.MOD_ID, "straw_hat_trinkets_loot_condition");

    private final ItemPredicate predicate;

    public StrawHatTrinketsLootCondition(ItemPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public @NotNull Set<LootContextParam<?>> getReferencedContextParams() {
        return ImmutableSet.of(LootContextParams.LAST_DAMAGE_PLAYER);
    }

    @Override
    public @NotNull LootItemConditionType getType() {
       return new LootItemConditionType(new StrawHatTrinketsLootConditionSerializer());
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

    public static class StrawHatTrinketsLootConditionSerializer implements Serializer<StrawHatTrinketsLootCondition> {

        @Override
        public void serialize(@NotNull JsonObject json, @NotNull StrawHatTrinketsLootCondition value, @NotNull JsonSerializationContext serializationContext) {
            json.add("predicate", value.predicate.serializeToJson());
        }

        @Override
        public @NotNull StrawHatTrinketsLootCondition deserialize(@NotNull JsonObject json, @NotNull JsonDeserializationContext serializationContext) {
            ItemPredicate itemPredicate = ItemPredicate.fromJson(json.get("predicate"));
            return new StrawHatTrinketsLootCondition(itemPredicate);
        }
    }
}
