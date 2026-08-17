package com.github.ysbbbbbb.kaleidoscopecookery.compat.create.ponder.scenes;

import com.github.ysbbbbbb.kaleidoscopecookery.block.kitchen.ShawarmaSpitBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen.ShawarmaSpitBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import com.zurrtum.create.catnip.math.Pointing;
import com.zurrtum.create.client.ponder.api.PonderPalette;
import com.zurrtum.create.client.ponder.api.scene.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.RedstoneWireBlock;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.RedstoneSide;

@Environment(EnvType.CLIENT)
public class ShawarmaSpitScenes {
    public static void introduction(SceneBuilder scene, SceneBuildingUtil util) {

        VectorUtil vector = util.vector();
        SelectionUtil select = util.select();
        PositionUtil grid = util.grid();

        scene.title("shawarma_spit", "");
        scene.configureBasePlate(0, 0, 5);
        scene.showBasePlate();

        BlockPos spitPos = grid.at(2, 1, 2);
        Selection spitSel = select.position(spitPos);
        Selection spitSel1 = select.position(spitPos.above());
        Selection selection = select.fromTo(2, 1, 2, 2, 2, 2);
        BlockPos redPos = grid.at(1, 1, 2);
        Selection redSel = select.position(redPos);
        BlockPos triggerPos = grid.at(0, 1, 2);
        Selection triggerSel = select.position(triggerPos);
        scene.world().setBlock(redPos, Blocks.REDSTONE_WIRE.defaultBlockState()
                .setValue(RedstoneWireBlock.EAST, RedstoneSide.SIDE)
                .setValue(RedstoneWireBlock.WEST, RedstoneSide.SIDE)
                .setValue(RedstoneWireBlock.POWER, 0), false);
        scene.world().setBlock(triggerPos, Blocks.LEVER.defaultBlockState()
                .setValue(LeverBlock.FACING, Direction.EAST)
                .setValue(LeverBlock.POWERED, false)
                .setValue(LeverBlock.FACE, AttachFace.FLOOR), false);

        scene.idle(20);
        scene.world().showSection(spitSel, Direction.DOWN);
        scene.world().showSection(spitSel1, Direction.DOWN);
        scene.idle(20);

        scene.overlay().showText(60).text("")
                .pointAt(vector.blockSurface(spitPos, Direction.WEST))
                .placeNearTarget();
        scene.overlay().showText(60).text("").placeNearTarget();
        scene.overlay().showControls(vector.blockSurface(spitPos, Direction.EAST), Pointing.RIGHT, 20)
                .rightClick()
                .withItem(new ItemStack(Items.BEEF));
        scene.idle(7);
        scene.world().modifyBlockEntity(spitPos, ShawarmaSpitBlockEntity.class, (e) -> {
            e.cookingItem = new ItemStack(Items.BEEF, 8);
            e.cookedItem = new ItemStack(Items.COOKED_BEEF, 8);
            e.cookTime = 100;
            e.refresh();
        });
        scene.idle(28);
        scene.overlay().showControls(vector.blockSurface(spitPos.above(), Direction.EAST), Pointing.RIGHT, 20)
                .rightClick()
                .withItem(new ItemStack(Items.BEEF));
        scene.idle(7);
        scene.world().modifyBlockEntity(spitPos.above(), ShawarmaSpitBlockEntity.class, (e) ->{
            e.cookingItem = new ItemStack(Items.BEEF, 8);
            e.cookedItem = new ItemStack(Items.COOKED_BEEF, 8);
            e.cookTime = 100;
            e.refresh();
        });
        scene.idle(33);

        scene.addKeyframe();
        scene.idle(20);
        scene.world().showSection(redSel, Direction.DOWN);
        scene.idle(3);
        scene.world().showSection(triggerSel, Direction.DOWN);
        scene.idle(20);

        scene.overlay().showText(40).text("").placeNearTarget();
        scene.overlay().showControls(vector.blockSurface(triggerPos, Direction.UP), Pointing.DOWN, 35)
                .rightClick();
        scene.idle(7);
        scene.world().modifyBlock(triggerPos, (s) -> Blocks.LEVER.defaultBlockState()
                .setValue(LeverBlock.FACING, Direction.EAST)
                .setValue(LeverBlock.POWERED, true)
                .setValue(LeverBlock.FACE, AttachFace.FLOOR), false);
        scene.world().modifyBlock(redPos, (s) -> Blocks.REDSTONE_WIRE.defaultBlockState()
                .setValue(RedstoneWireBlock.EAST, RedstoneSide.SIDE)
                .setValue(RedstoneWireBlock.WEST, RedstoneSide.SIDE)
                .setValue(RedstoneWireBlock.POWER, 15), false);
        scene.world().modifyBlock(spitPos, (s) -> ModBlocks.SHAWARMA_SPIT.defaultBlockState()
                .setValue(ShawarmaSpitBlock.FACING, Direction.NORTH)
                .setValue(ShawarmaSpitBlock.HALF, DoubleBlockHalf.LOWER)
                .setValue(ShawarmaSpitBlock.POWERED, true), false);
        scene.world().modifyBlock(spitPos.above(), (s) -> ModBlocks.SHAWARMA_SPIT.defaultBlockState()
                .setValue(ShawarmaSpitBlock.FACING, Direction.NORTH)
                .setValue(ShawarmaSpitBlock.HALF, DoubleBlockHalf.UPPER)
                .setValue(ShawarmaSpitBlock.POWERED, true), false);
        scene.idle(48);

        scene.addKeyframe();
        scene.idle(20);
        scene.overlay().showText(40).text("").placeNearTarget();
        scene.overlay().showControls(vector.blockSurface(grid.at(2, 3, 2), Direction.UP), Pointing.DOWN, 35)
                .withItem(new ItemStack(Items.CLOCK));
        scene.idle(55);

        scene.addKeyframe();
        scene.idle(20);
        scene.overlay().showText(40).text("")
                .pointAt(vector.blockSurface(spitPos, Direction.WEST))
                .placeNearTarget();
        scene.idle(20);
        scene.world().modifyBlockEntity(spitPos, ShawarmaSpitBlockEntity.class, (e) -> {
            e.cookingItem = ItemStack.EMPTY;
            e.cookedItem = new ItemStack(Items.COOKED_BEEF, 8);
            e.refresh();
        });
        scene.world().modifyBlockEntity(spitPos.above(), ShawarmaSpitBlockEntity.class, (e) -> {
            e.cookingItem = ItemStack.EMPTY;
            e.cookedItem = new ItemStack(Items.COOKED_BEEF, 8);
            e.refresh();
        });
        scene.idle(35);

        scene.addKeyframe();
        scene.idle(20);
        scene.overlay().showOutline(PonderPalette.RED, selection, selection, 65);
        scene.overlay().showText(60).text("")
                .pointAt(vector.blockSurface(spitPos, Direction.WEST))
                .colored(PonderPalette.RED)
                .placeNearTarget();
        scene.overlay().showControls(vector.blockSurface(spitPos, Direction.EAST), Pointing.RIGHT, 20)
                .rightClick();
        scene.idle(35);
        scene.overlay().showControls(vector.blockSurface(spitPos, Direction.EAST), Pointing.RIGHT, 20)
                .withItem(new ItemStack(ModItems.RED_CHILI));
        scene.idle(35);

        scene.addKeyframe();
        scene.idle(20);
        scene.overlay().showText(60).text("")
                .pointAt(vector.blockSurface(spitPos, Direction.WEST))
                .placeNearTarget();
        scene.overlay().showControls(vector.blockSurface(spitPos, Direction.EAST), Pointing.RIGHT, 20)
                .rightClick()
                .withItem(new ItemStack(ModItems.IRON_KITCHEN_KNIFE));
        scene.idle(7);
        scene.world().modifyBlockEntity(spitPos, ShawarmaSpitBlockEntity.class, (e) -> {
            e.cookedItem = ItemStack.EMPTY;
            e.refresh();
        });
        scene.idle(28);
        scene.overlay().showControls(vector.blockSurface(spitPos, Direction.EAST), Pointing.RIGHT, 20)
                .withItem(new ItemStack(Items.COOKED_BEEF));
        scene.idle(40);

        scene.addKeyframe();
        scene.idle(20);
        scene.overlay().showText(60).text("").placeNearTarget();
        scene.overlay().showControls(vector.blockSurface(triggerPos, Direction.UP), Pointing.DOWN, 20)
                .rightClick();
        scene.idle(7);
        scene.world().modifyBlock(triggerPos, (s) -> Blocks.LEVER.defaultBlockState()
                .setValue(LeverBlock.FACING, Direction.EAST)
                .setValue(LeverBlock.POWERED, false)
                .setValue(LeverBlock.FACE, AttachFace.FLOOR), false);
        scene.world().modifyBlock(redPos, (s) -> Blocks.REDSTONE_WIRE.defaultBlockState()
                .setValue(RedstoneWireBlock.EAST, RedstoneSide.SIDE)
                .setValue(RedstoneWireBlock.WEST, RedstoneSide.SIDE)
                .setValue(RedstoneWireBlock.POWER, 0), false);
        scene.world().modifyBlock(spitPos, (s) -> ModBlocks.SHAWARMA_SPIT.defaultBlockState()
                .setValue(ShawarmaSpitBlock.FACING, Direction.NORTH)
                .setValue(ShawarmaSpitBlock.HALF, DoubleBlockHalf.LOWER)
                .setValue(ShawarmaSpitBlock.POWERED, false), false);
        scene.world().modifyBlock(spitPos.above(), (s) -> ModBlocks.SHAWARMA_SPIT.defaultBlockState()
                .setValue(ShawarmaSpitBlock.FACING, Direction.NORTH)
                .setValue(ShawarmaSpitBlock.HALF, DoubleBlockHalf.UPPER)
                .setValue(ShawarmaSpitBlock.POWERED, false), false);
        scene.idle(28);
        scene.overlay().showControls(vector.blockSurface(spitPos.above(), Direction.EAST), Pointing.RIGHT, 20)
                .rightClick();
        scene.idle(7);
        scene.world().modifyBlockEntity(spitPos.above(), ShawarmaSpitBlockEntity.class, (e) -> {
            e.cookedItem = ItemStack.EMPTY;
            e.refresh();
        });
        scene.idle(28);
        scene.overlay().showControls(vector.blockSurface(spitPos.above(), Direction.EAST), Pointing.RIGHT, 20)
                .withItem(new ItemStack(Items.COOKED_BEEF));
        scene.idle(40);
    }
}
