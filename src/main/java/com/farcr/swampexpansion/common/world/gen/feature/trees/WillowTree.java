package com.farcr.swampexpansion.common.world.gen.feature.trees;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.*;

import javax.annotation.Nullable;

import com.farcr.swampexpansion.core.registries.SwampExFeatures;

import java.util.Random;

public class WillowTree extends Tree {
	@Nullable
	protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean p_225546_2_) {
		return SwampExFeatures.WILLOW_TREE.withConfiguration(DefaultBiomeFeatures.ACACIA_TREE_CONFIG);
	}
}