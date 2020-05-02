package com.farcr.swampexpansion.core.registries;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.farcr.swampexpansion.common.entity.SlabfishEntity;
import com.farcr.swampexpansion.common.entity.SlabfishOverlay;
import com.farcr.swampexpansion.common.entity.SlabfishType;
import com.farcr.swampexpansion.core.SwampExpansion;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTables;
import net.minecraft.world.storage.loot.TableLootEntry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = SwampExpansion.MODID)
public class SwampExEvents {
	private static final Set<ResourceLocation> RICE_SHIPWRECK_LOOT_INJECTIONS = Sets.newHashSet(LootTables.CHESTS_SHIPWRECK_SUPPLY);
	
	@SubscribeEvent
	public static void onInjectLoot(LootTableLoadEvent event) {
		if (RICE_SHIPWRECK_LOOT_INJECTIONS.contains(event.getName())) {
			LootPool pool = LootPool.builder().addEntry(TableLootEntry.builder(new ResourceLocation(SwampExpansion.MODID, "injections/rice_shipwreck")).weight(1).quality(0)).name("rice_shipwreck").build();
			event.getTable().addPool(pool);
		}
	}
	
	
	@SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onThrowableImpact(final ProjectileImpactEvent.Throwable event) {

        ThrowableEntity projectileEntity = event.getThrowable();

        if (projectileEntity instanceof PotionEntity) {
            PotionEntity potionEntity = ((PotionEntity) projectileEntity);
            ItemStack itemstack = potionEntity.getItem();
            Potion potion = PotionUtils.getPotionFromItem(itemstack);
            List<EffectInstance> list = PotionUtils.getEffectsFromStack(itemstack);

            if (potion == Potions.WATER && list.isEmpty()) {
                AxisAlignedBB axisalignedbb = potionEntity.getBoundingBox().grow(2.0D, 1.0D, 2.0D);
                List<SlabfishEntity> slabs = potionEntity.world.getEntitiesWithinAABB(SlabfishEntity.class, axisalignedbb);
                if(slabs != null && slabs.size() > 0) {
                    for (SlabfishEntity slabfish : slabs) {
                    	slabfish.setSlabfishOverlay(SlabfishOverlay.NONE);
                    }
                }
            }
        }
        
        if (projectileEntity instanceof SnowballEntity) {
        	SnowballEntity snowball = (SnowballEntity)projectileEntity;
        	if (snowball.getItem().getItem() == Items.SNOWBALL) {
        		if (event.getRayTraceResult().getType() == RayTraceResult.Type.ENTITY ) {
        			EntityRayTraceResult entity = (EntityRayTraceResult)event.getRayTraceResult();
        			if (entity.getEntity() instanceof SlabfishEntity) {
        				SlabfishEntity slabfish = (SlabfishEntity)entity.getEntity();
                    	slabfish.setSlabfishOverlay(SlabfishOverlay.SNOWY);
        			}
        		}
        	}
        }
    }
	
	protected static final Map<Block, BlockState> HOE_LOOKUP = Maps.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Blocks.FARMLAND.getDefaultState(), Blocks.GRASS_PATH, Blocks.FARMLAND.getDefaultState(), Blocks.DIRT, Blocks.FARMLAND.getDefaultState(), Blocks.COARSE_DIRT, Blocks.DIRT.getDefaultState()));

	@SubscribeEvent
	public static void underwaterHoe(RightClickBlock event) {
		if (event.getItemStack().getItem() instanceof HoeItem) {
			World world = event.getWorld();
			BlockPos blockpos = event.getPos();
			if (event.getFace() != Direction.DOWN) {
				BlockState blockstate = HOE_LOOKUP.get(world.getBlockState(blockpos).getBlock());
				if (blockstate != null) {
					PlayerEntity playerentity = event.getPlayer();
		            world.playSound(playerentity, blockpos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
		            playerentity.swingArm(event.getHand());
		            if (!world.isRemote) {
		            	world.setBlockState(blockpos, blockstate, 11);
		            	if (playerentity != null) {
		            		event.getItemStack().damageItem(1, playerentity, (p_220043_1_) -> {
		            			p_220043_1_.sendBreakAnimation(event.getHand());
		            		});
		            	}
		            }
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void SlabfishDeath(LivingDeathEvent event) {
		if (event.getEntity() instanceof SlabfishEntity) {
			SlabfishEntity entity = (SlabfishEntity)event.getEntity();
			if (entity.getEntityWorld().getDimension().getType() == DimensionType.THE_NETHER) {
				if (!entity.getEntityWorld().isRemote && entity.getSlabfishType() != SlabfishType.GHOST) {
					SlabfishEntity ghost = SwampExEntities.SLABFISH.get().create(entity.world);					
					ghost.addPotionEffect(new EffectInstance(Effects.LEVITATION, 140, 0, false, false));
					ghost.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 140, 0, false, false));
					entity.getEntityWorld().playSound(null, entity.getPosition(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.NEUTRAL, 1, 1);
					ghost.setPosition(entity.getPosX(), entity.getPosY(), entity.getPosZ());
					ghost.setLocationAndAngles(entity.getPosX(), entity.getPosY(), entity.getPosZ(), entity.rotationYaw, entity.rotationPitch);
					ghost.setNoAI(((MobEntity) entity).isAIDisabled());
		    		ghost.setGrowingAge(entity.getGrowingAge());
		    		if(entity.hasCustomName()) {
		    			ghost.setCustomName(entity.getCustomName());
		    			ghost.setCustomNameVisible(entity.isCustomNameVisible());
		    		}
		    		ghost.setSlabfishType(SlabfishType.GHOST);
					ghost.setFireTimer(0);
					entity.getEntityWorld().addEntity(ghost);
				}
			}
		}
	}
}

