package com.farcr.swampexpansion.core.registry;

import com.farcr.swampexpansion.core.SwampExpansion;
import com.teamabnormals.abnormals_core.core.utils.RegistryHelper;

import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "swampexpansion", bus = Mod.EventBusSubscriber.Bus.MOD)
public class SwampExSounds {
	
	public static final RegistryHelper HELPER = SwampExpansion.REGISTRY_HELPER;

	public static final RegistryObject<SoundEvent> ENTITY_SLABFISH_BURP 	= HELPER.createSoundEvent("entity.slabfish.burp");
	public static final RegistryObject<SoundEvent> ENTITY_SLABFISH_DEATH 	= HELPER.createSoundEvent("entity.slabfish.death");
	public static final RegistryObject<SoundEvent> ENTITY_SLABFISH_STEP 	= HELPER.createSoundEvent("entity.slabfish.step");
	public static final RegistryObject<SoundEvent> ENTITY_SLABFISH_HURT 	= HELPER.createSoundEvent("entity.slabfish.hurt");
	public static final RegistryObject<SoundEvent> ENTITY_SLABFISH_EAT	 	= HELPER.createSoundEvent("entity.slabfish.eat");
	public static final RegistryObject<SoundEvent> ENTITY_SLABFISH_TRANSFORM= HELPER.createSoundEvent("entity.slabfish.transform");
	public static final RegistryObject<SoundEvent> ENTITY_SLABFISH_BACKPACK	= HELPER.createSoundEvent("entity.slabfish.backpack");
	public static final RegistryObject<SoundEvent> ENTITY_SLABFISH_SWEATER	= HELPER.createSoundEvent("entity.slabfish.sweater");
	
	public static final RegistryObject<SoundEvent> SLABRAVE	= HELPER.createSoundEvent("music.record.slabrave");
}
