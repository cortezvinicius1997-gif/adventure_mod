package com.cortez.adventuremod.event;

import com.cortez.adventuremod.AdventureMod;
import com.cortez.adventuremod.util.IEntityDataSaver;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerEventHandler
{
    public static void register() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.player;
            IEntityDataSaver data = (IEntityDataSaver) player;

            NbtCompound persistent = data.getPersistentData();

            if (!persistent.contains("adventuremod_start")) {
                AdventureMod.LOGGER.info("Player register");
                persistent.putBoolean("adventuremod_start", true);
                persistent.putString("Oxigen", "3600s");
                persistent.putInt("thirst", 20);
                persistent.putFloat("thirst_saturation", 5.0f);
                persistent.putFloat("thirst_exhaustion", 0.0f);

                persistent.putInt("acclimatization", 0);

            } else {
                AdventureMod.LOGGER.info("Player started");

            }
        });
    }
}
