package com.farcr.swampexpansion.core;

import com.farcr.swampexpansion.client.render.WillowBoatRenderer;
import com.farcr.swampexpansion.common.entity.WillowBoatEntity;
import com.farcr.swampexpansion.common.worldgen.FeatureEditor;
import com.farcr.swampexpansion.core.registries.SwampExBlocks;
import com.farcr.swampexpansion.core.registries.SwampExData;
import com.farcr.swampexpansion.core.registries.SwampExItems;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("swampexpansion")
@Mod.EventBusSubscriber
public class SwampExpansion {
    //public static ServerProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public SwampExpansion() {
    	IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    	FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupCommon);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
        
        SwampExBlocks.BLOCKS.register(modEventBus);
        SwampExItems.ITEMS.register(modEventBus);
        SwampExBlocks.FLUIDS.register(modEventBus);
        
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setupCommon(final FMLCommonSetupEvent event) {
        SwampExData.registerBlockData();
        FeatureEditor.overRideFeatures();
    }
    
    @OnlyIn(Dist.CLIENT)
    private void setupClient(final FMLClientSetupEvent event) {
    	RenderingRegistry.registerEntityRenderingHandler(WillowBoatEntity.class, WillowBoatRenderer::new);
        SwampExData.registerBlockColors();
    }
}