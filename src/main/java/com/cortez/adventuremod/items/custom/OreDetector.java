package com.cortez.adventuremod.items.custom;

import com.cortez.adventuremod.util.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class OreDetector extends Item
{
    private boolean isGeneric;


    public OreDetector(boolean isGeneric, Settings settings) {
        super(settings);
        this.isGeneric = isGeneric;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getWorld().isClient()) {
            BlockPos positionClicked = context.getBlockPos();
            PlayerEntity player = context.getPlayer();
            boolean foundBlock = false;

            // Verifica se o detector de minério é genérico
            if (isGeneric) {
                Random random = new Random();
                int value = random.nextInt(2);

                // Em 50% dos casos, um minério é detectado aleatoriamente
                if (value == 1) {
                    for (int i = 0; i <= positionClicked.getY() + 64; i++) {
                        BlockState state = context.getWorld().getBlockState(positionClicked.down(i));
                        Block blockBelow = state.getBlock();
                        if (isValueBlock(state)) {
                            outputValuableCoordinates(positionClicked.down(i), player, blockBelow);
                            foundBlock = true;
                            break;
                        }
                    }

                    if (!foundBlock) {
                        player.sendMessage(Text.translatable("item.adventuremod.ore_detector.no_valuables"), false);
                    }
                }
                // Em 50% dos casos, não é detectado nenhum minério
                else {
                    player.sendMessage(Text.translatable("item.adventuremod.ore_detector.no_valuables"), false);
                }
            } else {

                for (int i = 0; i <= positionClicked.getY() + 64; i++) {
                    BlockState state = context.getWorld().getBlockState(positionClicked.down(i));
                    Block blockBelow = state.getBlock();

                    if (isValueBlock(state)) {
                        outputValuableCoordinates(positionClicked.down(i), player, blockBelow);
                        foundBlock = true;
                        break;
                    }
                }

                if (!foundBlock) {
                    player.sendMessage(Text.translatable("item.adventuremod.ore_detector.no_valuables"), false);
                }
            }
        }
        return super.useOnBlock(context);
    }

    private void outputValuableCoordinates(BlockPos blockPos, PlayerEntity player, Block blockBelow) {
        player.sendMessage(Text.literal(blockBelow.asItem().getName().getString() + " " +
                "( X: " + blockPos.getX() + ", Y: " + blockPos.getY() + ", Z: " + blockPos.getZ() + ")"), false);
    }

    private boolean isValueBlock(BlockState state) {
        return state.isIn(ModTags.Blocks.ORE_DETECTOR_BLOCKS);

    }
}
