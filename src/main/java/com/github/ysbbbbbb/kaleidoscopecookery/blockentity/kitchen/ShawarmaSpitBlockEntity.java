package com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen;

import com.github.ysbbbbbb.kaleidoscopecookery.api.blockentity.IShawarmaSpit;
import com.github.ysbbbbbb.kaleidoscopecookery.api.blockentity.ServerThreadSafe;
import com.github.ysbbbbbb.kaleidoscopecookery.block.kitchen.ShawarmaSpitBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.BaseBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModParticles;
import com.github.ysbbbbbb.kaleidoscopecookery.init.tag.TagMod;
import com.github.ysbbbbbb.kaleidoscopecookery.util.BlockDrop;
import com.github.ysbbbbbb.kaleidoscopecookery.util.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.NonNull;

public class ShawarmaSpitBlockEntity extends BaseBlockEntity implements IShawarmaSpit {
    private static final int MAX_ITEMS = 8;

    public static final String COOKING_ITEM = "CookingItem";
    public static final String COOKED_ITEM = "CookedItem";
    public static final String COOK_TIME = "CookTime";

    private final RecipeManager.CachedCheck<SingleRecipeInput, CampfireCookingRecipe> quickCheck = RecipeManager.createCheck(RecipeType.CAMPFIRE_COOKING);
    public ItemStack cookingItem = ItemStack.EMPTY;
    public ItemStack cookedItem = ItemStack.EMPTY;
    public int cookTime;

