package com.github.ysbbbbbb.kaleidoscopecookery.compat.create.automation.arm.point;

import com.github.ysbbbbbb.kaleidoscopecookery.api.blockentity.IPot;
import com.github.ysbbbbbb.kaleidoscopecookery.block.kitchen.PotBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen.PotBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.create.automation.util.AutomationArmPlayer;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.create.automation.util.ItemHandlerUtils;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.create.automation.util.StackPredicate;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.PotRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopecookery.init.tag.TagMod;
import com.github.ysbbbbbb.kaleidoscopecookery.item.KitchenShovelItem;
import com.github.ysbbbbbb.kaleidoscopecookery.item.OilPotItem;
import com.zurrtum.create.content.kinetics.mechanicalArm.ArmBlockEntity;
import com.zurrtum.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import com.zurrtum.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class PotPoint extends ArmInteractionPoint {
    public PotPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
        super(type, level, pos, state);
    }

    @Override
    protected Vec3 getInteractionPositionVector() {
        return Vec3.atLowerCornerOf(this.pos).add(0.5F, 0.3125F, 0.5F);
    }

    @Override
    public int getSlotCount(ArmBlockEntity armBlockEntity) {
        return 9;
    }

    @Override
    public ItemStack insert(ArmBlockEntity armBlockEntity, ItemStack stack, boolean simulate) {
        BlockEntity blockEntity = this.level.getBlockEntity(this.pos);
        if (!(this.level instanceof ServerLevel serverLevel)) {
            return stack;
        }
        if (blockEntity instanceof PotBlockEntity pot) {
            if (!pot.hasHeatSource(level)) return stack;
            RecipeHolder<PotRecipe> holder = pot.getAutomationRecipe(serverLevel);
            FakePlayer fakePlayer = AutomationArmPlayer.pot(serverLevel);
            switch (pot.getStatus()) {
                case IPot.PUT_INGREDIENT:
                    if (holder == null) {
                        return stack;
                    }
                    PotRecipe recipe = holder.value();
                    if (pot.getBlockState().getValue(PotBlock.HAS_OIL)) {
                        List<StackPredicate> required = recipe.ingredients().stream().filter(i->!i.isEmpty()).map(StackPredicate::new).toList();
                        required = ItemHandlerUtils.getRequired(required, pot.getInputs());
                        if (!required.isEmpty()) {
                            if (required.stream().anyMatch(p -> p.test(stack))) {
                                ItemStack remainder = stack.copy();
                                ItemStack toInsert = remainder.split(1);
                                if (!simulate) {
                                    pot.addIngredient(level, fakePlayer, toInsert);
                                    AutomationArmPlayer.clear(fakePlayer);
                                }
                                return remainder;
                            }
                        } else {
                            if (stack.is(TagMod.KITCHEN_SHOVEL)) {
                                ItemStack remainder = stack.copy();
                                remainder.setDamageValue(remainder.getDamageValue() + 1);
                                if(!simulate){
                                    pot.onShovelHit(level,fakePlayer, remainder);
                                    AutomationArmPlayer.clear(fakePlayer);
                                    return remainder;
                                }
                                return ItemStack.EMPTY;
                            }
                        }
                    } else {
                        if (isOilItem(stack)) {
                            if (!simulate) {
                                pot.onPlaceOil(level,fakePlayer,new ItemStack(ModItems.OIL));
                                AutomationArmPlayer.clear(fakePlayer);
                            }
                            return getPlaceOilReturn(stack);
                        }
                    }
                    break;
                case IPot.COOKING:
                    if (stack.is(TagMod.KITCHEN_SHOVEL)) {
                        if(!simulate){
                            pot.onShovelHit(level,fakePlayer,stack);
                            AutomationArmPlayer.clear(fakePlayer);
                            return stack;
                        }
                        return ItemStack.EMPTY;
                    }
                    break;
                case IPot.FINISHED, IPot.BURNT:
                    if (holder == null) {
                        return stack;
                    }
                    PotRecipe finishedRecipe = holder.value();
                    if (pot.hasCarrier()) {
                        int requiredCarrierCount = Math.max(1, pot.getResult().getCount());
                        if (finishedRecipe.carrier().test(stack) && stack.getCount() >= requiredCarrierCount) {
                            if (!simulate) {
                                ItemStack carrierStack = stack.copyWithCount(requiredCarrierCount);
                                if (!pot.takeOutProduct(level, fakePlayer, carrierStack)) {
                                    AutomationArmPlayer.clear(fakePlayer);
                                    return stack;
                                }
                                ItemStack result = fakePlayer.getMainHandItem().copy();
                                AutomationArmPlayer.clear(fakePlayer);
                                return result;
                            }
                            return stack.copyWithCount(stack.getCount() - requiredCarrierCount);
                        }
                    }
                    break;
            }
        }

        return stack;
    }

    @Override
    public ItemStack extract(ArmBlockEntity armBlockEntity, int slot, int amount, boolean simulate) {
        BlockEntity blockEntity = this.level.getBlockEntity(this.pos);
        if (!(this.level instanceof ServerLevel serverLevel)) {
            return ItemStack.EMPTY;
        }
        if (blockEntity instanceof PotBlockEntity pot) {
            if(!pot.hasHeatSource(level)) return ItemStack.EMPTY;
            RecipeHolder<PotRecipe> holder = pot.getAutomationRecipe(serverLevel);
            boolean needsCarrier = holder != null && pot.hasCarrier();
            if (!needsCarrier && (pot.getStatus() == IPot.FINISHED || pot.getStatus() == IPot.BURNT)) {
                ItemStack result = pot.getResult();
                if (!simulate) {
                    pot.reset();
                }
                return result;
            }
        }

        return ItemStack.EMPTY;
    }

    private ItemStack getPlaceOilReturn(ItemStack stack){
        ItemStack ans = ItemStack.EMPTY;
        if (stack.is(TagMod.OIL)) {
            ans = stack.copy();
            ans.split(1);
        } else if (stack.is(TagMod.KITCHEN_SHOVEL) && KitchenShovelItem.hasOil(stack)) {
            ans = stack.copy();
            KitchenShovelItem.setHasOil(ans, false);
        } else if (stack.is(ModItems.OIL_POT) && OilPotItem.hasOil(stack)) {
            ans = stack.copy();
            OilPotItem.shrinkOilCount(ans);
        }

        return ans;
    }

    private boolean isOilItem(ItemStack stack) {
        return stack.is(TagMod.OIL) || (stack.is(ModItems.KITCHEN_SHOVEL) && KitchenShovelItem.hasOil(stack)) || (stack.is(ModItems.OIL_POT) && OilPotItem.hasOil(stack));
    }
}
