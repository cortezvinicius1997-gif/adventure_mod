package com.cortez.adventuremod.block;

import com.cortez.adventuremod.AdventureMod;
import com.cortez.adventuremod.block.custom.PedestalBlock;
import com.cortez.adventuremod.block.custom.ProgrammingBlock;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class ModBlocks
{

    public static final Block PEDESTAL = registerBlock("pedestal_block", new PedestalBlock(AbstractBlock.Settings.create().nonOpaque()));
    public static final Block PROGRAMMINGBLOCK = registerBlock("programming_block", new ProgrammingBlock(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)));

    public static final Block NETHER_COAL_ORE = registerBlock(
            "nether_coal_ore",
            new ExperienceDroppingBlock(
                    UniformIntProvider.create(0, 1),
                    AbstractBlock.Settings.copy(Blocks.NETHER_GOLD_ORE)
                            .sounds(BlockSoundGroup.NETHER_ORE)
                            .requiresTool()
            )
    );

    public static final Block END_COAL_ORE = registerBlock(
            "end_coal_ore",
            new ExperienceDroppingBlock(
                    UniformIntProvider.create(0, 0),
                    AbstractBlock.Settings.copy(Blocks.END_STONE)
                            .sounds(BlockSoundGroup.STONE)
                            .requiresTool()
            )
    );

    public static final Block NETHER_IRON_ORE = registerBlock(
            "nether_iron_ore",
            new ExperienceDroppingBlock(
                    UniformIntProvider.create(0, 1),
                    AbstractBlock.Settings.copy(Blocks.NETHER_GOLD_ORE)
                            .sounds(BlockSoundGroup.NETHER_ORE)
                            .requiresTool()
            )
    );

    public static final Block END_IRON_ORE = registerBlock(
            "end_iron_ore",
            new ExperienceDroppingBlock(
                    UniformIntProvider.create(0, 0),
                    AbstractBlock.Settings.copy(Blocks.END_STONE)
                            .sounds(BlockSoundGroup.STONE)
                            .requiresTool()
            )
    );

    public static final Block NETHER_COPPER_ORE = registerBlock(
            "nether_copper_ore",
            new ExperienceDroppingBlock(
                    UniformIntProvider.create(0, 1),
                    AbstractBlock.Settings.copy(Blocks.NETHER_GOLD_ORE)
                            .sounds(BlockSoundGroup.NETHER_ORE)
                            .requiresTool()
            )
    );

    public static final Block END_COPPER_ORE = registerBlock(
            "end_copper_ore",
            new ExperienceDroppingBlock(
                    UniformIntProvider.create(0, 0),
                    AbstractBlock.Settings.copy(Blocks.END_STONE)
                            .sounds(BlockSoundGroup.STONE)
                            .requiresTool()
            )
    );

    public static final Block END_GOLD_ORE = registerBlock(
            "end_gold_ore",
            new ExperienceDroppingBlock(
                    UniformIntProvider.create(0, 0),
                    AbstractBlock.Settings.copy(Blocks.END_STONE)
                            .sounds(BlockSoundGroup.STONE)
                            .requiresTool()
            )
    );

    public static final Block NETHER_REDSTONE_ORE = registerBlock("nether_redstone_ore", new RedstoneOreBlock(AbstractBlock.Settings.copy(Blocks.NETHER_GOLD_ORE).sounds(BlockSoundGroup.NETHER_ORE).requiresTool().ticksRandomly().luminance(Blocks.createLightLevelFromLitBlockState(9))));

    public static final Block END_REDSTONE_ORE = registerBlock("end_redstone_ore", new RedstoneOreBlock(AbstractBlock.Settings.copy(Blocks.END_STONE).sounds(BlockSoundGroup.STONE).requiresTool().ticksRandomly().luminance(Blocks.createLightLevelFromLitBlockState(9))));

    public static final Block NETHER_LAPISLAZULI_ORE = registerBlock(
            "nether_lapislazuli_ore",
            new ExperienceDroppingBlock(
                    UniformIntProvider.create(0, 1),
                    AbstractBlock.Settings.copy(Blocks.NETHER_GOLD_ORE)
                            .sounds(BlockSoundGroup.NETHER_ORE)
                            .requiresTool()
            )
    );

    public static final Block END_LAPISLAZULI_ORE = registerBlock(
            "end_lapislazuli_ore",
            new ExperienceDroppingBlock(
                    UniformIntProvider.create(0, 0),
                    AbstractBlock.Settings.copy(Blocks.END_STONE)
                            .sounds(BlockSoundGroup.STONE)
                            .requiresTool()
            )
    );

    public static final Block NETHER_DIAMOND_ORE = registerBlock(
            "nether_diamond_ore",
            new ExperienceDroppingBlock(
                    UniformIntProvider.create(0, 1),
                    AbstractBlock.Settings.copy(Blocks.NETHER_GOLD_ORE)
                            .sounds(BlockSoundGroup.NETHER_ORE)
                            .requiresTool()
            )
    );

    public static final Block END_DIAMOND_ORE = registerBlock(
            "end_diamond_ore",
            new ExperienceDroppingBlock(
                    UniformIntProvider.create(0, 0),
                    AbstractBlock.Settings.copy(Blocks.END_STONE)
                            .sounds(BlockSoundGroup.STONE)
                            .requiresTool()
            )
    );

    public static final Block NETHER_EMERALD_ORE = registerBlock(
            "nether_emerald_ore",
            new ExperienceDroppingBlock(
                    UniformIntProvider.create(0, 1),
                    AbstractBlock.Settings.copy(Blocks.NETHER_GOLD_ORE)
                            .sounds(BlockSoundGroup.NETHER_ORE)
                            .requiresTool()
            )
    );

    public static final Block END_EMERALD_ORE = registerBlock(
            "end_emerald_ore",
            new ExperienceDroppingBlock(
                    UniformIntProvider.create(0, 0),
                    AbstractBlock.Settings.copy(Blocks.END_STONE)
                            .sounds(BlockSoundGroup.STONE)
                            .requiresTool()
            )
    );



    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(AdventureMod.MODID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(AdventureMod.MODID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks() {
        AdventureMod.LOGGER.info("Registering Mod Blocks for " + AdventureMod.MODID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
            entries.add(ModBlocks.PEDESTAL);
            entries.add(ModBlocks.PROGRAMMINGBLOCK);
            entries.add(ModBlocks.NETHER_COAL_ORE);
            entries.add(ModBlocks.END_COAL_ORE);
            entries.add(ModBlocks.NETHER_IRON_ORE);
            entries.add(ModBlocks.END_IRON_ORE);
            entries.add(ModBlocks.NETHER_COPPER_ORE);
            entries.add(ModBlocks.END_COPPER_ORE);
            entries.add(ModBlocks.END_GOLD_ORE);
            entries.add(ModBlocks.NETHER_REDSTONE_ORE);
            entries.add(ModBlocks.END_REDSTONE_ORE);
            entries.add(ModBlocks.NETHER_LAPISLAZULI_ORE);
            entries.add(ModBlocks.END_LAPISLAZULI_ORE);
            entries.add(ModBlocks.NETHER_DIAMOND_ORE);
            entries.add(ModBlocks.END_DIAMOND_ORE);
            entries.add(ModBlocks.NETHER_EMERALD_ORE);
            entries.add(ModBlocks.END_EMERALD_ORE);
        });
    }
}
