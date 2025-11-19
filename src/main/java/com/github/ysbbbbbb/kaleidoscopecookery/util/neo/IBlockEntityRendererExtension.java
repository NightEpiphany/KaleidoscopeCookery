package com.github.ysbbbbbb.kaleidoscopecookery.util.neo;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;

//From Neoforge
public interface IBlockEntityRendererExtension<T extends BlockEntity> extends BlockEntityRenderer<T> {
    default AABB getRenderBoundingBox(T blockEntity) {
        return new AABB(blockEntity.getBlockPos());
    }
}
