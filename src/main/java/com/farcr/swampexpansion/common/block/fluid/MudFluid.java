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

import java.util.function.BiFunction;

public abstract class MudFluid extends FlowingFluid implements IWaterLoggable {
    public MudFluid() {
        super();
    }

    public Fluid getFlowingFluid() {
        return BlockRegistry.FLOWING_MUD;
    }

    public Fluid getStillFluid() {
        return BlockRegistry.STILL_MUD;
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
        return (BlockState) BlockRegistry.MUD.getDefaultState().with(FlowingFluidBlock.LEVEL, getLevelFromState(state));
    }

    public boolean isEquivalentTo(Fluid fluid) {
        return fluid == BlockRegistry.STILL_MUD || fluid == BlockRegistry.FLOWING_MUD;
    }

    public int getLevelDecreasePerBlock(IWorldReader world) {
        return 1;
    }

    public int getTickRate(IWorldReader world) {
        return 5;
    }

    public boolean func_215665_a(IFluidState state, IBlockReader world, BlockPos pos, Fluid fluid, Direction direction) {
        return direction == Direction.DOWN;
    }

    protected float getExplosionResistance() {
        return 100.0F;
    }

    @Override
    protected FluidAttributes createAttributes() {
        return MudAttributes.builder(new ResourceLocation("swampexpansion", "block/mud_still"), new ResourceLocation("swampexpansion", "block/mud_flow")).translationKey("block.swampexpansion.mud").density(2000).viscosity(4500).build(this);
    }

    public static class MudBuilder extends FluidAttributes.Builder {
        public MudBuilder(ResourceLocation stillTexture, ResourceLocation flowingTexture, BiFunction<FluidAttributes.Builder,Fluid,FluidAttributes> factory) {
            super(stillTexture, flowingTexture, factory);
        }
    }

    public static class MudAttributes extends FluidAttributes {
        public MudAttributes(Builder builder, Fluid fluid) {
            super(builder, fluid);
        }

        public static Builder builder(ResourceLocation stillTexture, ResourceLocation flowingTexture) {
            return new MudBuilder(stillTexture, flowingTexture, MudAttributes::new);
        }
    }

    public static class Flowing extends MudFluid {
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

    public static class Source extends MudFluid {
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