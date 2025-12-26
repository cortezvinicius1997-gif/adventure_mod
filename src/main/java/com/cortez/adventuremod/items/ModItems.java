package com.cortez.adventuremod.items;

import com.cortez.adventuremod.AdventureMod;
import com.cortez.adventuremod.items.custom.MagicFruit;
import com.cortez.adventuremod.items.custom.MiniComputer;
import com.cortez.adventuremod.items.custom.OreDetector;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems
{


    public static final Item OREDETECTOR = registerItem("ore_detector", new OreDetector(false, new Item.Settings()));
    public static final Item MINICOMPUTER = registerItem("mini_computer", new MiniComputer(new Item.Settings()));
    /**
     * Criação de um item "OREDETECTORGENERIC"
     */
    public static final Item OREDETECTORGENERIC = registerItem("ore_detector_generic", new OreDetector(true, new Item.Settings()));


    public static final Item MAGIC_FRUIT = registerItem("magic_fruit", new MagicFruit(new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(AdventureMod.MODID, name), item);
    }

    public static void registerModItems() {
        AdventureMod.LOGGER.info("Registering Mod Items for " + AdventureMod.MODID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(OREDETECTOR);
            entries.add(OREDETECTORGENERIC);
            entries.add(MAGIC_FRUIT);
        });
    }
}
