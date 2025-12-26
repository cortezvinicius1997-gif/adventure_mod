package com.cortez.adventuremod.config;


import java.util.HashSet;
import java.util.Set;

public class AdventureConfigData
{
    public boolean debug;
    public boolean altitudeEffect;
    public boolean thirst;

    public static AdventureConfigData defaultConfig(){
        AdventureConfigData data = new AdventureConfigData();

        data.debug = true;
        data.altitudeEffect = true;
        data.thirst = true;


        return data;
    }
}
