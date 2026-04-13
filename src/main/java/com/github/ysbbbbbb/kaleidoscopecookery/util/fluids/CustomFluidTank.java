package com.github.ysbbbbbb.kaleidoscopecookery.util.fluids;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;

/**
 * 替代forge流体系统的流体槽，用于方块存储流体
 */
@SuppressWarnings("UnstableApiUsage")
public class CustomFluidTank extends SingleVariantStorage<FluidVariant> {
    public static final int MB_PER_BUCKET = 1000;

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

    public CompoundTag writeToNBT(CompoundTag tag) {
        tag.putLong("amount", this.amount);
        if (this.variant == null || this.variant.isBlank() || this.amount <= 0) {
            tag.putString("fluid", "");
            tag.remove("nbt");
            return tag;
        }
        ResourceLocation id = BuiltInRegistries.FLUID.getKey(this.variant.getFluid());
        tag.putString("fluid", id.toString());
        if (this.variant.getNbt() != null) {
            tag.put("nbt", this.variant.getNbt().copy());
        } else {
            tag.remove("nbt");
        }
        return tag;
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

    public void readFromNBT(CompoundTag tag) {
        long readAmount = tag.getLong("amount");
        if (readAmount <= 0) {
            this.amount = 0;
            this.variant = FluidVariant.blank();
            return;
        }

        String fluidId = tag.getString("fluid");
        if (fluidId.isEmpty()) {
            this.amount = 0;
            this.variant = FluidVariant.blank();
            return;
        }

        try {
            Fluid fluid = BuiltInRegistries.FLUID.get(new ResourceLocation(fluidId));
            CompoundTag nbt = tag.contains("nbt", Tag.TAG_COMPOUND) ? tag.getCompound("nbt") : null;
            this.variant = nbt == null ? FluidVariant.of(fluid) : FluidVariant.of(fluid, nbt);
            this.amount = Math.min(readAmount, this.capacity);
        } catch (RuntimeException e) {
            this.amount = 0;
            this.variant = FluidVariant.blank();
        }
    }
    public enum FluidAction {
        EXECUTE,
        SIMULATE;

        public boolean execute() {
            return this == EXECUTE;
        }
    }
}
