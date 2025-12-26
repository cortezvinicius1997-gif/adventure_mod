package com.cortez.adventuremod.event;

import com.cortez.adventuremod.util.ModTags;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;


public class AxeWoodcutter implements PlayerBlockBreakEvents.After
{

    @Override
    public void afterBlockBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity) {
        ItemStack heldItem = player.getMainHandStack();

        if (heldItem.isIn(ModTags.Items.AXE_WOOD_CUTTER_AXES)){
            if (state.isIn(ModTags.Blocks.AXE_WOOD_CUTTER_BLOCKS)) {
                breakEntireTree(world, pos);
            }
        }
    }

    private static void breakEntireTree(World world, BlockPos pos) {
        breakTreeRecursive(world, pos.up());
    }

    private static void breakTreeRecursive(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);

        if (state.isIn(ModTags.Blocks.AXE_WOOD_CUTTER_BLOCKS)) {
            world.breakBlock(pos, true);
            for (int xOffset = -1; xOffset <= 1; xOffset++) {
                for (int yOffset = -1; yOffset <= 1; yOffset++) {
                    for (int zOffset = -1; zOffset <= 1; zOffset++) {
                        breakTreeRecursive(world, pos.add(xOffset, yOffset, zOffset));
                    }
                }
            }
        }
    }

}
