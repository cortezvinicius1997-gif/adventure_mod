package com.cortez.adventuremod.effect;

import com.cortez.adventuremod.util.GetNBTData;
import com.cortez.adventuremod.util.IEntityDataSaver;
import com.cortez.adventuremod.AdventureMod;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

public class MontainSickness extends StatusEffect
{
    public MontainSickness(StatusEffectCategory category, int color) {
        super(category, color);
    }


    @Override
    public boolean applyUpdateEffect(LivingEntity pLivingEntity, int amplifier)
    {
        if (!pLivingEntity.getEntityWorld().isClient()) {

            BlockPos playerPos = pLivingEntity.getBlockPos();
            IEntityDataSaver dataPlayer = (IEntityDataSaver) pLivingEntity;

            GetNBTData getNBTData = new GetNBTData(dataPlayer);

            String oxigenStr = getNBTData.getString("Oxigen").orElse("3600s");
            int oxigen = Integer.parseInt(oxigenStr.replace("s", ""));

            if (playerPos.getY() >= 128 && playerPos.getY() < 280)
            {

                var pos = pLivingEntity.getBlockPos();
                int x = pos.getX();
                int y = pos.getY();
                int z = pos.getZ();

                // lê posição anterior
                int lastX = getNBTData.getInt("lastX").orElse(x);
                int lastY = getNBTData.getInt("lastY").orElse(y);
                int lastZ = getNBTData.getInt("lastZ").orElse(z);

                // detecta movimento real (mudança de bloco)
                boolean moving = x != lastX || y != lastY || z != lastZ;
                boolean correndo = pLivingEntity.isSprinting();

                if (moving && pLivingEntity instanceof ServerPlayerEntity player) {
                    if(oxigen > 0){
                        oxigen -= 1;
                    }else if (correndo){
                        AdventureMod.LOGGER.info("Correndo");
                        oxigen -= 2;
                    }
                }

                // atualiza posição
                dataPlayer.getPersistentData().putInt("lastX", x);
                dataPlayer.getPersistentData().putInt("lastY", y);
                dataPlayer.getPersistentData().putInt("lastZ", z);


            } else if (playerPos.getY() >= 144) {
                if(oxigen > 0){
                    oxigen -= 1;
                }
            }else if (playerPos.getY() >= 200){
                if(oxigen > 0){
                    oxigen -= 2;
                }
            }

            dataPlayer.getPersistentData().putString("Oxigen", oxigen + "s");
            AdventureMod.LOGGER.info("Oxigen: " + oxigen);

            // sincroniza com HUD de bolhas sem usar mecânica de mergulho
            int maxAirHud = pLivingEntity.getMaxAir(); // normalmente 300
            int airHud = (int)((oxigen / 3600.0) * maxAirHud);

            if (airHud <= 0) airHud = 1; // impede dano de sufocamento

            if (oxigen == 0){
                if(!pLivingEntity.hasStatusEffect(ModEffects.PULMONARYEDEMA)) {
                    pLivingEntity.addStatusEffect(new StatusEffectInstance(
                            ModEffects.PULMONARYEDEMA,
                            8000,
                            0,
                            true,
                            false
                    ));
                }
            }

            pLivingEntity.setAir(airHud);

        }
        return super.applyUpdateEffect(pLivingEntity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
