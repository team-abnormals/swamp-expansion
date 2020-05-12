package com.farcr.swampexpansion.core;

import com.farcr.swampexpansion.common.block.fluid.MudFluid;
import com.farcr.swampexpansion.core.other.SwampExData;
import com.farcr.swampexpansion.core.registry.SwampExBiomes;
import com.farcr.swampexpansion.core.registry.SwampExBlocks;
import com.farcr.swampexpansion.core.registry.SwampExEntities;
import com.farcr.swampexpansion.core.registry.SwampExFeatures;
import com.teamabnormals.abnormals_core.core.utils.RegistryHelper;

import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.fluid.IFluidState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("swampexpansion")
@Mod.EventBusSubscriber(modid = "swampexpansion")
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
        SwampExData.registerBlockData();
        SwampExBiomes.registerBiomesToDictionary();
        SwampExFeatures.generateFeatures();
    	SwampExEntities.addEntitySpawns();
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
    
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onFogColor(FogColors event) {
        ActiveRenderInfo info = event.getInfo();
        IFluidState state = info.getFluidState();
        if (state.getFluid() instanceof MudFluid) {
            event.setRed(0.140625F);
            event.setGreen(0.0625F);
            event.setBlue(0.015625F);
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onFogDensity(FogDensity event) {
        ActiveRenderInfo info = event.getInfo();
        IFluidState state = info.getFluidState();
        if (state.getFluid() instanceof MudFluid) {
            //GlStateManager.fogMode(FogMode.EXP);
            event.setDensity(1.0F);
            event.setCanceled(true);
        }

    }
}