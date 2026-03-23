package com.github.ysbbbbbb.kaleidoscopecookery.blockentity.decoration;

import com.github.ysbbbbbb.kaleidoscopecookery.api.blockentity.ServerThreadSafe;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.BaseBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModBlocks;
import net.fabricmc.fabric.api.transfer.v1.item.ContainerStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ItemStackWithSlot;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.NonNull;

public class FruitBasketBlockEntity extends BaseBlockEntity {
    public static final String ITEMS = "BasketItems";
    private final SimpleContainer items = new SimpleContainer(8);

    public FruitBasketBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlocks.FRUIT_BASKET_BE, pPos, pBlockState);
    }

    @SuppressWarnings("all")
    public void putOn(ItemStack stack) {
        if (!stack.getItem().canFitInsideContainerItems()) {
            return;
        }
        try (Transaction tx = Transaction.openOuter()) {
            ContainerStorage storage = ContainerStorage.of(this.items, null);
            long inserted = storage.insert(ItemVariant.of(stack), stack.getCount(), tx);
            if (inserted > 0) {
                tx.commit();
                stack.shrink((int) inserted);
                if (this.level != null) {
                    this.level.playSound(null, this.worldPosition, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS);
                }
                this.refresh();
            }
        }
    }

    @SuppressWarnings("all")
    public void takeOut(Player player) {
        for (int i = 0; i < items.getContainerSize(); i++) {
            ItemStack stack = items.getItem(i);
            if (stack.isEmpty()) {
                continue;
            }
            try (Transaction tx = Transaction.openOuter()) {
                ContainerStorage storage = ContainerStorage.of(this.items, null);
                ItemVariant itemVariant = ItemVariant.of(stack);
                long extracted = storage.extract(itemVariant, stack.getCount(), tx);
                if (extracted > 0) {
                    tx.commit();
                    player.getInventory().placeItemBackInInventory(itemVariant.toStack((int) extracted));
                    if (this.level != null) {
                        this.level.playSound(null, this.worldPosition, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS);
                    }
                    this.refresh();
                }
                return;
            }
        }
    }

    @ServerThreadSafe
    @Override
    protected void saveAdditional(@NonNull ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);
        ValueOutput.TypedOutputList<ItemStackWithSlot> typedOutputList = valueOutput.list(ITEMS, ItemStackWithSlot.CODEC);

        for (int i = 0; i < this.items.items.size(); i++) {
            ItemStack itemStack = this.items.items.get(i);
            if (!itemStack.isEmpty()) {
                typedOutputList.add(new ItemStackWithSlot(i, itemStack));
            }
        }
    }

    @ServerThreadSafe
    @Override
    protected void loadAdditional(@NonNull ValueInput valueInput) {
        super.loadAdditional(valueInput);

        for (ItemStackWithSlot itemStackWithSlot : valueInput.listOrEmpty(ITEMS, ItemStackWithSlot.CODEC)) {
            if (itemStackWithSlot.isValidInContainer(this.items.items.size())) {
                this.items.items.set(itemStackWithSlot.slot(), itemStackWithSlot.stack());
            }
        }
    }

    public NonNullList<ItemStack> getItems() {
        return items.items;
    }

    public void setItems(NonNullList<ItemStack> items) {
        this.items.clearContent();
        int maxSize = Math.min(items.size(), this.items.getContainerSize());
        for (int i = 0; i < maxSize; i++) {
            this.items.setItem(i, items.get(i));
        }
        this.refresh();
    }
}
