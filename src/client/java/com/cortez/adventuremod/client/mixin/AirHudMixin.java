package com.cortez.adventuremod.client.mixin;


import com.cortez.adventuremod.config.AdventureConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(InGameHud.class)
public class AirHudMixin {

    // ajuste para posicionar exatamente onde quiser
    private static final int AIR_Y_OFFSET = 10;

    @Redirect(
            method = "renderStatusBars",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V"
            )
    )
    private void adventuremod_moveAir(DrawContext context, Identifier id, int x, int y, int width, int height) {


        Identifier air = InGameHudAccessor.getAirTexture();
        Identifier burst = InGameHudAccessor.getAirBurstingTexture();

        if (id.equals(air) || id.equals(burst)) {
            int newY = y - AIR_Y_OFFSET;

            if (AdventureConfig.isThirst()){
                context.drawGuiTexture(id, x, newY, width, height);
            }else {
                context.drawGuiTexture(id, x, y, width, height);
            }
        } else {
            context.drawGuiTexture(id, x, y, width, height);
        }
    }
}