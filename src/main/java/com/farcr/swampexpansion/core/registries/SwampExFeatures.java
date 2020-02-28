package com.farcr.swampexpansion.core.registries;

import com.farcr.swampexpansion.common.world.gen.feature.CattailsFeature;
import com.farcr.swampexpansion.common.world.gen.feature.HangingWillowLeavesFeature;
import com.farcr.swampexpansion.common.world.gen.feature.WillowTreeFeature;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.MultipleRandomFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

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
    
    public static void addMiscSwampFeatures() {
        ForgeRegistries.BIOMES.getValues().forEach(SwampExFeatures::generate);
    }

	public static void generate(Biome biome) {
        if (biome.getCategory() == Biome.Category.SWAMP) {
            biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.RANDOM_SELECTOR, new MultipleRandomFeatureConfig(new Feature[]{Feature.FANCY_TREE}, new IFeatureConfig[] {IFeatureConfig.NO_FEATURE_CONFIG}, new float[] {0.5F}, Feature.NORMAL_TREE, IFeatureConfig.NO_FEATURE_CONFIG), Placement.COUNT_EXTRA_HEIGHTMAP, new AtSurfaceWithExtraConfig(0, 0.5F, 1)));
            DefaultBiomeFeatures.addHugeMushrooms(biome);
        }
    }

    public static void generateFeatures() {
        CattailsFeature.addFeature();
        HangingWillowLeavesFeature.addFeature();
        SwampExFeatures.addMiscSwampFeatures();
    }
}