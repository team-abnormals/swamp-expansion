package com.farcr.swampexpansion.core.registries;

import com.farcr.swampexpansion.common.entity.WillowBoatEntity;
import com.farcr.swampexpansion.common.item.WillowBoatItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "swampexpansion", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemRegistry {
	public static Item MUD_BALL;
	public static Item MUD_BRICK;
	public static Item WILLOW_SIGN;
	public static Item WILLOW_BOAT = new WillowBoatItem(WillowBoatEntity.Type.WILLOW, new Item.Properties().group(ItemGroup.TRANSPORTATION).maxStackSize(1)).setRegistryName("willow_boat");

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(
				WILLOW_BOAT
		);
	}
}