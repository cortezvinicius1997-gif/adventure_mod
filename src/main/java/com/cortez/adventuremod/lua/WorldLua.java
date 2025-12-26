package com.cortez.adventuremod.lua;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.explosion.ExplosionBehavior;

public class WorldLua
{
    private ServerWorld world;
    private ServerPlayerEntity player;

    public WorldLua(ServerWorld world, ServerPlayerEntity player){
        this.world = world;
        this.player = player;
    }

    public long getSeed(){
        return this.world.getSeed();
    }

    public void createExplosion(double x, double y, double z, float power){
        DamageSource damageSource = world.getDamageSources().explosion(this.player, this.player);
        ExplosionBehavior behavior = new ExplosionBehavior();
        this.world.createExplosion(this.player, damageSource, behavior, x, y, z, power, false, World.ExplosionSourceType.MOB);
    }

    public RegistryKey<World> getDimenssion(String type){
        switch (type){
            case "overworld":
                return World.OVERWORLD;
            case "nether":
                return World.NETHER;
            case "end":
                return World.END;
            default:
                return World.OVERWORLD;
        }

    }


}
