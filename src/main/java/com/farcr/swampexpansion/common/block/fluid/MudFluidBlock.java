package com.farcr.swampexpansion.common.block.fluid;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import java.util.function.Supplier;

public class MudFluidBlock extends FlowingFluidBlock {


    public MudFluidBlock(Supplier<? extends FlowingFluid> fluid, Properties properties) {
        super(fluid, properties);
    }

    @Override
    public int tickRate(IWorldReader p_149738_1_) { return 35; }

    @Override
    public boolean reactWithNeighbors(World worldIn, BlockPos pos, BlockState p_204515_3_)
    {
        for(Direction direction : Direction.values()) {
            if (direction != Direction.DOWN && worldIn.getFluidState(pos.offset(direction)).isTagged(FluidTags.LAVA)) {
                worldIn.setBlockState(pos, net.minecraftforge.event.ForgeEventFactory.fireFluidPlaceBlockEvent(worldIn, pos, pos, Blocks.DIRT.getDefaultState()));
            }
        }
        return super.reactWithNeighbors(worldIn, pos, p_204515_3_);
    }
    
    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
           entityIn.setMotionMultiplier(state, new Vec3d(0.9D, (double)0.8F, 0.9D));
     }

} 