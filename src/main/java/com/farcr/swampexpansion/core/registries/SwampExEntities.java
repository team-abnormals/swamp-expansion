package com.farcr.swampexpansion.core.registries;

import java.util.function.BiFunction;

import com.farcr.swampexpansion.client.render.SlabfishRenderer;
import com.farcr.swampexpansion.client.render.WillowBoatRenderer;
import com.farcr.swampexpansion.common.entity.SlabfishEntity;
import com.farcr.swampexpansion.common.entity.WillowBoatEntity;
import com.farcr.swampexpansion.core.SwampExpansion;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class SwampExEntities
{
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.ENTITIES, "swampexpansion");
	
	public static final RegistryObject<EntityType<WillowBoatEntity>> BOAT = ENTITY_TYPES.register("boat", () -> createEntity(WillowBoatEntity::new, null, EntityClassification.MISC, "boat", 1.375F, 0.5625F));
	public static final RegistryObject<EntityType<SlabfishEntity>> SLABFISH = ENTITY_TYPES.register("slabfish", () -> createLivingEntity(SlabfishEntity::new, EntityClassification.WATER_CREATURE, "slabfish", 0.5F, 0.9375F));

    
    @OnlyIn(Dist.CLIENT)
    public static void registerRendering()
    {
        RenderingRegistry.registerEntityRenderingHandler((EntityType<? extends WillowBoatEntity>)BOAT.get(), WillowBoatRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler((EntityType<? extends SlabfishEntity>)SLABFISH.get(), SlabfishRenderer::new);

    }
	
	public static void addEntitySpawns() {
		ForgeRegistries.BIOMES.getValues().stream().forEach(SwampExEntities::processSpawning);
	}
	
	private static void processSpawning(Biome biome) {
		if(biome.getCategory() == Category.SWAMP) {
    		biome.getSpawns(EntityClassification.WATER_CREATURE).add(new Biome.SpawnListEntry(SwampExEntities.SLABFISH.get(), 4, 2, 4));
        }
	}
	
    private static <T extends Entity> EntityType<T> createEntity(EntityType.IFactory<T> factory, BiFunction<FMLPlayMessages.SpawnEntity, World, T> clientFactory, EntityClassification entityClassification, String name, float width, float height) {
		ResourceLocation location = new ResourceLocation("swampexpansion", name);
		EntityType<T> entity = EntityType.Builder.create(factory, entityClassification)
			.size(width, height)
			.setTrackingRange(64)
			.setShouldReceiveVelocityUpdates(true)
			.setUpdateInterval(3)
			.setCustomClientFactory(clientFactory)
			.build(location.toString()
		);
		return entity;
	}
    
    private static <T extends LivingEntity> EntityType<T> createLivingEntity(EntityType.IFactory<T> factory, EntityClassification entityClassification, String name, float width, float height){
		ResourceLocation location = new ResourceLocation(SwampExpansion.MODID, name);
		EntityType<T> entity = EntityType.Builder.create(factory, entityClassification)
			.size(width, height)
			.setTrackingRange(64)
			.setShouldReceiveVelocityUpdates(true)
			.setUpdateInterval(3)
			.build(location.toString()
		);
		return entity;
	}    
}