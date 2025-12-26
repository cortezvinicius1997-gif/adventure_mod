package com.cortez.adventuremod.network;

import com.cortez.adventuremod.AdventureMod;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record ThirstSyncPayload(int thirst, float saturation, float exhaustion) implements CustomPayload {

    public static final CustomPayload.Id<ThirstSyncPayload> ID =
            new CustomPayload.Id<>(Identifier.of(AdventureMod.MODID, "thirst_sync"));

    public static final PacketCodec<RegistryByteBuf, ThirstSyncPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.VAR_INT, ThirstSyncPayload::thirst,
            PacketCodecs.FLOAT, ThirstSyncPayload::saturation,
            PacketCodecs.FLOAT, ThirstSyncPayload::exhaustion,
            ThirstSyncPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}