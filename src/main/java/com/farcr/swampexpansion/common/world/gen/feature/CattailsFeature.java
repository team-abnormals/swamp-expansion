package com.farcr.swampexpansion.common.world.gen.feature;

import com.farcr.swampexpansion.common.block.CattailBlock;
import com.farcr.swampexpansion.core.registries.SwampExBlocks;
import com.farcr.swampexpansion.core.registries.SwampExFeatures;
import com.mojang.datafixers.Dynamic;
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

public class CattailsFeature extends Feature<NoFeatureConfig> {
    public CattailsFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> config) {
        super(config);
    }

    @Override
    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random random, BlockPos pos, NoFeatureConfig config) {
        boolean place = false;
        for(int i = 0; i < 32; ++i) {
            BlockPos placePos = pos.add(random.nextInt(4) - random.nextInt(4), random.nextInt(2) - random.nextInt(2), random.nextInt(4) - random.nextInt(4));
            if ((world.hasWater(placePos) || world.isAirBlock(placePos)) && placePos.getY() < world.getWorld().getDimension().getHeight() - 2 && SwampExBlocks.CATTAIL.get().getDefaultState().isValidPosition(world, placePos)) {
            	((CattailBlock) SwampExBlocks.CATTAIL.get()).placeAt(world, placePos, 2);
            	place = true;
            }
        }
        return place;
    }

    public static void addFeature() {
        ForgeRegistries.BIOMES.getValues().forEach(CattailsFeature::generate);
    }

    @SuppressWarnings("unchecked")
	public static void generate(Biome biome) {
        if (biome.getCategory() == Biome.Category.SWAMP) {
            biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(SwampExFeatures.CATTAILS, new NoFeatureConfig(), Placement.COUNT_HEIGHTMAP_32, new FrequencyConfig(8)));
        }
    }
}