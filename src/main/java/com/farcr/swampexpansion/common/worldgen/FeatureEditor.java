package com.farcr.swampexpansion.common.worldgen;

import com.farcr.swampexpansion.core.registries.SwampExBlocks;
import net.minecraft.world.gen.feature.SwampTreeFeature;

public class FeatureEditor
{
    public static void overrideFeatures()
    {
        SwampTreeFeature.TRUNK = SwampExBlocks.WILLOW_LOG.get().getDefaultState();
        SwampTreeFeature.LEAF = SwampExBlocks.WILLOW_LEAVES.get().getDefaultState();
    }
}
