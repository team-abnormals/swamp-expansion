package com.farcr.swampexpansion.core.registries;

import java.util.Map;
import java.util.Set;

import com.farcr.swampexpansion.core.SwampExpansion;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTables;
import net.minecraft.world.storage.loot.TableLootEntry;
import net.minecraftforge.event.LootTableLoadEvent;
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
}

