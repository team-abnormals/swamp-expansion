package com.farcr.swampexpansion.common.worldgen;

import com.farcr.swampexpansion.core.registries.BlockRegistry;
import net.minecraft.world.gen.feature.SwampTreeFeature;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class FeatureEditer
{
    public static void overRideFeatures()
    {
        SwampTreeFeature.TRUNK = BlockRegistry.WILLOW_LOG.getDefaultState();
        SwampTreeFeature.LEAF = BlockRegistry.WILLOW_LEAVES.getDefaultState();
    }
}
