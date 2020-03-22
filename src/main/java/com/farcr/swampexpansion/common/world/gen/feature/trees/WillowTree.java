package com.farcr.swampexpansion.common.world.gen.feature.trees;

import java.util.Random;

import javax.annotation.Nullable;

import com.farcr.swampexpansion.common.world.biome.SwampExBiomeFeatures;
import com.farcr.swampexpansion.core.registries.SwampExFeatures;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

public class WillowTree extends Tree {
	@Nullable
	protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean p_225546_2_) {
		return SwampExFeatures.WILLOW_TREE.withConfiguration(SwampExBiomeFeatures.WILLOW_TREE_CONFIG);
	}
}