package com.github.ysbbbbbb.kaleidoscopecookery.item;

import com.github.ysbbbbbb.kaleidoscopecookery.api.item.ICustomEatEffect;
import com.github.ysbbbbbb.kaleidoscopecookery.config.ClientConfig;
import com.github.ysbbbbbb.kaleidoscopecookery.item.quality.Quality;
import com.github.ysbbbbbb.kaleidoscopecookery.item.quality.QualityUtils;
import com.google.common.collect.Lists;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class FoodWithEffectsItem extends Item implements ICustomEatEffect {
    private final List<MobEffectInstance> effectInstances = Lists.newArrayList();
    private final Function<Quality, List<MobEffectInstance>> effectCache = Util.memoize(
            quality -> QualityUtils.modifyEffects(this.effectInstances, quality)
    );
    private final BiFunction<Quality, FoodProperties, FoodProperties> foodPropertiesCache = Util.memoize(
            (quality, raw) -> QualityUtils.modifyFoodProperties(raw, quality)
    );

    public FoodWithEffectsItem(FoodProperties properties) {
        super(new Properties().food(properties));
        properties.effects().forEach(effect -> {
            if (effect.probability() >= 1F) {
                effectInstances.add(effect.effect());
            }
        });
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        FoodProperties foodProperties = itemStack.get(DataComponents.FOOD);
        if (foodProperties != null) {
            if (player.canEat(foodProperties.canAlwaysEat())) {
                itemStack.set(DataComponents.FOOD, modifyFoodProperties(itemStack));
                player.startUsingItem(interactionHand);
                return InteractionResultHolder.consume(itemStack);
            } else {
                return InteractionResultHolder.fail(itemStack);
            }
        } else {
            return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
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
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        ResourceLocation id = BuiltInRegistries.ITEM.getKey(stack.getItem());
        String key = "tooltip.%s.%s.maxim".formatted(id.getNamespace(), id.getPath());
        MutableComponent full = Component.translatable(key).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC);
        // 先拿到纯文本，再按 \n 切
        String text = full.getString();
        for (String line : text.split("\n")) {
            if (!line.isEmpty()) {
                tooltip.add(Component.literal(line).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
            } else {
                tooltip.add(CommonComponents.EMPTY);
            }
        }

        boolean showEffect = !this.effectInstances.isEmpty()
                             && ClientConfig.SHOW_FOOD_EFFECT_TOOLTIPS.get();

        // 品质
        if (QualityUtils.hasQuality(stack)) {
            Quality quality = QualityUtils.getQuality(stack);
            tooltip.add(quality.getTooltip());
            if (showEffect) {
                tooltip.add(CommonComponents.space());
                PotionContents.addPotionTooltip(this.effectCache.apply(quality), tooltip::add, 1.0F, context.tickRate());
            }
        } else {
            tooltip.add(CommonComponents.space());
            PotionContents.addPotionTooltip(this.effectInstances, tooltip::add, 1.0F, context.tickRate());
        }
    }
}
