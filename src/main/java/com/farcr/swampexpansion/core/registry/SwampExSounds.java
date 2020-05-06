package com.farcr.swampexpansion.core.registry;

import com.farcr.swampexpansion.core.SwampExpansion;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = "swampexpansion", bus = Mod.EventBusSubscriber.Bus.MOD)
public class SwampExSounds {
	
	public static final DeferredRegister<SoundEvent> SOUNDS = new DeferredRegister<>(ForgeRegistries.SOUND_EVENTS, "swampexpansion");

	public static final RegistryObject<SoundEvent> ENTITY_SLABFISH_BURP 	= reuseSoundEvent("entity.slabfish.burp", SoundEvents.ENTITY_PLAYER_BURP);
	public static final RegistryObject<SoundEvent> ENTITY_SLABFISH_DEATH 	= reuseSoundEvent("entity.slabfish.death", SoundEvents.ENTITY_COD_DEATH);
	public static final RegistryObject<SoundEvent> ENTITY_SLABFISH_STEP 	= reuseSoundEvent("entity.slabfish.step", SoundEvents.ENTITY_COD_FLOP);
	public static final RegistryObject<SoundEvent> ENTITY_SLABFISH_HURT 	= reuseSoundEvent("entity.slabfish.hurt", SoundEvents.ENTITY_COD_HURT);
	public static final RegistryObject<SoundEvent> ENTITY_SLABFISH_EAT	 	= reuseSoundEvent("entity.slabfish.eat", SoundEvents.ENTITY_GENERIC_EAT);
	public static final RegistryObject<SoundEvent> ENTITY_SLABFISH_TRANSFORM= reuseSoundEvent("entity.slabfish.transform", SoundEvents.BLOCK_FIRE_EXTINGUISH);
	public static final RegistryObject<SoundEvent> ENTITY_SLABFISH_BACKPACK	= reuseSoundEvent("entity.slabfish.backpack", SoundEvents.ENTITY_HORSE_SADDLE);
	public static final RegistryObject<SoundEvent> ENTITY_SLABFISH_SWEATER	= reuseSoundEvent("entity.slabfish.sweater", SoundEvents.ITEM_ARMOR_EQUIP_LEATHER);
	
	public static final SoundEvent SLABRAVE = new SoundEvent(new ResourceLocation(SwampExpansion.MODID, "music.record.slabrave")).setRegistryName("music.record.slabrave");
	
	@SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
		event.getRegistry().register(SLABRAVE);
    }
	
	private static RegistryObject<SoundEvent> reuseSoundEvent(String name, SoundEvent event) {
		return SOUNDS.register(name, () -> new SoundEvent(new ResourceLocation(SwampExpansion.MODID, name)));
	}
}
