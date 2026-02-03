package com.github.ysbbbbbb.kaleidoscopecookery.item;

import com.github.ysbbbbbb.kaleidoscopecookery.util.PortHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

public class KitchenKnifeItem extends Item {

    public KitchenKnifeItem(Properties p, ToolMaterial material, float attackDamageBonus, float attackSpeedBonus) {
        super(p.stacksTo(1).sword(material, attackDamageBonus, attackSpeedBonus));
    }



    @Override
    public boolean canDestroyBlock(@NonNull ItemStack itemStack, @NonNull BlockState blockState, @NonNull Level level, @NonNull BlockPos blockPos, @NonNull LivingEntity livingEntity) {
        return super.canDestroyBlock(itemStack, blockState, level, blockPos, livingEntity);
    }

    @Override
    public void hurtEnemy(@NonNull ItemStack stack, @NonNull LivingEntity target, @NonNull LivingEntity attacker) {


    }

    @Override
    public void postHurtEnemy(ItemStack stack, @NonNull LivingEntity target, @NonNull LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
    }

    @Override
    public void appendHoverText(@NonNull ItemStack stack, @NonNull TooltipContext tooltip, @NonNull TooltipDisplay tooltipDisplay, @NonNull Consumer<Component> consumer, @NonNull TooltipFlag tooltipFlag) {
        consumer.accept(Component.translatable("tooltip.kaleidoscope_cookery.kitchen_knife").withStyle(ChatFormatting.GRAY));
    }
}
