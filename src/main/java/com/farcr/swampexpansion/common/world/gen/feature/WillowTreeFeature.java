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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class WillowTreeFeature extends AbstractTreeFeature<NoFeatureConfig> {
   public WillowTreeFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i51425_1_) {
      super(p_i51425_1_, false);
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

            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

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
         } else if (isSoil(worldIn, position.down(), getSapling()) && position.getY() < worldIn.getMaxHeight() - i - 1) {
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
                        if (isAirOrLeaves(worldIn, blockpos) || func_214576_j(worldIn, blockpos)) {
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
               BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();

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
}