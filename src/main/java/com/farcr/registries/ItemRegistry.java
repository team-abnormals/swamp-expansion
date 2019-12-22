package com.farcr.registries;

import com.farcr.entity.EntityBoatBase;
import com.farcr.item.ItemBoatBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class ItemRegistry {
	public static Item mud_ball;
	public static Item mud_brick;
	public static Item willow_boat;
	public static Item willow_sign;

	public static Item mud_bricks;
	public static Item mud_brick_stairs;
	public static Item mud_brick_slab;
	public static Item mud_brick_wall;
	
	public static Item willow_planks;
	public static Item willow_stairs;
	public static Item willow_slab;
	public static Item willow_fence;
	public static Item willow_fence_gate;
	public static Item willow_pressure_plate;
	public static Item willow_door;
	public static Item willow_trapdoor;
	public static Item willow_button;
	
	public static Item willow_log;
	public static Item stripped_willow_log;
	public static Item stripped_willow_wood;
	public static Item willow_wood;
	public static Item willow_sapling;
	public static Item willow_leaves;
	public static Item willow_ladder;
	public static Item willow_bookshelf;

	public static Item wisteria_boat = new ItemBoatBase(EntityBoatBase.Type.WILLOW, new Item.Properties().group(ItemGroup.TRANSPORTATION).maxStackSize(1)).setRegistryName("willow_boat");
}