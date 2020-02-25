package com.farcr.swampexpansion.core.registries;

import com.farcr.swampexpansion.common.world.gen.feature.CattailsFeature;
import com.farcr.swampexpansion.common.world.gen.feature.HangingWillowLeavesFeature;
import com.farcr.swampexpansion.common.world.gen.feature.WillowTreeFeature;

import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "swampexpansion", bus = Mod.EventBusSubscriber.Bus.MOD)
@SuppressWarnings("rawtypes")
public class SwampExFeatures {
	public static Feature CATTAILS = new CattailsFeature(NoFeatureConfig::deserialize).setRegistryName("cattails");
	public static Feature HANGING_WILLOW_LEAVES = new HangingWillowLeavesFeature(NoFeatureConfig::deserialize).setRegistryName("hanging_willow_leaves");
	public static Feature WILLOW_TREE = new WillowTreeFeature(NoFeatureConfig::deserialize).setRegistryName("willow_tree");

    @SubscribeEvent
    public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
        //FlowerVillagePools.init();
        event.getRegistry().registerAll(
                CATTAILS, HANGING_WILLOW_LEAVES, WILLOW_TREE
        );
    }

    public static void generateFeatures() {
        CattailsFeature.addFeature();
        HangingWillowLeavesFeature.addFeature();
    }
}