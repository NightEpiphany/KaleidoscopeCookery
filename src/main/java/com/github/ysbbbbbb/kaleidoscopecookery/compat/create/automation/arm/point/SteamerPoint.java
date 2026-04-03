package com.github.ysbbbbbb.kaleidoscopecookery.compat.create.automation.arm.point;

import com.github.ysbbbbbb.kaleidoscopecookery.block.kitchen.SteamerBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen.SteamerBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.SteamerRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModRecipes;
import com.zurrtum.create.content.kinetics.mechanicalArm.ArmBlockEntity;
import com.zurrtum.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import com.zurrtum.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;

import java.util.Optional;

public class SteamerPoint extends ArmInteractionPoint {
    public static final RecipeManager.CachedCheck<SingleRecipeInput, SteamerRecipe> quickCheck = RecipeManager.createCheck(ModRecipes.STEAMER_RECIPE);

    public SteamerPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
        super(type, level, pos, state);
    }

    @Override
    public int getSlotCount(@NonNull ArmBlockEntity armBlockEntity) {
        return 8;
    }

    @Override
    public @NonNull ItemStack insert(@NonNull ArmBlockEntity armBlockEntity, @NonNull ItemStack stack, boolean simulate) {
        BlockEntity blockEntity = this.level.getBlockEntity(this.pos);
        if(blockEntity instanceof SteamerBlockEntity steamer && level instanceof ServerLevel serverLevel) {
            Optional<RecipeHolder<SteamerRecipe>> optional = getSteamerRecipe(serverLevel, stack);
            if (optional.isEmpty()) return stack;
            SteamerRecipe recipe = optional.get().value();
            NonNullList<ItemStack> items = steamer.getItems();
            ItemStack remainder = stack.copy();
            ItemStack toInsert = remainder.split(getEmptySlots(items));
            if (!simulate) {
                for (int i = 0; i < (steamer.getBlockState().getValue(SteamerBlock.HALF) ? 4 : 8); i++) {
                    if (items.get(i).isEmpty()) {
                        items.set(i,toInsert.split(1));
                        steamer.getCookingTime()[i] = recipe.getCookTick();
                        steamer.getCookingProgress()[i] = 0;
                        steamer.setChanged();
                        steamer.refresh();
                    }
                }
            }
            return remainder;
        }

        return stack;
    }

    @Override
    public @NonNull ItemStack extract(@NonNull ArmBlockEntity armBlockEntity, int slot, int amount, boolean simulate) {
        BlockEntity blockEntity = this.level.getBlockEntity(this.pos);
        if(blockEntity instanceof SteamerBlockEntity steamer && level instanceof ServerLevel serverLevel) {
            NonNullList<ItemStack> items = steamer.getItems();
            ItemStack result = ItemStack.EMPTY;
            for (int i = 0; i < (steamer.getBlockState().getValue(SteamerBlock.HALF) ? 4 : 8); i++) {
                ItemStack stack = items.get(i);
                Optional<RecipeHolder<SteamerRecipe>> optional = getSteamerRecipe(serverLevel, stack);
                if (optional.isEmpty())
                    if (result.isEmpty()) {
                        result = stack.copy();
                        if (!simulate) {
                            items.set(i,ItemStack.EMPTY);
                        }
                    }
                    else if (ItemStack.isSameItemSameComponents(stack, result)) {
                        result = result.copyWithCount(result.getCount() + stack.getCount());
                        if (!simulate) {
                            items.set(i,ItemStack.EMPTY);
                        }
                    }
            }

            return result;
        }
        return ItemStack.EMPTY;
    }

    private Optional<RecipeHolder<SteamerRecipe>> getSteamerRecipe(ServerLevel level, ItemStack stack) {
        return quickCheck.getRecipeFor(new SingleRecipeInput(stack), level);
    }

    private int getEmptySlots(NonNullList<ItemStack> items) {
        int count = 0;
        for (var stack : items)
            if (stack.isEmpty())
                count++;

        return count;
    }
}
