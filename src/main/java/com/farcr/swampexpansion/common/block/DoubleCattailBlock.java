package com.farcr.swampexpansion.common.block;

import java.util.Random;

import javax.annotation.Nullable;

import com.farcr.swampexpansion.core.registries.SwampExBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class DoubleCattailBlock extends DoublePlantBlock implements IGrowable, IWaterLoggable {
	public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final IntegerProperty AGE = BlockStateProperties.AGE_0_1;
	public static final BooleanProperty BOTTOM_WATERLOGGED = BooleanProperty.create("bottom_waterlogged");

	public DoubleCattailBlock(Properties properties) {
		super(properties);
		this.setDefaultState(
				this.getDefaultState().with(AGE, 0).with(HALF, DoubleBlockHalf.LOWER).with(WATERLOGGED, false));
	}
	
    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        Block block = state.getBlock();
        return block == Blocks.GRASS_BLOCK || block == Blocks.DIRT || block == Blocks.COARSE_DIRT || block == Blocks.PODZOL || block == Blocks.CLAY || block == Blocks.FARMLAND || block.isIn(BlockTags.DIRT_LIKE);
     }

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(AGE, HALF, WATERLOGGED, BOTTOM_WATERLOGGED);
	}

	@Override
	public void placeAt(IWorld world, BlockPos pos, int flags) {
		IFluidState ifluidstate = world.getFluidState(pos);
		IFluidState ifluidstateUp = world.getFluidState(pos.up());
		boolean bottomWaterLogging = Boolean
				.valueOf(ifluidstate.isTagged(FluidTags.WATER) && ifluidstate.getLevel() == 8)
				|| Boolean.valueOf(ifluidstateUp.isTagged(FluidTags.WATER) && ifluidstateUp.getLevel() == 8) ? true
						: false;
		world.setBlockState(pos,
				this.getDefaultState().with(HALF, DoubleBlockHalf.LOWER)
						.with(WATERLOGGED,
								Boolean.valueOf(ifluidstate.isTagged(FluidTags.WATER) && ifluidstate.getLevel() == 8))
						.with(BOTTOM_WATERLOGGED, bottomWaterLogging),
				flags);
		world.setBlockState(pos.up(), this.getDefaultState().with(HALF, DoubleBlockHalf.UPPER)
				.with(WATERLOGGED,
						Boolean.valueOf(ifluidstateUp.isTagged(FluidTags.WATER) && ifluidstateUp.getLevel() == 8))
				.with(BOTTOM_WATERLOGGED, bottomWaterLogging), flags);
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		if (state.get(HALF) != DoubleBlockHalf.UPPER) {
			return ((worldIn.getBlockState(pos.down()).isIn(BlockTags.DIRT_LIKE) || worldIn.getBlockState(pos.down()).getBlock() == Blocks.CLAY|| worldIn.getBlockState(pos.down()).getBlock() == Blocks.FARMLAND));
		} else {
			BlockState blockstate = worldIn.getBlockState(pos.down());
			if (state.getBlock() != this)
				return super.isValidPosition(state, worldIn, pos);
			return blockstate.getBlock() == this && blockstate.get(HALF) == DoubleBlockHalf.LOWER;
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
		super.tick(state, worldIn, pos, random);
		int i = state.get(AGE);
		if (state.get(HALF) == DoubleBlockHalf.UPPER && i < 1 && worldIn.getBlockState(pos.down().down()).getBlock() == Blocks.FARMLAND && worldIn.getLightSubtracted(pos.up(), 0) >= 9 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt(5) == 0)) {
			worldIn.setBlockState(pos, state.with(AGE, Integer.valueOf(i + 1)), 2);
			if (worldIn.getBlockState(pos.down()).getBlock() == this && worldIn.getBlockState(pos.down()).get(AGE) == 0) {
				worldIn.setBlockState(pos.down(), worldIn.getBlockState(pos.down()).with(AGE, Integer.valueOf(i + 1)), 2);
			}
			net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
		}
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
		BlockPos blockpos = context.getPos();
		return blockpos.getY() < context.getWorld().getDimension().getHeight() - 1
				&& context.getWorld().getBlockState(blockpos.up()).isReplaceable(context)
						? super.getStateForPlacement(context)
								.with(WATERLOGGED,
										Boolean.valueOf(
												ifluidstate.isTagged(FluidTags.WATER) && ifluidstate.getLevel() == 8))
								.with(BOTTOM_WATERLOGGED,
										Boolean.valueOf(
												ifluidstate.isTagged(FluidTags.WATER) && ifluidstate.getLevel() == 8))
						: null;
	}

	@SuppressWarnings("deprecation")
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn,
			BlockRayTraceResult hit) {
		int i = state.get(AGE);
		boolean flag = i == 1;
		if (!flag && player.getHeldItem(handIn).getItem() == Items.BONE_MEAL) {
			return false;
		} else if (i > 0) {
			Random rand = new Random();
			int j = 1 + rand.nextInt(3);
			spawnAsEntity(worldIn, pos, new ItemStack(SwampExBlocks.CATTAIL_SEEDS.get(), j));
			worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_SWEET_BERRIES_PICK_FROM_BUSH,
					SoundCategory.BLOCKS, 1.0F, 0.8F + worldIn.rand.nextFloat() * 0.4F);
			worldIn.setBlockState(pos, state.with(AGE, Integer.valueOf(0)), 2);
			if (state.get(HALF) == DoubleBlockHalf.UPPER) {
				worldIn.setBlockState(pos.down(), worldIn.getBlockState(pos.down()).with(AGE, Integer.valueOf(0)), 2);
			} else {
				worldIn.setBlockState(pos.up(), worldIn.getBlockState(pos.up()).with(AGE, Integer.valueOf(0)), 2);
			}
			return true;
		} else {
			return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
		}
	}

	@SuppressWarnings("deprecation")
	public IFluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}

	@Override
	public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
		return false;
	}

	public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
		return state.get(AGE) < 1;
	}

	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
		return state.get(AGE) < 1;
	}

	public void grow(World worldIn, Random rand, BlockPos pos, BlockState state) {
		int i = state.get(AGE);
		if (i < 1) {
			worldIn.setBlockState(pos, state.with(AGE, i + 1));
			if (state.get(HALF) == DoubleBlockHalf.UPPER) {
				worldIn.setBlockState(pos.down(), worldIn.getBlockState(pos.down()).with(AGE, i + 1));
			} else {
				worldIn.setBlockState(pos.up(), worldIn.getBlockState(pos.up()).with(AGE, i + 1));
			}
			
		}
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
			BlockPos currentPos, BlockPos facingPos) {
		DoubleBlockHalf doubleblockhalf = stateIn.get(HALF);
		if (stateIn.get(WATERLOGGED)) {
			worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
			if (doubleblockhalf == DoubleBlockHalf.UPPER && !stateIn.get(BOTTOM_WATERLOGGED)) {
				stateIn = stateIn.with(BOTTOM_WATERLOGGED, true);
				worldIn.setBlockState(currentPos.down(), worldIn.getBlockState(currentPos.down()).with(BOTTOM_WATERLOGGED, true), 2);
			} else if (doubleblockhalf == DoubleBlockHalf.LOWER && !stateIn.get(BOTTOM_WATERLOGGED)) {
				stateIn = stateIn.with(BOTTOM_WATERLOGGED, true);
				worldIn.setBlockState(currentPos.up(),
						worldIn.getBlockState(currentPos.up()).with(BOTTOM_WATERLOGGED, true), 2);
			}
		}
		if (facing.getAxis() != Direction.Axis.Y || doubleblockhalf == DoubleBlockHalf.LOWER != (facing == Direction.UP)
				|| facingState.getBlock() == this && facingState.get(HALF) != doubleblockhalf) {
			return doubleblockhalf == DoubleBlockHalf.LOWER && facing == Direction.DOWN
					&& !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : stateIn;
		} else {
			return Blocks.AIR.getDefaultState();
		}
	}
}