package com.github.ysbbbbbb.kaleidoscopecookery.item;

import com.github.ysbbbbbb.kaleidoscopecookery.api.item.ICustomEatEffect;
import com.github.ysbbbbbb.kaleidoscopecookery.config.ClientConfig;
import com.github.ysbbbbbb.kaleidoscopecookery.item.quality.Quality;
import com.github.ysbbbbbb.kaleidoscopecookery.item.quality.QualityUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class FoodWithEffectsItem extends Item implements ICustomEatEffect {
    private final BiFunction<Quality, FoodProperties, FoodProperties> foodPropertiesCache = Util.memoize(
            (quality, raw) -> QualityUtils.modifyFoodProperties(raw, quality)
    );
    private final BiFunction<Quality, Consumable, Consumable> foodConsumableCache = Util.memoize(
            (quality, raw) -> QualityUtils.modifyFoodConsumables(raw, quality)
    );

    public FoodWithEffectsItem(Properties p, FoodProperties properties) {
        this(p, properties, Consumable.builder().build());
    }

    public FoodWithEffectsItem(Properties p, FoodProperties properties, Consumable consumable, Item craftingItem) {
        super(p.food(properties, consumable).craftRemainder(craftingItem));
    }

    public FoodWithEffectsItem(Properties p, FoodProperties properties, Consumable consumable) {
        super(p.food(properties, consumable));
    }

    @Override
    public @NonNull InteractionResult use(@NonNull Level level, @NonNull Player player, @NonNull InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        FoodProperties foodProperties = itemStack.get(DataComponents.FOOD);
        if (foodProperties != null) {
            if (player.canEat(foodProperties.canAlwaysEat())) {
                itemStack.set(DataComponents.FOOD, modifyFoodProperties(itemStack));
                itemStack.set(DataComponents.CONSUMABLE, modifyConsumables(itemStack));
                player.startUsingItem(interactionHand);
                return InteractionResult.CONSUME;
            } else {
                return InteractionResult.FAIL;
            }
        } else {
            return InteractionResult.PASS;
        }
    }

    @SuppressWarnings("all")
    @Override
    public void appendHoverText(ItemStack stack, @NonNull TooltipContext tooltip, @NonNull TooltipDisplay tooltipDisplay, @NonNull Consumer<Component> consumer, @NonNull TooltipFlag tooltipFlag) {
        Identifier id = BuiltInRegistries.ITEM.getKey(stack.getItem());
        String key = "tooltip.%s.%s.maxim".formatted(id.getNamespace(), id.getPath());
        MutableComponent full = Component.translatable(key).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC);
        // 先拿到纯文本，再按 \n 切
        String text = full.getString();
        for (String line : text.split("\n")) {
            if (!line.isEmpty()) {
                consumer.accept(Component.literal(line).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
            } else {
                consumer.accept(CommonComponents.EMPTY);
            }
        }

        Consumable consumable = modifyConsumables(stack);
        List<MobEffectInstance> effects = QualityUtils.getStatusEffects(consumable);
        boolean showEffect = !effects.isEmpty()
                && ClientConfig.SHOW_FOOD_EFFECT_TOOLTIPS.get();

        // 品质
        if (QualityUtils.hasQuality(stack)) {
            Quality quality = QualityUtils.getQuality(stack);
            consumer.accept(quality.getTooltip());
            if (showEffect) {
                consumer.accept(CommonComponents.space());
                PotionContents.addPotionTooltip(effects, consumer, 1.0F, tooltip.tickRate());
            }
        } else if (showEffect) {
            consumer.accept(CommonComponents.space());
            PotionContents.addPotionTooltip(effects, consumer, 1.0F, tooltip.tickRate());
        }
    }

    @Override
    public @Nullable FoodProperties modifyFoodProperties(ItemStack stack) {
        FoodProperties raw = stack.get(DataComponents.FOOD);
        if (!QualityUtils.hasQuality(stack) || raw == null) {
            return raw;
        }
        // 如果有品质，那么依据品质
        Quality quality = QualityUtils.getQuality(stack);
        return this.foodPropertiesCache.apply(quality, raw);
    }

    @Override
    public Consumable modifyConsumables(ItemStack stack) {
        Consumable raw = stack.get(DataComponents.CONSUMABLE);
        if (!QualityUtils.hasQuality(stack) || raw == null) {
            return raw;
        }
        // 如果有品质，那么依据品质
        Quality quality = QualityUtils.getQuality(stack);
        return this.foodConsumableCache.apply(quality, raw);
    }
}
