package com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen;

import com.github.ysbbbbbb.kaleidoscopecookery.block.kitchen.OilPotBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.BaseBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopecookery.inventory.itemhandler.OilPotHandler;
import com.github.ysbbbbbb.kaleidoscopecookery.util.neo.IItemHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.github.ysbbbbbb.kaleidoscopecookery.block.kitchen.OilPotBlock.HAS_OIL;

public class OilPotBlockEntity extends BaseBlockEntity {
    public static final int MAX_OIL_COUNT = 256;
    private static final String OIL_COUNT = "OilCount";
    private final OilPotHandler invHandler = new OilPotHandler(this);
    private int oilCount = 0;

    public OilPotBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.OIL_POT_BE, pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        tag.putInt(OIL_COUNT, oilCount);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        if (tag.contains(OIL_COUNT)) {
            this.oilCount = tag.getInt(OIL_COUNT);
        }
    }

    public int getOilCount() {
        return oilCount;
    }

    public void setOilCount(int oilCount) {
        this.oilCount = oilCount;
        this.refresh();

        if (this.level == null) {
            return;
        }

        BlockState state = this.getBlockState();
        boolean hasOil = state.getValue(HAS_OIL);
        if (!hasOil && oilCount > 0) {
            level.setBlock(this.worldPosition, state.setValue(HAS_OIL, true), Block.UPDATE_ALL);
            return;
        }

        if (hasOil && oilCount <= 0) {
            level.setBlock(this.worldPosition, state.setValue(HAS_OIL, false), Block.UPDATE_ALL);
        }
    }

    @Deprecated
    @Nullable
    public IItemHandler createHandler() {
        BlockState state = this.getBlockState();
        if (state.getBlock() instanceof OilPotBlock) {
            return this.invHandler;
        }
        return null;
    }
}
