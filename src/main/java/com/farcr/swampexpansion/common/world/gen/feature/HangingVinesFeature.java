package com.farcr.swampexpansion.common.world.gen.feature;

import com.farcr.swampexpansion.core.registries.SwampExBlocks;
import com.farcr.swampexpansion.core.registries.SwampExFeatures;
import com.mojang.datafixers.Dynamic;

import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;
import java.util.function.Function;

public class HangingVinesFeature extends Feature<NoFeatureConfig> {
    public HangingVinesFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> config) {
        super(config);
    }

    @Override
    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random random, BlockPos pos, NoFeatureConfig config) {
        boolean place = false;
        for(int i = 0; i < 256; ++i) {
            BlockPos placePos = pos.add(random.nextInt(8) - random.nextInt(8), random.nextInt(2) - random.nextInt(2), random.nextInt(8) - random.nextInt(8));
            if (world.isAirBlock(placePos) && placePos.getY() < world.getWorld().getDimension().getHeight() - 2 && world.getBlockState(placePos.up()).getBlock().isIn(BlockTags.LEAVES) && SwampExBlocks.HANGING_WILLOW_LEAVES.get().getDefaultState().isValidPosition(world, placePos)) {
            	world.setBlockState(placePos, SwampExBlocks.HANGING_WILLOW_LEAVES.get().getDefaultState(), 2);
            	place = true;
            }
        }
        return place;
    }

    public static void addFeature() {
        ForgeRegistries.BIOMES.getValues().forEach(HangingVinesFeature::generate);
    }

    @SuppressWarnings("unchecked")
	public static void generate(Biome biome) {
        if (biome.getCategory() == Biome.Category.SWAMP) {
            biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(SwampExFeatures.HANGING_WILLOW_VINES, new NoFeatureConfig(), Placement.COUNT_HEIGHTMAP_32, new FrequencyConfig(64)));
        }
    }
}