package com.cortez.adventuremod.client.mixin;


import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(InGameHud.class)
public interface InGameHudAccessor {

    @Accessor("AIR_TEXTURE")
    static Identifier getAirTexture() {
        throw new AssertionError();
    }

    @Accessor("AIR_BURSTING_TEXTURE")
    static Identifier getAirBurstingTexture() {
        throw new AssertionError();
    }
}