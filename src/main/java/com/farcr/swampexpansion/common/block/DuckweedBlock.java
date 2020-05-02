package com.farcr.swampexpansion.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class DuckweedBlock extends BushBlock {
	protected static final VoxelShape DUCKWEED_AABB = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 1.5D, 15.0D);

	public DuckweedBlock(Block.Properties builder) {
		super(builder);
	}

//	@SuppressWarnings("deprecation")
//	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
//		super.onEntityCollision(state, worldIn, pos, entityIn);
//		if (worldIn instanceof ServerWorld && entityIn instanceof BoatEntity) {
//			worldIn.destroyBlock(new BlockPos(pos), true, entityIn);
//		}
//	}

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return DUCKWEED_AABB;
	}

	@Override
	public boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
		IFluidState ifluidstate = worldIn.getFluidState(pos);
		return ifluidstate.getFluid() == Fluids.WATER;
	}
	
	@Override
	public net.minecraftforge.common.PlantType getPlantType(IBlockReader world, BlockPos pos) {
		return net.minecraftforge.common.PlantType.Water;
	}
}
