package com.cortez.adventuremod.items.custom;

import com.cortez.adventuremod.AdventureMod;
import com.cortez.adventuremod.util.GetNBTData;
import com.cortez.adventuremod.util.IEntityDataSaver;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class MagicFruit extends Item
{
    public MagicFruit(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        player.setCurrentHand(hand);
        return super.use(world, player, hand);
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 32;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.EAT;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity entity) {
        if (entity == null || !(entity instanceof PlayerEntity)) {
            throw new IllegalArgumentException("Entity must be a player entity");
        }

        PlayerEntity player = (PlayerEntity) entity;

        if (!world.isClient) {
            // fome
            player.getHungerManager().add(8, 0.8F);

            // sede apenas no servidor
            if (player instanceof ServerPlayerEntity serverPlayer) {
                restoreThirst(serverPlayer, 8, 8.0F);
            }
        }

        // Toca o som de comer.
        player.playSound(SoundEvents.ENTITY_GENERIC_EAT, 1.0F, 1.0F);

        return super.finishUsing(stack, world, entity);
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
