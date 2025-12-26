package com.cortez.adventuremod.lua;

import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;

public class ServerLua
{
    private MinecraftServer server;
    public ServerLua(MinecraftServer server){
        this.server = server;
    }

    public void sendMessage(String message){
        this.server.sendMessage(Text.literal(message));
    }
}
