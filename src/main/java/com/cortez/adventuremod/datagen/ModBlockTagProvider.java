package com.cortez.adventuremod.datagen;

import com.cortez.adventuremod.block.ModBlocks;
import com.cortez.adventuremod.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider
{

    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {

        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.NETHER_COAL_ORE)
                .add(ModBlocks.END_COAL_ORE)
                .add(ModBlocks.NETHER_IRON_ORE)
                .add(ModBlocks.END_IRON_ORE)
                .add(ModBlocks.NETHER_COPPER_ORE)
                .add(ModBlocks.END_COPPER_ORE)
                .add(ModBlocks.END_GOLD_ORE)
                .add(ModBlocks.NETHER_REDSTONE_ORE)
                .add(ModBlocks.END_REDSTONE_ORE)
                .add(ModBlocks.NETHER_LAPISLAZULI_ORE)
                .add(ModBlocks.END_LAPISLAZULI_ORE)
                .add(ModBlocks.NETHER_DIAMOND_ORE)
                .add(ModBlocks.END_DIAMOND_ORE)
                .add(ModBlocks.NETHER_EMERALD_ORE)
                .add(ModBlocks.END_EMERALD_ORE);

        getOrCreateTagBuilder(ModTags.Blocks.ORE_DETECTOR_BLOCKS)
                .add(Blocks.COAL_ORE)
                .add(Blocks.DEEPSLATE_COAL_ORE)
                .add(Blocks.IRON_ORE)
                .add(Blocks.DEEPSLATE_IRON_ORE)
                .add(Blocks.COPPER_ORE)
                .add(Blocks.DEEPSLATE_COPPER_ORE)
                .add(Blocks.GOLD_ORE)
                .add(Blocks.DEEPSLATE_GOLD_ORE)
                .add(Blocks.REDSTONE_ORE)
                .add(Blocks.DEEPSLATE_REDSTONE_ORE)
                .add(Blocks.LAPIS_ORE)
                .add(Blocks.DEEPSLATE_LAPIS_ORE)
                .add(Blocks.EMERALD_ORE)
                .add(Blocks.DEEPSLATE_EMERALD_ORE)
                .add(Blocks.DIAMOND_ORE)
                .add(Blocks.DEEPSLATE_DIAMOND_ORE)
                .add(Blocks.NETHER_GOLD_ORE)
                .add(ModBlocks.NETHER_COAL_ORE)
                .add(ModBlocks.END_COAL_ORE)
                .add(ModBlocks.NETHER_IRON_ORE)
                .add(ModBlocks.END_IRON_ORE)
                .add(ModBlocks.NETHER_COPPER_ORE)
                .add(ModBlocks.END_COPPER_ORE)
                .add(ModBlocks.END_GOLD_ORE)
                .add(ModBlocks.NETHER_REDSTONE_ORE)
                .add(ModBlocks.END_REDSTONE_ORE)
                .add(ModBlocks.NETHER_LAPISLAZULI_ORE)
                .add(ModBlocks.END_LAPISLAZULI_ORE)
                .add(ModBlocks.NETHER_DIAMOND_ORE)
                .add(ModBlocks.END_DIAMOND_ORE)
                .add(ModBlocks.NETHER_EMERALD_ORE)
                .add(ModBlocks.END_EMERALD_ORE)
                .setReplace(false);



        getOrCreateTagBuilder(ModTags.Blocks.AXE_WOOD_CUTTER_BLOCKS)
                .add(Blocks.OAK_LOG)
                .add(Blocks.STRIPPED_OAK_LOG)
                .add(Blocks.BIRCH_LOG)
                .add(Blocks.STRIPPED_BIRCH_LOG)
                .add(Blocks.SPRUCE_LOG)
                .add(Blocks.STRIPPED_SPRUCE_LOG)
                .add(Blocks.JUNGLE_LOG)
                .add(Blocks.STRIPPED_JUNGLE_LOG)
                .add(Blocks.ACACIA_LOG)
                .add(Blocks.STRIPPED_ACACIA_LOG)
                .add(Blocks.DARK_OAK_LOG)
                .add(Blocks.STRIPPED_DARK_OAK_LOG)
                .add(Blocks.MANGROVE_LOG)
                .add(Blocks.STRIPPED_MANGROVE_LOG)
                .add(Blocks.MANGROVE_ROOTS)
                .add(Blocks.CHERRY_LOG)
                .add(Blocks.STRIPPED_CHERRY_LOG);

        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.NETHER_IRON_ORE)
                .add(ModBlocks.END_IRON_ORE)
                .add(ModBlocks.NETHER_COPPER_ORE)
                .add(ModBlocks.END_COPPER_ORE)
                .add(ModBlocks.NETHER_LAPISLAZULI_ORE)
                .add(ModBlocks.END_LAPISLAZULI_ORE);

        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.END_GOLD_ORE)
                .add(ModBlocks.NETHER_REDSTONE_ORE)
                .add(ModBlocks.END_REDSTONE_ORE)
                .add(ModBlocks.NETHER_DIAMOND_ORE)
                .add(ModBlocks.END_DIAMOND_ORE)
                .add(ModBlocks.NETHER_EMERALD_ORE)
                .add(ModBlocks.END_EMERALD_ORE);
    }
}
