package com.cortez.adventuremod.lua;

import com.cortez.adventuremod.AdventureMod;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.jse.JsePlatform; // Make sure this is imported
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.util.Random;

public class AdventureLua {
    private Globals globals;

    public AdventureLua() {
        // initializing with standardGlobals loads the compiler and base libs
        this.globals = JsePlatform.standardGlobals();

        javaApi();

        LoggerWrapper wrapper = new LoggerWrapper();
        LuaValue luaLogger = CoerceJavaToLua.coerce(wrapper);
        this.globals.set("logger", luaLogger);
    }

    public AdventureLua(MinecraftServer server, ServerWorld world, ServerPlayerEntity player) {
        this.globals = JsePlatform.standardGlobals();

        globals.set("logger", CoerceJavaToLua.coerce(new LoggerWrapper()));

        javaApi();

        boolean isOp = server.getPlayerManager().isOperator(player.getGameProfile());


        if (isOp) {
            globals.set("server", CoerceJavaToLua.coerce(server));
            globals.set("world", CoerceJavaToLua.coerce(world));
            globals.set("player", CoerceJavaToLua.coerce(player));
            globals.set("message", CoerceJavaToLua.coerce(new Message()));
        }else{
            globals.set("player", CoerceJavaToLua.coerce(new PlayerLua(player)));
            globals.set("world", CoerceJavaToLua.coerce(new WorldLua(world, player)));
            globals.set("server", CoerceJavaToLua.coerce(new ServerLua(server)));
        }

    }


    public AdventureLua(ServerWorld world, ServerPlayerEntity player) {
        this.globals = JsePlatform.standardGlobals();

        globals.set("logger",CoerceJavaToLua.coerce(new LoggerWrapper()));

        javaApi();
        boolean isOp = world.getServer().getPlayerManager().isOperator(player.getGameProfile());



        if(isOp) {
            globals.set("world", CoerceJavaToLua.coerce(world));
            globals.set("player", CoerceJavaToLua.coerce(player));
            globals.set("message", CoerceJavaToLua.coerce(new Message()));
            globals.set("CREATIVE", CoerceJavaToLua.coerce(GameMode.CREATIVE));
            globals.set("SURVIVAL", CoerceJavaToLua.coerce(GameMode.SURVIVAL));
            globals.set("ADVENTURE", CoerceJavaToLua.coerce(GameMode.ADVENTURE));
            globals.set("SPECTATOR", CoerceJavaToLua.coerce(GameMode.SPECTATOR));
        }else{
            globals.set("player", CoerceJavaToLua.coerce(new PlayerLua(player)));
            globals.set("world", CoerceJavaToLua.coerce(new WorldLua(world, player)));
            globals.set("server", CoerceJavaToLua.coerce(new ServerLua(world.getServer())));
        }
    }

    private void javaApi() {
        globals.set("Math", CoerceJavaToLua.coerce(Math.class));
        globals.set("random", CoerceJavaToLua.coerce(new Random()));
    }


    public void execLua(String code) {
        try {
            LuaValue chunk = this.globals.load(code);
            chunk.call();
        } catch (LuaError e) {
            AdventureMod.LOGGER.error("Lua execution failed: " + e.getMessage());
        }
    }





    public static class Message {
        public void send(ServerPlayerEntity player, String message){
            player.sendMessage(Text.literal(message));
        }
    }

    public static class LoggerWrapper {
        public void info(String message) {
            AdventureMod.LOGGER.info(message);
        }

        public void warn(String message) {
            AdventureMod.LOGGER.warn(message);
        }

        public void error(String message) {
            AdventureMod.LOGGER.error(message);
        }
    }
}