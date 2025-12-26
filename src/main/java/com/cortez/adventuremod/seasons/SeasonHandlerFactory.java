package com.cortez.adventuremod.seasons;

import com.cortez.adventuremod.seasons.impl.AdventureSeasonsHandler;
import com.cortez.adventuremod.seasons.impl.NoopSeasonHandler;
import net.fabricmc.loader.api.FabricLoader;

public class SeasonHandlerFactory
{
    public static ISeasonHandler create() {
        if (FabricLoader.getInstance().isModLoaded("adventure_seasons")) {
            return new AdventureSeasonsHandler();
        }
        return new NoopSeasonHandler();
    }
}
