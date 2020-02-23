package com.farcr.swampexpansion.core.registries;

import com.farcr.swampexpansion.common.world.gen.feature.CattailsFeature;
import com.farcr.swampexpansion.common.world.gen.feature.HangingVinesFeature;

import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "swampexpansion", bus = Mod.EventBusSubscriber.Bus.MOD)
@SuppressWarnings("rawtypes")
public class SwampExFeatures {
	public static Feature CATTAILS = new CattailsFeature(NoFeatureConfig::deserialize).setRegistryName("cattails");
	public static Feature HANGING_WILLOW_VINES = new HangingVinesFeature(NoFeatureConfig::deserialize).setRegistryName("hanging_willow_vines");

    @SubscribeEvent
    public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
        //FlowerVillagePools.init();
        event.getRegistry().registerAll(
                CATTAILS, HANGING_WILLOW_VINES
        );
    }

    public static void generateFeatures() {
        CattailsFeature.addFeature();
        HangingVinesFeature.addFeature();
    }
}