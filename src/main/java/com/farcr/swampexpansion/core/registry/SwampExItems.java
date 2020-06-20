package com.farcr.swampexpansion.core.registry;

import com.farcr.swampexpansion.common.item.DuckweedItem;
import com.farcr.swampexpansion.common.item.MudBallItem;
import com.farcr.swampexpansion.common.item.SlabfishBucketItem;
import com.farcr.swampexpansion.core.SwampExpansion;
import com.farcr.swampexpansion.core.other.SwampExFoods;
import com.teamabnormals.abnormals_core.common.items.AbnormalsMusicDiscItem;
import com.teamabnormals.abnormals_core.core.utils.RegistryHelper;

import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.item.Rarity;
import net.minecraft.item.SoupItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SwampExpansion.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SwampExItems {
	public static final RegistryHelper HELPER = SwampExpansion.REGISTRY_HELPER;
	
	public static final RegistryObject<Item> MUD_BALL 		= HELPER.createItem("mud_ball", () -> new MudBallItem(new Item.Properties().group(ItemGroup.MATERIALS)));
	public static final RegistryObject<Item> MUD_BRICK 		= HELPER.createItem("mud_brick", () -> new Item(new Item.Properties().group(ItemGroup.MATERIALS)));
	public static final RegistryObject<Item> MUD_BUCKET 	= HELPER.createItem("mud_bucket", () -> new BucketItem(SwampExFluids.MUD, new Item.Properties().containerItem(Items.BUCKET).maxStackSize(1)));
	public static final RegistryObject<Item> WILLOW_BOAT 	= HELPER.createBoatItem("willow", SwampExBlocks.WILLOW_PLANKS);

	public static final RegistryObject<Item> CATTAIL_SEEDS 	= HELPER.createItem("cattail_seeds", () -> new BlockNamedItem(SwampExBlocks.CATTAIL_SPROUTS.get(), new Item.Properties().group(ItemGroup.MATERIALS)));
	public static final RegistryObject<Item> DUCKWEED 		= HELPER.createItem("duckweed", () -> new DuckweedItem(SwampExBlocks.DUCKWEED.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
	public static final RegistryObject<Item> RICE 			= HELPER.createItem("rice", () -> new BlockItem(SwampExBlocks.RICE.get(), new Item.Properties().group(ItemGroup.MISC)));
	public static final RegistryObject<Item> RICE_BALL 		= HELPER.createItem("rice_ball", () -> new Item(new Item.Properties().food(SwampExFoods.RICE_BALL).group(ItemGroup.FOOD)));
	public static final RegistryObject<Item> SQUID_INK_RISOTTO 			= HELPER.createItem("squid_ink_risotto", () -> new SoupItem(new Item.Properties().maxStackSize(1).food(SwampExFoods.SQUID_INK_RISOTTO).group(ItemGroup.FOOD)));

	public static final RegistryObject<Item> COD_KELP_ROLL 				= HELPER.createItem("cod_kelp_roll", () -> new Item(new Item.Properties().food(SwampExFoods.COD_KELP_ROLL).group(ItemGroup.FOOD)));
	public static final RegistryObject<Item> TROPICAL_FISH_KELP_ROLL 	= HELPER.createItem("tropical_fish_kelp_roll", () -> new Item(new Item.Properties().food(SwampExFoods.TROPICAL_FISH_KELP_ROLL).group(ItemGroup.FOOD)));
	public static final RegistryObject<Item> CRAB_KELP_ROLL 			= HELPER.createCompatItem("quark", "crab_kelp_roll", new Item.Properties().food(SwampExFoods.CRAB_KELP_ROLL), ItemGroup.FOOD);
	public static final RegistryObject<Item> PIKE_KELP_ROLL 			= HELPER.createCompatItem("upgrade_aquatic", "pike_kelp_roll", new Item.Properties().food(SwampExFoods.PIKE_KELP_ROLL), ItemGroup.FOOD);
	public static final RegistryObject<Item> CAVEFISH_KELP_ROLL 		= HELPER.createCompatItem("none", "cavefish_kelp_roll", new Item.Properties().food(SwampExFoods.CAVEFISH_KELP_ROLL), ItemGroup.FOOD);
	
	public static final RegistryObject<Item> SALMON_RICE_CAKE 			= HELPER.createItem("salmon_rice_cake", () -> new Item(new Item.Properties().food(SwampExFoods.SALMON_RICE_CAKE).group(ItemGroup.FOOD)));
	public static final RegistryObject<Item> PUFFERFISH_RICE_CAKE 		= HELPER.createItem("pufferfish_rice_cake", () -> new Item(new Item.Properties().food(SwampExFoods.PUFFERFISH_RICE_CAKE).group(ItemGroup.FOOD)));
	public static final RegistryObject<Item> LIONFISH_RICE_CAKE 		= HELPER.createCompatItem("upgrade_aquatic", "lionfish_rice_cake", new Item.Properties().food(SwampExFoods.LIONFISH_RICE_CAKE), ItemGroup.FOOD);
	
	public static final RegistryObject<Item> MUSIC_DISC_SLABRAVE 	= HELPER.createItem("music_disc_slabrave", () -> new AbnormalsMusicDiscItem(12, SwampExSounds.SLABRAVE, new Item.Properties().maxStackSize(1).group(ItemGroup.MISC).rarity(Rarity.RARE)));
//	public static final RegistryObject<Item> AXOLOTL_BUCKET = HELPER.createItem("axolotl_bucket", () -> new AxolotlBucketItem(() -> SwampExEntities.AXOLOTL.get(), () -> Fluids.WATER, new Item.Properties().group(ItemGroup.MISC).maxStackSize(1)));
	public static final RegistryObject<Item> SLABFISH_BUCKET = HELPER.createItem("slabfish_bucket", () -> new SlabfishBucketItem(() -> SwampExEntities.SLABFISH.get(), () -> Fluids.WATER, new Item.Properties().group(ItemGroup.MISC).maxStackSize(1)));

	public static final RegistryObject<Item> SLABFISH_SPAWN_EGG = HELPER.createSpawnEggItem("slabfish", () -> SwampExEntities.SLABFISH.get(), 6263617, 13940616);
//	public static final RegistryObject<Item> AXOLOTL_SPAWN_EGG = HELPER.createSpawnEggItem("axolotl", () -> SwampExEntities.AXOLOTL.get(), 6263617, 13940616);

}
