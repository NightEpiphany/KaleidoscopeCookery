package com.github.ysbbbbbb.kaleidoscopecookery.item;

import com.github.ysbbbbbb.kaleidoscopecookery.block.food.FoodBiteBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.config.ClientConfig;
import com.github.ysbbbbbb.kaleidoscopecookery.util.PortHelper;
import com.google.common.collect.Lists;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class BowlFoodBlockItem extends BlockItem {
    private final List<MobEffectInstance> effectInstances = Lists.newArrayList();
    private final Optional<ItemLike> usingConvertsTo;

    public BowlFoodBlockItem(Block block, FoodProperties properties, Consumable consumable, @Nullable ItemLike usingConvertsTo, String name) {
        super(block, new Item.Properties().stacksTo(16).useBlockDescriptionPrefix()
                .food(properties, consumable).setId(PortHelper.createItemId(name))
        );
        this.usingConvertsTo = Optional.ofNullable(usingConvertsTo);
        consumable.onConsumeEffects().forEach(effect -> {
            if (effect instanceof ApplyStatusEffectsConsumeEffect(List<MobEffectInstance> effects, float probability)) {
                effectInstances.addAll(effects);
            }
        });
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NonNull ItemStack stack, @NonNull Level level, @NonNull LivingEntity entity) {
        if (level instanceof ServerLevel serverLevel && this.getBlock() instanceof FoodBiteBlock foodBiteBlock) {
            LootParams.Builder builder = (new LootParams.Builder(serverLevel))
                    .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(entity.blockPosition()))
                    .withParameter(LootContextParams.TOOL, ItemStack.EMPTY)
                    .withOptionalParameter(LootContextParams.THIS_ENTITY, entity)
                    .withOptionalParameter(LootContextParams.BLOCK_ENTITY, null);
            BlockState state = foodBiteBlock.defaultBlockState().setValue(foodBiteBlock.getBites(), foodBiteBlock.getMaxBites());
            List<ItemStack> drops = getDrops(state, builder);
            drops.forEach(itemStack -> {
                if (itemStack.isEmpty()) {
                    return;
                }
                // 需要剔除 usingConvertsTo，因为已经给过了
                if (this.usingConvertsTo.isPresent() && ItemStack.isSameItemSameComponents(itemStack, this.usingConvertsTo.get().asItem().getDefaultInstance())) {
                    return;
                }
                if (entity instanceof Player player) {
                    player.getInventory().placeItemBackInInventory(itemStack);
                } else {
                    ItemEntity itemEntity = new ItemEntity(level, entity.getX(), entity.getY(), entity.getZ(), itemStack);
                    level.addFreshEntity(itemEntity);
                }
            });
        }
        return super.finishUsingItem(stack, level, entity);
    }

    private List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        if (state.getBlock().getLootTable().isEmpty()) {
            return Collections.emptyList();
        } else {
            ResourceKey<LootTable> resourcekey = state.getBlock().getLootTable().get();
            LootParams lootParams = params.withParameter(LootContextParams.BLOCK_STATE, state).create(LootContextParamSets.BLOCK);
            ServerLevel serverLevel = lootParams.getLevel();
            LootTable lootTable = serverLevel.getServer().reloadableRegistries().getLootTable(resourcekey);
            return lootTable.getRandomItems(lootParams);
        }
    }

    @SuppressWarnings("deprecation")
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
        if (!this.effectInstances.isEmpty() && ClientConfig.SHOW_FOOD_EFFECT_TOOLTIPS.get()) {
            consumer.accept(CommonComponents.space());
            PotionContents.addPotionTooltip(this.effectInstances, consumer, 1.0F, tooltip.tickRate());
        }
    }
}
