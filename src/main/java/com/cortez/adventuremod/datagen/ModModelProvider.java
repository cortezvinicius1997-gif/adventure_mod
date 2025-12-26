package com.cortez.adventuremod.datagen;

import com.cortez.adventuremod.block.ModBlocks;
import com.cortez.adventuremod.items.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.NETHER_COAL_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.END_COAL_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.NETHER_IRON_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.END_IRON_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.NETHER_COPPER_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.END_COPPER_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.END_GOLD_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.NETHER_REDSTONE_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.END_REDSTONE_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.NETHER_LAPISLAZULI_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.END_LAPISLAZULI_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.NETHER_DIAMOND_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.END_DIAMOND_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.NETHER_EMERALD_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.END_EMERALD_ORE);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.OREDETECTOR, Models.GENERATED);
        itemModelGenerator.register(ModItems.OREDETECTORGENERIC, Models.GENERATED);
        itemModelGenerator.register(ModItems.MINICOMPUTER, Models.GENERATED);
        itemModelGenerator.register(ModItems.MAGIC_FRUIT, Models.GENERATED);
    }
}


