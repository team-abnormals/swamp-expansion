package com.farcr.swampexpansion.core.registries;

import com.farcr.swampexpansion.common.world.gen.feature.CattailsFeature;
import com.farcr.swampexpansion.common.world.gen.feature.HangingWillowLeavesFeature;
import com.farcr.swampexpansion.common.world.gen.feature.WillowTreeFeature;
import com.google.common.collect.ImmutableList;

import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.MultipleRandomFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.treedecorator.LeaveVineTreeDecorator;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = "swampexpansion", bus = Mod.EventBusSubscriber.Bus.MOD)
public class SwampExFeatures {
	public static Feature<NoFeatureConfig> CATTAILS = new CattailsFeature(NoFeatureConfig::deserialize);
	public static Feature<NoFeatureConfig> HANGING_WILLOW_LEAVES = new HangingWillowLeavesFeature(NoFeatureConfig::deserialize);
	public static Feature<TreeFeatureConfig> WILLOW_TREE = new WillowTreeFeature(TreeFeatureConfig::func_227338_a_, false);

    @SubscribeEvent
    public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
        event.getRegistry().registerAll(
                CATTAILS.setRegistryName("cattails"), 
                HANGING_WILLOW_LEAVES.setRegistryName("hanging_willow_leaves"), 
                WILLOW_TREE.setRegistryName("willow_tree")
        );
    }
    
    public static void addMiscSwampFeatures() {
        ForgeRegistries.BIOMES.getValues().forEach(SwampExFeatures::generate);
    }

	public static void generate(Biome biome) {
        if (biome.getCategory() == Biome.Category.SWAMP) {
            biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.FANCY_TREE.withConfiguration(DefaultBiomeFeatures.FANCY_TREE_CONFIG).func_227227_a_(0.33333334F)), Feature.NORMAL_TREE.withConfiguration(DefaultBiomeFeatures.OAK_TREE_CONFIG))).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(0, 0.05F, 1))));
            DefaultBiomeFeatures.addHugeMushrooms(biome);
        }
    }
	
	public static void overrideFeatures()
    {
    	  DefaultBiomeFeatures.SWAMP_TREE_CONFIG = (
    			  new TreeFeatureConfig.Builder(
    					  new SimpleBlockStateProvider(SwampExBlocks.WILLOW_LOG.get().getDefaultState()), 
    					  new SimpleBlockStateProvider(SwampExBlocks.WILLOW_LEAVES.get().getDefaultState()), 
    					  new BlobFoliagePlacer(3, 0))).baseHeight(5).heightRandA(3).foliageHeight(3).maxWaterDepth(1).decorators(ImmutableList.of(
    							  new LeaveVineTreeDecorator())).setSapling((net.minecraftforge.common.IPlantable)Blocks.OAK_SAPLING).build();

    }
	
	/*public static void overrideFeatures(Biome biome){
    	for (Decoration stage : GenerationStage.Decoration.values())
    	{
    		List<ConfiguredFeature<?, ?>> list = biome.getFeatures(stage);
    		for(int j = 0; j<list.size(); j++)
    	    {
    			ConfiguredFeature<?, ?> configuredFeature = list.get(j);
    			if (configuredFeature.config instanceof DecoratedFeatureConfig) {
    	    		 DecoratedFeatureConfig decorated = (DecoratedFeatureConfig)configuredFeature.config;
    	    		 
    	    		 if (decorated.feature.config instanceof OreFeatureConfig) {
    	    			 OreFeatureConfig ore = (OreFeatureConfig)decorated.feature.config;
    	    			 
    	    			 if (ore.state == Blocks.IRON_ORE.getDefaultState()) {
    	    				 biome.getFeatures(stage).remove(configuredFeature);
    	    			 }
    	    		 }
    	    	 }
    	    }
    	}    	   
    }*/

    public static void generateFeatures() {
        CattailsFeature.addFeature();
        HangingWillowLeavesFeature.addFeature();
        SwampExFeatures.addMiscSwampFeatures();
    }
}