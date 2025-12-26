package com.cortez.adventuremod.block.entity;

import com.cortez.adventuremod.AdventureMod;
import com.cortez.adventuremod.block.ModBlocks;
import com.cortez.adventuremod.block.entity.custom.PedestalBlockEntity;
import com.cortez.adventuremod.block.entity.custom.ProgrammingBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities
{
    public static final BlockEntityType<PedestalBlockEntity> PEDESTAL_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(AdventureMod.MODID, "pedestal_be"),
                    BlockEntityType.Builder.create(PedestalBlockEntity::new, ModBlocks.PEDESTAL).build(null));

    public static final BlockEntityType<ProgrammingBlockEntity> PROGRAMMING_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(AdventureMod.MODID, "programming_be"),
                    BlockEntityType.Builder.create(ProgrammingBlockEntity::new, ModBlocks.PROGRAMMINGBLOCK).build());

    public static void registerBlockEntities() {
        AdventureMod.LOGGER.info("Registering Block Entities for " + AdventureMod.MODID);
    }
}
