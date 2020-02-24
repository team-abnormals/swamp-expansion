package com.farcr.swampexpansion.core.util;

import net.minecraft.fluid.Fluid;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class SwampExTags {
	public static final Tag<Fluid> MUD = new FluidTags.Wrapper(new ResourceLocation("swampexpansion", "mud"));
}
