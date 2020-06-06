package com.farcr.swampexpansion.core.registry;

import com.farcr.swampexpansion.common.block.CattailBlock;
import com.farcr.swampexpansion.common.block.CattailSproutsBlock;
import com.farcr.swampexpansion.common.block.DoubleCattailBlock;
import com.farcr.swampexpansion.common.block.DoubleRiceBlock;
import com.farcr.swampexpansion.common.block.DuckweedBlock;
import com.farcr.swampexpansion.common.block.HangingWillowLeavesBlock;
import com.farcr.swampexpansion.common.block.MudVaseBlock;
import com.farcr.swampexpansion.common.block.RiceBlock;
import com.farcr.swampexpansion.common.block.fluid.MudFluidBlock;
import com.farcr.swampexpansion.common.world.gen.feature.trees.WillowTree;
import com.farcr.swampexpansion.core.SwampExpansion;
import com.farcr.swampexpansion.core.other.SwampExProperties;
import com.mojang.datafixers.util.Pair;
import com.teamabnormals.abnormals_core.common.blocks.AbnormalsLadderBlock;
import com.teamabnormals.abnormals_core.common.blocks.BookshelfBlock;
import com.teamabnormals.abnormals_core.common.blocks.LeafCarpetBlock;
import com.teamabnormals.abnormals_core.common.blocks.VerticalSlabBlock;
import com.teamabnormals.abnormals_core.common.blocks.sign.AbnormalsStandingSignBlock;
import com.teamabnormals.abnormals_core.common.blocks.sign.AbnormalsWallSignBlock;
import com.teamabnormals.abnormals_core.common.blocks.thatch.ThatchBlock;
import com.teamabnormals.abnormals_core.common.blocks.thatch.ThatchSlabBlock;
import com.teamabnormals.abnormals_core.common.blocks.thatch.ThatchStairsBlock;
import com.teamabnormals.abnormals_core.common.blocks.thatch.ThatchVerticalSlabBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.AbnormalsLeavesBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.AbnormalsLogBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.AbnormalsSaplingBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.AbnormalsWoodButtonBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.PlanksBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.StrippedLogBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.StrippedWoodBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.WoodBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.WoodDoorBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.WoodFenceBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.WoodFenceGateBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.WoodPressurePlateBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.WoodSlabBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.WoodStairsBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.WoodTrapDoorBlock;
import com.teamabnormals.abnormals_core.core.utils.RegistryHelper;

