package com.farcr.swampexpansion.core.registries;

import com.farcr.swampexpansion.core.util.TradeUtils;

import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "swampexpansion")
public class SwampExTrades {
	
	@SubscribeEvent
	public static void onWandererTradesEvent(WandererTradesEvent event) {
		event.getGenericTrades().add(new TradeUtils.ItemsForEmeraldsTrade(SwampExBlocks.CATTAIL.get(), 1, 1, 6, 1));
		event.getGenericTrades().add(new TradeUtils.ItemsForEmeraldsTrade(SwampExBlocks.WILLOW_SAPLING.get(), 5, 1, 8, 1));
	}
}
