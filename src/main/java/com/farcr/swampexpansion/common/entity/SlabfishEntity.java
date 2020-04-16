package com.farcr.swampexpansion.common.entity;

import java.util.List;

import javax.annotation.Nullable;

import com.farcr.swampexpansion.common.entity.goals.SlabbyBreedGoal;
import com.farcr.swampexpansion.common.entity.goals.SlabbyFollowParentGoal;
import com.farcr.swampexpansion.common.entity.goals.SlabbyGrabItemGoal;
import com.farcr.swampexpansion.common.entity.goals.SlabbySitGoal;
import com.farcr.swampexpansion.common.item.MudBallItem;
import com.farcr.swampexpansion.core.registries.SwampExBlocks;
import com.farcr.swampexpansion.core.registries.SwampExEntities;
import com.farcr.swampexpansion.core.registries.SwampExItems;
import com.farcr.swampexpansion.core.registries.SwampExTags;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.EvokerEntity;
import net.minecraft.entity.monster.IllusionerEntity;
import net.minecraft.entity.monster.PillagerEntity;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.entity.monster.VexEntity;
import net.minecraft.entity.monster.VindicatorEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
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
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

public class SlabfishEntity extends AnimalEntity implements IInventoryChangedListener {
	private static final DataParameter<Integer> SLABFISH_TYPE = EntityDataManager.createKey(SlabfishEntity.class, DataSerializers.VARINT);
	private static final DataParameter<Boolean> HAS_BACKPACK = EntityDataManager.createKey(SlabfishEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> BACKPACK_COLOR = EntityDataManager.createKey(SlabfishEntity.class, DataSerializers.VARINT);
	private static final DataParameter<Boolean> IS_MUDDY = EntityDataManager.createKey(SlabfishEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> IS_SITTING = EntityDataManager.createKey(SlabfishEntity.class, DataSerializers.BOOLEAN);

	protected SlabbySitGoal sitGoal;
	public static final EntitySize SIZE = EntitySize.fixed(0.6F, 0.2F);
	public static final EntitySize SIZE_CHILD = EntitySize.fixed(0.3159F, 0.1125F);
	
	private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Items.TROPICAL_FISH, SwampExItems.TROPICAL_FISH_KELP_ROLL.get());
	private static final Ingredient HEALING_ITEMS = Ingredient.fromTag(ItemTags.FISHES);
	private static final Ingredient SPEEDING_ITEMS = Ingredient.fromTag(SwampExTags.SUSHI);
	
	public Inventory slabfishBackpack;
	public float wingRotation;
	public float destPos;
	public float oFlapSpeed;
	public float oFlap;
	public float wingRotDelta = 1.0F;

	public SlabfishEntity(EntityType<? extends SlabfishEntity> type, World worldIn) {
		super(type, worldIn);
		this.setPathPriority(PathNodeType.WATER, 0.0F);
		this.initSlabfishBackpack();
	}

	protected void registerGoals() {
		this.sitGoal = new SlabbySitGoal(this);
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(1, this.sitGoal);
		this.goalSelector.addGoal(2, new PanicGoal(this, 1.4D));
		this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, EvokerEntity.class, 12.0F, 1.0D, 1.5D));
		this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, VindicatorEntity.class, 8.0F, 1.0D, 1.5D));
		this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, RavagerEntity.class, 8.0F, 1.0D, 1.5D));
		this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, VexEntity.class, 8.0F, 1.0D, 1.5D));
		this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, PillagerEntity.class, 15.0F, 1.0D, 1.5D));
		this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, IllusionerEntity.class, 12.0F, 1.0D, 1.5D));
		this.goalSelector.addGoal(3, new SlabbyBreedGoal(this, 1.0D));
		this.goalSelector.addGoal(4, new SlabbyGrabItemGoal(this, 1.1D));
		this.goalSelector.addGoal(5, new TemptGoal(this, 1.0D, false, TEMPTATION_ITEMS));
		this.goalSelector.addGoal(6, new SlabbyFollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(7, new RandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(9, new LookRandomlyGoal(this));
	}

	protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
		return this.isSitting() ? sizeIn.height * 0.6F : sizeIn.height * 0.8F;
	}
	
	public boolean canBreatheUnderwater() {
		return true;
	}

	protected void registerAttributes() {
		super.registerAttributes();
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0D);
		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
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
		this.dataManager.register(SLABFISH_TYPE, 0);
		this.getDataManager().register(HAS_BACKPACK, false);
		this.getDataManager().register(BACKPACK_COLOR, DyeColor.BROWN.getId());
		this.getDataManager().register(IS_MUDDY, false);
		this.getDataManager().register(IS_SITTING, false);
	}
	
	public ItemStack getPickedResult(RayTraceResult target) {
		return new ItemStack(SwampExItems.SLABFISH_SPAWN_EGG.get());
	}
	
	public boolean canPickUpItem(ItemStack stack) {
		if (this.hasBackpack()) {
			return true;
		}
		return false;
	}	

	protected boolean canEquipItem(ItemStack stack) {
		if (this.hasBackpack()) {
			return true;
		}
		return false;
	}
	
	public SlabfishType getTypeForBiome(IWorld world) {
		BlockPos pos = new BlockPos(this);
		Biome biome = world.getBiome(pos);
		if (((ServerWorld)this.world).findRaid(pos) != null) {
			return SlabfishType.TOTEM;
		} else if (pos.getY() <= 20) {
			return SlabfishType.CAVE;
		} else if (biome.getCategory() == Biome.Category.OCEAN) {
			return SlabfishType.OCEAN;
		} else if (biome.getCategory() == Biome.Category.JUNGLE) {
			return SlabfishType.JUNGLE;
		} else if (biome.getCategory() == Biome.Category.SAVANNA) {
			return SlabfishType.SAVANNA;
		} else if (biome.getCategory() == Biome.Category.MESA) {
			return SlabfishType.MESA;
		} else if (biome.getCategory() == Biome.Category.ICY) {
			return SlabfishType.SNOWY;
		} else if (biome.getCategory() == Biome.Category.DESERT) {
			return SlabfishType.DESERT;
		} else if (biome.getCategory() == Biome.Category.TAIGA) {
			return SlabfishType.TAIGA;
		}
		return SlabfishType.SWAMP;
	}
	
	@Override
	public EntitySize getSize(Pose pose) {
		return this.isInWater() ? this.isChild() ? SIZE_CHILD : SIZE : super.getSize(pose);
	}
	
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
		} else if (item == Items.CHEST && this.hasBackpack() == false) {
			this.setBackpacked(true);
			this.playBackpackSound();
			if(!player.abilities.isCreativeMode) {
				itemstack.shrink(1);
			}
			return true;
		} else if (item == Items.SHEARS && this.hasBackpack() == true) {
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
		} else if (!this.isSitting() & this.hasBackpack() & player.func_226563_dT_()) {
			this.setSitting(true);
			return true;
		} else if (this.isSitting() & player.func_226563_dT_()) {
			this.setSitting(false);
			return true;
		} else if (this.hasBackpack() == true) {
			this.openGUI(player);
			player.stopActiveHand();
			return true;
		}
		return super.processInteract(player, hand);
	}
	
	public void openGUI(PlayerEntity playerEntity) {
		this.playBackpackSound();
		playerEntity.openContainer(new SimpleNamedContainerProvider((p_213701_1_, p_213701_2_, p_213701_3_) -> {
			return new ChestContainer(ContainerType.GENERIC_9X3, p_213701_1_, playerEntity.inventory, this.slabfishBackpack, 3);
		}, this.getDisplayName()));
	}
	
	protected void updateEquipmentIfNeeded(ItemEntity itemEntity) {
		ItemStack itemstack = itemEntity.getItem();
		Item item = itemstack.getItem();
		Inventory inventory = this.slabfishBackpack;
		boolean flag = false;
		
		if(this.hasBackpack()) {
			for(int i = 0; i < inventory.getSizeInventory(); ++i) {
				ItemStack itemstack1 = inventory.getStackInSlot(i);
				if (itemstack1.isEmpty() || itemstack1.getItem() == item && itemstack1.getCount() < itemstack1.getMaxStackSize()) {
					flag = true;
					break;
				}
			}	
			if (!flag) {
				return;
			}

			int j = inventory.count(item);
			if (j == 256) {
				return;
			}

			if (j > 256) {
				inventory.func_223374_a(item, j - 256);
				return;
			}

			this.onItemPickup(itemEntity, itemstack.getCount());
			ItemStack itemstack2 = inventory.addItem(itemstack);
			if (itemstack2.isEmpty()) {
				itemEntity.remove();
			} else {
				itemstack.setCount(itemstack2.getCount());
			}
		}
	}
	
	protected int getInventorySize() {
		return 27;
	}

	public void tick() {
		super.tick();
		if (this.rand.nextFloat() < 0.1F && this.isMuddy()) {
			for(int i = 0; i < this.rand.nextInt(2) + 1; ++i) {
				this.doParticle(this.world, this.getPosX() - (double)0.3F, this.getPosX() + (double)0.3F, this.getPosZ() - (double)0.3F, this.getPosZ() + (double)0.3F, this.getPosYHeight(0.5D), new ItemParticleData(ParticleTypes.ITEM, new ItemStack(SwampExItems.MUD_BALL.get())));
			}
		}
	}

	private void doParticle(World world, double p_226397_2_, double p_226397_4_, double p_226397_6_, double p_226397_8_, double p_226397_10_, IParticleData p_226397_12_) {
		world.addParticle(p_226397_12_, MathHelper.lerp(world.rand.nextDouble(), p_226397_2_, p_226397_4_), p_226397_10_, MathHelper.lerp(world.rand.nextDouble(), p_226397_6_, p_226397_8_), 0.0D, 0.0D, 0.0D);
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
	}
	
	protected void onInsideBlock(BlockState state) {
		if (state.getBlock() == SwampExBlocks.MUD.get()) {
			if(!this.isMuddy()) setMuddy(true);
		}
		if (state.getBlock() == Blocks.WATER) {
			if(this.isMuddy()) setMuddy(false);
		}
	}

	public void livingTick() {
		if (this.getMotion().getX() > 0 || this.getMotion().getY() > 0 || this.getMotion().getZ() > 0) {
			if (this.isPotionActive(Effects.SPEED) && rand.nextInt(3) == 0 && !this.isInWater()) {
				double d0 = this.rand.nextGaussian() * 0.02D;
	            double d1 = this.rand.nextGaussian() * 0.02D;
	            double d2 = this.rand.nextGaussian() * 0.02D;
	            this.world.addParticle(ParticleTypes.CLOUD, this.getPosXRandom(0.5D), this.getPosY() + 0.2D, this.getPosZRandom(0.5D), d0, d1, d2);
			}
		}
		super.livingTick();
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

	public boolean onLivingFall(float distance, float damageMultiplier) {
		return false;
	}
	
	@Override
	public boolean canDespawn(double distanceToClosestPlayer) {
		return !this.hasBackpack() && !this.hasCustomName() &&!this.isSitting();
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
	
	public List<ItemEntity> getNearbyItems(float multiplier) {
		return this.world.getEntitiesWithinAABB(ItemEntity.class, this.getBoundingBox().grow(8.0F * multiplier, 4.0F, 8.0F * multiplier));
	}
	
	public boolean isPlayerNear(float multiplier) {
		return !this.getNearbyItems(multiplier).isEmpty();
	}
	
	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putByte("SlabfishType", (byte) this.getSlabfishType().getId());
		compound.putBoolean("HasBackpack", this.hasBackpack());
		compound.putBoolean("IsMuddy", this.isMuddy());
		compound.putByte("BackpackColor", (byte) this.getBackpackColor().getId());
		compound.putBoolean("Sitting", this.isSitting());
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
		this.setBackpacked(compound.getBoolean("HasBackpack"));
		this.setMuddy(compound.getBoolean("IsMuddy"));
		if(compound.contains("BackpackColor", 99)) {
			this.setBackpackColor(DyeColor.byId(compound.getInt("BackpackColor")));
		}
		if (this.sitGoal != null) {
			this.sitGoal.setSitting(compound.getBoolean("Sitting"));
		}
		this.setSitting(compound.getBoolean("Sitting"));
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
	
	public SlabbySitGoal getAISit() {
		return this.sitGoal;
	}
	
	public SlabfishType getSlabfishType() {
		return SlabfishType.byId(this.dataManager.get(SLABFISH_TYPE));
	}
	
	public void setSlabfishType(SlabfishType typeId) {
		this.dataManager.set(SLABFISH_TYPE, typeId.getId());
	}
	
	public boolean hasBackpack() {
		return this.dataManager.get(HAS_BACKPACK);
	}
	
	public void setBackpacked(boolean hasBackpack) {
		this.dataManager.set(HAS_BACKPACK, hasBackpack);
	}
	
	public boolean isMuddy() {
		return this.dataManager.get(IS_MUDDY);
	}
	
	public void setMuddy(boolean isMuddy) {
		this.dataManager.set(IS_MUDDY, isMuddy);
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
	
	public DyeColor getBackpackColor() {
		return DyeColor.byId(this.dataManager.get(BACKPACK_COLOR));
	}
	
	public void setBackpackColor(DyeColor color) {
		this.dataManager.set(BACKPACK_COLOR, color.getId());
	}
	
	
	protected void playBackpackSound() {
		this.playSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
	}

	public int getInventoryColumns() {
		return 9;
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
	      this.updateSlabfishSlots();
	      this.itemHandler = net.minecraftforge.common.util.LazyOptional.of(() -> new net.minecraftforge.items.wrapper.InvWrapper(this.slabfishBackpack));
	   }

	   /**
	    * Updates the items in the saddle and armor slots of the horse's inventory.
	    */
	   protected void updateSlabfishSlots() {
	   }

	   /**
	    * Called by InventoryBasic.onInventoryChanged() on a array that is never filled.
	    */
	   public void onInventoryChanged(IInventory invBasic) {
	      this.updateSlabfishSlots();
	   }
	
	@Nullable
	@Override
	public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, ILivingEntityData spawnDataIn, CompoundNBT dataTag) {
		spawnDataIn = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		SlabfishType type = this.getTypeForBiome(worldIn);
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
	
	@Override
    public IPacket<?> createSpawnPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
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
}