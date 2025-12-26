package com.cortez.adventuremod.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class CerebralEdma extends StatusEffect
{
    public CerebralEdma(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity pLivingEntity, int amplifier)
    {
        if (!pLivingEntity.getEntityWorld().isClient()) {
            pLivingEntity.damage(pLivingEntity.getDamageSources().drown(), 3.0F);
        }
        return super.applyUpdateEffect(pLivingEntity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
