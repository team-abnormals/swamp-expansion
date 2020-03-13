package com.farcr.swampexpansion.common.world.gen.feature;

import com.farcr.swampexpansion.core.registries.SwampExBlocks;
import com.mojang.datafixers.Dynamic;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.state.BooleanProperty;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.gen.IWorldGenerationBaseReader;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraftforge.common.IPlantable;

public class WillowTreeFeature extends TreeFeature {
	public WillowTreeFeature(Function<Dynamic<?>, ? extends TreeFeatureConfig> p_i51443_1_, boolean p_i51443_2_) {
		super(p_i51443_1_);
	}

   public boolean place(Set<BlockPos> changedBlocks, IWorldGenerationReader worldIn, Random rand, BlockPos position, MutableBoundingBox p_208519_5_) {
      int i = rand.nextInt(4) + 5;
      boolean flag = true;
      if (position.getY() >= 1 && position.getY() + i + 1 <= worldIn.getMaxHeight()) {
         for(int j = position.getY(); j <= position.getY() + 1 + i; ++j) {
            int k = 1;
            if (j == position.getY()) {
               k = 0;
            }

            if (j >= position.getY() + 1 + i - 2) {
               k = 3;
            }

            BlockPos.Mutable blockpos$mutableblockpos = new BlockPos.Mutable();

            for(int l = position.getX() - k; l <= position.getX() + k && flag; ++l) {
               for(int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; ++i1) {
                  if (j >= 0 && j < worldIn.getMaxHeight()) {
                     blockpos$mutableblockpos.setPos(l, j, i1);
                     if (!isAirOrLeaves(worldIn, blockpos$mutableblockpos)) {
                        if (isWater(worldIn, blockpos$mutableblockpos)) {
                           if (j > position.getY()) {
                              flag = false;
                           }
                        } else {
                           flag = false;
                        }
                     }
                  } else {
                     flag = false;
                  }
               }
            }
         }

         if (!flag) {
            return false;
         } else if (isSoil(worldIn, position.down(), (IPlantable)SwampExBlocks.WILLOW_SAPLING.get().getDefaultState()) && position.getY() < worldIn.getMaxHeight() - i - 1) {
            this.setDirtAt(worldIn, position.down(), position);

            for(int l1 = position.getY() - 3 + i; l1 <= position.getY() + i; ++l1) {
               int k2 = l1 - (position.getY() + i);
               int i3 = 2 - k2 / 2;

               for(int k3 = position.getX() - i3; k3 <= position.getX() + i3; ++k3) {
                  int l3 = k3 - position.getX();

                  for(int j1 = position.getZ() - i3; j1 <= position.getZ() + i3; ++j1) {
                     int k1 = j1 - position.getZ();
                     if (Math.abs(l3) != i3 || Math.abs(k1) != i3 || rand.nextInt(2) != 0 && k2 != 0) {
                        BlockPos blockpos = new BlockPos(k3, l1, j1);
                        if (isAirOrLeaves(worldIn, blockpos) || isTallPlants(worldIn, blockpos)) {
                           this.setLogState(changedBlocks, worldIn, blockpos, SwampExBlocks.WILLOW_LEAVES.get().getDefaultState(), p_208519_5_);
                        }
                        if (isAir(worldIn, blockpos.down()) && !(blockpos.getX() == position.getX() && blockpos.getZ() == position.getZ())) {
                        	if (rand.nextInt(3) == 0) {
                        		this.setLogState(changedBlocks, worldIn, blockpos.down(), SwampExBlocks.HANGING_WILLOW_LEAVES.get().getDefaultState(), p_208519_5_);
                        	}
                        }
                     }
                  }
               }
            }

            for(int i2 = 0; i2 < i; ++i2) {
               BlockPos blockpos3 = position.up(i2);
               if (isAirOrLeaves(worldIn, blockpos3) || isWater(worldIn, blockpos3)) {
                  this.setLogState(changedBlocks, worldIn, blockpos3, SwampExBlocks.WILLOW_LOG.get().getDefaultState(), p_208519_5_);
               }
            }

            for(int j2 = position.getY() - 3 + i; j2 <= position.getY() + i; ++j2) {
               int l2 = j2 - (position.getY() + i);
               int j3 = 2 - l2 / 2;
               BlockPos.Mutable blockpos$mutableblockpos1 = new BlockPos.Mutable();

               for(int i4 = position.getX() - j3; i4 <= position.getX() + j3; ++i4) {
                  for(int j4 = position.getZ() - j3; j4 <= position.getZ() + j3; ++j4) {
                     blockpos$mutableblockpos1.setPos(i4, j2, j4);
                     if (isLeaves(worldIn, blockpos$mutableblockpos1)) {
                        BlockPos blockpos4 = blockpos$mutableblockpos1.west();
                        BlockPos blockpos5 = blockpos$mutableblockpos1.east();
                        BlockPos blockpos1 = blockpos$mutableblockpos1.north();
                        BlockPos blockpos2 = blockpos$mutableblockpos1.south();
                        if (rand.nextInt(4) == 0 && isAir(worldIn, blockpos4)) {
                           this.addVine(worldIn, blockpos4, VineBlock.EAST);
                        }

                        if (rand.nextInt(4) == 0 && isAir(worldIn, blockpos5)) {
                           this.addVine(worldIn, blockpos5, VineBlock.WEST);
                        }

                        if (rand.nextInt(4) == 0 && isAir(worldIn, blockpos1)) {
                           this.addVine(worldIn, blockpos1, VineBlock.SOUTH);
                        }

                        if (rand.nextInt(4) == 0 && isAir(worldIn, blockpos2)) {
                           this.addVine(worldIn, blockpos2, VineBlock.NORTH);
                        }
                     }
                  }
               }
            }

            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private void addVine(IWorldGenerationReader worldIn, BlockPos pos, BooleanProperty prop) {
      BlockState blockstate = Blocks.VINE.getDefaultState().with(prop, Boolean.valueOf(true));
      this.setBlockState(worldIn, pos, blockstate);
      int i = 4;

      for(BlockPos blockpos = pos.down(); isAir(worldIn, blockpos) && i > 0; --i) {
         this.setBlockState(worldIn, blockpos, blockstate);
         blockpos = blockpos.down();
      }

   }
   
	protected final void setLogState(Set<BlockPos> changedBlocks, IWorldWriter worldIn, BlockPos pos, BlockState p_208520_4_, MutableBoundingBox p_208520_5_) {
	      this.func_208521_b(worldIn, pos, p_208520_4_);
	      p_208520_5_.expandTo(new MutableBoundingBox(pos, pos));
	      if (BlockTags.LOGS.contains(p_208520_4_.getBlock())) {
	         changedBlocks.add(pos.toImmutable());
	      }
	   }
	
	private void func_208521_b(IWorldWriter p_208521_1_, BlockPos p_208521_2_, BlockState p_208521_3_) {
		p_208521_1_.setBlockState(p_208521_2_, p_208521_3_, 18);
	   }
	
	public static boolean isLeaves(IWorldGenerationBaseReader worldIn, BlockPos pos) {
	      if (worldIn instanceof net.minecraft.world.IWorldReader) // FORGE: Redirect to state method when possible
	         return worldIn.hasBlockState(pos, state -> state.canBeReplacedByLeaves((net.minecraft.world.IWorldReader)worldIn, pos));
	      return worldIn.hasBlockState(pos, (p_227223_0_) -> {
	         return  p_227223_0_.isIn(BlockTags.LEAVES);
	      });
	   }
	
	@SuppressWarnings("deprecation")
	public static boolean isAirOrLeavesOrSapling(IWorldGenerationBaseReader worldIn, BlockPos pos) {
	      if (worldIn instanceof net.minecraft.world.IWorldReader) // FORGE: Redirect to state method when possible
	         return worldIn.hasBlockState(pos, state -> state.canBeReplacedByLeaves((net.minecraft.world.IWorldReader)worldIn, pos));
	      return worldIn.hasBlockState(pos, (p_227223_0_) -> {
	         return p_227223_0_.isAir() || p_227223_0_.isIn(BlockTags.LEAVES) || p_227223_0_ == SwampExBlocks.WILLOW_SAPLING.get().getDefaultState();
	      });
	   }
}