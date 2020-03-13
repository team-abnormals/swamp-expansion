package com.farcr.swampexpansion.common.world.biome;

import com.farcr.swampexpansion.common.world.gen.treedecorator.HangingWillowLeavesTreeDecorator;
import com.farcr.swampexpansion.core.registries.SwampExBlocks;
import com.google.common.collect.ImmutableList;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
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
}
