package com.cortez.adventuremod.screen;

import com.cortez.adventuremod.AdventureMod;
import com.cortez.adventuremod.screen.custom.PedestalScreenHandler;
import com.cortez.adventuremod.screen.custom.ProgrammingScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ModScreenHandlers
{
    public static final ScreenHandlerType<PedestalScreenHandler> PEDESTAL_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(AdventureMod.MODID, "pedestal_screen_handler"),
                    new ExtendedScreenHandlerType<>(PedestalScreenHandler::new, BlockPos.PACKET_CODEC));

    public static final ScreenHandlerType<ProgrammingScreenHandler> PROGRAMMING_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(AdventureMod.MODID, "programming_screen_handler"),
                    new ExtendedScreenHandlerType<>(ProgrammingScreenHandler::new, BlockPos.PACKET_CODEC));



    public static void registerScreenHandlers() {
        AdventureMod.LOGGER.info("Registering Screen Handlers for " + AdventureMod.MODID);
    }
}
