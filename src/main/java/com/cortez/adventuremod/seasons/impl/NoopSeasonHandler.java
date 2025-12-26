package com.cortez.adventuremod.seasons.impl;

import com.cortez.adventuremod.seasons.ISeasonHandler;
import net.minecraft.server.MinecraftServer;

public class NoopSeasonHandler implements ISeasonHandler
{

    @Override
    public void tick(MinecraftServer server) {

    }
}
