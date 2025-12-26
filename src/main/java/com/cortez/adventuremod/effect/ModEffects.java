package com.cortez.adventuremod.effect;

import com.cortez.adventuremod.AdventureMod;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModEffects {
    public static final RegistryEntry<StatusEffect> MONTAINSICKNESS = registerStatusEffect("montain_sickness",
            new MontainSickness(StatusEffectCategory.NEUTRAL, 0x36ebab)
                    .addAttributeModifier(EntityAttributes.GENERIC_ARMOR,
                            Identifier.of(AdventureMod.MODID, "montain_sickness"), -0.25f,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    public static final RegistryEntry<StatusEffect> PULMONARYEDEMA = registerStatusEffect("pulmonary_edema",
            new PulmonaryEdema(StatusEffectCategory.NEUTRAL, 0x40eaba)
                    .addAttributeModifier(EntityAttributes.GENERIC_ARMOR,
                            Identifier.of(AdventureMod.MODID, "pulmonary_edema"), -0.25f,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    public static final RegistryEntry<StatusEffect> CEREBRALEDEMA = registerStatusEffect("cerebral_edema",
            new CerebralEdma(StatusEffectCategory.NEUTRAL, 0x40eaba)
                    .addAttributeModifier(EntityAttributes.GENERIC_ARMOR,
                            Identifier.of(AdventureMod.MODID, "cerebral_edema"), -0.25f,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));


    private static RegistryEntry<StatusEffect> registerStatusEffect(String name, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(AdventureMod.MODID, name), statusEffect);
    }

    public static void registerEffects() {
        AdventureMod.LOGGER.info("Registering Mod Effects for " + AdventureMod.MODID);
    }
}