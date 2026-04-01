package com.github.ysbbbbbb.kaleidoscopecookery.compat.create.automation.arm.point;

import com.github.ysbbbbbb.kaleidoscopecookery.api.blockentity.IStockpot;
import com.github.ysbbbbbb.kaleidoscopecookery.api.recipe.soupbase.ISoupBase;
import com.github.ysbbbbbb.kaleidoscopecookery.block.kitchen.StockpotBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen.StockpotBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.create.automation.util.AutomationArmPlayer;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.create.automation.util.ItemHandlerUtils;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.create.automation.util.StackPredicate;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.StockpotRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.soupbase.SoupBaseManager;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopecookery.util.ItemUtils;
import com.zurrtum.create.content.kinetics.mechanicalArm.AllArmInteractionPointTypes;
import com.zurrtum.create.content.kinetics.mechanicalArm.ArmBlockEntity;
import com.zurrtum.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class StockPotPoint extends AllArmInteractionPointTypes.TopFaceArmInteractionPoint{
    public StockPotPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
        super(type, level, pos, state);
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
        if (blockEntity instanceof StockpotBlockEntity pot) {
            RecipeHolder<StockpotRecipe> holder = pot.getAutomationRecipe(serverLevel);
            if (holder == null) return stack;
            if (pot.hasLid()) return stack;
            StockpotRecipe recipe = holder.value();
            ISoupBase iSoupBase = SoupBaseManager.getSoupBase(recipe.soupBase());
            if (iSoupBase == null) {
                return stack;
            }
            FakePlayer fakePlayer = AutomationArmPlayer.stockpot(serverLevel);
            switch (pot.getStatus()) {
                case IStockpot.PUT_SOUP_BASE:
                    if (iSoupBase.isSoupBase(stack)) {
                        ItemStack remainder = stack.copy();
                        ItemStack toInsert = remainder.split(1);
                        if(!simulate){
                            pot.addSoupBase(level,fakePlayer,toInsert);
                            AutomationArmPlayer.clear(fakePlayer);
                        }
                        remainder = new ItemStack(ItemUtils.getContainerItem(stack));
                        return remainder;
                    }
                    break;
                case IStockpot.PUT_INGREDIENT: {
                    List<StackPredicate> required = recipe.ingredients().stream().filter(i -> !i.isEmpty()).map(StackPredicate::new).toList();
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
                        if (stack.is(ModItems.STOCKPOT_LID)) {
                            ItemStack remainder = stack.copy();
                            ItemStack toInsert = remainder.split(1);
                            if (!simulate) {
                                pot.setLidItem(toInsert);
                                pot.setChanged();
                                pot.refresh();
                                level.setBlockAndUpdate(pot.getBlockPos(), pot.getBlockState().setValue(StockpotBlock.HAS_LID, true));
                            }
                            return remainder;
                        }
                    }
                    break;
                }
                case IStockpot.COOKING:
                    break;
                case IStockpot.FINISHED: {
                    if (stack.isEmpty()) {
                        return stack;
                    } else if (recipe.carrier().test(stack)) {
                        if (!simulate) {
                            ItemStack carrierStack = stack.copyWithCount(1);
                            if (!pot.takeOutProduct(level, fakePlayer, carrierStack)) {
                                AutomationArmPlayer.clear(fakePlayer);
                                return stack;
                            }
                            ItemStack result = fakePlayer.getMainHandItem().copy();
                            AutomationArmPlayer.clear(fakePlayer);
                            return result;
                        }
                        return stack.copyWithCount(stack.getCount() - 1);
                    }
                    break;
                }
            }
        }

        return stack;
    }

    @Override
    public ItemStack extract(ArmBlockEntity armBlockEntity, int slot, int amount, boolean simulate) {
        BlockEntity blockEntity = this.level.getBlockEntity(this.pos);
        if(blockEntity instanceof StockpotBlockEntity pot){
            if(pot.getStatus() == 3 && pot.hasLid()){
                ItemStack lid = pot.getLidItem().isEmpty() ? ModItems.STOCKPOT_LID.getDefaultInstance() : pot.getLidItem().copy();
                if(!simulate){
                    pot.setLidItem(ItemStack.EMPTY);
                    pot.setChanged();
                    level.setBlockAndUpdate(pot.getBlockPos(), pot.getBlockState().setValue(StockpotBlock.HAS_LID, false));
                }
                return lid;
            }
        }
        return ItemStack.EMPTY;
    }
}
