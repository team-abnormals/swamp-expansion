package com.farcr.swampexpansion.common.entity;

import com.farcr.swampexpansion.core.registries.SwampExBlocks;
import com.farcr.swampexpansion.core.registries.SwampExEntities;
import com.farcr.swampexpansion.core.registries.SwampExItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LilyPadBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.client.CSteerBoatPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;

public class WillowBoatEntity extends BoatEntity {
	private static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager.createKey(WillowBoatEntity.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> FORWARD_DIRECTION = EntityDataManager.createKey(WillowBoatEntity.class, DataSerializers.VARINT);
	private static final DataParameter<Float> DAMAGE_TAKEN = EntityDataManager.createKey(WillowBoatEntity.class, DataSerializers.FLOAT);
	private static final DataParameter<Integer> BOAT_TYPE = EntityDataManager.createKey(WillowBoatEntity.class, DataSerializers.VARINT);
	private static final DataParameter<Boolean> field_199704_e = EntityDataManager.createKey(WillowBoatEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> field_199705_f = EntityDataManager.createKey(WillowBoatEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> ROCKING_TICKS = EntityDataManager.createKey(WillowBoatEntity.class, DataSerializers.VARINT);
	private final float[] paddlePositions = new float[2];
	private float momentum;
	private float outOfControlTicks;
	private float deltaRotation;
	private boolean leftInputDown;
	private boolean rightInputDown;
	private boolean forwardInputDown;
	private boolean backInputDown;
	private double waterLevel;
	private float boatGlide;
	private Status status;
	private Status previousStatus;
	private double lastYd;
	private boolean rocking;
	private boolean field_203060_aN;
	private float rockingIntensity;
	private float rockingAngle;
	private float prevRockingAngle;

	public WillowBoatEntity(EntityType<? extends BoatEntity> type, World world) {
		super(type, world);
		preventEntitySpawning = true;
	}

	public WillowBoatEntity(World worldIn, double x, double y, double z) {
		this(SwampExEntities.WILLOW_BOAT, worldIn);
		setPosition(x, y, z);
		setMotion(Vec3d.ZERO);
		prevPosX = x;
		prevPosY = y;
		prevPosZ = z;
	}

	public WillowBoatEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
		this(SwampExEntities.WILLOW_BOAT, world);
	}

	@Override
	protected void registerData() {
		dataManager.register(TIME_SINCE_HIT, 0);
		dataManager.register(FORWARD_DIRECTION, 1);
		dataManager.register(DAMAGE_TAKEN, 0.0F);
		dataManager.register(BOAT_TYPE, Type.WILLOW.ordinal());
		dataManager.register(field_199704_e, false);
		dataManager.register(field_199705_f, false);
		dataManager.register(ROCKING_TICKS, 0);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (isInvulnerableTo(source)) {
			return false;
		} else if (!world.isRemote && !removed) {
			if (source instanceof IndirectEntityDamageSource && source.getTrueSource() != null && isPassenger(source.getTrueSource())) {
				return false;
			} else {
				setForwardDirection(-getForwardDirection());
				setTimeSinceHit(10);
				setDamageTaken(getDamageTaken() + amount * 10.0F);
				markVelocityChanged();
				boolean flag = source.getTrueSource() instanceof PlayerEntity && ((PlayerEntity) source.getTrueSource()).abilities.isCreativeMode;
				if (flag || getDamageTaken() > 40.0F) {
					if (!flag && world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
						entityDropItem(getItemBoat());
					}
					remove();
				}
				return true;
			}
		} else {
			return true;
		}
	}

	@Override
	public void onEnterBubbleColumnWithAirAbove(boolean downwards) {
		if (!world.isRemote) {
			rocking = true;
			field_203060_aN = downwards;
			if (getRockingTicks() == 0) {
				setRockingTicks(60);
			}
		}
		world.addParticle(ParticleTypes.SPLASH, posX + (double) rand.nextFloat(), posY + 0.7D, posZ + (double) rand.nextFloat(), 0.0D, 0.0D, 0.0D);
		if (rand.nextInt(20) == 0) {
			world.playSound(posX, posY, posZ, getSplashSound(), getSoundCategory(), 1.0F, 0.8F + 0.4F * rand.nextFloat(), false);
		}
	}

	@Override
	public Item getItemBoat() {
		switch (getBoatModel()) {
			default:
			case WILLOW:
				return SwampExItems.WILLOW_BOAT.get();
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void performHurtAnimation() {
		setForwardDirection(-getForwardDirection());
		setTimeSinceHit(10);
		setDamageTaken(getDamageTaken() * 11.0F);
	}

	@Override
	public void tick() {
		previousStatus = status;
		status = getBoatStatus();
		if (status != Status.UNDER_WATER && status != Status.UNDER_FLOWING_WATER) {
			outOfControlTicks = 0.0F;
		} else {
			++outOfControlTicks;
		}
		if (!world.isRemote && outOfControlTicks >= 60.0F) {
			removePassengers();
		}
		if (getTimeSinceHit() > 0) {
			setTimeSinceHit(getTimeSinceHit() - 1);
		}
		if (getDamageTaken() > 0.0F) {
			setDamageTaken(getDamageTaken() - 1.0F);
		}
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		if (!world.isRemote) {
			setFlag(6, isGlowing());
		}
		baseTick();
		super.tickLerp();
		if (canPassengerSteer()) {
			if (getPassengers().isEmpty() || !(getPassengers().get(0) instanceof PlayerEntity)) {
				setPaddleState(false, false);
			}
			updateMotion();
			if (world.isRemote) {
				controlBoat();
				world.sendPacketToServer(new CSteerBoatPacket(getPaddleState(0), getPaddleState(1)));
			}
			move(MoverType.SELF, getMotion());
		} else {
			setMotion(Vec3d.ZERO);
		}
		updateRocking();
		for (int i = 0; i <= 1; ++i) {
			if (getPaddleState(i)) {
				if (!isSilent() && (double) (paddlePositions[i] % ((float) Math.PI * 2F)) <= (double) ((float) Math.PI / 4F) && ((double) paddlePositions[i] + (double) ((float) Math.PI / 8F)) % (double) ((float) Math.PI * 2F) >= (double) ((float) Math.PI / 4F)) {
					SoundEvent soundevent = getPaddleSound();
					if (soundevent != null) {
						Vec3d vec3d = getLook(1.0F);
						double d0 = i == 1 ? -vec3d.z : vec3d.z;
						double d1 = i == 1 ? vec3d.x : -vec3d.x;
						world.playSound((PlayerEntity) null, posX + d0, posY, posZ + d1, soundevent, getSoundCategory(), 1.0F, 0.8F + 0.4F * rand.nextFloat());
					}
				}
				paddlePositions[i] = (float) ((double) paddlePositions[i] + (double) ((float) Math.PI / 8F));
			} else {
				paddlePositions[i] = 0.0F;
			}
		}
		doBlockCollisions();
		List<Entity> list = world.getEntitiesInAABBexcluding(this, getBoundingBox().grow((double) 0.2F, (double) -0.01F, (double) 0.2F), EntityPredicates.pushableBy(this));
		if (!list.isEmpty()) {
			boolean flag = !world.isRemote && !(getControllingPassenger() instanceof PlayerEntity);
			for (int j = 0; j < list.size(); ++j) {
				Entity entity = list.get(j);
				if (!entity.isPassenger(this)) {
					if (flag && getPassengers().size() < 2 && !entity.isPassenger() && entity.getWidth() < getWidth() && entity instanceof LivingEntity && !(entity instanceof WaterMobEntity) && !(entity instanceof PlayerEntity)) {
						entity.startRiding(this);
					} else {
						applyEntityCollision(entity);
					}
				}
			}
		}
	}

	private void updateRocking() {
		if (world.isRemote) {
			int i = getRockingTicks();
			if (i > 0) {
				rockingIntensity += 0.05F;
			} else {
				rockingIntensity -= 0.1F;
			}
			rockingIntensity = MathHelper.clamp(rockingIntensity, 0.0F, 1.0F);
			prevRockingAngle = rockingAngle;
			rockingAngle = 10.0F * (float) Math.sin((double) (0.5F * (float) world.getGameTime())) * rockingIntensity;
		} else {
			if (!rocking) {
				setRockingTicks(0);
			}
			int k = getRockingTicks();
			if (k > 0) {
				--k;
				setRockingTicks(k);
				int j = 60 - k - 1;
				if (j > 0 && k == 0) {
					setRockingTicks(0);
					Vec3d vec3d = getMotion();
					if (field_203060_aN) {
						setMotion(vec3d.add(0.0D, -0.7D, 0.0D));
						removePassengers();
					} else {
						setMotion(vec3d.x, isPassenger(PlayerEntity.class) ? 2.7D : 0.6D, vec3d.z);
					}
				}
				rocking = false;
			}
		}
	}

	@Override
	@Nullable
	protected SoundEvent getPaddleSound() {
		switch (getBoatStatus()) {
			case IN_WATER:
			case UNDER_WATER:
			case UNDER_FLOWING_WATER:
				return SoundEvents.ENTITY_BOAT_PADDLE_WATER;
			case ON_LAND:
				return SoundEvents.ENTITY_BOAT_PADDLE_LAND;
			case IN_AIR:
			default:
				return null;
		}
	}

	@Override
	public void setPaddleState(boolean left, boolean right) {
		dataManager.set(field_199704_e, left);
		dataManager.set(field_199705_f, right);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public float getRowingTime(int side, float limbSwing) {
		return getPaddleState(side) ? (float) MathHelper.clampedLerp((double) paddlePositions[side] - (double) ((float) Math.PI / 8F), (double) paddlePositions[side], (double) limbSwing) : 0.0F;
	}

	private Status getBoatStatus() {
		Status boatentity$status = getUnderwaterStatus();
		if (boatentity$status != null) {
			waterLevel = getBoundingBox().maxY;
			return boatentity$status;
		} else if (checkInWater()) {
			return Status.IN_WATER;
		} else {
			float f = getBoatGlide();
			if (f > 0.0F) {
				boatGlide = f;
				return Status.ON_LAND;
			} else {
				return Status.IN_AIR;
			}
		}
	}

	@Override
	public float getWaterLevelAbove() {
		AxisAlignedBB axisalignedbb = getBoundingBox();
		int i = MathHelper.floor(axisalignedbb.minX);
		int j = MathHelper.ceil(axisalignedbb.maxX);
		int k = MathHelper.floor(axisalignedbb.maxY);
		int l = MathHelper.ceil(axisalignedbb.maxY - this.lastYd);
		int i1 = MathHelper.floor(axisalignedbb.minZ);
		int j1 = MathHelper.ceil(axisalignedbb.maxZ);
		try (BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain()) {
			label161:
			for (int k1 = k; k1 < l; ++k1) {
				float f = 0.0F;
				for (int l1 = i; l1 < j; ++l1) {
					for (int i2 = i1; i2 < j1; ++i2) {
						blockpos$pooledmutableblockpos.setPos(l1, k1, i2);
						IFluidState ifluidstate = world.getFluidState(blockpos$pooledmutableblockpos);
						if (ifluidstate.isTagged(FluidTags.WATER)) {
							f = Math.max(f, ifluidstate.func_215679_a(world, blockpos$pooledmutableblockpos));
						}

						if (f >= 1.0F) {
							continue label161;
						}
					}
				}
				if (f < 1.0F) {
					float f2 = (float) blockpos$pooledmutableblockpos.getY() + f;
					return f2;
				}
			}
			float f1 = (float) (l + 1);
			return f1;
		}
	}

	@Override
	public float getBoatGlide() {
		AxisAlignedBB axisalignedbb = getBoundingBox();
		AxisAlignedBB axisalignedbb1 = new AxisAlignedBB(axisalignedbb.minX, axisalignedbb.minY - 0.001D, axisalignedbb.minZ, axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		int i = MathHelper.floor(axisalignedbb1.minX) - 1;
		int j = MathHelper.ceil(axisalignedbb1.maxX) + 1;
		int k = MathHelper.floor(axisalignedbb1.minY) - 1;
		int l = MathHelper.ceil(axisalignedbb1.maxY) + 1;
		int i1 = MathHelper.floor(axisalignedbb1.minZ) - 1;
		int j1 = MathHelper.ceil(axisalignedbb1.maxZ) + 1;
		VoxelShape voxelshape = VoxelShapes.create(axisalignedbb1);
		float f = 0.0F;
		int k1 = 0;
		try (BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain()) {
			for (int l1 = i; l1 < j; ++l1) {
				for (int i2 = i1; i2 < j1; ++i2) {
					int j2 = (l1 != i && l1 != j - 1 ? 0 : 1) + (i2 != i1 && i2 != j1 - 1 ? 0 : 1);
					if (j2 != 2) {
						for (int k2 = k; k2 < l; ++k2) {
							if (j2 <= 0 || k2 != k && k2 != l - 1) {
								blockpos$pooledmutableblockpos.setPos(l1, k2, i2);
								BlockState blockstate = world.getBlockState(blockpos$pooledmutableblockpos);
								if (!(blockstate.getBlock() instanceof LilyPadBlock) && VoxelShapes.compare(blockstate.getCollisionShape(world, blockpos$pooledmutableblockpos).withOffset((double) l1, (double) k2, (double) i2), voxelshape, IBooleanFunction.AND)) {
									f += blockstate.getSlipperiness(world, blockpos$pooledmutableblockpos, this);
									++k1;
								}
							}
						}
					}
				}
			}
		}
		return f / (float) k1;
	}

	private boolean checkInWater() {
		AxisAlignedBB axisalignedbb = getBoundingBox();
		int i = MathHelper.floor(axisalignedbb.minX);
		int j = MathHelper.ceil(axisalignedbb.maxX);
		int k = MathHelper.floor(axisalignedbb.minY);
		int l = MathHelper.ceil(axisalignedbb.minY + 0.001D);
		int i1 = MathHelper.floor(axisalignedbb.minZ);
		int j1 = MathHelper.ceil(axisalignedbb.maxZ);
		boolean flag = false;
		waterLevel = Double.MIN_VALUE;
		try (BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain()) {
			for (int k1 = i; k1 < j; ++k1) {
				for (int l1 = k; l1 < l; ++l1) {
					for (int i2 = i1; i2 < j1; ++i2) {
						blockpos$pooledmutableblockpos.setPos(k1, l1, i2);
						IFluidState ifluidstate = world.getFluidState(blockpos$pooledmutableblockpos);
						if (ifluidstate.isTagged(FluidTags.WATER)) {
							float f = (float) l1 + ifluidstate.func_215679_a(world, blockpos$pooledmutableblockpos);
							waterLevel = Math.max((double) f, waterLevel);
							flag |= axisalignedbb.minY < (double) f;
						}
					}
				}
			}
		}
		return flag;
	}

	@Nullable
	private Status getUnderwaterStatus() {
		AxisAlignedBB axisalignedbb = getBoundingBox();
		double d0 = axisalignedbb.maxY + 0.001D;
		int i = MathHelper.floor(axisalignedbb.minX);
		int j = MathHelper.ceil(axisalignedbb.maxX);
		int k = MathHelper.floor(axisalignedbb.maxY);
		int l = MathHelper.ceil(d0);
		int i1 = MathHelper.floor(axisalignedbb.minZ);
		int j1 = MathHelper.ceil(axisalignedbb.maxZ);
		boolean flag = false;

		try (BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain()) {
			for (int k1 = i; k1 < j; ++k1) {
				for (int l1 = k; l1 < l; ++l1) {
					for (int i2 = i1; i2 < j1; ++i2) {
						blockpos$pooledmutableblockpos.setPos(k1, l1, i2);
						IFluidState ifluidstate = world.getFluidState(blockpos$pooledmutableblockpos);
						if (ifluidstate.isTagged(FluidTags.WATER) && d0 < (double) ((float) blockpos$pooledmutableblockpos.getY() + ifluidstate.func_215679_a(world, blockpos$pooledmutableblockpos))) {
							if (!ifluidstate.isSource()) {
								return Status.UNDER_FLOWING_WATER;
							}
							flag = true;
						}
					}
				}
			}
		}
		return flag ? Status.UNDER_WATER : null;
	}

	private void updateMotion() {
		double gravity = (double) -0.04F;
		double d1 = hasNoGravity() ? 0.0D : gravity;
		double d2 = 0.0D;
		momentum = 0.05F;
		if (previousStatus == Status.IN_AIR && status != Status.IN_AIR && status != Status.ON_LAND) {
			waterLevel = getBoundingBox().minY + (double) getHeight();
			setPosition(posX, (double) (getWaterLevelAbove() - getHeight()) + 0.101D, posZ);
			setMotion(getMotion().mul(1.0D, 0.0D, 1.0D));
			lastYd = 0.0D;
			status = Status.IN_WATER;
		} else {
			if (status == Status.IN_WATER) {
				d2 = (waterLevel - getBoundingBox().minY) / (double) getHeight();
				momentum = 0.9F;
			} else if (status == Status.UNDER_FLOWING_WATER) {
				d1 = -7.0E-4D;
				momentum = 0.9F;
			} else if (status == Status.UNDER_WATER) {
				d2 = (double) 0.01F;
				momentum = 0.45F;
			} else if (status == Status.IN_AIR) {
				momentum = 0.9F;
			} else if (status == Status.ON_LAND) {
				momentum = boatGlide;
				if (getControllingPassenger() instanceof PlayerEntity) {
					boatGlide /= 2.0F;
				}
			}

			Vec3d vec3d = getMotion();
			setMotion(vec3d.x * (double) momentum, vec3d.y + d1, vec3d.z * (double) momentum);
			deltaRotation *= momentum;
			if (d2 > 0.0D) {
				Vec3d vec3d1 = this.getMotion();
				setMotion(vec3d1.x, (vec3d1.y + d2 * 0.06153846016296973D) * 0.75D, vec3d1.z);
			}
		}
	}

	private void controlBoat() {
		if (isBeingRidden()) {
			float f = 0.0F;
			if (leftInputDown) {
				--deltaRotation;
			}
			if (rightInputDown) {
				++deltaRotation;
			}
			if (rightInputDown != leftInputDown && !forwardInputDown && !backInputDown) {
				f += 0.005F;
			}
			rotationYaw += deltaRotation;
			if (forwardInputDown) {
				f += 0.04F;
			}
			if (backInputDown) {
				f -= 0.005F;
			}
			setMotion(getMotion().add((double) (MathHelper.sin(-rotationYaw * ((float) Math.PI / 180F)) * f), 0.0D, (double) (MathHelper.cos(rotationYaw * ((float) Math.PI / 180F)) * f)));
			setPaddleState(rightInputDown && !leftInputDown || forwardInputDown, leftInputDown && !rightInputDown || forwardInputDown);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void updatePassenger(Entity passenger) {
		if (isPassenger(passenger)) {
			float f = 0.0F;
			float f1 = (float) ((removed ? (double) 0.01F : getMountedYOffset()) + passenger.getYOffset());
			if (getPassengers().size() > 1) {
				int i = getPassengers().indexOf(passenger);
				if (i == 0) {
					f = 0.2F;
				} else {
					f = -0.6F;
				}
				if (passenger instanceof AnimalEntity) {
					f = (float) ((double) f + 0.2D);
				}
			}
			Vec3d vec3d = (new Vec3d((double) f, 0.0D, 0.0D)).rotateYaw(-rotationYaw * ((float) Math.PI / 180F) - ((float) Math.PI / 2F));
			passenger.setPosition(posX + vec3d.x, posY + (double) f1, posZ + vec3d.z);
			passenger.rotationYaw += deltaRotation;
			passenger.setRotationYawHead(passenger.getRotationYawHead() + deltaRotation);
			applyYawToEntity(passenger);
			if (passenger instanceof AnimalEntity && getPassengers().size() > 1) {
				int j = passenger.getEntityId() % 2 == 0 ? 90 : 270;
				passenger.setRenderYawOffset(((AnimalEntity) passenger).renderYawOffset + (float) j);
				passenger.setRotationYawHead(passenger.getRotationYawHead() + (float) j);
			}
		}
	}

	@Override
	protected void applyYawToEntity(Entity entityToUpdate) {
		entityToUpdate.setRenderYawOffset(rotationYaw);
		float f = MathHelper.wrapDegrees(entityToUpdate.rotationYaw - rotationYaw);
		float f1 = MathHelper.clamp(f, -105.0F, 105.0F);
		entityToUpdate.prevRotationYaw += f1 - f;
		entityToUpdate.rotationYaw += f1 - f;
		entityToUpdate.setRotationYawHead(entityToUpdate.rotationYaw);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void applyOrientationToEntity(Entity entityToUpdate) {
		applyYawToEntity(entityToUpdate);
	}

	@Override
	protected void writeAdditional(CompoundNBT compound) {
		compound.putString("Type", getBoatModel().getName());
	}

	@Override
	protected void readAdditional(CompoundNBT compound) {
		if (compound.contains("Type", 8)) {
			setBoatModel(Type.getTypeFromString(compound.getString("Type")));
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
		lastYd = getMotion().y;
		if (!isPassenger()) {
			if (onGroundIn) {
				if (fallDistance > 3.0F) {
					if (status != Status.ON_LAND) {
						fallDistance = 0.0F;
						return;
					}
					fall(fallDistance, 1.0F);
					if (!world.isRemote && !removed) {
						remove();
						if (world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
							for (int i = 0; i < 3; ++i) {
								entityDropItem(getBoatModel().asPlank());
							}
							for (int j = 0; j < 2; ++j) {
								entityDropItem(Items.STICK);
							}
						}
					}
				}
				fallDistance = 0.0F;
			} else if (!world.getFluidState((new BlockPos(this)).down()).isTagged(FluidTags.WATER) && y < 0.0D) {
				fallDistance = (float) ((double) fallDistance - y);
			}
		}
	}

	@Override
	public boolean getPaddleState(int side) {
		return dataManager.<Boolean>get(side == 0 ? field_199704_e : field_199705_f) && getControllingPassenger() != null;
	}

	@Override
	public void setDamageTaken(float damageTaken) {
		dataManager.set(DAMAGE_TAKEN, damageTaken);
	}

	@Override
	public float getDamageTaken() {
		return dataManager.get(DAMAGE_TAKEN);
	}

	@Override
	public void setTimeSinceHit(int timeSinceHit) {
		dataManager.set(TIME_SINCE_HIT, timeSinceHit);
	}

	@Override
	public int getTimeSinceHit() {
		return dataManager.get(TIME_SINCE_HIT);
	}

	private void setRockingTicks(int p_203055_1_) {
		dataManager.set(ROCKING_TICKS, p_203055_1_);
	}

	private int getRockingTicks() {
		return dataManager.get(ROCKING_TICKS);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public float getRockingAngle(float partialTicks) {
		return MathHelper.lerp(partialTicks, prevRockingAngle, rockingAngle);
	}

	@Override
	public void setForwardDirection(int forwardDirection) {
		dataManager.set(FORWARD_DIRECTION, forwardDirection);
	}

	@Override
	public int getForwardDirection() {
		return dataManager.get(FORWARD_DIRECTION);
	}

	public void setBoatModel(Type boatType) {
		dataManager.set(BOAT_TYPE, boatType.ordinal());
	}

	public Type getBoatModel() {
		return Type.byId(dataManager.get(BOAT_TYPE));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void updateInputs(boolean p_184442_1_, boolean p_184442_2_, boolean p_184442_3_, boolean p_184442_4_) {
		leftInputDown = p_184442_1_;
		rightInputDown = p_184442_2_;
		forwardInputDown = p_184442_3_;
		backInputDown = p_184442_4_;
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	public enum Type {
		WILLOW(SwampExBlocks.WILLOW_PLANKS.get(), "willow");

		private final String name;
		private final Block block;

		private Type(Block p_i48146_3_, String p_i48146_4_) {
			name = p_i48146_4_;
			block = p_i48146_3_;
		}

		public String getName() {
			return name;
		}

		public Block asPlank() {
			return block;
		}

		public String toString() {
			return name;
		}

		public static Type byId(int id) {
			Type[] aboatentity$type = values();
			if (id < 0 || id >= aboatentity$type.length) {
				id = 0;
			}
			return aboatentity$type[id];
		}

		public static Type getTypeFromString(String nameIn) {
			Type[] aboatentity$type = values();
			for (int i = 0; i < aboatentity$type.length; ++i) {
				if (aboatentity$type[i].getName().equals(nameIn)) {
					return aboatentity$type[i];
				}
			}
			return aboatentity$type[0];
		}
	}
}