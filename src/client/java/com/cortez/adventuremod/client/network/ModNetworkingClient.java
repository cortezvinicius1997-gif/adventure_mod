package com.cortez.adventuremod.client.network;

import com.cortez.adventuremod.client.calendar.ClientCalendarData;
import com.cortez.adventuremod.network.CalendarSyncPayload;
import com.cortez.adventuremod.network.ThirstSyncPayload;
import com.cortez.adventuremod.util.IEntityDataSaver;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;

public class ModNetworkingClient {
    public static void registerS2CPackets(){

        ClientPlayNetworking.registerGlobalReceiver(
                ThirstSyncPayload.ID,
                (payload, context) -> {
                    context.client().execute(() -> {
                        MinecraftClient client = context.client();
                        if (client.player != null) {
                            IEntityDataSaver data = (IEntityDataSaver) client.player;

                            // Atualizar os dados locais do cliente
                            data.getPersistentData().putInt("thirst", payload.thirst());
                            data.getPersistentData().putFloat("thirst_saturation", payload.saturation());
                            data.getPersistentData().putFloat("thirst_exhaustion", payload.exhaustion());
                        }
                    });
                }
        );

        // Receiver para sincronização do calendário
        ClientPlayNetworking.registerGlobalReceiver(
                CalendarSyncPayload.ID,
                (payload, context) -> {
                    context.client().execute(() -> {
                        ClientCalendarData.update(payload);
                    });
                }
        );
    }
}
