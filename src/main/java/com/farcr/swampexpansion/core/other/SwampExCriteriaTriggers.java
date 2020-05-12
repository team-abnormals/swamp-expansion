package com.farcr.swampexpansion.core.other;

import com.farcr.swampexpansion.core.SwampExpansion;
import com.teamabnormals.abnormals_core.common.advancement.EmptyTrigger;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = SwampExpansion.MODID)
public class SwampExCriteriaTriggers {
	public static final EmptyTrigger BACKPACK_SLABFISH = CriteriaTriggers.register(new EmptyTrigger(prefix("backpack_slabfish")));
	
	public static final EmptyTrigger SWAMP_SLABFISH = CriteriaTriggers.register(new EmptyTrigger(prefix("swamp_slabfish")));
	public static final EmptyTrigger MARSH_SLABFISH = CriteriaTriggers.register(new EmptyTrigger(prefix("marsh_slabfish")));
	
	private static ResourceLocation prefix(String name) {
		return new ResourceLocation(SwampExpansion.MODID, name);
	}
}	
