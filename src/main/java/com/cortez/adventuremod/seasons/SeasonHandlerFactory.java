package com.cortez.adventuremod.seasons;

import com.cortez.adventuremod.AdventureMod;
import com.cortez.adventuremod.config.AdventureConfig;
import com.cortez.adventuremod.seasons.impl.AdventureSeasonsHandler;
import com.cortez.adventuremod.seasons.impl.NoopSeasonHandler;
import net.fabricmc.loader.api.FabricLoader;

public class SeasonHandlerFactory
{
    public static ISeasonHandler create() {
        // Usa o sistema interno de calendário por padrão
        // O mod adventure_seasons externo tem bugs conhecidos
        // Se quiser usar o mod externo, defina useExternalSeasons=true na config

        boolean useExternal = false; // Por padrão usa o sistema interno

        if (useExternal && FabricLoader.getInstance().isModLoaded("adventure_seasons")) {
            try {
                AdventureMod.LOGGER.info("[Adventure Mod] Usando sistema de estações externo (adventure_seasons)");
                return new AdventureSeasonsHandler();
            } catch (Exception e) {
                AdventureMod.LOGGER.error("[Adventure Mod] Erro ao carregar adventure_seasons, usando sistema interno", e);
                return new NoopSeasonHandler();
            }
        }

        AdventureMod.LOGGER.info("[Adventure Mod] Usando sistema de calendário interno");
        return new NoopSeasonHandler();
    }
}
