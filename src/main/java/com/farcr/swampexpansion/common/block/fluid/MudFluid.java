package com.farcr.swampexpansion.common.block.fluid;

import net.minecraft.block.*;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public abstract class MudFluid extends ForgeFlowingFluid implements IWaterLoggable {
    public MudFluid(Properties properties) {
        super(properties);
    }


}