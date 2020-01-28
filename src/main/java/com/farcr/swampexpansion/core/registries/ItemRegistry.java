package com.farcr.swampexpansion.core.registries;

import com.farcr.swampexpansion.common.entity.WillowBoatEntity;
import com.farcr.swampexpansion.common.item.WillowBoatItem;
import net.minecraft.item.*;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "swampexpansion", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemRegistry {
	public static Item MUD_BALL = new Item(new Item.Properties().group(ItemGroup.MATERIALS)).setRegistryName("mud_ball");
	public static Item MUD_BRICK = new Item(new Item.Properties().group(ItemGroup.MATERIALS)).setRegistryName("mud_brick");
	public static Item WILLOW_SIGN = new SignItem(new Item.Properties().group(ItemGroup.DECORATIONS), BlockRegistry.WILLOW_SIGN, BlockRegistry.WILLOW_SIGN_WALL).setRegistryName("willow_sign");
	//public static Item MUD_BUCKET = new BucketItem(BlockRegistry.STILL_MUD, new Item.Properties().containerItem(Items.BUCKET).maxStackSize(1).group(ItemGroup.MISC)).setRegistryName("mud_bucket");
	public static Item WILLOW_BOAT = new WillowBoatItem(WillowBoatEntity.Type.WILLOW, new Item.Properties().group(ItemGroup.TRANSPORTATION).maxStackSize(1)).setRegistryName("willow_boat");
	public static Item CATTAIL_SEEDS = new Item(new Item.Properties().group(ItemGroup.MATERIALS)).setRegistryName("cattail_seeds");

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(
				WILLOW_BOAT,
				MUD_BALL, MUD_BRICK,
				CATTAIL_SEEDS
		);
	}
}