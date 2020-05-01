package com.farcr.swampexpansion.common.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

import com.farcr.swampexpansion.common.entity.goals.SlabbyBreedGoal;
import com.farcr.swampexpansion.common.entity.goals.SlabbyFollowParentGoal;
import com.farcr.swampexpansion.common.entity.goals.SlabbyGrabItemGoal;
import com.farcr.swampexpansion.common.entity.goals.SlabbySitGoal;
import com.farcr.swampexpansion.common.item.MudBallItem;
import com.farcr.swampexpansion.core.registries.SwampExBiomes;
import com.farcr.swampexpansion.core.registries.SwampExBlocks;
import com.farcr.swampexpansion.core.registries.SwampExCriteriaTriggers;
import com.farcr.swampexpansion.core.registries.SwampExEntities;
import com.farcr.swampexpansion.core.registries.SwampExItems;
import com.farcr.swampexpansion.core.registries.SwampExTags;
import com.google.common.collect.Maps;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.EvokerEntity;
import net.minecraft.entity.monster.IllusionerEntity;
import net.minecraft.entity.monster.PillagerEntity;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.entity.monster.VexEntity;
import net.minecraft.entity.monster.VindicatorEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.EggItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.NameTagItem;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;

public class SlabfishEntity extends AnimalEntity implements IInventoryChangedListener {
	private static final DataParameter<Integer> SLABFISH_TYPE = EntityDataManager.createKey(SlabfishEntity.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> SLABFISH_OVERLAY = EntityDataManager.createKey(SlabfishEntity.class, DataSerializers.VARINT);
	private static final DataParameter<Boolean> IS_SITTING = EntityDataManager.createKey(SlabfishEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> SLABFISH_REAL_TYPE = EntityDataManager.createKey(SlabfishEntity.class, DataSerializers.VARINT);

	private static final DataParameter<Boolean> HAS_BACKPACK = EntityDataManager.createKey(SlabfishEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> BACKPACK_COLOR = EntityDataManager.createKey(SlabfishEntity.class, DataSerializers.VARINT);
	
	private static final DataParameter<Boolean> HAS_SWEATER = EntityDataManager.createKey(SlabfishEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> SWEATER_COLOR = EntityDataManager.createKey(SlabfishEntity.class, DataSerializers.VARINT);
	
	protected SlabbySitGoal sitGoal;
	public static final EntitySize SIZE = EntitySize.fixed(0.6F, 0.2F);
	public static final EntitySize SIZE_CHILD = EntitySize.fixed(0.3159F, 0.1125F);
	
	private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Items.TROPICAL_FISH, SwampExItems.TROPICAL_FISH_KELP_ROLL.get());
	private static final Ingredient HEALING_ITEMS = Ingredient.fromTag(ItemTags.FISHES);
	private static final Ingredient SPEEDING_ITEMS = Ingredient.fromTag(SwampExTags.SUSHI);
	
	public Inventory slabfishBackpack;
	private UUID lightningUUID;
	public float wingRotation;
	public float destPos;
	public float oFlapSpeed;
	public float oFlap;
	public float wingRotDelta = 1.0F;
	
	public boolean incrementingTimer = false;
	public int timerTicks = 0;
	
	private static final Map<List<String>, SlabfishType> NAMES = Util.make(Maps.newHashMap(), (skins) -> {
		skins.put(Arrays.asList("cameron", "cam", "cringe"), SlabfishType.CAMERON);
		skins.put(Arrays.asList("bagel", "shyguy", "shy guy", "bagielo"), SlabfishType.BAGEL);
		skins.put(Arrays.asList("gore", "gore.", "musicano"), SlabfishType.GORE);
		skins.put(Arrays.asList("snake", "snake block", "snakeblock"), SlabfishType.SNAKE);
	});
	
	public SlabfishEntity(EntityType<? extends SlabfishEntity> type, World worldIn) {
		super(type, worldIn);
		this.setPathPriority(PathNodeType.WATER, 0.0F);
		this.initSlabfishBackpack();
	}
	
	protected void registerAttributes() {
		super.registerAttributes();
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0D);
		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
		this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
	}

	protected void registerGoals() {
		this.sitGoal = new SlabbySitGoal(this);
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(1, this.sitGoal);
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, EvokerEntity.class, 12.0F, 1.0D, 1.5D));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, VindicatorEntity.class, 8.0F, 1.0D, 1.5D));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, RavagerEntity.class, 8.0F, 1.0D, 1.5D));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, VexEntity.class, 8.0F, 1.0D, 1.5D));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, PillagerEntity.class, 15.0F, 1.0D, 1.5D));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, IllusionerEntity.class, 12.0F, 1.0D, 1.5D));
		this.goalSelector.addGoal(4, new SlabbyBreedGoal(this, 1.0D));
		this.goalSelector.addGoal(5, new SlabbyGrabItemGoal(this, 1.1D));
		this.goalSelector.addGoal(6, new TemptGoal(this, 1.0D, false, TEMPTATION_ITEMS));
		this.goalSelector.addGoal(7, new MeleeAttackGoal(this, 1.0D, false));
		this.goalSelector.addGoal(8, new SlabbyFollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(9, new RandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(10, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(11, new LookRandomlyGoal(this));
		
		this.targetSelector.addGoal(1, new SlabfishEntity.SlabfishFuedGoal(this));
	}
	
	@Override
	protected void registerData() {
		super.registerData();
		this.getDataManager().register(SLABFISH_TYPE, 0);
		this.getDataManager().register(SLABFISH_OVERLAY, 0);
		this.getDataManager().register(IS_SITTING, false);
		this.getDataManager().register(SLABFISH_REAL_TYPE, 0);

		this.getDataManager().register(HAS_BACKPACK, false);
		this.getDataManager().register(BACKPACK_COLOR, DyeColor.BROWN.getId());
		
		this.getDataManager().register(HAS_SWEATER, false);
		this.getDataManager().register(SWEATER_COLOR, DyeColor.WHITE.getId());
	}
	
	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putInt("SlabfishType", this.getSlabfishVisibleType().getId());
		compound.putInt("SlabfishOverlay", this.getSlabfishOverlay().getId());
		compound.putBoolean("Sitting", this.isSitting());
		compound.putInt("SlabfishRealType", this.getSlabfishRealType().getId());

		compound.putBoolean("HasBackpack", this.hasBackpack());
		compound.putByte("BackpackColor", (byte) this.getBackpackColor().getId());
		
		compound.putBoolean("HasSweater", this.hasSweater());
		compound.putByte("SweaterColor", (byte) this.getSweaterColor().getId());

		if (this.hasBackpack()) {
	         ListNBT listnbt = new ListNBT();

	         for(int i = 0; i < this.slabfishBackpack.getSizeInventory(); ++i) {
	        	 ItemStack itemstack = this.slabfishBackpack.getStackInSlot(i);
	        	 if (!itemstack.isEmpty()) {
	        		 CompoundNBT compoundnbt = new CompoundNBT();
	        		 compoundnbt.putByte("Slot", (byte)i);
	        		 itemstack.write(compoundnbt);
	        		 listnbt.add(compoundnbt);
	        	 }
	         }
	         compound.put("Items", listnbt);
		}
	}
	
	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		this.setSlabfishType(SlabfishType.byId(compound.getInt("SlabfishType")));
		this.setSlabfishOverlay(SlabfishOverlay.byId(compound.getInt("SlabfishOverlay")));
		if (this.sitGoal != null) this.sitGoal.setSitting(compound.getBoolean("Sitting"));
		this.setSlabfishRealType(SlabfishType.byId(compound.getInt("SlabfishRealType")));
		this.setSitting(compound.getBoolean("Sitting"));

		this.setBackpacked(compound.getBoolean("HasBackpack"));
		if(compound.contains("BackpackColor", 99)) this.setBackpackColor(DyeColor.byId(compound.getInt("BackpackColor")));
		
		this.setSweatered(compound.getBoolean("HasSweater"));
		if(compound.contains("SweaterColor", 99)) this.setSweaterColor(DyeColor.byId(compound.getInt("SweaterColor")));
		
		if (this.hasBackpack()) {
			ListNBT listnbt = compound.getList("Items", 10);
			this.initSlabfishBackpack();

			for(int i = 0; i < listnbt.size(); ++i) {
				CompoundNBT compoundnbt = listnbt.getCompound(i);
				int j = compoundnbt.getByte("Slot") & 255;
	            if (j < this.slabfishBackpack.getSizeInventory()) {
	            	this.slabfishBackpack.setInventorySlotContents(j, ItemStack.read(compoundnbt));
	            }
			}
		}
	}
	
	// GENERAL //
	
	@Override
	public boolean processInteract(PlayerEntity player, Hand hand) {
		ItemStack itemstack = player.getHeldItem(hand);
		Item item = itemstack.getItem();
		this.initSlabfishBackpack();
		if (item instanceof SpawnEggItem || item instanceof NameTagItem || item == Items.TROPICAL_FISH || item == SwampExItems.TROPICAL_FISH_KELP_ROLL.get() || item instanceof EggItem || item instanceof MudBallItem) {
	         return super.processInteract(player, hand);
		} else if(item instanceof DyeItem && this.hasBackpack() == true) {
			DyeColor dyecolor = ((DyeItem) item).getDyeColor();
			if(dyecolor != this.getBackpackColor()) {
				this.setBackpackColor(dyecolor);
				if(!player.abilities.isCreativeMode) {
					itemstack.shrink(1);
				}
				return true;
			}
		} else if (SWEATER_MAP.containsKey(item) && !(this.hasSweater() && this.getSweaterColor() == SWEATER_MAP.get(item)) && !player.func_226563_dT_()) {
			IItemProvider previousSweater = Items.AIR;
			this.playBackpackSound();
			if(!player.abilities.isCreativeMode) {
				itemstack.shrink(1);
			}
			if (this.hasSweater()) {
				previousSweater = REVERSE_MAP.get(this.getSweaterColor());
			}
			this.setSweaterColor(SWEATER_MAP.get(item));
			if (!this.hasSweater()) {
				this.setSweatered(true);
			} else {
				ItemEntity itementity = this.entityDropItem(previousSweater, 0);
				if (itementity != null) {
					itementity.setMotion(itementity.getMotion().add((double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F), (double)(this.rand.nextFloat() * 0.05F), (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F)));
				}
				this.playBackpackSound();
			}
			return true;
		} else if (item == Items.CHEST && this.hasBackpack() == false) {
			this.setBackpacked(true);
			this.playBackpackSound();
			if(!player.abilities.isCreativeMode) {
				itemstack.shrink(1);
			}
			if (player instanceof ServerPlayerEntity) {
				ServerPlayerEntity serverplayerentity = (ServerPlayerEntity) player;
				if(!world.isRemote()) {
					SwampExCriteriaTriggers.BACKPACK_SLABFISH.trigger(serverplayerentity); 
				}
			}
			return true;
		} else if (item == Items.SHEARS && this.hasSweater() == true && !player.func_226563_dT_()) {
			this.setSweatered(false);
			this.playBackpackSound();
			ItemEntity itementity = this.entityDropItem(REVERSE_MAP.get(this.getSweaterColor()), 0);
	        if (itementity != null) {
	           itementity.setMotion(itementity.getMotion().add((double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F), (double)(this.rand.nextFloat() * 0.05F), (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F)));
	        }
			if (!this.world.isRemote) {
				itemstack.damageItem(1, player, (tool) -> {
					tool.sendBreakAnimation(hand);
	            });
			}
			return true;
		} else if (item == Items.SHEARS && this.hasBackpack() == true && player.func_226563_dT_()) {
			this.setBackpackColor(DyeColor.BROWN);
            this.dropInventory();
			this.setBackpacked(false);
			this.playBackpackSound();
            this.slabfishBackpack.clear();
			if (!this.world.isRemote) {
				itemstack.damageItem(1, player, (tool) -> {
					tool.sendBreakAnimation(hand);
	            });
			}
			return true;
		} else if (item.isIn(ItemTags.MUSIC_DISCS)) {
			if (!player.abilities.isCreativeMode) {
				itemstack.shrink(1);
			}
			world.playSound(this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.NEUTRAL, 1F, 1F, true);
			for(int i = 0; i < 7; ++i) {
	            double d0 = this.rand.nextGaussian() * 0.02D;
	            double d1 = this.rand.nextGaussian() * 0.02D;
	            double d2 = this.rand.nextGaussian() * 0.02D;
	            this.world.addParticle(ParticleTypes.NOTE, this.getPosXRandom(1.0D), this.getPosYRandom() + 0.5D, this.getPosZRandom(1.0D), d0, d1, d2);
	         }
			ItemEntity itementity = this.entityDropItem(SwampExItems.MUSIC_DISC_SLABRAVE.get(), 1);
	        if (itementity != null) {
	           itementity.setMotion(itementity.getMotion().add((double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F), (double)(this.rand.nextFloat() * 0.05F), (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F)));
	        }
			return true;
		} else if(HEALING_ITEMS.test(itemstack)) {
			if (this.getHealth() < this.getMaxHealth()) {
				if (!player.abilities.isCreativeMode) {
					itemstack.shrink(1);
				}
				world.playSound(this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.NEUTRAL, 1F, 1F, true);
				this.world.setEntityState(this, (byte)18);		
				this.heal((float)item.getFood().getHealing());
				return true;
			}
		} else if(SPEEDING_ITEMS.test(itemstack)) {
			if (!player.abilities.isCreativeMode) {
				itemstack.shrink(1);
			}
			world.playSound(this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.NEUTRAL, 1F, 1F, true);
			this.addPotionEffect(new EffectInstance(Effects.SPEED, 3600, 2, true, true));
			for(int i = 0; i < 7; ++i) {
	            double d0 = this.rand.nextGaussian() * 0.02D;
	            double d1 = this.rand.nextGaussian() * 0.02D;
	            double d2 = this.rand.nextGaussian() * 0.02D;
	            this.world.addParticle(ParticleTypes.CLOUD, this.getPosXRandom(1.0D), this.getPosYRandom() + 0.5D, this.getPosZRandom(1.0D), d0, d1, d2);
	         }
			return true;
		} else if (!this.isSitting() && this.hasBackpack() && player.func_226563_dT_() && !this.isInWater()) {
			this.setSitting(true);
			return true;
		} else if (this.isSitting() && player.func_226563_dT_()) {
			this.setSitting(false);
			return true;
		} else if (this.hasBackpack() == true) {
			this.openGUI(player);
			player.stopActiveHand();
			return true;
		}
		return super.processInteract(player, hand);
	}
	
	protected void onInsideBlock(BlockState state) {
		if (state.getBlock() == SwampExBlocks.MUD.get()) {
			if(this.getSlabfishOverlay() != SlabfishOverlay.MUDDY) this.setSlabfishOverlay(SlabfishOverlay.MUDDY);
		}
		if (state.getBlock() == Blocks.WATER) {
			if(this.getSlabfishOverlay() == SlabfishOverlay.MUDDY) this.setSlabfishOverlay(SlabfishOverlay.NONE);
		}
	}
	
	public void livingTick() {
		super.livingTick();

		if (this.isMoving()) {
			if (this.isPotionActive(Effects.SPEED) && rand.nextInt(3) == 0 && !this.isInWater()) {
				double d0 = this.rand.nextGaussian() * 0.02D;
	            double d1 = this.rand.nextGaussian() * 0.02D;
	            double d2 = this.rand.nextGaussian() * 0.02D;
	            this.world.addParticle(ParticleTypes.CLOUD, this.getPosXRandom(0.5D), this.getPosY() + 0.2D, this.getPosZRandom(0.5D), d0, d1, d2);
			}
		}
		
		if (this.rand.nextFloat() < 0.1F && this.getSlabfishOverlay() == SlabfishOverlay.MUDDY) {
			for(int i = 0; i < this.rand.nextInt(2) + 1; ++i) {
				this.doParticle(this.world, this.getPosX() - (double)0.3F, this.getPosX() + (double)0.3F, this.getPosZ() - (double)0.3F, this.getPosZ() + (double)0.3F, this.getPosYHeight(0.5D), new ItemParticleData(ParticleTypes.ITEM, new ItemStack(SwampExItems.MUD_BALL.get())));
			}
		}
		
		
		List<PlayerEntity> playerList = world.getEntitiesWithinAABB(PlayerEntity.class, this.getBoundingBox().grow(5.0D, 5.0D, 5.0D));

		for(PlayerEntity player : playerList) {
			if (player instanceof ServerPlayerEntity) {
				ServerPlayerEntity serverplayerentity = (ServerPlayerEntity) player;
				if(!world.isRemote()) {
					if (this.getSlabfishVisibleType() == SlabfishType.SWAMP) SwampExCriteriaTriggers.SWAMP_SLABFISH.trigger(serverplayerentity); 
					if (this.getSlabfishVisibleType() == SlabfishType.MARSH) SwampExCriteriaTriggers.MARSH_SLABFISH.trigger(serverplayerentity); 
				}
			}
		}
		this.recalculateSize();
		this.setCanPickUpLoot(this.hasBackpack());
		
		this.oFlap = this.wingRotation;
		this.oFlapSpeed = this.destPos;
		this.destPos = (float)((double)this.destPos + (double)(this.onGround ? -1 : 4) * 0.3D);
		this.destPos = MathHelper.clamp(this.destPos, 0.0F, 1.0F);
		if (!this.onGround && this.wingRotDelta < 1.0F) {
			this.wingRotDelta = 1.0F;
		}

		this.wingRotDelta = (float)((double)this.wingRotDelta * 0.9D);
		Vec3d vec3d = this.getMotion();
		if (!this.onGround && vec3d.y < 0.0D) {
			this.setMotion(vec3d.mul(1.0D, 0.6D, 1.0D));
		}

		this.wingRotation += this.wingRotDelta * 2.0F;
		
	}
	
	private boolean isMoving() {
		return this.getMotion().getX() > 0 || this.getMotion().getY() > 0 || this.getMotion().getZ() > 0;
	}
	
	// DETAILS //
	
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
	
	protected void playBackpackSound() {
		this.playSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
	}
	
	private void doParticle(World world, double p_226397_2_, double p_226397_4_, double p_226397_6_, double p_226397_8_, double p_226397_10_, IParticleData p_226397_12_) {
		world.addParticle(p_226397_12_, MathHelper.lerp(world.rand.nextDouble(), p_226397_2_, p_226397_4_), p_226397_10_, MathHelper.lerp(world.rand.nextDouble(), p_226397_6_, p_226397_8_), 0.0D, 0.0D, 0.0D);
	}
	
	// STATS //
	
	public SlabfishEntity createChild(AgeableEntity ageable) {
		SlabfishEntity baby = SwampExEntities.SLABFISH.get().create(this.world);
		baby.setSlabfishType(this.getSlabfishVisibleType());
		return baby;
	}
	
	public ItemStack getPickedResult(RayTraceResult target) {
		return new ItemStack(SwampExItems.SLABFISH_SPAWN_EGG.get());
	}
	
	public boolean isBreedingItem(ItemStack stack) {
		return TEMPTATION_ITEMS.test(stack);
	}
	
	@Override
	public EntitySize getSize(Pose pose) {
		return this.isInWater() ? this.isChild() ? SIZE_CHILD : SIZE : super.getSize(pose);
	}

	protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
		return this.isSitting() ? sizeIn.height * 0.6F : sizeIn.height * 0.8F;
	}
	
	public boolean canBreatheUnderwater() {
		return true;
	}
	
	public boolean isPushedByWater() {
		return false;
	}
	
	public boolean onLivingFall(float distance, float damageMultiplier) {
		return false;
	}
	
	
	public int getMaxSpawnedInChunk() {
		return 5;
	}
	
	protected float getWaterSlowDown() {
		return 0.95F;
	}
	
	// SLABFISH TYPE //
	
	public SlabfishType getTypeForConditions(IWorld world) {
		BlockPos pos = new BlockPos(this);
		Biome biome = world.getBiome(pos);
		
		List<Biome> MARSH = new ArrayList<Biome>();
		MARSH.add(SwampExBiomes.MARSH.get());
		MARSH.add(SwampExBiomes.MUSHROOM_MARSH.get());
		
		List<Biome> ROSEWOOD = new ArrayList<Biome>();
		ROSEWOOD.add(findBiome("atmospheric", "rosewood_forest"));
		ROSEWOOD.add(findBiome("atmospheric", "rosewood_mountains"));
		ROSEWOOD.add(findBiome("atmospheric", "rosewood_plateau"));
		ROSEWOOD.add(findBiome("atmospheric", "rosewood_forest_plateau"));
		
		List<Biome> DUNES = new ArrayList<Biome>();
		DUNES.add(findBiome("atmospheric", "dunes"));
		DUNES.add(findBiome("atmospheric", "rocky_dunes"));
		DUNES.add(findBiome("atmospheric", "petrified_dunes"));
		DUNES.add(findBiome("atmospheric", "flourishing_dunes"));
		
		List<Biome> MAPLE = new ArrayList<Biome>();
		MAPLE.add(findBiome("autumnity", "maple_forest"));
		MAPLE.add(findBiome("autumnity", "maple_forest_hills"));
		MAPLE.add(findBiome("autumnity", "pumpkin_fields"));
		
		List<Biome> POISE = new ArrayList<Biome>();
		POISE.add(findBiome("endergetic", "poise_forest"));
		
		if (((ServerWorld)this.world).findRaid(pos) != null) return SlabfishType.TOTEM;
		if (pos.getY() <= 20) return SlabfishType.CAVE;
		
		if (MARSH.contains(biome)) return SlabfishType.MARSH;
		if (MAPLE.contains(biome)) return SlabfishType.MAPLE;
		if (ROSEWOOD.contains(biome)) return SlabfishType.ROSEWOOD;
		if (DUNES.contains(biome)) return SlabfishType.DUNES;
		if (POISE.contains(biome)) return SlabfishType.POISE;
		
		if (biome == Biomes.ICE_SPIKES) return SlabfishType.ICY;
		
		if (biome.getCategory() == Biome.Category.OCEAN) return SlabfishType.OCEAN;
		if (biome.getCategory() == Biome.Category.RIVER) return SlabfishType.RIVER;
		if (biome.getCategory() == Biome.Category.JUNGLE) return SlabfishType.JUNGLE;
		if (biome.getCategory() == Biome.Category.SAVANNA) return SlabfishType.SAVANNA;
		if (biome.getCategory() == Biome.Category.MESA) return SlabfishType.MESA;
		if (biome.getCategory() == Biome.Category.ICY) return SlabfishType.SNOWY;
		if (biome.getCategory() == Biome.Category.DESERT) return SlabfishType.DESERT;
		if (biome.getCategory() == Biome.Category.TAIGA) return SlabfishType.TAIGA;
		if (biome.getCategory() == Biome.Category.FOREST) return SlabfishType.FOREST;
		if (biome.getCategory() == Biome.Category.PLAINS) return SlabfishType.PLAINS;
		
		if (world.getDimension().getType() == DimensionType.THE_NETHER) return SlabfishType.NETHER;
		if (world.getDimension().getType() == DimensionType.THE_END) return SlabfishType.END;

		return SlabfishType.SWAMP;
	}
	
	public Biome findBiome(String modid, String name) {
		return ForgeRegistries.BIOMES.getValue(new ResourceLocation(modid, name));
	}
	
	public SlabfishType getTypeForBreeding(IWorld world, SlabfishEntity parent1, SlabfishEntity parent2) {
		BlockPos pos = new BlockPos(this);
		Biome biome = world.getBiome(pos);
		
		if (world.getBiome(pos).getCategory() == Biome.Category.NETHER && (parent1.getSlabfishVisibleType() == SlabfishType.SKELETON || parent1.getSlabfishVisibleType() == SlabfishType.WITHER) && (parent2.getSlabfishVisibleType() == SlabfishType.SKELETON || parent2.getSlabfishVisibleType() == SlabfishType.WITHER)) {
	    	  return SlabfishType.WITHER;
		}
		
		if (world.getBiome(pos).getCategory() == Biome.Category.ICY && (parent1.getSlabfishVisibleType() == SlabfishType.SKELETON || parent1.getSlabfishVisibleType() == SlabfishType.STRAY) && (parent2.getSlabfishVisibleType() == SlabfishType.SKELETON || parent2.getSlabfishVisibleType() == SlabfishType.STRAY)) {
	    	  return SlabfishType.STRAY;
		}
		
		if (this.getTypeForConditions(world) == SlabfishType.SWAMP && biome.getCategory() != Biome.Category.SWAMP) {
			if (rand.nextBoolean()) { 
				return parent1.getSlabfishVisibleType();
			} else {
				return parent2.getSlabfishVisibleType();
			}
		} else {
			if (rand.nextBoolean()) {
				return this.getTypeForConditions(world);
			} else {
				if (rand.nextBoolean()) { 
					return parent1.getSlabfishVisibleType();
				} else {
					return parent2.getSlabfishVisibleType();
				}
			}
		}
	}
	
	public void setCustomName(@Nullable ITextComponent name) {
		super.setCustomName(name);
		if (name != null && this.getSlabfishRealType() != SlabfishType.GHOST) {
			for(Map.Entry<List<String>, SlabfishType> entries : NAMES.entrySet()) {
				if(entries.getKey().contains(name.getString().toLowerCase().trim())) {
					this.setSlabfishVisibleType(entries.getValue());
					return;
				} 
			}
		}
		this.setSlabfishVisibleType(this.getSlabfishRealType());
	}
	
	public void onStruckByLightning(LightningBoltEntity lightningBolt) {
		UUID uuid = lightningBolt.getUniqueID();
		if (!uuid.equals(this.lightningUUID) && this.getSlabfishRealType() != SlabfishType.GHOST) {
			this.setSlabfishType(SlabfishType.SKELETON);
			this.lightningUUID = uuid;
			this.playSound(SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, 2.0F, 1.0F);
		}	
	}
	
	public void setSlabfishType(SlabfishType typeId) {
		this.setSlabfishVisibleType(typeId);
		this.setSlabfishRealType(typeId);
	}
	
	public SlabfishType getSlabfishVisibleType() {
		return SlabfishType.byId(this.dataManager.get(SLABFISH_TYPE));
	}
	
	public void setSlabfishVisibleType(SlabfishType typeId) {
		this.dataManager.set(SLABFISH_TYPE, typeId.getId());
	}
	
	public SlabfishType getSlabfishRealType() {
		return SlabfishType.byId(this.dataManager.get(SLABFISH_REAL_TYPE));
	}
	
	public void setSlabfishRealType(SlabfishType typeId) {
		this.dataManager.set(SLABFISH_REAL_TYPE, typeId.getId());
	}
	
	@Nullable
	@Override
	public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, ILivingEntityData spawnDataIn, CompoundNBT dataTag) {
		spawnDataIn = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		SlabfishType type = this.getTypeForConditions(worldIn);
		if (spawnDataIn instanceof SlabfishEntity.SlabfishData) {
			type = ((SlabfishEntity.SlabfishData)spawnDataIn).typeData;
		}
		this.setSlabfishType(type);
		
		return spawnDataIn;
	}
	
	public static class SlabfishData implements ILivingEntityData {
		public final SlabfishType typeData;

		public SlabfishData(SlabfishType type) {
			this.typeData = type;
		}
	}
	
	// DATA //
	
	public SlabbySitGoal getAISit() {
		return this.sitGoal;
	}
	
	public boolean isSitting() {
		return this.dataManager.get(IS_SITTING);
	}
	
	public void setSitting(boolean isSitting) {
		this.dataManager.set(IS_SITTING, isSitting);
		if (this.sitGoal != null) {
			this.sitGoal.setSitting(isSitting);
		}
	}
	
	public boolean hasBackpack() {
		return this.dataManager.get(HAS_BACKPACK);
	}
	
	public void setBackpacked(boolean hasBackpack) {
		this.dataManager.set(HAS_BACKPACK, hasBackpack);
	}
	
	public DyeColor getBackpackColor() {
		return DyeColor.byId(this.dataManager.get(BACKPACK_COLOR));
	}
	
	public void setBackpackColor(DyeColor color) {
		this.dataManager.set(BACKPACK_COLOR, color.getId());
	}
	
	public boolean hasSweater() {
		return this.dataManager.get(HAS_SWEATER);
	}
	
	public void setSweatered(boolean hasSweater) {
		this.dataManager.set(HAS_SWEATER, hasSweater);
	}
	
	public DyeColor getSweaterColor() {
		return DyeColor.byId(this.dataManager.get(SWEATER_COLOR));
	}
	
	public void setSweaterColor(DyeColor color) {
		this.dataManager.set(SWEATER_COLOR, color.getId());
	}
	
	public SlabfishOverlay getSlabfishOverlay() {
		return SlabfishOverlay.byId(this.dataManager.get(SLABFISH_OVERLAY));
	}
	
	public void setSlabfishOverlay(SlabfishOverlay typeId) {
		this.dataManager.set(SLABFISH_OVERLAY, typeId.getId());
	}
	
	// INVENTORY //
	
	public void openGUI(PlayerEntity playerEntity) {
		this.playBackpackSound();
		playerEntity.openContainer(new SimpleNamedContainerProvider((p_213701_1_, p_213701_2_, p_213701_3_) -> {
			return new ChestContainer(ContainerType.GENERIC_9X2, p_213701_1_, playerEntity.inventory, this.slabfishBackpack, 2);
		}, this.getDisplayName()));
	}
	
	protected boolean canEquipItem(ItemStack stack) {
		if (this.hasBackpack()) {
			return true;
		}
		return false;
	}
	
	public boolean canPickUpItem(ItemStack stack) {
		if (this.hasBackpack()) {
			return true;
		}
		return false;
	}
	
	protected int getInventorySize() {
		return 18;
	}
	
	public void initSlabfishBackpack() {
		Inventory inventory = this.slabfishBackpack;
		this.slabfishBackpack = new Inventory(this.getInventorySize());
		if (inventory != null) {
			inventory.removeListener(this);
			int i = Math.min(inventory.getSizeInventory(), this.slabfishBackpack.getSizeInventory());

			for(int j = 0; j < i; ++j) {
				ItemStack itemstack = inventory.getStackInSlot(j);
				if (!itemstack.isEmpty()) {
					this.slabfishBackpack.setInventorySlotContents(j, itemstack.copy());
				}
			}
		}
		
		this.slabfishBackpack.addListener(this);
		this.itemHandler = net.minecraftforge.common.util.LazyOptional.of(() -> new net.minecraftforge.items.wrapper.InvWrapper(this.slabfishBackpack));
	}
	
	public List<ItemEntity> getNearbyItems(float multiplier) {
		return this.world.getEntitiesWithinAABB(ItemEntity.class, this.getBoundingBox().grow(8.0F * multiplier, 4.0F, 8.0F * multiplier));
	}
	
	public boolean isPlayerNear(float multiplier) {
		return !this.getNearbyItems(multiplier).isEmpty();
	}
	
	protected void dropInventory() {
		super.dropInventory();
		if (this.hasBackpack()) {
			ItemEntity itementity = this.entityDropItem(Items.CHEST, 1);
	        if (itementity != null) {
	           itementity.setMotion(itementity.getMotion().add((double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F), (double)(this.rand.nextFloat() * 0.05F), (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F)));
	        }
			if (this.slabfishBackpack != null) {
				for(int i = 0; i < this.slabfishBackpack.getSizeInventory(); ++i) {
					ItemStack itemstack = this.slabfishBackpack.getStackInSlot(i);
		            if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
		            	this.entityDropItem(itemstack);
		            }
				}
				
			}
		}
		if (this.hasSweater()) {
			ItemEntity itementity = this.entityDropItem(REVERSE_MAP.get(this.getSweaterColor()), 1);
	        if (itementity != null) {
	           itementity.setMotion(itementity.getMotion().add((double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F), (double)(this.rand.nextFloat() * 0.05F), (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F)));
	        }
		}
		
	}
	
	protected void updateEquipmentIfNeeded(ItemEntity itemEntity) {
		ItemStack itemstack = itemEntity.getItem();
		Inventory inventory = this.slabfishBackpack;
		
		if(this.hasBackpack()) {
			for(int i = 0; i < inventory.getSizeInventory(); ++i) {
				ItemStack itemstack1 = inventory.getStackInSlot(i);
				if (itemstack1.isEmpty() || itemstack1.getItem() == itemstack.getItem() && itemstack1.getCount() < itemstack1.getMaxStackSize() && ItemStack.areItemsEqual(itemstack, itemstack1)) {
					inventory.addItem(itemstack);
					this.onItemPickup(itemEntity, itemstack.getCount());
					itemEntity.remove();
					break;
				}
			}	
		}
	}
	
	public void onInventoryChanged(IInventory invBasic) {
	}
	
	private net.minecraftforge.common.util.LazyOptional<?> itemHandler = null;

	@Override
	public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable net.minecraft.util.Direction facing) {
		if (this.isAlive() && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && itemHandler != null)
			return itemHandler.cast();
		return super.getCapability(capability, facing);
	}

	@Override
	public void remove(boolean keepData) {
		super.remove(keepData);
		if (!keepData && itemHandler != null) {
			itemHandler.invalidate();
			itemHandler = null;
		}
	}
	
	// MISC //
	
	static class SlabfishFuedGoal extends NearestAttackableTargetGoal<SlabfishEntity> {
		
		public SlabfishFuedGoal(SlabfishEntity slabby) {
			super(slabby, SlabfishEntity.class, 0, true, true, LivingEntity::attackable);
		}

		public boolean shouldExecute() {
			if (super.shouldExecute()) {
		  		boolean flag = false;
				List<String> musicano = Arrays.asList("gore", "gore.", "musicano");
		  		List<String> snake = Arrays.asList("snake", "snakeblock", "snake block");
		  		
		  		SlabfishEntity slabby = (SlabfishEntity)this.goalOwner; 
		  		SlabfishEntity target = (SlabfishEntity)this.nearestTarget;
		  		
		  		if (target != null && slabby.hasCustomName() && target.hasCustomName()) {
					String name 		= slabby.getName().getString().toLowerCase().trim();
	  				String targetName 	= target.getName().getString().toLowerCase().trim();
	  				
		  			if (musicano.contains(name) && snake.contains(targetName)) flag = true;	  			
		  			else if (snake.contains(name) && musicano.contains(targetName)) flag = true;
		  		}
		  		return flag;
			}
			return super.shouldExecute();
		}
		
		public void startExecuting() {
			super.startExecuting();
			this.goalOwner.setIdleTime(0);
		}
	}
	
	@Override
    public IPacket<?> createSpawnPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
	
	private static final Map<Item, DyeColor> SWEATER_MAP = Util.make(Maps.newHashMap(), (sweaterMap) -> {
		sweaterMap.put(Items.WHITE_WOOL, DyeColor.WHITE);
		sweaterMap.put(Items.ORANGE_WOOL, DyeColor.ORANGE);
		sweaterMap.put(Items.MAGENTA_WOOL, DyeColor.MAGENTA);
		sweaterMap.put(Items.LIGHT_BLUE_WOOL, DyeColor.LIGHT_BLUE);
		sweaterMap.put(Items.YELLOW_WOOL, DyeColor.YELLOW);
		sweaterMap.put(Items.LIME_WOOL, DyeColor.LIME);
		sweaterMap.put(Items.PINK_WOOL, DyeColor.PINK);
		sweaterMap.put(Items.GRAY_WOOL, DyeColor.GRAY);
		sweaterMap.put(Items.LIGHT_GRAY_WOOL, DyeColor.LIGHT_GRAY);
		sweaterMap.put(Items.CYAN_WOOL, DyeColor.CYAN);
		sweaterMap.put(Items.PURPLE_WOOL, DyeColor.PURPLE);
		sweaterMap.put(Items.BLUE_WOOL, DyeColor.BLUE);
		sweaterMap.put(Items.BROWN_WOOL, DyeColor.BROWN);
		sweaterMap.put(Items.GREEN_WOOL, DyeColor.GREEN);
		sweaterMap.put(Items.RED_WOOL, DyeColor.RED);
		sweaterMap.put(Items.BLACK_WOOL, DyeColor.BLACK);
		
	});
	
	private static final Map<DyeColor, Item> REVERSE_MAP = Util.make(Maps.newEnumMap(DyeColor.class), (reverseMap) -> {
		reverseMap.put(DyeColor.WHITE, Items.WHITE_WOOL);
		reverseMap.put(DyeColor.ORANGE, Items.ORANGE_WOOL);
		reverseMap.put(DyeColor.MAGENTA, Items.MAGENTA_WOOL);
		reverseMap.put(DyeColor.LIGHT_BLUE, Items.LIGHT_BLUE_WOOL);
		reverseMap.put(DyeColor.YELLOW, Items.YELLOW_WOOL);
		reverseMap.put(DyeColor.LIME, Items.LIME_WOOL);
		reverseMap.put(DyeColor.PINK, Items.PINK_WOOL);
		reverseMap.put(DyeColor.GRAY, Items.GRAY_WOOL);
		reverseMap.put(DyeColor.LIGHT_GRAY, Items.LIGHT_GRAY_WOOL);
		reverseMap.put(DyeColor.CYAN, Items.CYAN_WOOL);
		reverseMap.put(DyeColor.PURPLE, Items.PURPLE_WOOL);
		reverseMap.put(DyeColor.BLUE, Items.BLUE_WOOL);
		reverseMap.put(DyeColor.BROWN, Items.BROWN_WOOL);
		reverseMap.put(DyeColor.GREEN, Items.GREEN_WOOL);
		reverseMap.put(DyeColor.RED, Items.RED_WOOL);
		reverseMap.put(DyeColor.BLACK, Items.BLACK_WOOL);
	});
}
