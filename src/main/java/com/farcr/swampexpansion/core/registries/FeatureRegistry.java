package com.farcr.swampexpansion.core.registries;

import com.farcr.swampexpansion.common.world.gen.feature.CattailsFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "swampexpansion", bus = Mod.EventBusSubscriber.Bus.MOD)
public class FeatureRegistry {
    public static Feature CATTAILS = new CattailsFeature(NoFeatureConfig::deserialize).setRegistryName("cattails");

    @SubscribeEvent
    public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
        //FlowerVillagePools.init();
        event.getRegistry().registerAll(
                CATTAILS
        );
    }

    public static void generateFeatures() {
        CattailsFeature.addFeature();
    }
}