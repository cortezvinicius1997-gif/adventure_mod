package com.cortez.adventuremod.util;

import com.cortez.adventuremod.AdventureMod;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> ORE_DETECTOR_BLOCKS = createTag("ore_detector_blocks");
        public static final TagKey<Block> AXE_WOOD_CUTTER_BLOCKS = createTag("axe_wood_cutter_blocks");

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(AdventureMod.MODID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> AXE_WOOD_CUTTER_AXES = createTag("axe_wood_cutter_axes");
        private static TagKey<Item> createTag(String name){
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(AdventureMod.MODID, name));
        }
    }
}
