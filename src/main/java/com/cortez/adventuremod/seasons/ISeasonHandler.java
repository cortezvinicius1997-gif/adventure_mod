package com.cortez.adventuremod.seasons;

import com.cortez.adventuremod.calendar.CalendarDate;
import com.cortez.adventuremod.calendar.Season;
import net.minecraft.server.MinecraftServer;

public interface ISeasonHandler
{
    void tick(MinecraftServer server);

    /**
     * Retorna a estação atual.
     */
    default Season getCurrentSeason() {
        return Season.SPRING;
    }

    /**
     * Retorna a data atual do calendário.
     */
    default CalendarDate getCurrentDate() {
        return new CalendarDate();
    }

    /**
     * Verifica se o sistema de calendário está ativo.
     */
    default boolean isCalendarActive() {
        return false;
    }
}
