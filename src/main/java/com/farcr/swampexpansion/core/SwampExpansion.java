package com.farcr.swampexpansion.core;

import com.farcr.swampexpansion.core.other.SwampExData;
import com.farcr.swampexpansion.core.registry.SwampExBiomes;
import com.farcr.swampexpansion.core.registry.SwampExBlocks;
import com.farcr.swampexpansion.core.registry.SwampExEntities;
import com.farcr.swampexpansion.core.registry.SwampExFeatures;
import com.teamabnormals.abnormals_core.core.utils.RegistryHelper;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@SuppressWarnings("deprecation")
@Mod(SwampExpansion.MODID)
@Mod.EventBusSubscriber(modid = SwampExpansion.MODID)
public class SwampExpansion {
	public static final String MODID = "swampexpansion";
	public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MODID);

    public SwampExpansion() {
    	IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    	
    	REGISTRY_HELPER.getDeferredBlockRegister().register(modEventBus);
    	REGISTRY_HELPER.getDeferredItemRegister().register(modEventBus);
    	REGISTRY_HELPER.getDeferredEntityRegister().register(modEventBus);
    	REGISTRY_HELPER.getDeferredTileEntityRegister().register(modEventBus);
    	REGISTRY_HELPER.getDeferredSoundRegister().register(modEventBus);

        SwampExBlocks.PAINTINGS.register(modEventBus);
        SwampExBiomes.BIOMES.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
        
        modEventBus.addListener(this::setupCommon);
    	DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
        	modEventBus.addListener(this::setupClient);
        	modEventBus.addListener(this::registerItemColors);
        });
    }

    private void setupCommon(final FMLCommonSetupEvent event) {
    	DeferredWorkQueue.runLater(() -> {
    		SwampExData.registerCompostables();
    		SwampExData.registerFlammables();
    		SwampExBiomes.registerBiomesToDictionary();
    		SwampExFeatures.generateFeatures();
    		SwampExEntities.addEntitySpawns();
    	});
    }
    
    private void setupClient(final FMLClientSetupEvent event) {
    	SwampExData.setRenderLayers();
    	SwampExEntities.registerRendering();
        SwampExData.registerBlockColors();
    }
    
    @OnlyIn(Dist.CLIENT)
	private void registerItemColors(ColorHandlerEvent.Item event) {
    	REGISTRY_HELPER.processSpawnEggColors(event);
	}
}