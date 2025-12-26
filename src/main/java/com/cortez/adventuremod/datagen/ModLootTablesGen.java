package com.cortez.adventuremod.datagen;

import com.cortez.adventuremod.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModLootTablesGen extends FabricBlockLootTableProvider {
    public ModLootTablesGen(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.NETHER_COAL_ORE, Items.COAL);
        addDrop(ModBlocks.END_COAL_ORE, Items.COAL);
        addDrop(ModBlocks.NETHER_IRON_ORE, Items.RAW_IRON);
        addDrop(ModBlocks.END_IRON_ORE, Items.RAW_IRON);
        addDrop(ModBlocks.END_GOLD_ORE, Items.RAW_GOLD);
        addDrop(ModBlocks.NETHER_DIAMOND_ORE, Items.DIAMOND);
        addDrop(ModBlocks.END_DIAMOND_ORE, Items.DIAMOND);
        addDrop(ModBlocks.NETHER_EMERALD_ORE, Items.EMERALD);
        addDrop(ModBlocks.END_EMERALD_ORE, Items.EMERALD);
        addDrop(ModBlocks.NETHER_COPPER_ORE, copperOreDrops(ModBlocks.NETHER_COPPER_ORE));
        addDrop(ModBlocks.END_COPPER_ORE, copperOreDrops(ModBlocks.END_COPPER_ORE));
        addDrop(ModBlocks.NETHER_REDSTONE_ORE, redstoneOreDrops(ModBlocks.NETHER_REDSTONE_ORE));
        addDrop(ModBlocks.END_REDSTONE_ORE, redstoneOreDrops(ModBlocks.END_REDSTONE_ORE));
        addDrop(ModBlocks.NETHER_LAPISLAZULI_ORE, lapisOreDrops(ModBlocks.NETHER_LAPISLAZULI_ORE));
        addDrop(ModBlocks.END_LAPISLAZULI_ORE, lapisOreDrops(ModBlocks.NETHER_LAPISLAZULI_ORE));
    }
}
