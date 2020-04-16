package com.farcr.swampexpansion.common.entity.goals;

import java.util.EnumSet;

import com.farcr.swampexpansion.common.entity.SlabfishEntity;

import net.minecraft.entity.ai.goal.Goal;

public class SlabbySitGoal extends Goal {
	private final SlabfishEntity slabb;
	private boolean isSitting;

	public SlabbySitGoal(SlabfishEntity entityIn) {
		this.slabb = entityIn;
		this.setMutexFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean shouldContinueExecuting() {
		return this.isSitting;
	}

	/**
	 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
	 * method as well.
	 */
	public boolean shouldExecute() {
		if (this.slabb.isInWaterOrBubbleColumn()) {
			this.slabb.setSitting(false);
			return false;
		} else if (!this.slabb.onGround) {
			this.slabb.setSitting(false);
			return false;
		} else {
			return isSitting;
		}
	}
	
	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting() {
		this.slabb.getNavigator().clearPath();
		this.slabb.setSitting(true);
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by another one
	 */
	public void resetTask() {
		this.slabb.setSitting(false);
	}

	/**
	 * Sets the sitting flag.
	 */
	public void setSitting(boolean sitting) {
		this.isSitting = sitting;
	}
}
