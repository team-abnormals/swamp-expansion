package com.farcr.swampexpansion.common.block.fluid;

import com.farcr.swampexpansion.core.registries.BlockRegistry;
import com.farcr.swampexpansion.core.registries.ItemRegistry;
import net.minecraft.block.*;
import net.minecraft.fluid.*;
import net.minecraft.item.Item;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import java.util.function.BiFunction;

public abstract class MudFluid extends ForgeFlowingFluid implements IWaterLoggable {
    public MudFluid(Properties properties) {
        super(properties);
    }


}