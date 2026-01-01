package com.cortez.adventuremod.network;

import com.cortez.adventuremod.AdventureMod;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

/**
 * Payload para sincronizar dados do calendário com o cliente.
 */
public record CalendarSyncPayload(
        int year,
        int dayOfYear,
        int hour,
        int minute,
        int seasonIndex
) implements CustomPayload {

    public static final CustomPayload.Id<CalendarSyncPayload> ID =
            new CustomPayload.Id<>(Identifier.of(AdventureMod.MODID, "calendar_sync"));

    public static final PacketCodec<RegistryByteBuf, CalendarSyncPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.VAR_INT, CalendarSyncPayload::year,
            PacketCodecs.VAR_INT, CalendarSyncPayload::dayOfYear,
            PacketCodecs.VAR_INT, CalendarSyncPayload::hour,
            PacketCodecs.VAR_INT, CalendarSyncPayload::minute,
            PacketCodecs.VAR_INT, CalendarSyncPayload::seasonIndex,
            CalendarSyncPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}

