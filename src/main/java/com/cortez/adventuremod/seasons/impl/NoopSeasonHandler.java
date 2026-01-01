package com.cortez.adventuremod.seasons.impl;

import com.cortez.adventuremod.calendar.CalendarDate;
import com.cortez.adventuremod.calendar.CalendarManager;
import com.cortez.adventuremod.calendar.Season;
import com.cortez.adventuremod.seasons.ISeasonHandler;
import net.minecraft.server.MinecraftServer;

/**
 * Handler interno de estações que usa o sistema de calendário do mod.
 * Usado quando o mod adventure_seasons não está instalado.
 */
public class NoopSeasonHandler implements ISeasonHandler
{
    private CalendarManager calendarManager;

    @Override
    public void tick(MinecraftServer server) {
        if (calendarManager == null) {
            calendarManager = CalendarManager.get(server);
        }
        calendarManager.tick(server);
    }

    @Override
    public Season getCurrentSeason() {
        if (calendarManager != null) {
            return calendarManager.getCurrentSeason();
        }
        return Season.SPRING;
    }

    @Override
    public CalendarDate getCurrentDate() {
        if (calendarManager != null) {
            return calendarManager.getCurrentDate();
        }
        return new CalendarDate();
    }

    @Override
    public boolean isCalendarActive() {
        return calendarManager != null;
    }
}
