package com.farcr.swampexpansion.core.registries;

import com.farcr.swampexpansion.common.world.biome.MarshBiome;
import com.farcr.swampexpansion.common.world.biome.MushroomMarshBiome;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SwampExBiomes {
	public static final DeferredRegister<Biome> BIOMES = new DeferredRegister<>(ForgeRegistries.BIOMES, "swampexpansion");

	public static final RegistryObject<Biome> MARSH = BIOMES.register("marsh", () -> new MarshBiome());
	public static final RegistryObject<Biome> MUSHROOM_MARSH = BIOMES.register("mushroom_marsh", () -> new MushroomMarshBiome());

	public static void registerBiomesToDictionary() {
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(MARSH.get(), 7));
        
        BiomeDictionary.addTypes(MARSH.get(), 
        		BiomeDictionary.Type.PLAINS,
        		BiomeDictionary.Type.SWAMP);
        
        BiomeDictionary.addTypes(MARSH.get(), 
        		BiomeDictionary.Type.RARE,
        		BiomeDictionary.Type.PLAINS,
        		BiomeDictionary.Type.SWAMP);
	}
}
