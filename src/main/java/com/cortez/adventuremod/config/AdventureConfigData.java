package com.cortez.adventuremod.config;


import java.util.HashSet;
import java.util.Set;

public class AdventureConfigData
{
    public boolean debug;
    public boolean altitudeEffect;
    public boolean thirst;

    // Configurações do calendário
    public boolean calendarEnabled;
    public boolean calendarHudEnabled;
    public int daysPerSeason; // Dias por estação (padrão 28)

    public static AdventureConfigData defaultConfig(){
        AdventureConfigData data = new AdventureConfigData();

        data.debug = true;
        data.altitudeEffect = true;
        data.thirst = true;

        // Padrões do calendário
        data.calendarEnabled = true;
        data.calendarHudEnabled = true;
        data.daysPerSeason = 28;

        return data;
    }
}
