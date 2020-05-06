package com.farcr.swampexpansion.core.registry;

import com.farcr.swampexpansion.common.block.BookshelfBlock;
import com.farcr.swampexpansion.common.block.CattailBlock;
import com.farcr.swampexpansion.common.block.CattailSproutsBlock;
import com.farcr.swampexpansion.common.block.DoubleCattailBlock;
import com.farcr.swampexpansion.common.block.DoubleRiceBlock;
import com.farcr.swampexpansion.common.block.DuckweedBlock;
import com.farcr.swampexpansion.common.block.HangingWillowLeavesBlock;
import com.farcr.swampexpansion.common.block.LadderBlock;
import com.farcr.swampexpansion.common.block.LeafCarpetBlock;
import com.farcr.swampexpansion.common.block.MudVaseBlock;
import com.farcr.swampexpansion.common.block.RiceBlock;
import com.farcr.swampexpansion.common.block.VerticalSlabBlock;
import com.farcr.swampexpansion.common.block.WillowButtonBlock;
import com.farcr.swampexpansion.common.block.WillowSaplingBlock;
import com.farcr.swampexpansion.common.block.fluid.MudFluidBlock;
import com.farcr.swampexpansion.common.block.thatch.ThatchBlock;
import com.farcr.swampexpansion.common.block.thatch.ThatchSlabBlock;
import com.farcr.swampexpansion.common.block.thatch.ThatchStairsBlock;
import com.farcr.swampexpansion.common.block.thatch.ThatchVerticalSlabBlock;
import com.farcr.swampexpansion.common.world.gen.feature.trees.WillowTree;
import com.farcr.swampexpansion.core.other.SwampExProperties;
import com.farcr.swampexpansion.core.util.RegistryUtils;

import net.minecraft.block.Block;
import net.minecraft.block.Block.Properties;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("deprecation")
@Mod.EventBusSubscriber(modid = "swampexpansion", bus = Mod.EventBusSubscriber.Bus.MOD)
public class SwampExBlocks {
    
