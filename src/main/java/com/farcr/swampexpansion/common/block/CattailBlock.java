package com.farcr.swampexpansion.common.block;

import com.farcr.swampexpansion.core.util.BlockProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nullable;

public class CattailBlock extends Block implements IWaterLoggable {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_3;
    public static final EnumProperty<Type> TYPE = EnumProperty.create("type", Type.class);

    public CattailBlock() {
        super(BlockProperties.CATTAIL);
        setDefaultState(stateContainer.getBaseState().with(WATERLOGGED, false).with(AGE, 0).with(TYPE, Type.TOP));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE, TYPE, WATERLOGGED);
    }

    public void placeAt(IWorld worldIn, BlockPos pos, int flags) {
        worldIn.setBlockState(pos, getDefaultState().with(TYPE, Type.BOTTOM), flags);
        worldIn.setBlockState(pos.up(), getDefaultState().with(TYPE, Type.MIDDLE), flags);
        worldIn.setBlockState(pos.up(2), getDefaultState().with(TYPE, Type.TOP), flags);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockPos pos = context.getPos();
        IFluidState fluidState = context.getWorld().getFluidState(pos);
        return getDefaultState().with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
        BlockPos down = pos.down();
        return world.getBlockState(down).func_224755_d(world, down, Direction.UP) && (world.getBlockState(down).isIn(BlockTags.DIRT_LIKE) || world.getBlockState(down).getBlock() == Blocks.CLAY);
    }

    @SuppressWarnings("deprecation")
	public BlockState updatePostPlacement(BlockState state, Direction direction, BlockState state2, IWorld world, BlockPos pos, BlockPos pos2) {
        if ((Boolean)state.get(WATERLOGGED)) {
            world.getPendingFluidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.updatePostPlacement(state, direction, state2, world, pos, pos2);
    }

    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }

    public enum Type implements IStringSerializable {
        TOP("top"),
        MIDDLE("middle"),
        BOTTOM("bottom");

        private String name;

        Type(String nameIn) {
            name = nameIn;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}