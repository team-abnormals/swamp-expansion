package com.farcr.swampexpansion.common.entity.goals;

import java.util.EnumSet;
import java.util.List;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.AnimalEntity;

public class SlabbyFollowParentGoal extends Goal {
	private final AnimalEntity childAnimal;
	private AnimalEntity parentAnimal;
	private final double moveSpeed;
	private int delayCounter;

	public SlabbyFollowParentGoal(AnimalEntity animal, double speed) {
		this.childAnimal = animal;
		this.moveSpeed = speed;
		this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
	}

	   public boolean shouldExecute() {
	      if (this.childAnimal.getGrowingAge() >= 0) {
	         return false;
	      } else {
	         List<AnimalEntity> list = this.childAnimal.world.getEntitiesWithinAABB(this.childAnimal.getClass(), this.childAnimal.getBoundingBox().grow(8.0D, 4.0D, 8.0D));
	         AnimalEntity animalentity = null;
	         double d0 = Double.MAX_VALUE;

	         for(AnimalEntity animalentity1 : list) {
	            if (animalentity1.getGrowingAge() >= 0) {
	               double d1 = this.childAnimal.getDistanceSq(animalentity1);
	               if (!(d1 > d0)) {
	                  d0 = d1;
	                  animalentity = animalentity1;
	               }
	            }
	         }

	         if (animalentity == null) {
	            return false;
	         } else if (d0 < 9.0D) {
	            return false;
	         } else {
	            this.parentAnimal = animalentity;
	            return true;
	         }
	      }
	   }

	   public boolean shouldContinueExecuting() {
	      if (this.childAnimal.getGrowingAge() >= 0) {
	         return false;
	      } else if (!this.parentAnimal.isAlive()) {
	         return false;
	      } else {
	         double d0 = this.childAnimal.getDistanceSq(this.parentAnimal);
	         return !(d0 < 9.0D) && !(d0 > 256.0D);
	      }
	   }

	   public void startExecuting() {
	      this.delayCounter = 0;
	   }

	   public void resetTask() {
	      this.parentAnimal = null;
	   }

	   public void tick() {
	      if (--this.delayCounter <= 0) {
	         this.delayCounter = 10;
	         this.childAnimal.getNavigator().tryMoveToEntityLiving(this.parentAnimal, this.moveSpeed);
	      }
	   }
	}
