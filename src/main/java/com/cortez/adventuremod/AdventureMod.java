package com.cortez.adventuremod;

import com.cortez.adventuremod.util.ModRegister;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdventureMod implements ModInitializer {


    public static final String MODID = "adventuremod";
    public static Logger LOGGER = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitialize()
    {
        ModRegister.register();
    }

}
