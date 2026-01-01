package com.cortez.adventuremod.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class ModNetworking {

    public static void registerC2SPackets() {

        // Registra o tipo do payload
        PayloadTypeRegistry.playC2S().register(
                SaveProgramPacket.ID,
                SaveProgramPacket.CODEC
        );

        // Registra o receiver no servidor
        ServerPlayNetworking.registerGlobalReceiver(
                SaveProgramPacket.ID,
                (payload, context) -> SaveProgramPacket.receive(payload, context.player())
        );


    }

    public static void registerS2CPackets() {
        // Registre aqui packets server-to-client se necessário
        PayloadTypeRegistry.playS2C().register(
                ThirstSyncPayload.ID,
                ThirstSyncPayload.CODEC
        );

        // Registra o payload de sincronização do calendário
        PayloadTypeRegistry.playS2C().register(
                CalendarSyncPayload.ID,
                CalendarSyncPayload.CODEC
        );
    }
}