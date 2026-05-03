package com.github.ysbbbbbb.kaleidoscopecookery.mixin.client;

import com.github.ysbbbbbb.kaleidoscopecookery.util.neo.IBlockEntityRendererExtension;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;

@Implements({
        @Interface(iface = IBlockEntityRendererExtension.class, prefix = "bounding_box$")
})
@Mixin(value = BlockEntityRenderer.class, priority = 100)
public interface BlockEntityRendererMixin {
}
