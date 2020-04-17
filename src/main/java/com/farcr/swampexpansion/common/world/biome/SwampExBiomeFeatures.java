package com.farcr.swampexpansion.common.world.biome;

import java.util.List;

import com.farcr.swampexpansion.common.world.gen.treedecorator.HangingWillowLeavesTreeDecorator;
import com.farcr.swampexpansion.core.registries.SwampExBlocks;
import com.farcr.swampexpansion.core.registries.SwampExFeatures;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DecoratedFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.MultipleRandomFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.SeaGrassConfig;
import net.minecraft.world.gen.feature.SphereReplaceConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.TwoFeatureChoiceConfig;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.HeightWithChanceConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.treedecorator.LeaveVineTreeDecorator;

public class SwampExBiomeFeatures {
	public static BlockState WILLOW_LOG = SwampExBlocks.WILLOW_LOG.get().getDefaultState();
	public static BlockState WILLOW_LEAVES = SwampExBlocks.WILLOW_LEAVES.get().getDefaultState();
	
	public static final TreeFeatureConfig WILLOW_TREE_CONFIG = (
			new TreeFeatureConfig.Builder(
					new SimpleBlockStateProvider(WILLOW_LOG), 
					new SimpleBlockStateProvider(WILLOW_LEAVES), 
					new BlobFoliagePlacer(3, 0)))
			.baseHeight(5)
			.heightRandA(3)
			.foliageHeight(3)
			.maxWaterDepth(1)
			.decorators(ImmutableList.of(new LeaveVineTreeDecorator(), new HangingWillowLeavesTreeDecorator()))
			.setSapling((net.minecraftforge.common.IPlantable)SwampExBlocks.WILLOW_SAPLING.get()).build();
	
	public static void addMushrooms(Biome biome) {
		biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_BOOLEAN_SELECTOR.withConfiguration(new TwoFeatureChoiceConfig(Feature.HUGE_RED_MUSHROOM.withConfiguration(DefaultBiomeFeatures.BIG_RED_MUSHROOM), Feature.HUGE_BROWN_MUSHROOM.withConfiguration(DefaultBiomeFeatures.BIG_BROWN_MUSHROOM))).withPlacement(Placement.COUNT_HEIGHTMAP.configure(new FrequencyConfig(1))));
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.BROWN_MUSHROOM_CONFIG).withPlacement(Placement.COUNT_CHANCE_HEIGHTMAP.configure(new HeightWithChanceConfig(1, 0.25F))));
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.RED_MUSHROOM_CONFIG).withPlacement(Placement.COUNT_CHANCE_HEIGHTMAP_DOUBLE.configure(new HeightWithChanceConfig(1, 0.125F))));
	}
	
	public static void addMarshMushrooms(Biome biome) {
		biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.HUGE_BROWN_MUSHROOM.withConfiguration(DefaultBiomeFeatures.BIG_BROWN_MUSHROOM).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(0, 0.32F, 1))));
	}
	
	public static void addMarshVegetation(Biome biome) {
		biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.TALL_GRASS_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(130))));
		biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.FANCY_TREE.withConfiguration(DefaultBiomeFeatures.FANCY_TREE_CONFIG).func_227227_a_(0.33333334F)), Feature.NORMAL_TREE.withConfiguration(DefaultBiomeFeatures.OAK_TREE_CONFIG))).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(0, 0.25F, 1))));
		biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.FLOWER.withConfiguration(DefaultBiomeFeatures.BLUE_ORCHID_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(1))));
		biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.GRASS_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(5))));
		biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.DEAD_BUSH_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(1))));
		biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.LILY_PAD_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(1))));
		biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.BROWN_MUSHROOM_CONFIG).withPlacement(Placement.COUNT_CHANCE_HEIGHTMAP.configure(new HeightWithChanceConfig(8, 0.25F))));
		biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.RED_MUSHROOM_CONFIG).withPlacement(Placement.COUNT_CHANCE_HEIGHTMAP_DOUBLE.configure(new HeightWithChanceConfig(8, 0.125F)))); 
		biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.withConfiguration(new SphereReplaceConfig(Blocks.COARSE_DIRT.getDefaultState(), 8, 3, Lists.newArrayList(Blocks.DIRT.getDefaultState()))).withPlacement(Placement.COUNT_TOP_SOLID.configure(new FrequencyConfig(2))));
		biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.SEAGRASS.withConfiguration(new SeaGrassConfig(128, 0.6D)).withPlacement(Placement.TOP_SOLID_HEIGHTMAP.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
	}
	
	public static void addMarshPools(Biome biome) {
		biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.SPRING_FEATURE.withConfiguration(DefaultBiomeFeatures.WATER_SPRING_CONFIG).withPlacement(Placement.COUNT_BIASED_RANGE.configure(new CountRangeConfig(150, 8, 8, 800))));
		biome.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(Blocks.WATER.getDefaultState())).withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(48))));
	}
	
	public static void addCattails(Biome biome) {
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, SwampExFeatures.CATTAILS.withConfiguration(new NoFeatureConfig()).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(12))));
	}
	
	public static void addDenseCattails(Biome biome) {
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, SwampExFeatures.DENSE_CATTAILS.withConfiguration(new NoFeatureConfig()).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(8))));
	}
	
	public static void addRice(Biome biome) {
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, SwampExFeatures.RICE.withConfiguration(new NoFeatureConfig()).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(2))));
	}
	
	public static void addWillowTrees(Biome biome) {
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, SwampExFeatures.WILLOW_TREE.withConfiguration(WILLOW_TREE_CONFIG).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(3, 0.1F, 1))));
	}
	
	public static void addSwampOaks(Biome biome) {
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.FANCY_TREE.withConfiguration(DefaultBiomeFeatures.FANCY_TREE_CONFIG).func_227227_a_(0.33333334F)), Feature.NORMAL_TREE.withConfiguration(DefaultBiomeFeatures.OAK_TREE_CONFIG))).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(0, 0.05F, 1))));
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
