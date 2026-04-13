package com.github.ysbbbbbb.kaleidoscopecookery.util.fluids;

import com.github.ysbbbbbb.kaleidoscopecookery.util.ItemUtils;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

@SuppressWarnings("all")
public class FluidUtils {
    /**
     * 类似于 forge 提供的 FluidUtil 里的方法，但不限于玩家实体
     * <p>
     * 该方法尝试将流体从物品容器转移到流体容器里，成功返回 true，失败返回 false
     *
     * @param user    任意实体，通常是玩家，但也可以是其他生物
     * @param bucket  物品形态的容器，一般是桶，但也可以是其他模组的容器
     * @param handler 流体容器的存储接口，一般是方块实体的流体槽
     * @param amount  要转移的流体量，单位为毫桶（mB）
     * @return 如果成功转移了流体，返回 true；如果没有转移任何流体，返回 false
     */
    public static boolean emptyItem(LivingEntity user, ItemStack bucket, Storage<FluidVariant> handler, int amount) {
        if (bucket.isEmpty() || handler == null) {
            return false;
        }
        ItemStack copy = bucket.copyWithCount(1);
        ContainerItemContext context = ContainerItemContext.withConstant(copy);
        Storage<FluidVariant> itemStorage = context.find(FluidStorage.ITEM);
        if (itemStorage == null) {
            return false;
        }
        long maxTransfer = toTransferAmount(amount);
        if (maxTransfer <= 0) {
            return false;
        }
        FluidVariant resource = getFirstResource(itemStorage);
        if (resource.isBlank()) {
            return false;
        }

        try (Transaction transaction = Transaction.openOuter()) {
            long available = getFirstAmount(itemStorage);
            long moveAmount = Math.min(maxTransfer, available);
            if (moveAmount <= 0) {
                return false;
            }
            long inserted = handler.insert(resource, moveAmount, transaction);
            if (inserted <= 0) {
                return false;
            }
            long extracted = itemStorage.extract(resource, inserted, transaction);
            if (extracted != inserted) {
                return false;
            }
            transaction.commit();
        }

        ItemVariant resultVariant = context.getItemVariant();
        ItemStack result = resultVariant.toStack((int) Math.min(Integer.MAX_VALUE, context.getAmount()));
        if (!(user instanceof Player player) || !player.isCreative()) {
            bucket.shrink(1);
        }
        ItemUtils.getItemToLivingEntity(user, onConsumed(result));
        SoundEvent sound = FluidVariantAttributes.getEmptySound(resource);
        if (sound != null) {
            user.playSound(sound);
        }
        return true;
    }

    /**
     * 消耗流体后的 ItemStack
     *
     * @param stack 消耗前的 ItemStack
     * @return 消耗后的 ItemStack
     */
    public static ItemStack onConsumed(ItemStack stack) {
        if (isFluidContainer(stack) && !stack.is(Items.BUCKET)) {
            return Items.BUCKET.getDefaultInstance();
        }
        return stack;
    }

    /**
     * 类似于 forge 提供的 FluidUtil 里的方法，但不限于玩家实体
     * <p>
     * 该方法尝试将流体从流体容器转移到物品容器里，成功返回 true，失败返回 false
     *
     * @param user    任意实体，通常是玩家，但也可以是其他生物
     * @param bucket  物品形态的容器，一般是桶，但也可以是其他模组的容器
     * @param handler 流体容器的存储接口，一般是方块实体的流体槽
     * @param amount  要转移的流体量，单位为毫桶（mB）
     * @return 如果成功转移了流体，返回 true；如果没有转移任何流体，返回 false
     */
    public static boolean fillItem(LivingEntity user, ItemStack bucket, Storage<FluidVariant> handler, int amount) {
        if (bucket.isEmpty() || handler == null) {
            return false;
        }
        ItemStack copy = bucket.copyWithCount(1);
        ContainerItemContext context = ContainerItemContext.withConstant(copy);
        Storage<FluidVariant> itemStorage = context.find(FluidStorage.ITEM);
        if (itemStorage == null) {
            return false;
        }
        long maxTransfer = toTransferAmount(amount);
        if (maxTransfer <= 0) {
            return false;
        }
        FluidVariant resource = getFirstResource(handler);
        if (resource.isBlank()) {
            return false;
        }
        long available = getFirstAmount(handler);
        long moveAmount = Math.min(maxTransfer, available);
        if (moveAmount <= 0) {
            return false;
        }

        try (Transaction transaction = Transaction.openOuter()) {
            long inserted = itemStorage.insert(resource, moveAmount, transaction);
            if (inserted <= 0) {
                return false;
            }
            long extracted = handler.extract(resource, inserted, transaction);
            if (extracted != inserted) {
                return false;
            }
            transaction.commit();
        }

        if (!(user instanceof Player player) || !player.isCreative()) {
            bucket.shrink(1);
        }
        ItemUtils.getItemToLivingEntity(user, resource.getFluid().getBucket().getDefaultInstance());
        SoundEvent sound = FluidVariantAttributes.getFillSound(resource);
        if (sound != null) {
            user.playSound(sound);
        }
        return true;
    }

    public static boolean isFluidContainer(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        ContainerItemContext context = ContainerItemContext.withConstant(stack);
        return context.find(FluidStorage.ITEM) != null;
    }

    private static long toTransferAmount(int milliBuckets) {
        if (milliBuckets <= 0) {
            return 0;
        }
        return (long) milliBuckets * FluidConstants.BUCKET / (long) CustomFluidTank.MB_PER_BUCKET;
    }

    private static FluidVariant getFirstResource(Storage<FluidVariant> storage) {
        for (StorageView<FluidVariant> view : storage) {
            if (!view.isResourceBlank() && view.getAmount() > 0) {
                return view.getResource();
            }
        }
        return FluidVariant.blank();
    }

    private static long getFirstAmount(Storage<FluidVariant> storage) {
        for (StorageView<FluidVariant> view : storage) {
            if (!view.isResourceBlank() && view.getAmount() > 0) {
                return view.getAmount();
            }
        }
        return 0;
    }
}
