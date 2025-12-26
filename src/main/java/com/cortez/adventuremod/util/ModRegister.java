package com.cortez.adventuremod.util;

import com.cortez.adventure_seasons.events.StartServer;
import com.cortez.adventure_seasons.lib.AdventureSeason;
import com.cortez.adventuremod.block.ModBlocks;
import com.cortez.adventuremod.block.entity.ModBlockEntities;
import com.cortez.adventuremod.commands.ModCommands;
import com.cortez.adventuremod.config.AdventureConfig;
import com.cortez.adventuremod.effect.ModEffects;
import com.cortez.adventuremod.AdventureMod;
import com.cortez.adventuremod.event.*;
import com.cortez.adventuremod.group.ModItemGroups;
import com.cortez.adventuremod.items.ModItems;
import com.cortez.adventuremod.network.ModNetworking;
import com.cortez.adventuremod.screen.ModScreenHandlers;
import com.cortez.adventuremod.seasons.ISeasonHandler;
import com.cortez.adventuremod.seasons.SeasonHandlerFactory;
import com.cortez.adventuremod.world.gen.ModWorldGeneration;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;

public class ModRegister
{
    public static void register(){
        registerConfig();
        registerItems();
        registerBlocks();
        registerBlocksEntity();
        regiterWorldGen();
        registerGroups();
        registerEvents();
        registerEffects();
        networkRegister();
        registerScreenHandler();
        registerCommands();
    }



    private static void registerConfig()
    {
        AdventureConfig.load();
    }


    private static void registerScreenHandler() {
        ModScreenHandlers.registerScreenHandlers();
    }


    private static void networkRegister(){
        ModNetworking.registerC2SPackets();
        ModNetworking.registerS2CPackets();
    }

    private static void registerEffects(){
        ModEffects.registerEffects();
    }

    private static void registerItems() {
        AdventureMod.LOGGER.info("Register Items " + AdventureMod.MODID);
        ModItems.registerModItems();
    }

    private static void registerBlocks()
    {
        ModBlocks.registerModBlocks();
    }

    private static void registerGroups()
    {
        ModItemGroups.registerItemGroups();
    }


    private static void  registerEvents(){
        PlayerEventHandler.register();
        PlayerBlockBreakEvents.AFTER.register(new AxeWoodcutter());

        ISeasonHandler seasonHandler = SeasonHandlerFactory.create();

        ServerTickEvents.START_SERVER_TICK.register(new PlayerTickHandler(seasonHandler));

    }

    private static void registerBlocksEntity()
    {
        ModBlockEntities.registerBlockEntities();
    }

    private static void regiterWorldGen() {
        ModWorldGeneration.generateModWorldGen();
    }

    private static void registerCommands()
    {
        ModCommands.registerCommand();
    }
}
