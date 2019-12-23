package com.farcr.swampexpansion.core.registries;

import com.farcr.swampexpansion.common.entity.WillowBoatEntity;
import com.farcr.swampexpansion.common.item.WillowBoatItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SignItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "swampexpansion", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemRegistry {
	public static Item MUD_BALL = new Item(new Item.Properties().group(ItemGroup.MATERIALS));
	public static Item MUD_BRICK = new Item(new Item.Properties().group(ItemGroup.MATERIALS)).setRegistryName("mud_brick");
	public static Item WILLOW_SIGN = new SignItem(new Item.Properties().group(ItemGroup.DECORATIONS), BlockRegistry.WILLOW_SIGN, BlockRegistry.WILLOW_SIGN_WALL).setRegistryName("willow_sign");
	public static Item MUD_BUCKET = new BucketItem(BlockRegistry.MUD, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("mud_bucket");
	public static Item WILLOW_BOAT = new WillowBoatItem(WillowBoatEntity.Type.WILLOW, new Item.Properties().group(ItemGroup.TRANSPORTATION).maxStackSize(1)).setRegistryName("willow_boat");

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(
				WILLOW_BOAT,
				MUD_BRICK, MUD_BUCKET
		);
	}
}