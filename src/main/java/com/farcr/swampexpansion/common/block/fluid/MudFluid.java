package com.farcr.swampexpansion.common.block.fluid;

import com.farcr.swampexpansion.core.registries.ItemRegistry;
import net.minecraft.block.*;
import net.minecraft.fluid.*;
import net.minecraft.item.Item;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class MudFluid extends FlowingFluid implements IWaterLoggable {
    public MudFluid() {
        super();
    }

    public Fluid getFlowingFluid() {
        return Fluids.FLOWING_WATER;
    }

    public Fluid getStillFluid() {
        return Fluids.WATER;
    }

    @OnlyIn(Dist.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.SOLID;
    }

    public Item getFilledBucket() {
        return ItemRegistry.MUD_BUCKET;
    }

    protected boolean canSourcesMultiply() {
        return false;
    }

    protected void beforeReplacingBlock(IWorld world, BlockPos pos, BlockState state) {
        TileEntity tile = state.getBlock().hasTileEntity() ? world.getTileEntity(pos) : null;
        Block.spawnDrops(state, world.getWorld(), pos, tile);
    }

    public int getSlopeFindDistance(IWorldReader world) {
        return 4;
    }

    public BlockState getBlockState(IFluidState state) {
        return (BlockState) Blocks.WATER.getDefaultState().with(FlowingFluidBlock.LEVEL, getLevelFromState(state));
    }

    public boolean isEquivalentTo(Fluid fluid) {
        return fluid == Fluids.WATER || fluid == Fluids.FLOWING_WATER;
    }

    public int getLevelDecreasePerBlock(IWorldReader world) {
        return 1;
    }

    public int getTickRate(IWorldReader world) {
        return 5;
    }

    public boolean func_215665_a(IFluidState state, IBlockReader world, BlockPos pos, Fluid fluid, Direction direction) {
        return direction == Direction.DOWN && !fluid.isIn(FluidTags.WATER);
    }

    protected float getExplosionResistance() {
        return 100.0F;
    }

    public static class Flowing extends WaterFluid {
        public Flowing() {
        }

        protected void fillStateContainer(StateContainer.Builder<Fluid, IFluidState> builder) {
            super.fillStateContainer(builder);
            builder.add(LEVEL_1_8);
        }

        public int getLevel(IFluidState state) {
            return (Integer)state.get(LEVEL_1_8);
        }

        public boolean isSource(IFluidState state) {
            return false;
        }
    }

    public static class Source extends WaterFluid {
        public Source() {
        }

        public int getLevel(IFluidState state) {
            return 8;
        }

        public boolean isSource(IFluidState state) {
            return true;
        }
    }
}