import net.minecraft.block.Block;
import net.minecraft.block.Block.Properties;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
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
    
	public static final RegistryHelper HELPER = SwampExpansion.REGISTRY_HELPER;
	public static final DeferredRegister<PaintingType> PAINTINGS = new DeferredRegister<>(ForgeRegistries.PAINTING_TYPES, "swampexpansion");

	public static final RegistryObject<Block> MUD_BRICKS 				= HELPER.createBlock("mud_bricks", () -> new Block(SwampExProperties.MUD_BRICKS), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> MUD_BRICK_STAIRS 			= HELPER.createBlock("mud_brick_stairs", () -> new StairsBlock(MUD_BRICKS.get().getDefaultState(), SwampExProperties.MUD_BRICKS), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> MUD_BRICK_SLAB 			= HELPER.createBlock("mud_brick_slab", () -> new SlabBlock(SwampExProperties.MUD_BRICKS), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> MUD_BRICK_WALL 			= HELPER.createBlock("mud_brick_wall", () -> new WallBlock(SwampExProperties.MUD_BRICKS), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> MUD_BRICK_VERTICAL_SLAB	= HELPER.createCompatBlock("quark", "mud_brick_vertical_slab", () -> new VerticalSlabBlock(SwampExProperties.MUD_BRICKS), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> MUD_VASE	 				= HELPER.createBlock("mud_vase", () -> new MudVaseBlock(SwampExProperties.FLOWER_POT), ItemGroup.DECORATIONS);

	public static final RegistryObject<Block> STRIPPED_WILLOW_LOG 	= HELPER.createBlock("stripped_willow_log", () -> new StrippedLogBlock(SwampExProperties.LOG), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> STRIPPED_WILLOW_WOOD 	= HELPER.createBlock("stripped_willow_wood", () -> new StrippedWoodBlock(SwampExProperties.LOG), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> WILLOW_LOG			= HELPER.createBlock("willow_log", () -> new AbnormalsLogBlock(STRIPPED_WILLOW_LOG, MaterialColor.GREEN, SwampExProperties.LOG), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> WILLOW_WOOD 			= HELPER.createBlock("willow_wood", () -> new WoodBlock(STRIPPED_WILLOW_WOOD, SwampExProperties.LOG), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> WILLOW_PLANKS 		= HELPER.createBlock("willow_planks", () -> new PlanksBlock(SwampExProperties.WILLOW_PLANKS), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> WILLOW_SLAB 			= HELPER.createBlock("willow_slab", () -> new WoodSlabBlock(SwampExProperties.WILLOW_PLANKS), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> WILLOW_STAIRS 		= HELPER.createBlock("willow_stairs", () -> new WoodStairsBlock(WILLOW_PLANKS.get().getDefaultState(), SwampExProperties.WILLOW_PLANKS), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> VERTICAL_WILLOW_PLANKS= HELPER.createCompatBlock("quark", "vertical_willow_planks", () -> new Block(SwampExProperties.WILLOW_PLANKS), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> WILLOW_FENCE 			= HELPER.createBlock("willow_fence", () -> new WoodFenceBlock(SwampExProperties.WILLOW_PLANKS), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> WILLOW_VERTICAL_SLAB 	= HELPER.createCompatBlock("quark", "willow_vertical_slab", () -> new VerticalSlabBlock(SwampExProperties.WILLOW_PLANKS), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> WILLOW_FENCE_GATE 	= HELPER.createBlock("willow_fence_gate", () -> new WoodFenceGateBlock(SwampExProperties.WILLOW_PLANKS), ItemGroup.REDSTONE);
	public static final RegistryObject<Block> WILLOW_PRESSURE_PLATE = HELPER.createBlock("willow_pressure_plate", () -> new WoodPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, SwampExProperties.WILLOW_PRESSURE_PLATE), ItemGroup.REDSTONE);
	public static final RegistryObject<Block> WILLOW_DOOR 			= HELPER.createBlock("willow_door", () -> new WoodDoorBlock(SwampExProperties.WILLOW_DOORS), ItemGroup.REDSTONE);
	public static final RegistryObject<Block> WILLOW_TRAPDOOR 		= HELPER.createBlock("willow_trapdoor", () -> new WoodTrapDoorBlock(SwampExProperties.WILLOW_DOORS), ItemGroup.REDSTONE);
	public static final RegistryObject<Block> WILLOW_SAPLING 		= HELPER.createBlock("willow_sapling", () -> new AbnormalsSaplingBlock(new WillowTree(), SwampExProperties.SAPLING), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> POTTED_WILLOW_SAPLING = HELPER.createBlockNoItem("potted_willow_sapling", () -> new FlowerPotBlock(SwampExBlocks.WILLOW_SAPLING.get(), SwampExProperties.FLOWER_POT));
	public static final RegistryObject<Block> WILLOW_BUTTON 		= HELPER.createBlock("willow_button", () -> new AbnormalsWoodButtonBlock(SwampExProperties.WILLOW_BUTTON), ItemGroup.REDSTONE);
	public static final RegistryObject<Block> WILLOW_LEAVES 		= HELPER.createBlock("willow_leaves", () -> new AbnormalsLeavesBlock(SwampExProperties.LEAVES), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> HANGING_WILLOW_LEAVES = HELPER.createBlock("hanging_willow_leaves", () -> new HangingWillowLeavesBlock(SwampExProperties.LEAVES), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> WILLOW_LEAF_CARPET 	= HELPER.createCompatBlock("quark", "willow_leaf_carpet", () -> new LeafCarpetBlock(SwampExProperties.LEAVES.notSolid()), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> WILLOW_LADDER 		= HELPER.createCompatBlock("quark", "willow_ladder", () -> new AbnormalsLadderBlock(SwampExProperties.LADDER), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> WILLOW_BOOKSHELF 		= HELPER.createCompatBlock("quark", "willow_bookshelf", () -> new BookshelfBlock(SwampExProperties.BOOKSHELF), ItemGroup.DECORATIONS);
	public static final Pair<RegistryObject<AbnormalsStandingSignBlock>, RegistryObject<AbnormalsWallSignBlock>> SIGNS = HELPER.createSignBlock("willow", MaterialColor.GREEN);

	public static final RegistryObject<Block> CATTAIL_SPROUTS 		= HELPER.createBlockNoItem("cattail_sprouts", () -> new CattailSproutsBlock(SwampExProperties.CATTAIL));
	public static final RegistryObject<Block> CATTAIL 				= HELPER.createBlock("cattail", () -> new CattailBlock(SwampExProperties.CATTAIL), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> TALL_CATTAIL 			= HELPER.createBlock("tall_cattail", () -> new DoubleCattailBlock(SwampExProperties.CATTAIL), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> POTTED_CATTAIL		= HELPER.createBlockNoItem("potted_cattail",	() -> new FlowerPotBlock(SwampExBlocks.CATTAIL.get(), SwampExProperties.FLOWER_POT));
	public static final RegistryObject<Block> CATTAIL_SEED_SACK		= HELPER.createCompatBlock("quark", "cattail_seed_sack", () -> new Block(Block.Properties.create(Material.WOOL, MaterialColor.WHITE_TERRACOTTA).hardnessAndResistance(0.5F).sound(SoundType.CLOTH)), ItemGroup.DECORATIONS);

	public static final RegistryObject<Block> CATTAIL_THATCH 				= HELPER.createBlock("cattail_thatch", () -> new ThatchBlock(SwampExProperties.THATCH), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> CATTAIL_THATCH_SLAB     		= HELPER.createBlock("cattail_thatch_slab", () -> new ThatchSlabBlock(SwampExProperties.THATCH), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> CATTAIL_THATCH_STAIRS   		= HELPER.createBlock("cattail_thatch_stairs", () -> new ThatchStairsBlock(CATTAIL_THATCH.get().getDefaultState(), SwampExProperties.THATCH), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> CATTAIL_THATCH_VERTICAL_SLAB	= HELPER.createCompatBlock("quark","cattail_thatch_vertical_slab", () -> new ThatchVerticalSlabBlock(SwampExProperties.THATCH), ItemGroup.BUILDING_BLOCKS);
	
	public static final RegistryObject<Block> RICE 		= HELPER.createBlockNoItem("rice", () -> new RiceBlock(SwampExProperties.RICE));
	public static final RegistryObject<Block> TALL_RICE = HELPER.createBlockNoItem("tall_rice", () -> new DoubleRiceBlock(SwampExProperties.RICE));
	public static final RegistryObject<Block> RICE_SACK	= HELPER.createCompatBlock("quark", "rice_sack", () -> new Block(Block.Properties.create(Material.WOOL, MaterialColor.WHITE_TERRACOTTA).hardnessAndResistance(0.5F).sound(SoundType.CLOTH)), ItemGroup.DECORATIONS);

	public static final RegistryObject<Block> DUCKWEED 						= HELPER.createBlockNoItem("duckweed", () -> new DuckweedBlock(SwampExProperties.DUCKWEED));
	public static final RegistryObject<Block> DUCKWEED_THATCH          		= HELPER.createBlock("duckweed_thatch", () -> new ThatchBlock(SwampExProperties.THATCH), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> DUCKWEED_THATCH_SLAB     		= HELPER.createBlock("duckweed_thatch_slab", () -> new ThatchSlabBlock(SwampExProperties.THATCH), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> DUCKWEED_THATCH_STAIRS   		= HELPER.createBlock("duckweed_thatch_stairs", () -> new ThatchStairsBlock(CATTAIL_THATCH.get().getDefaultState(), SwampExProperties.THATCH), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> DUCKWEED_THATCH_VERTICAL_SLAB	= HELPER.createCompatBlock("quark","duckweed_thatch_vertical_slab", () -> new ThatchVerticalSlabBlock(SwampExProperties.THATCH), ItemGroup.BUILDING_BLOCKS);

	public static final RegistryObject<PaintingType> SNAKE_BLOCK 			= PAINTINGS.register("snake_block", () -> new PaintingType(32, 32));
	public static final RegistryObject<PaintingType> SOMETHING_IN_THE_WATER = PAINTINGS.register("something_in_the_water", () -> new PaintingType(48, 32));
	//public static final RegistryObject<PaintingType> SLABFISH 			= PAINTINGS.register("slabfish", () -> new PaintingType(80, 80));

	public static final RegistryObject<Block> GIANT_TALL_GRASS = HELPER.createBlock("giant_tall_grass", () -> new DoublePlantBlock(Block.Properties.from(Blocks.TALL_GRASS)), ItemGroup.DECORATIONS);
	
	public static final RegistryObject<Block> MUD = HELPER.createBlockNoItem("mud", () -> new MudFluidBlock(() -> {return SwampExFluids.FLOWING_MUD;}, Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));
}
