package com.cortez.adventuremod.world;

import com.cortez.adventuremod.AdventureMod;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;

import java.util.List;

public class ModPlacedFeatures {

    public static final RegistryKey<PlacedFeature> NETHER_COAL_ORE_PLACED_KEY = registerKey("nether_coal_ore_placed");
    public static final RegistryKey<PlacedFeature> END_COAL_ORE_PLACED_KEY = registerKey("end_coal_ore_placed");

    public static final RegistryKey<PlacedFeature> NETHER_IRON_ORE_PLACED_KEY = registerKey("nether_iron_ore_placed");
    public static final RegistryKey<PlacedFeature> END_IRON_ORE_PLACED_KEY = registerKey("end_iron_ore_placed");


    public static final RegistryKey<PlacedFeature> NETHER_COPPER_ORE_PLACED_KEY = registerKey("nether_copper_ore_placed");

    public static final RegistryKey<PlacedFeature> END_COPPER_ORE_PLACED_KEY = registerKey("end_copper_ore_placed");

    public static final RegistryKey<PlacedFeature> END_GOLD_ORE_PLACED_KEY = registerKey("end_gold_ore_placed");

    public static final RegistryKey<PlacedFeature> NETHER_REDSTONE_ORE_PLACED_KEY = registerKey("nether_redstone_ore_placed");

    public static final RegistryKey<PlacedFeature> END_REDSTONE_ORE_PLACED_KEY = registerKey("end_redstone_ore_placed");


    public static final RegistryKey<PlacedFeature> NETHER_LAPISLAZULI_ORE_PLACED_KEY = registerKey("nether_lapislazuli_ore_placed");

    public static final RegistryKey<PlacedFeature> END_LAPISLAZULI_ORE_PLACED_KEY = registerKey("end_lapislazuli_ore_placed");


    public static final RegistryKey<PlacedFeature> NETHER_DIAMOND_ORE_PLACED_KEY = registerKey("nether_diamond_ore_placed");

    public static final RegistryKey<PlacedFeature> END_DIAMOND_ORE_PLACED_KEY = registerKey("end_diamond_ore_placed");

    public static final RegistryKey<PlacedFeature> NETHER_EMERALD_ORE_PLACED_KEY = registerKey("nether_emerald_ore_placed");

    public static final RegistryKey<PlacedFeature> END_EMERALD_ORE_PLACED_KEY = registerKey("end_emerald_ore_placed");

    public static void bootstrap(Registerable<PlacedFeature> context){
        var configuredFeatures = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(context, NETHER_COAL_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.NETHER_COAL_ORE_KEY),
                ModOrePlacement.modifiersWithCount(15, // Veins per Chunk
                        HeightRangePlacementModifier.uniform(YOffset.fixed(25), YOffset.fixed(115))));


        register(context, END_COAL_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.END_COAL_ORE_KEY),
                ModOrePlacement.modifiersWithCount(5, // Veins per Chunk
                        HeightRangePlacementModifier.uniform(YOffset.fixed(25), YOffset.fixed(55))));

        register(context, NETHER_IRON_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.NETHER_IRON_ORE_KEY),
                ModOrePlacement.modifiersWithCount(10, // Veins per Chunk
                        HeightRangePlacementModifier.uniform(YOffset.fixed(40), YOffset.fixed(110))));


        register(context, END_IRON_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.END_IRON_ORE_KEY),
                ModOrePlacement.modifiersWithCount(5, // Veins per Chunk
                        HeightRangePlacementModifier.uniform(YOffset.fixed(40), YOffset.fixed(65))));


        register(context, NETHER_COPPER_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.NETHER_COPPER_ORE_KEY),
                ModOrePlacement.modifiersWithCount(8, // Veins per Chunk
                        HeightRangePlacementModifier.uniform(YOffset.fixed(35), YOffset.fixed(75))));

        register(context, END_COPPER_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.END_COPPER_ORE_KEY),
                ModOrePlacement.modifiersWithCount(5, // Veins per Chunk
                        HeightRangePlacementModifier.uniform(YOffset.fixed(35), YOffset.fixed(75))));

        register(context, END_GOLD_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.END_GOLD_ORE_KEY),
                ModOrePlacement.modifiersWithCount(4, // Veins per Chunk
                        HeightRangePlacementModifier.uniform(YOffset.fixed(30), YOffset.fixed(65))));


        register(context, NETHER_REDSTONE_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.NETHER_REDSTONE_ORE_KEY),
                ModOrePlacement.modifiersWithCount(8, // Veins per Chunk
                        HeightRangePlacementModifier.uniform(YOffset.fixed(80), YOffset.fixed(115))));

        register(context, END_REDSTONE_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.END_REDSTONE_ORE_KEY),
                ModOrePlacement.modifiersWithCount(3, // Veins per Chunk
                        HeightRangePlacementModifier.uniform(YOffset.fixed(35), YOffset.fixed(75))));

        register(context, NETHER_LAPISLAZULI_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.NETHER_LAPISLAZULI_ORE_KEY),
                ModOrePlacement.modifiersWithCount(2, // Veins per Chunk
                        HeightRangePlacementModifier.uniform(YOffset.fixed(30), YOffset.fixed(60))));

        register(context, END_LAPISLAZULI_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.END_LAPISLAZULI_ORE_KEY),
                ModOrePlacement.modifiersWithCount(2, // Veins per Chunk
                        HeightRangePlacementModifier.uniform(YOffset.fixed(30), YOffset.fixed(60))));


        register(context, NETHER_DIAMOND_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.NETHER_DIAMOND_ORE_KEY),
                ModOrePlacement.modifiersWithCount(3, // Veins per Chunk
                        HeightRangePlacementModifier.uniform(YOffset.fixed(15), YOffset.fixed(30))));

        register(context, END_DIAMOND_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.END_DIAMOND_ORE_KEY),
                ModOrePlacement.modifiersWithCount(3, // Veins per Chunk
                        HeightRangePlacementModifier.uniform(YOffset.fixed(30), YOffset.fixed(60))));

        register(context, NETHER_EMERALD_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.NETHER_EMERALD_ORE_KEY),
                ModOrePlacement.modifiersWithCount(2, // Veins per Chunk
                        HeightRangePlacementModifier.uniform(YOffset.fixed(25), YOffset.fixed(55))));

        register(context, END_EMERALD_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.END_EMERALD_ORE_KEY),
                ModOrePlacement.modifiersWithCount(2, // Veins per Chunk
                        HeightRangePlacementModifier.uniform(YOffset.fixed(25), YOffset.fixed(55))));
    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(AdventureMod.MODID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key,
                                                                                   RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                                                                   PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }
}
