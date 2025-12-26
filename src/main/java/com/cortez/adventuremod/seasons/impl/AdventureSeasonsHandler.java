package com.cortez.adventuremod.seasons.impl;

import com.cortez.adventure_seasons.events.StartServer;
import com.cortez.adventure_seasons.lib.AdventureSeason;
import com.cortez.adventuremod.seasons.ISeasonHandler;
import net.minecraft.server.MinecraftServer;

public class AdventureSeasonsHandler implements ISeasonHandler
{

    private final AdventureSeason season;

    public AdventureSeasonsHandler() {
        AdventureSeason adventureSeason = new AdventureSeason();
        this.season = StartServer.register(adventureSeason);
    }

    @Override
    public void tick(MinecraftServer server) {
        season.tick(server);
    }
}
