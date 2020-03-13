package com.farcr.swampexpansion.core.registries;

import java.util.List;

import com.farcr.swampexpansion.common.world.biome.SwampExBiomeFeatures;
import com.farcr.swampexpansion.common.world.gen.feature.CattailsFeature;
import com.farcr.swampexpansion.common.world.gen.feature.WillowTreeFeature;
import com.google.common.collect.ImmutableList;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DecoratedFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.MultipleRandomFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = "swampexpansion", bus = Mod.EventBusSubscriber.Bus.MOD)
public class SwampExFeatures {
	
	public static Feature<NoFeatureConfig> CATTAILS 		= new CattailsFeature(NoFeatureConfig::deserialize);
	public static Feature<TreeFeatureConfig> WILLOW_TREE 	= new WillowTreeFeature(TreeFeatureConfig::func_227338_a_);

    @SubscribeEvent
    public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
        event.getRegistry().registerAll(
                CATTAILS.setRegistryName("cattails"), 
                WILLOW_TREE.setRegistryName("willow_tree")
        );
    }
    
    public static void generateFeatures() {
        ForgeRegistries.BIOMES.getValues().forEach(SwampExFeatures::generate);
    }

	public static void generate(Biome biome) {
        if (biome.getCategory() == Biome.Category.SWAMP) {
            overrideFeatures(biome);
            DefaultBiomeFeatures.addHugeMushrooms(biome);
        	biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, WILLOW_TREE.withConfiguration(SwampExBiomeFeatures.WILLOW_TREE_CONFIG).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(2, 0.1F, 1))));
            biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.FANCY_TREE.withConfiguration(DefaultBiomeFeatures.FANCY_TREE_CONFIG).func_227227_a_(0.33333334F)), Feature.NORMAL_TREE.withConfiguration(DefaultBiomeFeatures.OAK_TREE_CONFIG))).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(0, 0.05F, 1))));
        }
        if (biome.getTempCategory() != Biome.TempCategory.COLD && biome.getCategory() == Biome.Category.SWAMP || biome.getCategory() == Biome.Category.RIVER) {
            biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, SwampExFeatures.CATTAILS.withConfiguration(new NoFeatureConfig()).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(12))));
        }
    }
	
	public static void overrideFeatures(Biome biome){
    	for (Decoration stage : GenerationStage.Decoration.values())
    	{
    		List<ConfiguredFeature<?, ?>> list = biome.getFeatures(stage);
    		for(int j = 0; j<list.size(); j++)
    	    {
    			ConfiguredFeature<?, ?> configuredFeature = list.get(j);
    			if (configuredFeature.config instanceof DecoratedFeatureConfig) {
    	    		 DecoratedFeatureConfig decorated = (DecoratedFeatureConfig)configuredFeature.config;
    	    		 
    	    		 if (decorated.feature.config instanceof TreeFeatureConfig) {
    	    			 TreeFeatureConfig tree = (TreeFeatureConfig)decorated.feature.config;
    	    			 
    	    			 if (tree == DefaultBiomeFeatures.SWAMP_TREE_CONFIG) {
    	    				 biome.getFeatures(stage).remove(configuredFeature);
    	    			 }
    	    		 }
    	    	 }
    	    }
    	}    	   
    }
}