	public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, "swampexpansion");
	public static final DeferredRegister<PaintingType> PAINTINGS = new DeferredRegister<>(ForgeRegistries.PAINTING_TYPES, "swampexpansion");

	public static final RegistryObject<Block> MUD_BRICKS 				= RegistryUtils.createBlock("mud_bricks", () -> new Block(SwampExProperties.MUD_BRICKS), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> MUD_BRICK_STAIRS 			= RegistryUtils.createBlock("mud_brick_stairs", () -> new StairsBlock(MUD_BRICKS.get().getDefaultState(), SwampExProperties.MUD_BRICKS), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> MUD_BRICK_SLAB 			= RegistryUtils.createBlock("mud_brick_slab", () -> new SlabBlock(SwampExProperties.MUD_BRICKS), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> MUD_BRICK_WALL 			= RegistryUtils.createBlock("mud_brick_wall", () -> new WallBlock(SwampExProperties.MUD_BRICKS), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> MUD_BRICK_VERTICAL_SLAB	= RegistryUtils.createBlockCompat("quark", "mud_brick_vertical_slab", () -> new VerticalSlabBlock(SwampExProperties.MUD_BRICKS), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> MUD_VASE	 				= RegistryUtils.createBlock("mud_vase", () -> new MudVaseBlock(SwampExProperties.MUD_BRICKS), ItemGroup.DECORATIONS);

	public static final RegistryObject<Block> WILLOW_LOG			= RegistryUtils.createBlock("willow_log", () -> new RotatedPillarBlock(SwampExProperties.LOG), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> WILLOW_WOOD 			= RegistryUtils.createBlock("willow_wood", () -> new RotatedPillarBlock(SwampExProperties.LOG), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> STRIPPED_WILLOW_LOG 	= RegistryUtils.createBlock("stripped_willow_log", () -> new RotatedPillarBlock(SwampExProperties.LOG), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> STRIPPED_WILLOW_WOOD 	= RegistryUtils.createBlock("stripped_willow_wood", () -> new RotatedPillarBlock(SwampExProperties.LOG), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> WILLOW_PLANKS 		= RegistryUtils.createBlock("willow_planks", () -> new Block(SwampExProperties.WILLOW_PLANKS), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> WILLOW_SLAB 			= RegistryUtils.createBlock("willow_slab", () -> new SlabBlock(SwampExProperties.WILLOW_PLANKS), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> WILLOW_STAIRS 		= RegistryUtils.createBlock("willow_stairs", () -> new StairsBlock(WILLOW_PLANKS.get().getDefaultState(), SwampExProperties.WILLOW_PLANKS), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> VERTICAL_WILLOW_PLANKS= RegistryUtils.createBlockCompat("quark", "vertical_willow_planks", () -> new Block(SwampExProperties.WILLOW_PLANKS), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> WILLOW_FENCE 			= RegistryUtils.createBlock("willow_fence", () -> new FenceBlock(SwampExProperties.WILLOW_PLANKS), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> WILLOW_VERTICAL_SLAB 	= RegistryUtils.createBlockCompat("quark", "willow_vertical_slab", () -> new VerticalSlabBlock(SwampExProperties.WILLOW_PLANKS), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> WILLOW_FENCE_GATE 	= RegistryUtils.createBlock("willow_fence_gate", () -> new FenceGateBlock(SwampExProperties.WILLOW_PLANKS), ItemGroup.REDSTONE);
	public static final RegistryObject<Block> WILLOW_PRESSURE_PLATE = RegistryUtils.createBlock("willow_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, SwampExProperties.WILLOW_PRESSURE_PLATE), ItemGroup.REDSTONE);
	public static final RegistryObject<Block> WILLOW_DOOR 			= RegistryUtils.createBlock("willow_door", () -> new DoorBlock(SwampExProperties.WILLOW_DOORS), ItemGroup.REDSTONE);
	public static final RegistryObject<Block> WILLOW_TRAPDOOR 		= RegistryUtils.createBlock("willow_trapdoor", () -> new TrapDoorBlock(SwampExProperties.WILLOW_DOORS), ItemGroup.REDSTONE);
	public static final RegistryObject<Block> WILLOW_SAPLING 		= RegistryUtils.createBlock("willow_sapling", () -> new WillowSaplingBlock(new WillowTree(), SwampExProperties.SAPLING), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> POTTED_WILLOW_SAPLING = RegistryUtils.createBlockNoItem("potted_willow_sapling", () -> new FlowerPotBlock(SwampExBlocks.WILLOW_SAPLING.get(), SwampExProperties.FLOWER_POT));
	public static final RegistryObject<Block> WILLOW_BUTTON 		= RegistryUtils.createBlock("willow_button", () -> new WillowButtonBlock(), ItemGroup.REDSTONE);
	public static final RegistryObject<Block> WILLOW_LEAVES 		= RegistryUtils.createBlock("willow_leaves", () -> new LeavesBlock(SwampExProperties.LEAVES), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> HANGING_WILLOW_LEAVES = RegistryUtils.createBlock("hanging_willow_leaves", () -> new HangingWillowLeavesBlock(SwampExProperties.LEAVES), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> WILLOW_LEAF_CARPET 	= RegistryUtils.createBlockCompat("quark", "willow_leaf_carpet", () -> new LeafCarpetBlock(SwampExProperties.LEAVES.notSolid()), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> WILLOW_LADDER 		= RegistryUtils.createBlockCompat("quark", "willow_ladder", () -> new LadderBlock(SwampExProperties.LADDER), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> WILLOW_BOOKSHELF 		= RegistryUtils.createBlockCompat("quark", "willow_bookshelf", () -> new BookshelfBlock(SwampExProperties.BOOKSHELF), ItemGroup.DECORATIONS);
	//public static final RegistryObject<Block> WILLOW_SIGN 		= RegistryUtils.createBlock("willow_sign", () -> new StandingSignBlock(BlockProperties.WILLOW_PLANKS), ItemGroup.BUILDING_BLOCKS);
	//public static final RegistryObject<Block> WILLOW_WALL_SIGN 	= RegistryUtils.createBlock("willow_wall_sign", () -> new WallSignBlock(BlockProperties.WILLOW_PLANKS), ItemGroup.BUILDING_BLOCKS);
	
	public static final RegistryObject<Block> CATTAIL_SPROUTS 		= RegistryUtils.createBlockNoItem("cattail_sprouts", () -> new CattailSproutsBlock(SwampExProperties.CATTAIL));
	public static final RegistryObject<Block> CATTAIL 				= RegistryUtils.createBlock("cattail", () -> new CattailBlock(SwampExProperties.CATTAIL), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> TALL_CATTAIL 			= RegistryUtils.createBlock("tall_cattail", () -> new DoubleCattailBlock(SwampExProperties.CATTAIL), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> POTTED_CATTAIL		= RegistryUtils.createBlockNoItem("potted_cattail",	() -> new FlowerPotBlock(SwampExBlocks.CATTAIL.get(), SwampExProperties.FLOWER_POT));
	public static final RegistryObject<Block> CATTAIL_SEED_SACK		= RegistryUtils.createBlockCompat("quark", "cattail_seed_sack", () -> new Block(Block.Properties.create(Material.WOOL, MaterialColor.WHITE_TERRACOTTA).hardnessAndResistance(0.5F).sound(SoundType.CLOTH)), ItemGroup.DECORATIONS);

	public static final RegistryObject<Block> CATTAIL_THATCH 				= RegistryUtils.createBlock("cattail_thatch", () -> new ThatchBlock(SwampExProperties.THATCH), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> CATTAIL_THATCH_SLAB     		= RegistryUtils.createBlock("cattail_thatch_slab", () -> new ThatchSlabBlock(SwampExProperties.THATCH), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> CATTAIL_THATCH_STAIRS   		= RegistryUtils.createBlock("cattail_thatch_stairs", () -> new ThatchStairsBlock(CATTAIL_THATCH.get().getDefaultState(), SwampExProperties.THATCH), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> CATTAIL_THATCH_VERTICAL_SLAB	= RegistryUtils.createBlockCompat("quark","cattail_thatch_vertical_slab", () -> new ThatchVerticalSlabBlock(SwampExProperties.THATCH), ItemGroup.BUILDING_BLOCKS);
	
	public static final RegistryObject<Block> RICE 		= RegistryUtils.createBlockNoItem("rice", () -> new RiceBlock(SwampExProperties.RICE));
	public static final RegistryObject<Block> TALL_RICE = RegistryUtils.createBlockNoItem("tall_rice", () -> new DoubleRiceBlock(SwampExProperties.RICE));
	public static final RegistryObject<Block> RICE_SACK	= RegistryUtils.createBlockCompat("quark", "rice_sack", () -> new Block(Block.Properties.create(Material.WOOL, MaterialColor.WHITE_TERRACOTTA).hardnessAndResistance(0.5F).sound(SoundType.CLOTH)), ItemGroup.DECORATIONS);

	public static final RegistryObject<Block> DUCKWEED 						= RegistryUtils.createDuckweed("duckweed", () -> new DuckweedBlock(SwampExProperties.DUCKWEED), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> DUCKWEED_THATCH          		= RegistryUtils.createBlock("duckweed_thatch", () -> new ThatchBlock(SwampExProperties.THATCH), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> DUCKWEED_THATCH_SLAB     		= RegistryUtils.createBlock("duckweed_thatch_slab", () -> new ThatchSlabBlock(SwampExProperties.THATCH), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> DUCKWEED_THATCH_STAIRS   		= RegistryUtils.createBlock("duckweed_thatch_stairs", () -> new ThatchStairsBlock(CATTAIL_THATCH.get().getDefaultState(), SwampExProperties.THATCH), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> DUCKWEED_THATCH_VERTICAL_SLAB	= RegistryUtils.createBlockCompat("quark","duckweed_thatch_vertical_slab", () -> new ThatchVerticalSlabBlock(SwampExProperties.THATCH), ItemGroup.BUILDING_BLOCKS);

	public static final RegistryObject<PaintingType> SNAKE_BLOCK 			= PAINTINGS.register("snake_block", () -> new PaintingType(32, 32));
	public static final RegistryObject<PaintingType> SOMETHING_IN_THE_WATER = PAINTINGS.register("something_in_the_water", () -> new PaintingType(48, 32));
	//public static final RegistryObject<PaintingType> SLABFISH 			= PAINTINGS.register("slabfish", () -> new PaintingType(80, 80));

	public static final RegistryObject<Block> MUD = RegistryUtils.createBlockNoItem("mud", () -> new MudFluidBlock(() -> {return SwampExFluids.FLOWING_MUD;}, Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));
}
