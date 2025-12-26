package com.cortez.adventuremod.event;

import com.cortez.adventure_seasons.lib.AdventureSeason;
import com.cortez.adventuremod.AdventureMod;
import com.cortez.adventuremod.config.AdventureConfig;
import com.cortez.adventuremod.effect.ModEffects;
import com.cortez.adventuremod.network.ThirstSyncPayload;
import com.cortez.adventuremod.seasons.ISeasonHandler;
import com.cortez.adventuremod.util.GetNBTData;
import com.cortez.adventuremod.util.IEntityDataSaver;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

public class PlayerTickHandler implements ServerTickEvents.StartTick {

    private final ISeasonHandler seasonHandler;


    public PlayerTickHandler(ISeasonHandler seasonHandler) {
        this.seasonHandler = seasonHandler;
    }


    @Override
    public void onStartTick(MinecraftServer server) {
        initSeason(server);
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            if (!player.isFallFlying()){
                if (!player.isCreative()) {
                    if (AdventureConfig.isAltitudeEffect()) {
                        altitudeMonit(player);
                    }

                    if (AdventureConfig.isThirst()) {
                        thirstSystem(player);
                    }
                }
            }
        }
    }

    private void initSeason(MinecraftServer server)
    {
        seasonHandler.tick(server);
    }

    private void altitudeMonit(ServerPlayerEntity player){
        BlockPos playerPos = player.getBlockPos();

        IEntityDataSaver dataPlayer = (IEntityDataSaver) player;


        GetNBTData getNBTData = new GetNBTData(dataPlayer);



        if (player.isDead()) {
            dataPlayer.getPersistentData().putString("Oxigen", 3600 + "s");
        }

        String oxigenStr = getNBTData.getString("Oxigen").orElse("3600s");
        int oxigen = Integer.parseInt(oxigenStr.replace("s", ""));

        int acclimatization = getNBTData.getInt("acclimatization").orElse(0);

        if (AdventureConfig.isDebug()) {
            AdventureMod.LOGGER.info("Aclimatation: " + acclimatization);
        }

        // mantém seu código original
        if (playerPos.getY() >= 128 && playerPos.getY() < 141) {
            if(acclimatization == 0){
                if (!player.hasStatusEffect(ModEffects.MONTAINSICKNESS)) {
                    player.addStatusEffect(new StatusEffectInstance(
                            ModEffects.MONTAINSICKNESS,
                            8000,
                            0,
                            true,
                            false
                    ));
                }

                if (!player.hasStatusEffect(StatusEffects.WEAKNESS)) {
                    player.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.WEAKNESS,
                            800,
                            0,
                            true,
                            false
                    ));
                }
            }else{
                if (player.hasStatusEffect(ModEffects.MONTAINSICKNESS)) {
                    player.removeStatusEffect(ModEffects.MONTAINSICKNESS);
                }

                if (player.hasStatusEffect(ModEffects.PULMONARYEDEMA)) {
                    player.removeStatusEffect(ModEffects.PULMONARYEDEMA);
                }

                if (player.hasStatusEffect(ModEffects.CEREBRALEDEMA)) {
                    player.removeStatusEffect(ModEffects.CEREBRALEDEMA);
                }

                if (oxigen < 3600) {
                    oxigen += 1;
                }
            }
        }else if(playerPos.getY() >= 141 && playerPos.getY() < 191){
            if(acclimatization == 0){
                if (!player.hasStatusEffect(ModEffects.MONTAINSICKNESS)) {
                    player.addStatusEffect(new StatusEffectInstance(
                            ModEffects.MONTAINSICKNESS,
                            8000,
                            0,
                            true,
                            false
                    ));
                }

                if (!player.hasStatusEffect(StatusEffects.WEAKNESS)) {
                    player.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.WEAKNESS,
                            800,
                            0,
                            true,
                            false
                    ));
                }

                if (!player.hasStatusEffect(StatusEffects.SLOWNESS)) {
                    player.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.SLOWNESS,
                            800,
                            0,
                            true,
                            false
                    ));
                }

                if (!player.hasStatusEffect(StatusEffects.NAUSEA)) {
                    player.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.NAUSEA,
                            800,
                            0,
                            true,
                            false
                    ));
                }
            }else{
                if (player.hasStatusEffect(ModEffects.MONTAINSICKNESS)) {
                    player.removeStatusEffect(ModEffects.MONTAINSICKNESS);
                }

                if (player.hasStatusEffect(ModEffects.PULMONARYEDEMA)) {
                    player.removeStatusEffect(ModEffects.PULMONARYEDEMA);
                }

                if (player.hasStatusEffect(ModEffects.CEREBRALEDEMA)) {
                    player.removeStatusEffect(ModEffects.CEREBRALEDEMA);
                }

                if (oxigen < 3600) {
                    oxigen += 1;
                }

                dataPlayer.getPersistentData().putString("Oxigen", oxigen + "s");
                if (AdventureConfig.isDebug()) {
                    AdventureMod.LOGGER.info("Oxigen: " + oxigen);
                }

                // se estiver na água, NÃO alterar o ar
                if (!player.isSubmergedInWater() && !player.isTouchingWater()) {

                    int maxAirHud = player.getMaxAir();
                    int airHud = (int) ((oxigen / 3600.0) * maxAirHud);

                    player.setAir(airHud);
                }else {
                    int maxAirHud = player.getMaxAir();
                    if (AdventureConfig.isDebug()){
                        AdventureMod.LOGGER.info("Oxigen: " + maxAirHud);
                    }
                    dataPlayer.getPersistentData().putString("Oxigen", 3600 + "s");
                }
            }

        }else if (playerPos.getY() >= 191 && playerPos.getY() < 270){
            if(acclimatization == 0){
                if (!player.hasStatusEffect(ModEffects.PULMONARYEDEMA)) {
                    player.addStatusEffect(new StatusEffectInstance(
                            ModEffects.PULMONARYEDEMA,
                            8000,
                            0,
                            true,
                            false
                    ));
                }

                if (!player.hasStatusEffect(StatusEffects.WEAKNESS)) {
                    player.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.WEAKNESS,
                            800,
                            0,
                            true,
                            false
                    ));
                }

                if (!player.hasStatusEffect(StatusEffects.SLOWNESS)) {
                    player.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.SLOWNESS,
                            800,
                            0,
                            true,
                            false
                    ));
                }

                if (!player.hasStatusEffect(StatusEffects.NAUSEA)) {
                    player.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.NAUSEA,
                            800,
                            0,
                            true,
                            false
                    ));
                }
            }else if (acclimatization == 1){
                if (!player.hasStatusEffect(ModEffects.MONTAINSICKNESS)) {
                    player.addStatusEffect(new StatusEffectInstance(
                            ModEffects.MONTAINSICKNESS,
                            8000,
                            0,
                            true,
                            false
                    ));
                }

                if (!player.hasStatusEffect(StatusEffects.WEAKNESS)) {
                    player.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.WEAKNESS,
                            800,
                            0,
                            true,
                            false
                    ));
                }

                if (!player.hasStatusEffect(StatusEffects.SLOWNESS)) {
                    player.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.SLOWNESS,
                            800,
                            0,
                            true,
                            false
                    ));
                }

                if (!player.hasStatusEffect(StatusEffects.NAUSEA)) {
                    player.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.NAUSEA,
                            800,
                            0,
                            true,
                            false
                    ));
                }
            }else{
                if (player.hasStatusEffect(ModEffects.MONTAINSICKNESS)) {
                    player.removeStatusEffect(ModEffects.MONTAINSICKNESS);
                }

                if (player.hasStatusEffect(ModEffects.PULMONARYEDEMA)) {
                    player.removeStatusEffect(ModEffects.PULMONARYEDEMA);
                }

                if (oxigen < 3600) {
                    oxigen += 1;
                }

                dataPlayer.getPersistentData().putString("Oxigen", oxigen + "s");
                if (AdventureConfig.isDebug()) {
                    AdventureMod.LOGGER.info("Oxigen: " + oxigen);
                }

                // se estiver na água, NÃO alterar o ar
                if (!player.isSubmergedInWater() && !player.isTouchingWater()) {

                    int maxAirHud = player.getMaxAir();
                    int airHud = (int) ((oxigen / 3600.0) * maxAirHud);

                    player.setAir(airHud);
                }else {
                    int maxAirHud = player.getMaxAir();
                    if (AdventureConfig.isDebug()) {
                        AdventureMod.LOGGER.info("Oxigen: " + maxAirHud);
                    }

                    dataPlayer.getPersistentData().putString("Oxigen", 3600 + "s");
                }
            }
        }else if(playerPos.getY() >= 270) {
            if(acclimatization == 0){
                if (!player.hasStatusEffect(ModEffects.CEREBRALEDEMA)) {
                    player.addStatusEffect(new StatusEffectInstance(
                            ModEffects.CEREBRALEDEMA,
                            8000,
                            0,
                            true,
                            false
                    ));
                }

                if (!player.hasStatusEffect(StatusEffects.WEAKNESS)) {
                    player.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.WEAKNESS,
                            800,
                            0,
                            true,
                            false
                    ));
                }

                if (!player.hasStatusEffect(StatusEffects.SLOWNESS)) {
                    player.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.SLOWNESS,
                            800,
                            0,
                            true,
                            false
                    ));
                }

                if (!player.hasStatusEffect(StatusEffects.NAUSEA)) {
                    player.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.NAUSEA,
                            800,
                            0,
                            true,
                            false
                    ));
                }
            }else if(acclimatization == 1){
                if (!player.hasStatusEffect(ModEffects.PULMONARYEDEMA)) {
                    player.addStatusEffect(new StatusEffectInstance(
                            ModEffects.PULMONARYEDEMA,
                            8000,
                            0,
                            true,
                            false
                    ));
                }

                if (!player.hasStatusEffect(StatusEffects.WEAKNESS)) {
                    player.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.WEAKNESS,
                            800,
                            0,
                            true,
                            false
                    ));
                }

                if (!player.hasStatusEffect(StatusEffects.SLOWNESS)) {
                    player.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.SLOWNESS,
                            800,
                            0,
                            true,
                            false
                    ));
                }

                if (!player.hasStatusEffect(StatusEffects.NAUSEA)) {
                    player.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.NAUSEA,
                            800,
                            0,
                            true,
                            false
                    ));
                }
            }else if (acclimatization == 2){
                if (!player.hasStatusEffect(StatusEffects.WEAKNESS)) {
                    player.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.WEAKNESS,
                            800,
                            0,
                            true,
                            false
                    ));
                }

                if (!player.hasStatusEffect(StatusEffects.SLOWNESS)) {
                    player.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.SLOWNESS,
                            800,
                            0,
                            true,
                            false
                    ));
                }

                if (!player.hasStatusEffect(StatusEffects.NAUSEA)) {
                    player.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.NAUSEA,
                            800,
                            0,
                            true,
                            false
                    ));
                }
            }else {
                if (player.hasStatusEffect(ModEffects.MONTAINSICKNESS)) {
                    player.removeStatusEffect(ModEffects.MONTAINSICKNESS);
                }

                if (player.hasStatusEffect(ModEffects.PULMONARYEDEMA)) {
                    player.removeStatusEffect(ModEffects.PULMONARYEDEMA);
                }

                if (player.hasStatusEffect(ModEffects.CEREBRALEDEMA)) {
                    player.removeStatusEffect(ModEffects.CEREBRALEDEMA);
                }

                if (oxigen < 3600) {
                    oxigen += 1;
                }

                dataPlayer.getPersistentData().putString("Oxigen", oxigen + "s");
                if (AdventureConfig.isDebug()) {
                    AdventureMod.LOGGER.info("Oxigen: " + oxigen);
                }

                // se estiver na água, NÃO alterar o ar
                if (!player.isSubmergedInWater() && !player.isTouchingWater()) {

                    int maxAirHud = player.getMaxAir();
                    int airHud = (int) ((oxigen / 3600.0) * maxAirHud);

                    player.setAir(airHud);
                }else {
                    int maxAirHud = player.getMaxAir();
                    if (AdventureConfig.isDebug()) {
                        AdventureMod.LOGGER.info("Oxigen: " + maxAirHud);
                    }
                    dataPlayer.getPersistentData().putString("Oxigen", 3600 + "s");
                }
            }
        }else {

            if (player.hasStatusEffect(ModEffects.MONTAINSICKNESS)) {
                player.removeStatusEffect(ModEffects.MONTAINSICKNESS);
            }

            if (player.hasStatusEffect(ModEffects.PULMONARYEDEMA)) {
                player.removeStatusEffect(ModEffects.PULMONARYEDEMA);
            }

            if (player.hasStatusEffect(ModEffects.CEREBRALEDEMA)) {
                player.removeStatusEffect(ModEffects.CEREBRALEDEMA);
            }

            if (oxigen < 3600) {
                oxigen += 1;
            }

            dataPlayer.getPersistentData().putString("Oxigen", oxigen + "s");
            if (AdventureConfig.isDebug()) {
                AdventureMod.LOGGER.info("Oxigen: " + oxigen);
            }

            // se estiver na água, NÃO alterar o ar
            if (!player.isSubmergedInWater() && !player.isTouchingWater()) {

                int maxAirHud = player.getMaxAir();
                int airHud = (int) ((oxigen / 3600.0) * maxAirHud);

                player.setAir(airHud);
            }else {
                int maxAirHud = player.getMaxAir();
                if (AdventureConfig.isDebug()) {
                    AdventureMod.LOGGER.info("Oxigen: " + maxAirHud);
                }
                dataPlayer.getPersistentData().putString("Oxigen", 3600 + "s");
            }
        }

        // sincronização do HUD
        if (playerPos.getY() < 128) {

            dataPlayer.getPersistentData().putString("Oxigen", oxigen + "s");
            if (AdventureConfig.isDebug()) {
                AdventureMod.LOGGER.info("Oxigen: " + oxigen);
            }

            // se estiver na água, NÃO alterar o ar
            if (!player.isSubmergedInWater() && !player.isTouchingWater()) {

                int maxAirHud = player.getMaxAir();
                int airHud = (int) ((oxigen / 2600.0) * maxAirHud);

                player.setAir(airHud);
            }else {
                int maxAirHud = player.getMaxAir();
                if(AdventureConfig.isDebug()) {
                    AdventureMod.LOGGER.info("Oxigen: " + maxAirHud);
                }
                dataPlayer.getPersistentData().putString("Oxigen", 2600 + "s");
            }

        }
    }

    private void thirstSystem(ServerPlayerEntity player){

        IEntityDataSaver data = (IEntityDataSaver) player;

        GetNBTData getNBTData = new GetNBTData(data);

        int thirst = getNBTData.getInt("thirst").orElse(20);
        float saturation = getNBTData.getFloat("thirst_saturation").orElse(5.0F);
        float exhaustion = getNBTData.getFloat("thirst_exhaustion").orElse(0.0F);

        if(player.isDead()){
            thirst = 20;
            saturation = 5.0F;
            exhaustion = 0.0F;

            data.getPersistentData().putInt("thirst", thirst);
            data.getPersistentData().putFloat("thirst_saturation", saturation);
            data.getPersistentData().putFloat("thirst_exhaustion", exhaustion);
        }


        if (AdventureConfig.isDebug()) {
            AdventureMod.LOGGER.info("Thirst: " + thirst);
            AdventureMod.LOGGER.info("saturation: " + saturation);
            AdventureMod.LOGGER.info("exhaustion: " + exhaustion);
        }

        // posição antiga
        double oldX = data.getPersistentData().getDouble("oldX");
        double oldY = data.getPersistentData().getDouble("oldY");
        double oldZ = data.getPersistentData().getDouble("oldZ");

        double dx = player.getX() - oldX;
        double dy = player.getY() - oldY;
        double dz = player.getZ() - oldZ;

        boolean moved = dx != 0 || dy != 0 || dz != 0;
        boolean sprinting = player.isSprinting();
        boolean swimming = player.isSwimming();

        // detecta salto no servidor
        boolean wasOnGround = data.getPersistentData().getBoolean("wasOnGround");
        boolean jumping = wasOnGround && !player.isOnGround();
        data.getPersistentData().putBoolean("wasOnGround", player.isOnGround());

        if (moved){
            exhaustion += 0.01F;

            if (sprinting) {
                exhaustion += 0.1F;
            }

            if (swimming) {
                exhaustion += 0.02F;
            }

            if (jumping) {
                exhaustion += 0.05F;
            }
        }

        // igual ao sistema de fome vanilla
        if (exhaustion > 4.0F){
            exhaustion -= 4.0F;

            if (saturation > 0){
                saturation -= 1.0F;
                if (saturation < 0){
                    saturation = 0;
                }
            } else {
                thirst -= 1;
                if (thirst < 0) thirst = 0;
            }
        }

        // dano ao zerar sede
        if (thirst <= 0){
            player.damage(player.getDamageSources().drown(), 1.0F);
        }

        // salvar novos valores
        data.getPersistentData().putInt("thirst", thirst);
        data.getPersistentData().putFloat("thirst_saturation", saturation);
        data.getPersistentData().putFloat("thirst_exhaustion", exhaustion);

        data.getPersistentData().putDouble("oldX", player.getX());
        data.getPersistentData().putDouble("oldY", player.getY());
        data.getPersistentData().putDouble("oldZ", player.getZ());

        ServerPlayNetworking.send(
                player,
                new ThirstSyncPayload(thirst, saturation, exhaustion)
        );
    }


}
