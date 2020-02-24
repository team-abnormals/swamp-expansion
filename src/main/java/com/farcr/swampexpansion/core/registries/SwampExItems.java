package com.farcr.swampexpansion.core.registries;

import com.farcr.swampexpansion.common.entity.WillowBoatEntity;
import com.farcr.swampexpansion.common.item.MudBallItem;
import com.farcr.swampexpansion.common.item.WillowBoatItem;
import com.farcr.swampexpansion.core.util.RegistryUtils;

import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = "swampexpansion", bus = Mod.EventBusSubscriber.Bus.MOD)
public class SwampExItems {
	public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, "swampexpansion");
	
	public static RegistryObject<Item> MUD_BALL = RegistryUtils.createItem("mud_ball", () -> new MudBallItem(new Item.Properties().group(ItemGroup.MATERIALS)));
	public static RegistryObject<Item> MUD_BRICK = RegistryUtils.createItem("mud_brick", () -> new Item(new Item.Properties().group(ItemGroup.MATERIALS)));
	//public static RegistryObject<Item> WILLOW_SIGN = RegistryUtils.createItem("willow_sign", () -> new SignItem(new Item.Properties().group(ItemGroup.DECORATIONS), SwampExBlocks.WILLOW_SIGN, SwampExBlocks.WILLOW_SIGN_WALL));
	@SuppressWarnings("deprecation")
	public static RegistryObject<Item> MUD_BUCKET = RegistryUtils.createItem("mud_bucket", () -> new BucketItem(SwampExFluids.MUD, new Item.Properties().containerItem(Items.BUCKET).maxStackSize(1).group(ItemGroup.MATERIALS)));
	public static RegistryObject<Item> WILLOW_BOAT = RegistryUtils.createItem("willow_boat", () -> new WillowBoatItem(WillowBoatEntity.Type.WILLOW, new Item.Properties().group(ItemGroup.TRANSPORTATION).maxStackSize(1)));
}
