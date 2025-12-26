package com.cortez.adventuremod.mixin;

import com.cortez.adventuremod.AdventureMod;
import com.cortez.adventuremod.util.GetNBTData;
import com.cortez.adventuremod.util.IEntityDataSaver;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class DrinkWaterMixin {

    @Inject(method = "consumeItem", at = @At("HEAD"))
    private void onConsumeItem(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;

        // Verifica se é um jogador no servidor
        if (entity instanceof ServerPlayerEntity player) {
            ItemStack stack = player.getActiveItem();

            // Verifica se é uma poção
            if (stack.isOf(Items.POTION)) {
                PotionContentsComponent potionContents = stack.get(DataComponentTypes.POTION_CONTENTS);

                // Verifica se é água pura (sem efeitos ou potion type WATER)
                if (potionContents != null) {
                    boolean isWater = potionContents.potion().isEmpty() ||
                            potionContents.potion().get().equals(Potions.WATER);

                    if (isWater) {
                        restoreThirst(player, 6, 6.0F);

                        // Log para debug
                        AdventureMod.LOGGER.info("Player " + player.getName().getString() + " drank water!");
                    }
                }
            }
        }
    }

    private void restoreThirst(ServerPlayerEntity player, int thirstAmount, float saturationAmount) {
        IEntityDataSaver data = (IEntityDataSaver) player;

        GetNBTData getNBTData = new GetNBTData(data);

        int currentThirst = getNBTData.getInt("thirst").orElse(20);
        float currentSaturation = getNBTData.getFloat("thirst_saturation").orElse(5.0F);

        // Adiciona sede (máximo 20)
        int newThirst = Math.min(currentThirst + thirstAmount, 20);

        // Adiciona saturação (máximo = thirst atual)
        float newSaturation = Math.min(currentSaturation + saturationAmount, newThirst);

        // Salva os novos valores
        data.getPersistentData().putInt("thirst", newThirst);
        data.getPersistentData().putFloat("thirst_saturation", newSaturation);

        AdventureMod.LOGGER.info("Thirst restored! Thirst: " + newThirst + ", Saturation: " + newSaturation);
    }
}