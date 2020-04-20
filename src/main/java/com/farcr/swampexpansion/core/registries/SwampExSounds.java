package com.farcr.swampexpansion.core.registries;

import com.farcr.swampexpansion.core.SwampExpansion;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "swampexpansion", bus = Mod.EventBusSubscriber.Bus.MOD)
public class SwampExSounds {
	
	public static SoundEvent SLABRAVE = new SoundEvent(new ResourceLocation(SwampExpansion.MODID, "music.record.slabrave")).setRegistryName("music.record.slabrave");
	
	@SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
		event.getRegistry().register(SLABRAVE);
    }
}
