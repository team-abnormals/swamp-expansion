package com.farcr.swampexpansion.common.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WallOrFloorItem;

public class FuelItem extends BlockItem {
    private static int burnTime;

    public FuelItem(Block block, Properties properties, int burnTimeIn) {
        super(block, properties);
        burnTime = burnTimeIn;
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return burnTime;
    }

    public static class WallOrFloorFuelItem extends WallOrFloorItem {

        public WallOrFloorFuelItem(Block floorBlock, Block wallBlockIn, Properties propertiesIn, int burnTimeIn) {
            super(floorBlock, wallBlockIn, propertiesIn);
            burnTime = burnTimeIn;
        }
    }
}