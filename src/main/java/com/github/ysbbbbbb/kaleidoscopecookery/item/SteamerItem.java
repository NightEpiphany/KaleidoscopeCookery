package com.github.ysbbbbbb.kaleidoscopecookery.item;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.block.kitchen.SteamerBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen.SteamerBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SteamerItem extends BlockItem {
    public static final ResourceLocation HAS_ITEMS = ResourceLocation.fromNamespaceAndPath(KaleidoscopeCookery.MOD_ID, "has_items");
    private static final int NONE = 0;
    private static final int HAS = 1;

    public SteamerItem() {
        super(ModBlocks.STEAMER, new Properties());
    }

    @Override
    protected boolean placeBlock(BlockPlaceContext context, BlockState state) {
        Level level = context.getLevel();
        Direction face = context.getClickedFace();
        BlockPos clickedPos = context.getClickedPos();
        // 点击顶部才能放置
        if (face == Direction.DOWN) {
            return false;
        }
        BlockEntity blockEntity = level.getBlockEntity(clickedPos);
        ItemStack stack = context.getItemInHand();
        if (stack.is(this) && stack.getCount() == 1) {
            if (blockEntity instanceof SteamerBlockEntity steamer)
                steamer.mergeItem(stack, context.getLevel());
            else {
                CompoundTag data = stack.getOrDefault(DataComponents.BLOCK_ENTITY_DATA, CustomData.EMPTY).copyTag();
                NonNullList<ItemStack> items = NonNullList.withSize(8, ItemStack.EMPTY);
                ContainerHelper.loadAllItems(data, items, level.registryAccess());
                if (!items.get(4).isEmpty()) { return context.getLevel().setBlock(context.getClickedPos(), state.setValue(SteamerBlock.HALF, false), 11); }
            }
        }
        return super.placeBlock(context, state);
    }

    @Override
    public int getDefaultMaxStackSize() {
        if (this.getDefaultInstance().has(DataComponents.BLOCK_ENTITY_DATA)) {
            return 1;
        }
        return super.getDefaultMaxStackSize();
    }

    @Environment(EnvType.CLIENT)
    public static float getTexture(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
        CompoundTag data = stack.getOrDefault(DataComponents.BLOCK_ENTITY_DATA, CustomData.EMPTY).copyTag();
        if (stack.has(DataComponents.BLOCK_ENTITY_DATA)) {
            NonNullList<ItemStack> items = NonNullList.withSize(8, ItemStack.EMPTY);
            if (level != null) {
                ContainerHelper.loadAllItems(data, items, level.registryAccess());
                if (!items.getFirst().isEmpty() || !items.get(4).isEmpty())
                    return HAS;
            }
        }
        return NONE;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.kaleidoscope_cookery.steamer").withStyle(ChatFormatting.GRAY));
    }
}
