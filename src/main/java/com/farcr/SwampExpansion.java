package com.farcr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.java.com.farcr.block.BlockDoorBase;
import main.java.com.farcr.block.BlockLadderBase;
import main.java.com.farcr.block.BlockPressurePlateBase;
import main.java.com.farcr.block.BlockSaplingBase;
import main.java.com.farcr.block.BlockStairsBase;
import main.java.com.farcr.block.BlockTrapdoorBase;
import main.java.com.farcr.block.BlockWoodButtonBase;
import com.farcr.registries.BlockRegistry;
import com.farcr.registries.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.LogBlock;
import net.minecraft.block.PressurePlateBlock.Sensitivity;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.WallBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("swampexpansion")
public class SwampExpansion
{
	public static SwampExpansion instance;
	public static final String modid = "swampexpansion";
	private static final Logger logger = LogManager.getLogger(modid);
	
	public SwampExpansion()
	{
		instance = this;
		
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientRegistries);	
		
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	private void setup(final FMLCommonSetupEvent event)
	{
		logger.info("Registered Setup Method");
	}
	
	private void clientRegistries(final FMLClientSetupEvent event)
	{
		logger.info("Registered ClientRegistries method.");
	}
	
	private static ResourceLocation location(String name)
	{
		return new ResourceLocation(modid, name);
	}
	
