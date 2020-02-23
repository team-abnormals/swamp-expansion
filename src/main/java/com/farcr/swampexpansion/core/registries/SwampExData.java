package com.farcr.swampexpansion.core.registries;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.AxeItem;
import net.minecraft.util.IItemProvider;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.fml.ModList;

public class SwampExData {
	public static void registerBlockData() {
		//compostable blocks
		registerCompostable(SwampExBlocks.WILLOW_LEAVES.get(),0.3F);
		registerCompostable(SwampExBlocks.WILLOW_SAPLING.get(),0.3F);
		

		//strippable blocks
		registerStrippable(SwampExBlocks.WILLOW_LOG.get(), SwampExBlocks.STRIPPED_WILLOW_LOG.get());
		registerStrippable(SwampExBlocks.WILLOW_WOOD.get(), SwampExBlocks.STRIPPED_WILLOW_WOOD.get());

		//flammable blocks
		registerFlammable(SwampExBlocks.WILLOW_LEAVES.get(), 30, 60);
		registerFlammable(SwampExBlocks.WILLOW_LOG.get(), 5, 5);
		registerFlammable(SwampExBlocks.WILLOW_WOOD.get(), 5, 5);
		registerFlammable(SwampExBlocks.STRIPPED_WILLOW_LOG.get(), 5, 5);
		registerFlammable(SwampExBlocks.STRIPPED_WILLOW_WOOD.get(), 5, 5);
		registerFlammable(SwampExBlocks.WILLOW_PLANKS.get(), 5, 20);
		registerFlammable(SwampExBlocks.WILLOW_SLAB.get(), 5, 20);
		registerFlammable(SwampExBlocks.WILLOW_STAIRS.get(), 5, 20);
		registerFlammable(SwampExBlocks.WILLOW_FENCE.get(), 5, 20);
		registerFlammable(SwampExBlocks.WILLOW_FENCE_GATE.get(), 5, 20);
		
		
		if(ModList.get().isLoaded("quark")) {
			registerCompostable(SwampExBlocks.WILLOW_LEAF_CARPET.get(),0.3F);
			
			registerFlammable(SwampExBlocks.VERTICAL_WILLOW_PLANKS.get(), 5, 20);
			registerFlammable(SwampExBlocks.WILLOW_LEAF_CARPET.get(), 30, 60);
			registerFlammable(SwampExBlocks.VERTICAL_WILLOW_SLAB.get(), 5, 20);
			registerFlammable(SwampExBlocks.WILLOW_BOOKSHELF.get(), 5, 20);
		}
	}
	
	public static void registerBlockColors() {
        BlockColors blockColors = Minecraft.getInstance().getBlockColors();
        blockColors.register((x, world, pos, u) -> world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.get(0.5D, 1.0D), SwampExBlocks.WILLOW_LEAVES.get());
        ItemColors itemColors = Minecraft.getInstance().getItemColors();
        itemColors.register((color, items) -> FoliageColors.get(0.5D, 1.0D), SwampExBlocks.WILLOW_LEAVES.get());
        
        blockColors.register((x, world, pos, u) -> world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.get(0.5D, 1.0D), SwampExBlocks.WILLOW_LEAF_CARPET.get());
        itemColors.register((color, items) -> FoliageColors.get(0.5D, 1.0D), SwampExBlocks.WILLOW_LEAF_CARPET.get());
    }

	public static void registerFlammable(Block block, int encouragement, int flammability) {
		FireBlock fire = (FireBlock) Blocks.FIRE;
		fire.setFireInfo(block, encouragement, flammability);
	}

	public static void registerCompostable(IItemProvider item, float chance) {
		ComposterBlock.CHANCES.put(item.asItem(), chance);
	}

	public static void registerStrippable(Block log, Block stripped) {
		AxeItem.BLOCK_STRIPPING_MAP = Maps.newHashMap(AxeItem.BLOCK_STRIPPING_MAP);
		AxeItem.BLOCK_STRIPPING_MAP.put(log, stripped);
	}
}