    public ShawarmaSpitBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlocks.SHAWARMA_SPIT_BE, pPos, pBlockState);
    }

    @Override
    public boolean onPutCookingItem(Level level, ItemStack itemStack) {
        // 先判断能否放入物品
        if (!this.cookingItem.isEmpty() || !this.cookedItem.isEmpty()) {
            return false;
        }
        if (level instanceof ServerLevel serverLevel) {
            // 尝试通过输入的物品寻找营火配方
            SingleRecipeInput singleRecipeInput = new SingleRecipeInput(itemStack);
            return this.quickCheck.getRecipeFor(singleRecipeInput, serverLevel).map(recipe -> {
                // 如果找到了配方，则设置正在烹饪的物品和烹饪时间
                this.cookingItem = itemStack.split(MAX_ITEMS);
                this.cookedItem = recipe.value().assemble(singleRecipeInput, level.registryAccess());
                this.cookedItem.setCount(this.cookingItem.getCount());
                this.cookTime = recipe.value().cookingTime();
                this.refresh();
                level.playSound(null,
                            worldPosition.getX() + 0.5,
                            worldPosition.getY() + 0.5,
                            worldPosition.getZ() + 0.5,
                            SoundEvents.ITEM_FRAME_ADD_ITEM,
                            SoundSource.BLOCKS,
                            0.5F + level.random.nextFloat(),
                            level.random.nextFloat() * 0.7F + 0.6F);
                return true;
            }).orElse(false);
        } else {
            return false;
        }
    }

    @Override
    public boolean onTakeCookedItem(Level level, LivingEntity entity, ItemStack mainHandItem) {

        // 如果有烹饪完成的物品，则将其取出
        if (this.cookTime <= 0 && !this.cookedItem.isEmpty()) {
            if (mainHandItem.isEmpty()) {
                takeItem(level, entity);
            } else {
                giveItem(level, entity, mainHandItem, this.cookedItem.copy());
            }
            return true;
        }

        // 如果没有烹饪完成，返回原材料并重置
        if (this.cookTime > 0 && !this.cookingItem.isEmpty()) {
            if (mainHandItem.isEmpty()) {
                takeItem(level, entity);
            } else {
                giveItem(level, entity, mainHandItem, this.cookingItem.copy());
            }
            return true;
        }

        return false;
    }

    public void takeItem(Level level, LivingEntity entity) {
        if (this.cookTime <= 0 && !this.cookedItem.isEmpty()) {
            BlockDrop.popResource(level, this.getBlockPos(), 0.75, this.cookedItem.copy());
            if (this.getBlockState().getValue(ShawarmaSpitBlock.POWERED))
                entity.hurt(level.damageSources().inFire(), 1);
        }
        if (this.cookTime > 0 && !this.cookingItem.isEmpty())
            BlockDrop.popResource(level, this.getBlockPos(), 0.75, this.cookingItem.copy());

        if (level instanceof ServerLevel) {
            level.playSound(null,
                    worldPosition.getX() + 0.415,
                    worldPosition.getY() + 0.435,
                    worldPosition.getZ() + 0.425,
                    SoundEvents.ITEM_FRAME_REMOVE_ITEM,
                    SoundSource.BLOCKS,
                    0.25F + level.random.nextFloat(),
                    level.random.nextFloat() * 0.7F + 0.6F);
        }
        this.cookingItem = ItemStack.EMPTY;
        this.cookedItem = ItemStack.EMPTY;
        this.cookTime = 0;
        this.refresh();
    }

    private void giveItem(Level level, LivingEntity entity, ItemStack mainHandItem, ItemStack copy) {
        this.cookingItem = ItemStack.EMPTY;
        this.cookedItem = ItemStack.EMPTY;
        this.cookTime = 0;
        this.refresh();

        if (this.getBlockState().getValue(ShawarmaSpitBlock.POWERED) && !mainHandItem.is(TagMod.KITCHEN_KNIFE)) {
            entity.hurt(level.damageSources().inFire(), 1);
        }
        ItemUtils.getItemToLivingEntity(entity, copy);
        if (level instanceof ServerLevel) {
            level.playSound(null,
                    worldPosition.getX() + 0.5,
                    worldPosition.getY() + 0.5,
                    worldPosition.getZ() + 0.5,
                    SoundEvents.ITEM_FRAME_REMOVE_ITEM,
                    SoundSource.BLOCKS,
                    0.5F + level.random.nextFloat(),
                    level.random.nextFloat() * 0.7F + 0.6F);
        }
    }

    public void tick() {
        if (cookingItem.isEmpty()) {
            if (!cookedItem.isEmpty()) {
                this.spawnParticles();
            }
            return;
        }
        this.spawnParticles();
        if (cookTime > 0) {
            cookTime--;
        } else {
            if (level instanceof ServerLevel) {
                level.playSound(null,
                        worldPosition.getX() + 0.5,
                        worldPosition.getY() + 0.5,
                        worldPosition.getZ() + 0.5,
                        SoundEvents.FIRE_EXTINGUISH,
                        SoundSource.BLOCKS,
                        0.5F + level.random.nextFloat(),
                        level.random.nextFloat() * 0.7F + 0.6F);
            }
            this.cookingItem = ItemStack.EMPTY;
            this.refresh();
        }
    }

    private void spawnParticles() {
        if (level instanceof ServerLevel serverLevel) {
            if (level.random.nextFloat() < 0.25f) {
                serverLevel.sendParticles(ModParticles.COOKING,
                        worldPosition.getX() + 0.5,
                        worldPosition.getY() + 0.5,
                        worldPosition.getZ() + 0.5,
                        1,
                        0.25, 0.2, 0.25,
                        0.1f);
            }
            if (level.random.nextInt(20) == 0) {
                serverLevel.playSound(null,
                        worldPosition.getX() + 0.5,
                        worldPosition.getY() + 0.5,
                        worldPosition.getZ() + 0.5,
                        SoundEvents.CAMPFIRE_CRACKLE,
                        SoundSource.BLOCKS,
                        0.5F + level.random.nextFloat(),
                        level.random.nextFloat() * 0.7F + 0.6F);
            }
        }
    }

    @ServerThreadSafe
    @Override
    protected void saveAdditional(@NonNull ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);
        if (!this.cookingItem.isEmpty())
            valueOutput.store(COOKING_ITEM, ItemStack.CODEC, this.cookingItem);
        if (!this.cookedItem.isEmpty())
            valueOutput.store(COOKED_ITEM, ItemStack.CODEC, this.cookedItem);
        valueOutput.putInt(COOK_TIME, this.cookTime);
    }

    @ServerThreadSafe
    @Override
    protected void loadAdditional(@NonNull ValueInput valueInput) {
        super.loadAdditional(valueInput);
        if (valueInput.contains(COOKING_ITEM))
            this.cookingItem = valueInput.read(COOKING_ITEM, ItemStack.CODEC).orElse(ItemStack.EMPTY);
        if (valueInput.contains(COOKED_ITEM))
            this.cookedItem = valueInput.read(COOKED_ITEM, ItemStack.CODEC).orElse(ItemStack.EMPTY);
        this.cookTime = valueInput.getIntOr(COOK_TIME, 0);
    }

    @Override
    public boolean hasItem() {
        return !this.cookingItem.isEmpty() || !this.cookedItem.isEmpty();
    }
}
