package com.cortez.adventuremod.lua;

import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PlayerLua
{
    private ServerPlayerEntity player;

    public PlayerLua(ServerPlayerEntity player){
        this.player = player;
    }

    public void sendMessage(String msg){
        this.player.sendMessage(Text.literal(msg));
    }

    public BlockPos getPos(){
        return this.player.getBlockPos();
    }

    public void teleport(double x, double y, double z, @Nullable RegistryKey<World> dimenssionReg) {
        if (dimenssionReg == null){
            this.player.requestTeleport(x, y, z);
        }else if (dimenssionReg == World.OVERWORLD || dimenssionReg == World.NETHER || dimenssionReg == World.END){
            ServerWorld dimenssion = player.getServer().getWorld(dimenssionReg);
            this.player.teleport(dimenssion, x, y, z, player.getYaw(), player.getPitch());
        }
    }

    public String getName(){
        return player.getName().getString();
    }

    public RegistryKey<World> getCurrentDimenssion(){
        RegistryKey<World> dimension = player.getEntityWorld().getRegistryKey();
        return dimension;
    }
}
