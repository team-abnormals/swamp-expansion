package com.farcr.swampexpansion.common.block;

import com.farcr.swampexpansion.core.util.BlockProperties;
import net.minecraft.block.Block;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.BlockStateProperties;

public class CattailBlock extends Block {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_3;

    public CattailBlock() {
        super(BlockProperties.CATTAIL);
    }
}