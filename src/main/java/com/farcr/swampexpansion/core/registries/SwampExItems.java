package com.farcr.swampexpansion.core.registries;

import java.util.List;

import com.farcr.swampexpansion.common.entity.WillowBoatEntity;
import com.farcr.swampexpansion.common.item.MudBallItem;
import com.farcr.swampexpansion.common.item.WillowBoatItem;
import com.farcr.swampexpansion.core.util.RegistryUtils;
import com.google.common.collect.Lists;

import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.item.SoupItem;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("deprecation")
@Mod.EventBusSubscriber(modid = "swampexpansion", bus = Mod.EventBusSubscriber.Bus.MOD)
public class SwampExItems {
	public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, "swampexpansion");
	public static final List<RegistryObject<Item>> SPAWN_EGGS = Lists.newArrayList();
	
	public static RegistryObject<Item> MUD_BALL = RegistryUtils.createItem("mud_ball", () -> new MudBallItem(new Item.Properties().group(ItemGroup.MATERIALS)));
	public static RegistryObject<Item> MUD_BRICK = RegistryUtils.createItem("mud_brick", () -> new Item(new Item.Properties().group(ItemGroup.MATERIALS)));
//	public static RegistryObject<Item> WILLOW_SIGN = RegistryUtils.createItem("willow_sign", () -> new SignItem(new Item.Properties().group(ItemGroup.DECORATIONS), SwampExBlocks.WILLOW_SIGN, SwampExBlocks.WILLOW_SIGN_WALL));
	public static RegistryObject<Item> MUD_BUCKET = RegistryUtils.createItem("mud_bucket", () -> new BucketItem(SwampExFluids.MUD, new Item.Properties().containerItem(Items.BUCKET).maxStackSize(1)));
	public static RegistryObject<Item> WILLOW_BOAT = RegistryUtils.createItem("willow_boat", () -> new WillowBoatItem(WillowBoatEntity.Type.WILLOW, new Item.Properties().group(ItemGroup.TRANSPORTATION).maxStackSize(1)));
	
	public static RegistryObject<Item> CATTAIL_SEEDS = RegistryUtils.createItem("cattail_seeds", () -> new BlockNamedItem(SwampExBlocks.CATTAIL_SPROUTS.get(), new Item.Properties().group(ItemGroup.MATERIALS)));
	public static RegistryObject<Item> RICE = RegistryUtils.createItem("rice", () -> new BlockItem(SwampExBlocks.RICE.get(), new Item.Properties().group(ItemGroup.MISC)));
	public static RegistryObject<Item> RICE_BALL = RegistryUtils.createItem("rice_ball", () -> new Item(new Item.Properties().food(SwampExFoods.RICE_BALL).group(ItemGroup.FOOD)));
	
	public static RegistryObject<Item> COD_KELP_ROLL = RegistryUtils.createItem("cod_kelp_roll", () -> new Item(new Item.Properties().food(SwampExFoods.COD_KELP_ROLL).group(ItemGroup.FOOD)));
	public static RegistryObject<Item> TROPICAL_FISH_KELP_ROLL = RegistryUtils.createItem("tropical_fish_kelp_roll", () -> new Item(new Item.Properties().food(SwampExFoods.TROPICAL_FISH_KELP_ROLL).group(ItemGroup.FOOD)));
	public static RegistryObject<Item> CRAB_KELP_ROLL = RegistryUtils.createItem("crab_kelp_roll", () -> new Item(new Item.Properties().food(SwampExFoods.CRAB_KELP_ROLL).group(ModList.get().isLoaded("quark") ? ItemGroup.FOOD : null)));
	public static RegistryObject<Item> PIKE_KELP_ROLL = RegistryUtils.createItem("pike_kelp_roll", () -> new Item(new Item.Properties().food(SwampExFoods.PIKE_KELP_ROLL).group(ModList.get().isLoaded("upgrade_aquatic") ? ItemGroup.FOOD : null)));
	public static RegistryObject<Item> CAVEFISH_KELP_ROLL = RegistryUtils.createItem("cavefish_kelp_roll", () -> new Item(new Item.Properties().food(SwampExFoods.CAVEFISH_KELP_ROLL).group(ModList.get().isLoaded("maby") ? ItemGroup.FOOD : null)));

	public static RegistryObject<Item> SALMON_RICE_CAKE = RegistryUtils.createItem("salmon_rice_cake", () -> new Item(new Item.Properties().food(SwampExFoods.SALMON_RICE_CAKE).group(ItemGroup.FOOD)));
	public static RegistryObject<Item> PUFFERFISH_RICE_CAKE = RegistryUtils.createItem("pufferfish_rice_cake", () -> new Item(new Item.Properties().food(SwampExFoods.PUFFERFISH_RICE_CAKE).group(ItemGroup.FOOD)));
	public static RegistryObject<Item> LIONFISH_RICE_CAKE = RegistryUtils.createItem("lionfish_rice_cake", () -> new Item(new Item.Properties().food(SwampExFoods.LIONFISH_RICE_CAKE).group(ModList.get().isLoaded("upgrade_aquatic") ? ItemGroup.FOOD : null)));
	
	public static RegistryObject<Item> SQUID_INK_RISOTTO = RegistryUtils.createItem("squid_ink_risotto", () -> new SoupItem(new Item.Properties().food(SwampExFoods.SQUID_INK_RISOTTO).group(ItemGroup.FOOD)));
	
	public static final RegistryObject<Item> SLABFISH_SPAWN_EGG = RegistryUtils.createSpawnEggItem("slabfish", () -> SwampExEntities.SLABFISH.get(), 6263617, 13940616);
}
