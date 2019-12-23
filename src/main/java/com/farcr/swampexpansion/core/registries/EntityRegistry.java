package com.farcr.swampexpansion.core.registries;

import com.farcr.swampexpansion.common.entity.WillowBoatEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.FMLPlayMessages;

import java.util.function.BiFunction;

@Mod.EventBusSubscriber(modid = "swampexpansion", bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityRegistry {
    public static EntityType<WillowBoatEntity> WILLOW_BOAT = createBasicEntity(WillowBoatEntity::new, WillowBoatEntity::new, EntityClassification.MISC, "willow_boat", 1.375F, 0.5625F);

    private static <T extends Entity> EntityType<T> createBasicEntity(EntityType.IFactory<T> factory, BiFunction<FMLPlayMessages.SpawnEntity, World, T> clientFactory, EntityClassification entityClassification, String name, float width, float height) {
        ResourceLocation location = new ResourceLocation("swampexpansion", name);
        EntityType<T> entity = EntityType.Builder.create(factory, entityClassification).size(width, height).setTrackingRange(64).setShouldReceiveVelocityUpdates(true).setUpdateInterval(3).setCustomClientFactory(clientFactory).build(location.toString());
        entity.setRegistryName(location);
        return entity;
    }

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
        event.getRegistry().registerAll(
                WILLOW_BOAT
        );
    }
}