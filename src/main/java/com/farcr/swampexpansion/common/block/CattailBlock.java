package com.farcr.swampexpansion.common.block;

import com.farcr.swampexpansion.core.util.BlockProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class CattailBlock extends Block {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_3;
    public static final EnumProperty<Type> TYPE = EnumProperty.create("type", Type.class);

    public CattailBlock() {
        super(BlockProperties.CATTAIL);
        setDefaultState(stateContainer.getBaseState().with(WATERLOGGED, false).with(AGE, 0).with(TYPE, Type.BOTTOM));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, AGE, TYPE);
    }

    public void placeAt(IWorld worldIn, BlockPos pos, int flags) {
        worldIn.setBlockState(pos, getDefaultState().with(TYPE, Type.BOTTOM), flags);
        worldIn.setBlockState(pos.up(), getDefaultState().with(TYPE, Type.MIDDLE), flags);
        worldIn.setBlockState(pos.up(2), getDefaultState().with(TYPE, Type.TOP), flags);
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