	@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents
	{
		@SubscribeEvent
		public static void registerItems(final RegistryEvent.Register<Item> event) {
			event.getRegistry().registerAll (
					ItemRegistry.mud_ball = new Item(new Item.Properties().group(ItemGroup.MISC).maxStackSize(1)).setRegistryName(location("mud_ball")),
				    ItemRegistry.mud_brick = new Item(new Item.Properties().group(ItemGroup.MISC)).setRegistryName(location("mud_brick")),
				    ItemRegistry.willow_boat = new Item(new Item.Properties().group(ItemGroup.TRANSPORTATION)).setRegistryName(location("willow_boat")),
				    ItemRegistry.willow_sapling =new BlockItem(BlockRegistry.willow_sapling,new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName(location("willow_sapling")),
				    
				    ItemRegistry.mud_bricks =new BlockItem(BlockRegistry.mud_bricks,new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(location("mud_bricks")),
				    ItemRegistry.mud_brick_stairs =new BlockItem(BlockRegistry.mud_brick_stairs,new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(location("mud_brick_stairs")),
				    ItemRegistry.mud_brick_slab =new BlockItem(BlockRegistry.mud_brick_slab,new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(location("mud_brick_slab")),
				    ItemRegistry.mud_brick_wall =new BlockItem(BlockRegistry.mud_brick_wall,new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName(location("mud_brick_wall")),
				    
				    ItemRegistry.willow_planks =new BlockItem(BlockRegistry.willow_planks,new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(location("willow_planks")),
				    ItemRegistry.willow_stairs =new BlockItem(BlockRegistry.willow_stairs,new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(location("willow_stairs")),
				    ItemRegistry.willow_slab =new BlockItem(BlockRegistry.willow_slab,new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(location("willow_slab")),
				    ItemRegistry.willow_fence =new BlockItem(BlockRegistry.willow_fence,new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName(location("willow_fence")),
				    ItemRegistry.willow_fence_gate =new BlockItem(BlockRegistry.willow_fence_gate,new Item.Properties().group(ItemGroup.REDSTONE)).setRegistryName(location("willow_fence_gate")),
				    ItemRegistry.willow_pressure_plate =new BlockItem(BlockRegistry.willow_pressure_plate,new Item.Properties().group(ItemGroup.REDSTONE)).setRegistryName(location("willow_pressure_plate")),
				    ItemRegistry.willow_door =new BlockItem(BlockRegistry.willow_door,new Item.Properties().group(ItemGroup.REDSTONE)).setRegistryName(location("willow_door")),
				    ItemRegistry.willow_trapdoor =new BlockItem(BlockRegistry.willow_trapdoor,new Item.Properties().group(ItemGroup.REDSTONE)).setRegistryName(location("willow_trapdoor")),
				    ItemRegistry.willow_button =new BlockItem(BlockRegistry.willow_button,new Item.Properties().group(ItemGroup.REDSTONE)).setRegistryName(location("willow_button")),

				    ItemRegistry.willow_log =new BlockItem(BlockRegistry.willow_log,new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(location("willow_log")),
				    ItemRegistry.stripped_willow_log =new BlockItem(BlockRegistry.stripped_willow_log,new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(location("stripped_willow_log")),
				    ItemRegistry.willow_wood =new BlockItem(BlockRegistry.willow_wood,new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(location("willow_wood")),
				    ItemRegistry.stripped_willow_wood =new BlockItem(BlockRegistry.stripped_willow_wood,new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(location("stripped_willow_wood")),
				    ItemRegistry.willow_leaves =new BlockItem(BlockRegistry.willow_leaves,new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName(location("willow_leaves"))
			 );
			//Quark
			if (ModList.get().isLoaded("quark")) {
				event.getRegistry().registerAll (
						ItemRegistry.willow_ladder = new BlockItem(BlockRegistry.willow_ladder,new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName(location("willow_ladder")),
						ItemRegistry.willow_bookshelf = new BlockItem(BlockRegistry.willow_bookshelf,new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName(location("willow_bookshelf"))
				);
			}
			logger.info("Registered Items.");
		}
		
		@SubscribeEvent
		public static void registerBlocks(final RegistryEvent.Register<Block> event) {
			event.getRegistry().registerAll (
					BlockRegistry.mud_bricks = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(2, 30).sound(SoundType.STONE)).setRegistryName(location("mud_bricks")),
					BlockRegistry.mud_brick_stairs = new BlockStairsBase(BlockRegistry.mud_bricks.getDefaultState(),Block.Properties.from(BlockRegistry.mud_bricks)).setRegistryName(location("mud_brick_stairs")),
					BlockRegistry.mud_brick_slab = new SlabBlock(Block.Properties.from(BlockRegistry.mud_bricks)).setRegistryName(location("mud_brick_slab")),
					BlockRegistry.mud_brick_wall = new WallBlock(Block.Properties.from(BlockRegistry.mud_bricks)).setRegistryName(location("mud_brick_wall")),
					
					BlockRegistry.willow_planks = new Block(Block.Properties.create(Material.WOOD,MaterialColor.WOOD).hardnessAndResistance(2, 15).sound(SoundType.WOOD)).setRegistryName(location("willow_planks")),
					BlockRegistry.willow_stairs = new BlockStairsBase(BlockRegistry.willow_planks.getDefaultState(),Block.Properties.from(BlockRegistry.willow_planks)).setRegistryName(location("willow_stairs")),
					BlockRegistry.willow_slab = new SlabBlock(Block.Properties.from(BlockRegistry.willow_planks)).setRegistryName(location("willow_slab")),
					BlockRegistry.willow_fence = new FenceBlock(Block.Properties.from(BlockRegistry.willow_planks)).setRegistryName(location("willow_fence")),
					BlockRegistry.willow_fence_gate = new FenceGateBlock(Block.Properties.from(BlockRegistry.willow_planks)).setRegistryName(location("willow_fence_gate")),
					BlockRegistry.willow_pressure_plate = new BlockPressurePlateBase(Sensitivity.EVERYTHING , Block.Properties.from(BlockRegistry.willow_planks)).setRegistryName(location("willow_pressure_plate")),
					BlockRegistry.willow_door = new BlockDoorBase(Block.Properties.from(BlockRegistry.willow_planks)).setRegistryName(location("willow_door")),
					BlockRegistry.willow_trapdoor = new BlockTrapdoorBase(Block.Properties.from(BlockRegistry.willow_planks)).setRegistryName(location("willow_trapdoor")),
					BlockRegistry.willow_button = new BlockWoodButtonBase(Block.Properties.from(BlockRegistry.willow_planks)).setRegistryName(location("willow_button")),

					BlockRegistry.willow_log = new LogBlock(MaterialColor.WOOD, Block.Properties.create(Material.WOOD).hardnessAndResistance(2, 10).sound(SoundType.WOOD)).setRegistryName(location("willow_log")),
					BlockRegistry.stripped_willow_log = new LogBlock(MaterialColor.WOOD, Block.Properties.create(Material.WOOD).hardnessAndResistance(2, 10).sound(SoundType.WOOD)).setRegistryName(location("stripped_willow_log")),
					BlockRegistry.willow_wood = new LogBlock(MaterialColor.WOOD, Block.Properties.from(BlockRegistry.willow_log)).setRegistryName(location("willow_wood")),
					BlockRegistry.stripped_willow_wood = new LogBlock(MaterialColor.WOOD, Block.Properties.from(BlockRegistry.stripped_willow_log)).setRegistryName(location("stripped_willow_wood")),
					BlockRegistry.willow_sapling = new BlockSaplingBase(Block.Properties.create(Material.PLANTS).sound(SoundType.PLANT), null).setRegistryName(location("willow_sapling")),
					BlockRegistry.willow_leaves = new LeavesBlock(Block.Properties.create(Material.LEAVES).sound(SoundType.PLANT)).setRegistryName(location("willow_leaves"))
			);
			//Quark
			if (ModList.get().isLoaded("quark")) {
				event.getRegistry().registerAll (
						BlockRegistry.willow_ladder = new BlockLadderBase(Block.Properties.from(BlockRegistry.willow_planks)).setRegistryName(location("willow_ladder")),
						BlockRegistry.willow_bookshelf = new Block(Block.Properties.from(BlockRegistry.willow_planks)).setRegistryName(location("willow_bookshelf"))
				);
			}
			logger.info("Registered Blocks.");
		}
	}
}