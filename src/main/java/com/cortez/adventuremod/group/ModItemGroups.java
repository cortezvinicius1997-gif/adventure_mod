package com.cortez.adventuremod.group;

import com.cortez.adventuremod.AdventureMod;
import com.cortez.adventuremod.block.ModBlocks;
import com.cortez.adventuremod.items.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static final ItemGroup ADVENTURE_MOD_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(AdventureMod.MODID, "adventure"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.adventure_mod"))
                    .icon(()-> new ItemStack(ModItems.OREDETECTOR)).entries(((displayContext, entries) -> {
                        entries.add(ModItems.OREDETECTOR);
                        entries.add(ModItems.OREDETECTORGENERIC);
                        entries.add(ModItems.MINICOMPUTER);
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
                        entries.add(ModItems.MAGIC_FRUIT);

                    })).build());

    public static void registerItemGroups() {
        AdventureMod.LOGGER.info("Registering Item Groups for " + AdventureMod.MODID);
    }
}
