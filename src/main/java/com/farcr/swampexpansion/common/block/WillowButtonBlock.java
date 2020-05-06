package com.farcr.swampexpansion.common.block;

import com.farcr.swampexpansion.core.other.SwampExProperties;

import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.IWorldReader;

public class WillowButtonBlock extends AbstractButtonBlock {

    public WillowButtonBlock() {
        super(true, SwampExProperties.WILLOW_BUTTON);
    }

    @Override
    public int tickRate(IWorldReader worldIn) {
        return 30;
    }

    protected SoundEvent getSoundEvent(boolean powered) {
        return powered ? SoundEvents.BLOCK_WOODEN_BUTTON_CLICK_ON : SoundEvents.BLOCK_WOODEN_BUTTON_CLICK_OFF;
    }
}