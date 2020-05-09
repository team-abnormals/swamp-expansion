package com.farcr.swampexpansion.core.other;

import com.farcr.swampexpansion.core.registry.SwampExBlocks;
import com.farcr.swampexpansion.core.registry.SwampExItems;
import com.farcr.swampexpansion.core.util.TradeUtils;

import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "swampexpansion")
public class SwampExTrades {
	
	@SubscribeEvent
	public static void onWandererTradesEvent(WandererTradesEvent event) {
		event.getGenericTrades().add(new TradeUtils.ItemsForEmeraldsTrade(SwampExItems.CATTAIL_SEEDS.get(), 1, 1, 6, 1));
		event.getGenericTrades().add(new TradeUtils.ItemsForEmeraldsTrade(SwampExBlocks.WILLOW_SAPLING.get(), 5, 1, 8, 1));
		event.getGenericTrades().add(new TradeUtils.ItemsForEmeraldsTrade(SwampExItems.RICE.get(), 1, 1, 12, 1));
	}
	
	@SubscribeEvent
	public static void onVillagerTradesEvent(VillagerTradesEvent event) {
		if(event.getType() == VillagerProfession.FARMER) {
			event.getTrades().get(1).add(new TradeUtils.EmeraldsForItemsTrade(SwampExItems.RICE.get(), 23, 1, 6, 1));
		}
    }
}
