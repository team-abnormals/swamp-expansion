package com.farcr.swampexpansion.common.block;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BushBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class CattailBlock extends BushBlock implements IWaterLoggable {
	protected static final VoxelShape SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public CattailBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, true));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    public void placeAt(IWorld worldIn, BlockPos pos, int flags) {
        worldIn.setBlockState(pos, getDefaultState(), flags);
        worldIn.setBlockState(pos.up(), getDefaultState(), flags);
        worldIn.setBlockState(pos.up(2), getDefaultState(), flags);
    }
    
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Vec3d vec3d = state.getOffset(worldIn, pos);
        return SHAPE.withOffset(vec3d.x, vec3d.y, vec3d.z);
     }
    
    public Block.OffsetType getOffsetType() {
        return Block.OffsetType.XZ;
     }

    @Nullable
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
		boolean flag = ifluidstate.isTagged(FluidTags.WATER) && ifluidstate.getLevel() == 8;
		return this.getDefaultState().with(WATERLOGGED, flag);
	}

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
        BlockPos down = pos.down();
        return world.getBlockState(down).func_224755_d(world, down, Direction.UP) && (world.getBlockState(down).isIn(BlockTags.DIRT_LIKE) || world.getBlockState(down).getBlock() == Blocks.CLAY);
    }
    
    @SuppressWarnings("deprecation")
    @Override
	public IFluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);	
	}

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (!state.isValidPosition(worldIn, currentPos)) {
			return Blocks.AIR.getDefaultState();
		} else {
			if (state.get(WATERLOGGED)) {
				worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));	
			}
			return super.updatePostPlacement(state, facing, facingState, worldIn, currentPos, facingPos);	
		}	
	}
}