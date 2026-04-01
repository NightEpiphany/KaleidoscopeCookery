package com.github.ysbbbbbb.kaleidoscopecookery.compat.create.automation.init;

import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen.PotBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen.StockpotBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.compat.create.automation.util.AutomationRecipeUtils;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModDataComponents;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopecookery.item.RecipeItem;
import com.zurrtum.create.content.logistics.BigItemStack;
import com.zurrtum.create.content.logistics.packagePort.PackagePortBlockEntity;
import com.zurrtum.create.content.logistics.packagerLink.LogisticallyLinkedBehaviour;
import com.zurrtum.create.content.logistics.packagerLink.LogisticsManager;
import com.zurrtum.create.content.logistics.stockTicker.StockTickerBlockEntity;
import com.zurrtum.create.infrastructure.component.PackageOrderWithCrafts;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SignText;
import net.minecraft.world.phys.BlockHitResult;

import java.util.ArrayList;
import java.util.List;

public final class InitAutomationEvents {
    private InitAutomationEvents() {
    }

    public static void init() {
        UseBlockCallback.EVENT.register(InitAutomationEvents::onUseBlock);
    }

    private static InteractionResult onUseBlock(Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!itemStack.is(ModItems.RECIPE_ITEM)) {
            return InteractionResult.PASS;
        }
        BlockEntity blockEntity = level.getBlockEntity(hitResult.getBlockPos());
        if (blockEntity == null) {
            return InteractionResult.PASS;
        }
        InteractionResult recipeSelectionResult = handleRecipeSelection(player, level, itemStack, blockEntity, hitResult);
        if (recipeSelectionResult != InteractionResult.PASS) {
            return recipeSelectionResult;
        }
        return handleRecipeAddress(player, level, itemStack, blockEntity);
    }

    private static InteractionResult handleRecipeSelection(Player player, Level level, ItemStack itemStack, BlockEntity blockEntity, BlockHitResult hitResult) {
        if (!RecipeItem.hasRecipe(itemStack)) {
            return InteractionResult.PASS;
        }
        if (!(blockEntity instanceof PotBlockEntity) && !(blockEntity instanceof StockpotBlockEntity)) {
            return InteractionResult.PASS;
        }
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }
        if (!(level instanceof ServerLevel serverLevel)) {
            return InteractionResult.PASS;
        }
        RecipeItem.RecipeRecord record = RecipeItem.getRecipe(itemStack);
        if (record == null) {
            return InteractionResult.PASS;
        }
        boolean bound = false;
        if (blockEntity instanceof PotBlockEntity pot) {
            bound = AutomationRecipeUtils.findPotRecipe(serverLevel, record)
                    .map(holder -> {
                        pot.setAutomationRecipeId(holder.id().identifier());
                        return true;
                    })
                    .orElse(false);
        } else if (blockEntity instanceof StockpotBlockEntity stockpot) {
            bound = AutomationRecipeUtils.findStockpotRecipe(serverLevel, record)
                    .map(holder -> {
                        stockpot.setAutomationRecipeId(holder.id().identifier());
                        return true;
                    })
                    .orElse(false);
        }
        if (!bound) {
            return InteractionResult.PASS;
        }
        player.getCooldowns().addCooldown(itemStack, 5);
        level.playSound(null, hitResult.getBlockPos(), SoundEvents.BOOK_PAGE_TURN, SoundSource.PLAYERS, 0.8F, 1.0F);
        return InteractionResult.SUCCESS;
    }

    private static InteractionResult handleRecipeAddress(Player player, Level level, ItemStack itemStack, BlockEntity blockEntity) {
        if (blockEntity instanceof StockTickerBlockEntity ticker) {
            if (!RecipeItem.hasRecipe(itemStack)) {
                return InteractionResult.PASS;
            }
            if (level.isClientSide()) {
                return InteractionResult.SUCCESS;
            }
            RecipeItem.RecipeRecord record = RecipeItem.getRecipe(itemStack);
            if (record == null) {
                return InteractionResult.PASS;
            }
            List<ItemStack> stacks = new ArrayList<>(record.input());
            if (level instanceof ServerLevel serverLevel) {
                if (record.type().equals(RecipeItem.STOCKPOT)) {
                    AutomationRecipeUtils.findStockpotRecipe(serverLevel, record)
                            .map(holder -> holder.value().carrier())
                            .ifPresent(carrier -> {
                                carrier.items()
                                        .findFirst()
                                        .map(item -> item.value().getDefaultInstance())
                                        .ifPresent(stacks::add);
                            });
                } else if (record.type().equals(RecipeItem.POT)) {
                    AutomationRecipeUtils.findPotRecipe(serverLevel, record)
                            .map(holder -> holder.value().carrier())
                            .ifPresent(carrier -> {
                                carrier.items()
                                        .findFirst()
                                        .map(item -> item.value().getDefaultInstance())
                                        .ifPresent(stacks::add);
                            });
                }
            }
            List<BigItemStack> requests = stacks.stream()
                    .filter(stack -> !stack.isEmpty())
                    .map(stack -> new BigItemStack(stack, stack.getCount()))
                    .toList();
            String address = itemStack.getOrDefault(ModDataComponents.RECIPE_ADDRESS, "");
            PackageOrderWithCrafts order = PackageOrderWithCrafts.simple(requests);
            LogisticsManager.broadcastPackageRequest(ticker.behaviour.freqId, LogisticallyLinkedBehaviour.RequestType.RESTOCK, order, null, address);
            return InteractionResult.SUCCESS;
        }
        if (blockEntity instanceof SignBlockEntity sign) {
            if (!player.isSecondaryUseActive()) {
                return InteractionResult.PASS;
            }
            if (level.isClientSide()) {
                return InteractionResult.SUCCESS;
            }
            String address = readSignAddress(sign);
            if (!address.isBlank()) {
                itemStack.set(ModDataComponents.RECIPE_ADDRESS, address);
                player.getCooldowns().addCooldown(itemStack, 10);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        }
        if (blockEntity instanceof PackagePortBlockEntity port) {
            if (!player.isSecondaryUseActive()) {
                return InteractionResult.PASS;
            }
            if (level.isClientSide()) {
                return InteractionResult.SUCCESS;
            }
            itemStack.set(ModDataComponents.RECIPE_ADDRESS, port.addressFilter);
            player.getCooldowns().addCooldown(itemStack, 10);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    private static String readSignAddress(SignBlockEntity sign) {
        StringBuilder builder = new StringBuilder();
        appendSignText(builder, sign.getText(true));
        appendSignText(builder, sign.getText(false));
        return builder.toString().trim();
    }

    private static void appendSignText(StringBuilder builder, SignText signText) {
        for (Component component : signText.getMessages(false)) {
            String string = component.getString().trim();
            if (!string.isBlank()) {
                if (!builder.isEmpty()) {
                    builder.append(' ');
                }
                builder.append(string);
            }
        }
    }
}
