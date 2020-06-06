package com.farcr.swampexpansion.core.other;

import com.farcr.swampexpansion.core.SwampExpansion;
import com.farcr.swampexpansion.core.registry.SwampExBlocks;
import com.farcr.swampexpansion.core.registry.SwampExItems;
import com.teamabnormals.abnormals_core.core.utils.TradeUtils;

import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SwampExpansion.MODID)
public class SwampExTrades {
	
	@SubscribeEvent
	public static void onWandererTradesEvent(WandererTradesEvent event) {
		event.getGenericTrades().add(new TradeUtils.ItemsForEmeraldsTrade(SwampExItems.CATTAIL_SEEDS.get(), 1, 1, 6, 1));
		event.getGenericTrades().add(new TradeUtils.ItemsForEmeraldsTrade(SwampExBlocks.WILLOW_SAPLING.get(), 5, 1, 8, 1));
		event.getGenericTrades().add(new TradeUtils.ItemsForEmeraldsTrade(SwampExItems.RICE.get(), 1, 1, 12, 1));
		event.getGenericTrades().add(new TradeUtils.ItemsForEmeraldsTrade(SwampExItems.DUCKWEED.get(), 1, 2, 6, 1));
		event.getRareTrades().add(new TradeUtils.ItemsForEmeraldsTrade(SwampExItems.SLABFISH_BUCKET.get(), 5, 1, 4, 1));
	}
	
	@SubscribeEvent
	public static void onVillagerTradesEvent(VillagerTradesEvent event) {
		if(event.getType() == VillagerProfession.FARMER) {
			event.getTrades().get(1).add(new TradeUtils.EmeraldsForItemsTrade(SwampExItems.RICE.get(), 23, 1, 6, 1));
		}
		
		if(event.getType() == VillagerProfession.FISHERMAN) {
			event.getTrades().get(3).add(new TradeUtils.ItemsForEmeraldsTrade(SwampExItems.COD_KELP_ROLL.get(), 5, 2, 6, 15));
			event.getTrades().get(3).add(new TradeUtils.ItemsForEmeraldsTrade(SwampExItems.SALMON_RICE_CAKE.get(), 5, 2, 6, 15));
			event.getTrades().get(4).add(new TradeUtils.ItemsForEmeraldsTrade(SwampExItems.PUFFERFISH_RICE_CAKE.get(), 3, 4, 5, 30));
			event.getTrades().get(5).add(new TradeUtils.ItemsForEmeraldsTrade(SwampExItems.TROPICAL_FISH_KELP_ROLL.get(), 3, 4, 5, 30));
			
			if(ModList.get().isLoaded("upgrade_aquatic")) {
				event.getTrades().get(4).add(new TradeUtils.ItemsForEmeraldsTrade(SwampExItems.PIKE_KELP_ROLL.get(), 4, 1, 3, 25));
				event.getTrades().get(5).add(new TradeUtils.ItemsForEmeraldsTrade(SwampExItems.LIONFISH_RICE_CAKE.get(), 3, 4, 5, 30));	
			}
			
			if(ModList.get().isLoaded("quark")) {
				event.getTrades().get(4).add(new TradeUtils.ItemsForEmeraldsTrade(SwampExItems.CRAB_KELP_ROLL.get(), 4, 1, 3, 25));
			}
		}
    }
}
