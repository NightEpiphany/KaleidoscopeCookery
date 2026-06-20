package com.github.ysbbbbbb.kaleidoscopecookery.item;

import com.github.ysbbbbbb.kaleidoscopecookery.api.item.IHasContainer;
import com.google.common.collect.Lists;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class BowlFoodOnlyItem extends FoodWithEffectsItem implements IHasContainer {

    public BowlFoodOnlyItem(Properties p, FoodProperties properties, Consumable consumable) {
        super(p.food(properties, consumable), properties, consumable);
    }

    public BowlFoodOnlyItem(Properties p, FoodProperties properties, Consumable consumable, Item craftingItem) {
        super(p.food(properties, consumable), properties, consumable, craftingItem);
    }

    public ItemStack finishUsingItemRaw(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        Consumable consumable = itemStack.get(DataComponents.CONSUMABLE);
        return consumable != null ? consumable.onConsume(level, livingEntity, itemStack) : itemStack;
    }
    @Override
    public @NotNull ItemStack finishUsingItem(@NonNull ItemStack stack, @NonNull Level level, @NonNull LivingEntity entity) {
        ItemStack itemStack = finishUsingItemRaw(stack, level, entity);
        ItemStack bowl = new ItemStack(Items.BOWL);
        if (itemStack.isEmpty()) {
            return bowl;
        }
        if (entity instanceof Player player) {
            player.getInventory().placeItemBackInInventory(bowl);
            for (MobEffectInstance effectInstance : effectInstances) {
                player.addEffect(copy(effectInstance));
            }
        } else {
            ItemEntity itemEntity = new ItemEntity(level, entity.getX(), entity.getY(), entity.getZ(), bowl);
            level.addFreshEntity(itemEntity);
        }
        return itemStack;
    }

    @Override
    public Item getContainerItem() {
        return Items.BOWL;
    }
}
