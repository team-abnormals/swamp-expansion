/*
This class was taken and edited from Quark to allow for Compatiblity between it and Bloomful.
*/

package com.farcr.swampexpansion.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;

public class LeafCarpetBlock extends Block {
    private static final VoxelShape CARPET = makeCuboidShape(0, 0, 0, 16, 1, 16);

	public LeafCarpetBlock(Properties properties) {
	    super(properties);
	}

	@Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
	    return CARPET;
	}

	@Override
    public VoxelShape getCollisionShape(@Nonnull BlockState state, @Nonnull IBlockReader world, @Nonnull BlockPos pos, ISelectionContext context) {
	    return VoxelShapes.empty();
	}

	@Override
    public BlockRenderLayer getRenderLayer() {
	    return BlockRenderLayer.CUTOUT_MIPPED;
	}
}