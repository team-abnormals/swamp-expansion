package com.farcr.swampexpansion.core.registries;

import com.farcr.swampexpansion.common.block.*;
import com.farcr.swampexpansion.common.block.LadderBlock;
import com.farcr.swampexpansion.common.block.fluid.MudFluid;
import com.farcr.swampexpansion.common.item.FuelItem;
import com.farcr.swampexpansion.core.util.BlockProperties;
import com.google.common.collect.Maps;
import net.minecraft.block.*;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "swampexpansion", bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockRegistry {
	public static Block HARDENED_MUD = new Block(BlockProperties.MUD_BRICKS).setRegistryName("hardened_mud");
	public static Block MUD_BRICKS = new Block(BlockProperties.MUD_BRICKS).setRegistryName("mud_bricks");
	public static Block MUD_BRICK_STAIRS = new StairsBlock(MUD_BRICKS.getDefaultState(), BlockProperties.MUD_BRICKS).setRegistryName("mud_stairs");
	public static Block MUD_BRICK_SLAB = new SlabBlock(BlockProperties.MUD_BRICKS).setRegistryName("mud_slab");
	public static Block MUD_BRICK_WALL = new WallBlock(BlockProperties.MUD_BRICKS).setRegistryName("mud_wall");
	public static Block WILLOW_PLANKS = new Block(BlockProperties.WILLOW_PLANKS).setRegistryName("willow_planks");
	public static Block WILLOW_SLAB = new SlabBlock(BlockProperties.WILLOW_PLANKS).setRegistryName("willow_slab");
	public static Block WILLOW_STAIRS = new StairsBlock(WILLOW_PLANKS.getDefaultState(), BlockProperties.WILLOW_PLANKS).setRegistryName("willow_stairs");
	public static Block WILLOW_FENCE = new FenceBlock(BlockProperties.WILLOW_PLANKS).setRegistryName("willow_fence");
	public static Block WILLOW_FENCE_GATE = new FenceGateBlock(BlockProperties.WILLOW_PLANKS).setRegistryName("willow_fence_gate");
	public static Block WILLOW_LOG = new RotatedPillarBlock(BlockProperties.LOG).setRegistryName("willow_log");
	public static Block STRIPPED_WILLOW_LOG = new RotatedPillarBlock(BlockProperties.LOG).setRegistryName("stripped_willow_log");
	public static Block WILLOW_PRESSURE_PLATE = new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockProperties.WILLOW_PRESSURE_PLATE).setRegistryName("willow_pressure_plate");
	public static Block WILLOW_DOOR = new DoorBlock(BlockProperties.WILLOW_DOORS).setRegistryName("willow_door");
	public static Block WILLOW_TRAPDOOR = new TrapDoorBlock(BlockProperties.WILLOW_DOORS).setRegistryName("willow_trapdoor");
	public static Block WILLOW_WOOD = new RotatedPillarBlock(BlockProperties.LOG).setRegistryName("willow_wood");
	public static Block STRIPPED_WILLOW_WOOD = new RotatedPillarBlock(BlockProperties.LOG).setRegistryName("stripped_willow_wood");
	public static Block WILLOW_SAPLING = new WillowSaplingBlock(BlockProperties.SAPLING, null).setRegistryName("willow_sapling");
	public static Block WILLOW_BUTTON = new WillowButtonBlock().setRegistryName("willow_button");
	public static Block WILLOW_LEAVES = new LeavesBlock(BlockProperties.LEAVES).setRegistryName("willow_leaves");
	public static Block WILLOW_BOOKSHELF = new Block(BlockProperties.BOOKSHELF).setRegistryName("willow_bookshelf");
	public static Block POTTED_WILLOW_SAPLING = new FlowerPotBlock(BlockRegistry.WILLOW_SAPLING, BlockProperties.FLOWER_POT).setRegistryName("potted_willow_sapling");
	public static Block WILLOW_SIGN = new StandingSignBlock(BlockProperties.WILLOW_PLANKS).setRegistryName("willow_sign");
	public static Block WILLOW_SIGN_WALL = new WallSignBlock(BlockProperties.WILLOW_PLANKS).setRegistryName("willow_sign_wall");

	//quark
	public static Block WILLOW_LADDER = new LadderBlock(BlockProperties.LADDER).setRegistryName("willow_ladder");
	public static Block VERTICAL_WILLOW_PLANKS = new Block(BlockProperties.WILLOW_PLANKS).setRegistryName("horizontal_willow_planks");
	public static Block VERTICAL_WILLOW_SLAB = new VerticalSlabBlock(BlockProperties.WILLOW_PLANKS).setRegistryName("vertical_willow_slab");
	public static Block WILLOW_LEAF_CARPET = new LeafCarpetBlock(BlockProperties.LEAVES).setRegistryName("willow_leaf_carpet");

	//fluids
	public static FlowingFluid FLOWING_MUD = new MudFluid.Flowing();
	public static FlowingFluid MUD = new MudFluid.Source();

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> registry) {
		registry.getRegistry().registerAll(
				HARDENED_MUD,
				MUD_BRICKS, MUD_BRICK_STAIRS, MUD_BRICK_SLAB, MUD_BRICK_WALL,
				WILLOW_PLANKS, WILLOW_LOG, STRIPPED_WILLOW_LOG, WILLOW_WOOD, STRIPPED_WILLOW_WOOD,
				WILLOW_STAIRS, WILLOW_SLAB, WILLOW_FENCE, WILLOW_FENCE_GATE,
				WILLOW_DOOR, WILLOW_TRAPDOOR,
				WILLOW_BUTTON, WILLOW_PRESSURE_PLATE,
				WILLOW_LEAVES,
				WILLOW_SAPLING, POTTED_WILLOW_SAPLING
		);
		if (ModList.get().isLoaded("quark")) {
			registry.getRegistry().registerAll(
					WILLOW_LADDER, VERTICAL_WILLOW_PLANKS, VERTICAL_WILLOW_SLAB,
					WILLOW_BOOKSHELF,
					WILLOW_LEAF_CARPET
			);
		}
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		Item.Properties buildingBlocks = new Item.Properties().group(ItemGroup.BUILDING_BLOCKS);
		Item.Properties decorations = new Item.Properties().group(ItemGroup.DECORATIONS);
		Item.Properties redstone = new Item.Properties().group(ItemGroup.REDSTONE);
		event.getRegistry().registerAll(
				new BlockItem(HARDENED_MUD, buildingBlocks).setRegistryName(HARDENED_MUD.getRegistryName()),
				new BlockItem(MUD_BRICKS, buildingBlocks).setRegistryName(MUD_BRICKS.getRegistryName()),
				new BlockItem(MUD_BRICK_STAIRS, buildingBlocks).setRegistryName(MUD_BRICK_STAIRS.getRegistryName()),
				new BlockItem(MUD_BRICK_SLAB, buildingBlocks).setRegistryName(MUD_BRICK_SLAB.getRegistryName()),
				new BlockItem(MUD_BRICK_WALL, buildingBlocks).setRegistryName(MUD_BRICK_WALL.getRegistryName()),
				new BlockItem(WILLOW_PLANKS, buildingBlocks).setRegistryName(WILLOW_PLANKS.getRegistryName()),
				new BlockItem(WILLOW_STAIRS, buildingBlocks).setRegistryName(WILLOW_STAIRS.getRegistryName()),
				new BlockItem(WILLOW_SLAB, buildingBlocks).setRegistryName(WILLOW_SLAB.getRegistryName()),
				new BlockItem(WILLOW_FENCE, decorations).setRegistryName(WILLOW_FENCE.getRegistryName()),
				new BlockItem(WILLOW_FENCE_GATE, redstone).setRegistryName(WILLOW_FENCE_GATE.getRegistryName()),
				new BlockItem(WILLOW_DOOR, redstone).setRegistryName(WILLOW_DOOR.getRegistryName()),
				new BlockItem(WILLOW_TRAPDOOR, redstone).setRegistryName(WILLOW_TRAPDOOR.getRegistryName()),
				new BlockItem(WILLOW_BUTTON, redstone).setRegistryName(WILLOW_BUTTON.getRegistryName()),
				new BlockItem(WILLOW_PRESSURE_PLATE, redstone).setRegistryName(WILLOW_PRESSURE_PLATE.getRegistryName())
		);
		if (ModList.get().isLoaded("quark")) {
		event.getRegistry().registerAll(
				new FuelItem(WILLOW_LADDER, decorations, 300).setRegistryName(WILLOW_LADDER.getRegistryName()),
				new BlockItem(VERTICAL_WILLOW_PLANKS, buildingBlocks).setRegistryName(VERTICAL_WILLOW_PLANKS.getRegistryName()),
				new FuelItem(VERTICAL_WILLOW_SLAB, buildingBlocks, 150).setRegistryName(VERTICAL_WILLOW_SLAB.getRegistryName()),
				new BlockItem(WILLOW_LEAF_CARPET, decorations).setRegistryName(WILLOW_LEAF_CARPET.getRegistryName())
			);
		}
	}

	@SubscribeEvent
	public static void registerFluids(RegistryEvent.Register<Fluid> registry) {
		FLOWING_MUD.setRegistryName("flowing_mud");
		MUD.setRegistryName("mud");
		registry.getRegistry().registerAll(
				MUD, FLOWING_MUD
		);
	}

	public static void registerBlockData() {
		//compostable blocks
		registerCompostable(WILLOW_LEAVES,0.35F);
		registerCompostable(WILLOW_SAPLING,0.35F);
		registerCompostable(WILLOW_LEAF_CARPET,0.35F);

		//strippable blocks
		registerStrippable(WILLOW_LOG, STRIPPED_WILLOW_LOG);
		registerStrippable(WILLOW_WOOD, STRIPPED_WILLOW_WOOD);

		//flammable blocks
		registerFlammable(WILLOW_LEAVES, 30, 60);
		registerFlammable(WILLOW_LOG, 5, 5);
		registerFlammable(WILLOW_WOOD, 5, 5);
		registerFlammable(STRIPPED_WILLOW_LOG, 5, 5);
		registerFlammable(STRIPPED_WILLOW_WOOD, 5, 5);
		registerFlammable(WILLOW_PLANKS, 5, 20);
		registerFlammable(WILLOW_SLAB, 5, 20);
		registerFlammable(WILLOW_STAIRS, 5, 20);
		registerFlammable(WILLOW_FENCE, 5, 20);
		registerFlammable(WILLOW_FENCE_GATE, 5, 20);
		registerFlammable(VERTICAL_WILLOW_PLANKS, 5, 20);
		registerFlammable(WILLOW_LEAF_CARPET, 30, 60);
		registerFlammable(VERTICAL_WILLOW_SLAB, 5, 20);
	}

	public static void registerFlammable(Block block, int encouragement, int flammability) {
		FireBlock fire = (FireBlock) Blocks.FIRE;
		fire.setFireInfo(block, encouragement, flammability);
	}

	public static void registerCompostable(IItemProvider item, float chance) {
		ComposterBlock.CHANCES.put(item.asItem(), chance);
	}

	public static void registerStrippable(Block log, Block stripped) {
		AxeItem.BLOCK_STRIPPING_MAP = Maps.newHashMap(AxeItem.BLOCK_STRIPPING_MAP);
		AxeItem.BLOCK_STRIPPING_MAP.put(log, stripped);
	}
}