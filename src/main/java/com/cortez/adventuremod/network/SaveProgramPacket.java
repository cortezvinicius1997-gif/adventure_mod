package com.cortez.adventuremod.network;

import com.cortez.adventuremod.AdventureMod;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public record SaveProgramPacket(String code, String programName, String programDescription) implements CustomPayload {

    public static final Id<SaveProgramPacket> ID =
            new Id<>(Identifier.of(AdventureMod.MODID, "save_program"));

    public static final PacketCodec<RegistryByteBuf, SaveProgramPacket> CODEC =
            PacketCodec.tuple(
                    PacketCodecs.STRING, SaveProgramPacket::code,
                    PacketCodecs.STRING, SaveProgramPacket::programName,
                    PacketCodecs.STRING, SaveProgramPacket::programDescription,
                    SaveProgramPacket::new
            );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void receive(SaveProgramPacket packet, ServerPlayerEntity player) {
        // Executa no servidor
        player.server.execute(() -> {
            // Verifica se o player está com a tela de programação aberta
            if (player.currentScreenHandler instanceof com.cortez.adventuremod.screen.custom.ProgrammingScreenHandler handler) {

                var miniComputerStack = handler.slots.get(0).getStack();

                if (!miniComputerStack.isEmpty()) {
                    // Obtém ou cria o NBT
                    NbtComponent customData = miniComputerStack.get(DataComponentTypes.CUSTOM_DATA);
                    NbtCompound nbt = customData != null ? customData.copyNbt() : new NbtCompound();

                    // Salva o código
                    nbt.putString("program_code", packet.code());
                    if(!packet.programName().isEmpty()) {
                        nbt.putString("program_name", packet.programName());
                    }
                    if(!packet.programDescription().isEmpty()) {
                        nbt.putString("program_description", packet.programDescription());
                    }

                    // Aplica de volta ao item
                    miniComputerStack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt));

                    // Marca o slot como modificado
                    handler.slots.get(0).markDirty();



                    AdventureMod.LOGGER.info("Código salvo no servidor para o player: " + player.getName().getString());
                }
            }
        });
    }
}
