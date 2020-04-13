package com.farcr.swampexpansion.common.entity;

import javax.annotation.Nullable;

import com.farcr.swampexpansion.common.entity.goals.SlabfishBreedGoal;
import com.farcr.swampexpansion.core.registries.SwampExEntities;
import com.farcr.swampexpansion.core.registries.SwampExItems;

import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraftforge.registries.ForgeRegistries;

public class SlabfishEntity extends AnimalEntity {
	private static final DataParameter<Integer> SLABFISH_TYPE = EntityDataManager.createKey(SlabfishEntity.class, DataSerializers.VARINT);
	public static final EntitySize SIZE = EntitySize.fixed(0.6F, 0.2F);
	public static final EntitySize SIZE_CHILD = EntitySize.fixed(0.3159F, 0.1125F);
	
	private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Items.TROPICAL_FISH);
	public float wingRotation;
	public float destPos;
	public float oFlapSpeed;
	public float oFlap;
	public float wingRotDelta = 1.0F;

	public SlabfishEntity(EntityType<? extends SlabfishEntity> type, World worldIn) {
		super(type, worldIn);
		this.setPathPriority(PathNodeType.WATER, 0.0F);
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.4D));
		this.goalSelector.addGoal(2, new SlabfishBreedGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, false, TEMPTATION_ITEMS));
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
	}

	protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
		return this.isChild() ? sizeIn.height * 0.85F : sizeIn.height * 0.92F;
	}
	
	public boolean canBreatheUnderwater() {
		return true;
	}

	protected void registerAttributes() {
		super.registerAttributes();
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0D);
		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
	}
	
	public int getMaxSpawnedInChunk() {
		return 5;
	}
	
	protected float getWaterSlowDown() {
		return 0.95F;
	}
	
	@Override
	protected void registerData() {
		super.registerData();
		this.dataManager.register(SLABFISH_TYPE, 1);
	}
	
	public ItemStack getPickedResult(RayTraceResult target) {
		return new ItemStack(SwampExItems.SLABFISH_SPAWN_EGG.get());
	}
	
	public static String getNameById(int id) {
		switch(id) {
			case 1:
				return "swamp";
			case 2:
				return "ocean";
			case 3:
				return "marsh";
			case 4:
				return "mire";
		}
		return "";
	}
	
	@Override
	public EntitySize getSize(Pose pose) {
		return this.isInWater() ? this.isChild() ? SIZE_CHILD : SIZE : super.getSize(pose);
	}
	
	public void livingTick() {
		super.livingTick();
		this.recalculateSize();
		this.oFlap = this.wingRotation;
		this.oFlapSpeed = this.destPos;
		this.destPos = (float)((double)this.destPos + (double)(this.onGround ? -1 : 4) * 0.3D);
		this.destPos = MathHelper.clamp(this.destPos, 0.0F, 1.0F);
		if (!this.onGround && this.wingRotDelta < 1.0F) {
			this.wingRotDelta = 1.0F;
		}

		this.wingRotDelta = (float)((double)this.wingRotDelta * 0.9D);

		this.wingRotation += this.wingRotDelta * 2.0F;
	}
	
	public static void addSpawn() {
		ForgeRegistries.BIOMES.getValues().stream().forEach(SlabfishEntity::processSpawning);
	}
	
	private static void processSpawning(Biome biome) {
		if(biome.getCategory() == Category.SWAMP) {
    		biome.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(SwampExEntities.SLABFISH.get(), 5, 2, 4));
        } else if (biome.getCategory() == Category.OCEAN) {
    		biome.getSpawns(EntityClassification.WATER_CREATURE).add(new Biome.SpawnListEntry(SwampExEntities.SLABFISH.get(), 3, 2, 4));
    	}
	}

	public boolean onLivingFall(float distance, float damageMultiplier) {
		return false;
	}

	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_COD_AMBIENT;
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.ENTITY_COD_HURT;
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_COD_DEATH;
	}

	protected void playStepSound(BlockPos pos, BlockState blockIn) {
	   this.playSound(SoundEvents.ENTITY_COD_FLOP, 0.15F, 1.0F);
	}

	public SlabfishEntity createChild(AgeableEntity ageable) {
		return SwampExEntities.SLABFISH.get().create(this.world);
	}

	public boolean isBreedingItem(ItemStack stack) {
		return TEMPTATION_ITEMS.test(stack);
	}
	
	public boolean isPushedByWater() {
		return false;
	}
	
	public int getTypeForBiome(IWorld world) {
		Biome biome = world.getBiome(new BlockPos(this));
		if (biome.getCategory() == Biome.Category.OCEAN) {
			return 2;
		} else if (biome.getCategory() == Biome.Category.FOREST) {
			return 3;
		} else if (biome.getCategory() == Biome.Category.TAIGA) {
			return 4;
		}
		return 1;
	}
	
	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putInt("SlabfishType", this.getSlabfishType());
	}
	
	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		this.setSlabfishType(MathHelper.clamp(compound.getInt("SlabfishType"), 1, 2));
	}
	
	public int getSlabfishType() {
		return this.dataManager.get(SLABFISH_TYPE);
	}
	
	public void setSlabfishType(int typeId) {
		this.dataManager.set(SLABFISH_TYPE, typeId);
	}
	
	@Nullable
	@Override
	public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, ILivingEntityData spawnDataIn, CompoundNBT dataTag) {
		spawnDataIn = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		int type = this.getTypeForBiome(worldIn);
		if (spawnDataIn instanceof SlabfishEntity.SlabfishData) {
			type = ((SlabfishEntity.SlabfishData)spawnDataIn).typeData;
		}
		this.setSlabfishType(type);
		
		return spawnDataIn;
	}
	
	public static class SlabfishData implements ILivingEntityData {
		public final int typeData;

		public SlabfishData(int type) {
			this.typeData = type;
		}
	}
}
