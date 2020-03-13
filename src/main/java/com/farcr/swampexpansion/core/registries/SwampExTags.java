package com.farcr.swampexpansion.core.registries;

import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class SwampExTags {
	public static final Tag<Block> CATTAIL_PLANTABLE_ON = new BlockTags.Wrapper(new ResourceLocation("swampexpansion", "cattail_plantable_on"));
	
	public static final Tag<Fluid> MUD = new FluidTags.Wrapper(new ResourceLocation("swampexpansion", "mud"));

}
