package com.github.ysbbbbbb.kaleidoscopecookery.util;

import com.github.ysbbbbbb.kaleidoscopecookery.util.neo.IBlockEntityRendererExtension;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.rendertype.OutputTarget;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.BiFunction;
import java.util.function.Function;

public class RenderCulling {


    public static final Function<Identifier, RenderType> ENTITY_TRANSLUCENT_ITEM_TARGET = Util.memoize(
            texture -> {
                RenderSetup state = RenderSetup.builder(RenderPipelines.ENTITY_TRANSLUCENT_CULL)
                        .withTexture("Sampler0", texture)
                        .setOutputTarget(OutputTarget.ITEM_ENTITY_TARGET)
                        .useLightmap()
                        .useOverlay()
                        .affectsCrumbling()
                        .sortOnUpload()
                        .setOutline(RenderSetup.OutlineProperty.NONE)
                        .createRenderSetup();
                return RenderType.create("entity_translucent_item_target", state);
            }
    );

    @ApiStatus.Internal
    @SuppressWarnings("unchecked")
    public static <T extends BlockEntity> boolean isBlockEntityRendererVisible(BlockEntityRenderDispatcher dispatcher, BlockEntity blockEntity, Frustum frustum) {
        IBlockEntityRendererExtension<T> renderer = (IBlockEntityRendererExtension<T>) dispatcher.getRenderer(blockEntity);
        return renderer != null && frustum.isVisible(renderer.getRenderBoundingBox((T) blockEntity));
    }
}
