package com.github.ysbbbbbb.kaleidoscopecookery.util.fluids;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.level.material.Fluid;

@SuppressWarnings("all")
/**
 * 替代forge流体系统的流体槽，用于方块存储流体
 */
public class CustomFluidTank extends SingleVariantStorage<FluidVariant> {
    public static final int MB_PER_BUCKET = 1000;
    private static final String TAG_VARIANT = "variant";
    private static final String TAG_VARIANT_LEGACY = "fluidVariant";
    private static final String TAG_AMOUNT = "amount";

    private final long capacity;
    private final Runnable onContentsChanged;

    public CustomFluidTank(long capacity, Runnable onContentsChanged) {
        this.capacity = capacity;
        this.onContentsChanged = onContentsChanged;
        this.variant = FluidVariant.blank();
        this.amount = 0;
    }

    @Override
    protected FluidVariant getBlankVariant() {
        return FluidVariant.blank();
    }

    @Override
    protected long getCapacity(FluidVariant variant) {
        return capacity;
    }

    @Override
    protected void onFinalCommit() {
        if (onContentsChanged != null) {
            onContentsChanged.run();
        }
    }

    public FluidVariant getFluidVariant() {
        return this.variant;
    }

    public Fluid getFluid() {
        return this.variant.getFluid();
    }

    public int getFluidAmountMb() {
        if (this.amount <= 0) {
            return 0;
        }
        long mb = this.amount * (long) MB_PER_BUCKET / FluidConstants.BUCKET;
        if (mb <= 0) {
            return 0;
        }
        return (int) Math.min(Integer.MAX_VALUE, mb);
    }

    public long getFluidAmountTransfer() {
        return this.amount;
    }

    public long getCapacityTransfer() {
        return this.capacity;
    }

    public CompoundTag serializeNBT(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        return writeToNBT(registries, tag);
    }

    public CompoundTag writeToNBT(HolderLookup.Provider registries, CompoundTag tag) {
        FluidVariant variantToWrite = this.amount > 0 && !this.variant.isBlank() ? this.variant : FluidVariant.blank();
        CompoundTag variantTag = encodeVariant(registries, variantToWrite);
        tag.put(TAG_VARIANT, variantTag);
        tag.put(TAG_VARIANT_LEGACY, variantTag);
        tag.putLong(TAG_AMOUNT, Math.max(0L, this.amount));
        return tag;
    }

    public void readFromNBT(HolderLookup.Provider registries, CompoundTag tag) {
        CompoundTag variantTag;
        if (tag.contains(TAG_VARIANT)) {
            variantTag = tag.getCompound(TAG_VARIANT);
        } else {
            variantTag = tag.getCompound(TAG_VARIANT_LEGACY);
        }
        FluidVariant loadedVariant = decodeVariant(registries, variantTag);
        long loadedAmount = tag.getLong(TAG_AMOUNT);
        if (loadedAmount <= 0 || loadedVariant.isBlank()) {
            this.variant = FluidVariant.blank();
            this.amount = 0;
            return;
        }
        this.variant = loadedVariant;
        this.amount = Math.min(loadedAmount, this.capacity);
    }

    public long fill(FluidVariant resource, long maxAmount, FluidAction action) {
        if (resource == null || resource.isBlank() || maxAmount <= 0) {
            return 0;
        }
        if (this.amount <= 0 || this.variant.isBlank()) {
            long filled = Math.min(this.capacity, maxAmount);
            if (filled > 0 && action.execute()) {
                this.variant = resource;
                this.amount = filled;
                if (this.onContentsChanged != null) {
                    this.onContentsChanged.run();
                }
            }
            return filled;
        }
        if (!this.variant.equals(resource)) {
            return 0;
        }
        long space = this.capacity - this.amount;
        if (space <= 0) {
            return 0;
        }
        long filled = Math.min(space, maxAmount);
        if (filled > 0 && action.execute()) {
            this.amount += filled;
            if (this.onContentsChanged != null) {
                this.onContentsChanged.run();
            }
        }
        return filled;
    }

    public long fill(FluidVariant resource, int maxAmount, FluidAction action) {
        return fill(resource, (long) maxAmount, action);
    }

    public long drain(FluidVariant resource, long maxAmount, FluidAction action) {
        if (resource == null || resource.isBlank() || maxAmount <= 0) {
            return 0;
        }
        if (this.amount <= 0 || this.variant.isBlank()) {
            return 0;
        }
        if (!this.variant.equals(resource)) {
            return 0;
        }
        return drain(maxAmount, action);
    }

    public long drain(long maxAmount, FluidAction action) {
        if (maxAmount <= 0 || this.amount <= 0 || this.variant.isBlank()) {
            return 0;
        }
        long drained = Math.min(maxAmount, this.amount);
        if (drained > 0 && action.execute()) {
            this.amount -= drained;
            if (this.amount <= 0) {
                this.amount = 0;
                this.variant = FluidVariant.blank();
            }
            if (this.onContentsChanged != null) {
                this.onContentsChanged.run();
            }
        }
        return drained;
    }

    public long drain(int maxAmount, FluidAction action) {
        return drain((long) maxAmount, action);
    }

    private static CompoundTag encodeVariant(HolderLookup.Provider registries, FluidVariant variant) {
        RegistryOps<Tag> ops = RegistryOps.create(NbtOps.INSTANCE, registries);
        Tag encoded = FluidVariant.CODEC.encodeStart(ops, variant).result().orElse(new CompoundTag());
        if (encoded instanceof CompoundTag compound) {
            return compound;
        }
        return new CompoundTag();
    }

    private static FluidVariant decodeVariant(HolderLookup.Provider registries, CompoundTag tag) {
        RegistryOps<Tag> ops = RegistryOps.create(NbtOps.INSTANCE, registries);
        return FluidVariant.CODEC.parse(ops, tag).result().orElse(FluidVariant.blank());
    }

    public enum FluidAction {
        EXECUTE,
        SIMULATE;

        public boolean execute() {
            return this == EXECUTE;
        }
    }

}
