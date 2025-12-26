package com.cortez.adventuremod.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.SugarCaneBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SugarCaneBlock.class)
public abstract class SugarCaneBlockMixin extends Block implements Fertilizable {

    public SugarCaneBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        // Encontra a base da cana-de-açúcar
        BlockPos basePos = pos;
        while (world.getBlockState(basePos.down()).getBlock() instanceof SugarCaneBlock) {
            basePos = basePos.down();
        }

        // Conta a altura atual
        int height = 0;
        BlockPos checkPos = basePos;
        while (world.getBlockState(checkPos).getBlock() instanceof SugarCaneBlock) {
            checkPos = checkPos.up();
            height++;
        }

        // Verifica se pode crescer (máximo 3 blocos)
        if (height >= 3) return false;

        // Verifica se há espaço vazio acima
        return world.getBlockState(checkPos).isAir();
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        // Encontra o topo da cana-de-açúcar
        BlockPos topPos = pos;
        while (world.getBlockState(topPos.up()).getBlock() instanceof SugarCaneBlock) {
            topPos = topPos.up();
        }

        // Encontra a base para calcular altura
        BlockPos basePos = pos;
        while (world.getBlockState(basePos.down()).getBlock() instanceof SugarCaneBlock) {
            basePos = basePos.down();
        }

        int currentHeight = topPos.getY() - basePos.getY() + 1;

        // Cresce 1-2 blocos aleatoriamente, respeitando o limite de 3
        int growthAmount = 1 + random.nextInt(2);

        for (int i = 0; i < growthAmount; i++) {
            if (currentHeight + i >= 3) break;

            BlockPos growPos = topPos.up(i + 1);

            // Verifica se a posição está vazia
            if (!world.getBlockState(growPos).isAir()) break;

            // Coloca novo bloco de cana-de-açúcar
            world.setBlockState(growPos, Blocks.SUGAR_CANE.getDefaultState(), Block.NOTIFY_ALL);
        }
    }
}
