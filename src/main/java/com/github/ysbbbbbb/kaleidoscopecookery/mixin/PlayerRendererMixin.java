package com.github.ysbbbbbb.kaleidoscopecookery.mixin;

import com.github.ysbbbbbb.kaleidoscopecookery.item.LiftBlockItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerRenderer.class)
@Environment(EnvType.CLIENT)
public class PlayerRendererMixin {
    @Inject(method = "getArmPose", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/AbstractClientPlayer;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;", shift = At.Shift.AFTER), cancellable = true)
    private static void getArmPose(AbstractClientPlayer player, InteractionHand hand, CallbackInfoReturnable<HumanoidModel.ArmPose> cir) {
        final HumanoidModel.ArmPose liftPose = HumanoidModel.ArmPose.SPYGLASS; // fallback
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.isEmpty()) cir.setReturnValue(HumanoidModel.ArmPose.EMPTY);
        if (itemstack.getItem() instanceof LiftBlockItem)
            cir.setReturnValue(liftPose);
    }
}
