package com.farcr.swampexpansion.common.item;

import com.farcr.swampexpansion.common.entity.WillowBoatEntity;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;

public class WillowBoatItem extends Item {
    private static final Predicate<Entity> field_219989_a = EntityPredicates.NOT_SPECTATING.and(Entity::canBeCollidedWith);
    private final WillowBoatEntity.Type type;

    public WillowBoatItem(WillowBoatEntity.Type typeIn, Properties properties) {
        super(properties);
        type = typeIn;
        DispenserBlock.registerDispenseBehavior(this, new DispenserBoatBehavior(typeIn));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        RayTraceResult raytraceresult = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.ANY);
        if (raytraceresult.getType() == RayTraceResult.Type.MISS) {
            return new ActionResult<>(ActionResultType.PASS, itemstack);
        } else {
            Vec3d vec3d = playerIn.getLook(1.0F);
            List<Entity> list = worldIn.getEntitiesInAABBexcluding(playerIn, playerIn.getBoundingBox().expand(vec3d.scale(5.0D)).grow(1.0D), field_219989_a);
            if (!list.isEmpty()) {
                Vec3d vec3d1 = playerIn.getEyePosition(1.0F);
                for(Entity entity : list) {
                    AxisAlignedBB axisalignedbb = entity.getBoundingBox().grow((double)entity.getCollisionBorderSize());
                    if (axisalignedbb.contains(vec3d1)) {
                        return new ActionResult<>(ActionResultType.PASS, itemstack);
                    }
                }
            }
            if (raytraceresult.getType() == RayTraceResult.Type.BLOCK) {
                WillowBoatEntity boatentity = new WillowBoatEntity(worldIn, raytraceresult.getHitVec().x, raytraceresult.getHitVec().y, raytraceresult.getHitVec().z);
                boatentity.setBoatModel(type);
                boatentity.rotationYaw = playerIn.rotationYaw;
                if (!worldIn.isCollisionBoxesEmpty(boatentity, boatentity.getBoundingBox().grow(-0.1D))) {
                    return new ActionResult<>(ActionResultType.FAIL, itemstack);
                } else {
                    if (!worldIn.isRemote) {
                        worldIn.addEntity(boatentity);
                    }
                    if (!playerIn.abilities.isCreativeMode) {
                        itemstack.shrink(1);
                    }
                    playerIn.addStat(Stats.ITEM_USED.get(this));
                    return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
                }
            } else {
                return new ActionResult<>(ActionResultType.PASS, itemstack);
            }
        }
    }

    static class DispenserBoatBehavior extends DefaultDispenseItemBehavior {
        private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();
        private final WillowBoatEntity.Type type;

        public DispenserBoatBehavior(WillowBoatEntity.Type typeIn) {
            type = typeIn;
        }

        @SuppressWarnings("deprecation")
        public ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack stack) {
            Direction direction = iBlockSource.getBlockState().get(DispenserBlock.FACING);
            World world = iBlockSource.getWorld();
            double x = iBlockSource.getX() + (double) ((float) direction.getXOffset() * 1.125f);
            double y = iBlockSource.getY() + (double) ((float) direction.getYOffset() * 1.125f);
            double z = iBlockSource.getZ() + (double) ((float) direction.getZOffset() * 1.125f);
            BlockPos pos = iBlockSource.getBlockPos().offset(direction);
            double adjustY;
            if (world.getFluidState(pos).isTagged(FluidTags.WATER)) {
                adjustY = 1d;
            } else {
                if (!world.getBlockState(pos).isAir() || !world.getFluidState(pos.down()).isTagged(FluidTags.WATER)) {
                    return defaultDispenseItemBehavior.dispense(iBlockSource, stack);
                }
                adjustY = 0d;
            }
            WillowBoatEntity boat = new WillowBoatEntity(world, x, y + adjustY, z);
            boat.setBoatModel(type);
            boat.rotationYaw = direction.getHorizontalAngle();
            world.addEntity(boat);
            stack.shrink(1);
            return stack;
        }

        protected void playDispenseSound(IBlockSource iBlockSource) {
            iBlockSource.getWorld().playEvent(1000, iBlockSource.getBlockPos(), 0);
        }
    }
}