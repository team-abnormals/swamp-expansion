package com.farcr.swampexpansion.common.world.gen.feature.trees;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.*;

import javax.annotation.Nullable;
import java.util.Random;

public class SwampTree extends Tree {
    @Nullable
    protected AbstractTreeFeature<NoFeatureConfig> getTreeFeature(Random random) {
        return new SwampTreeFeature(NoFeatureConfig::deserialize);
    }
}