package com.farcr.swampexpansion.core;

import com.farcr.swampexpansion.client.render.WillowBoatRenderer;
import com.farcr.swampexpansion.common.block.fluid.MudFluid;
import com.farcr.swampexpansion.common.entity.WillowBoatEntity;
import com.farcr.swampexpansion.common.worldgen.FeatureEditor;
import com.farcr.swampexpansion.core.registries.SwampExBlocks;
import com.farcr.swampexpansion.core.registries.SwampExData;
import com.farcr.swampexpansion.core.registries.SwampExFeatures;
import com.farcr.swampexpansion.core.registries.SwampExItems;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.GlStateManager.FogMode;

import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.fluid.IFluidState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("swampexpansion")
@Mod.EventBusSubscriber(modid = "swampexpansion")
public class SwampExpansion {
	
    public SwampExpansion() {
    	IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    	FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupCommon);
    	DistExecutor.runWhenOn(Dist.CLIENT, () -> this::initSetupClient);
        
        SwampExBlocks.BLOCKS.register(modEventBus);
        SwampExItems.ITEMS.register(modEventBus);
        SwampExBlocks.PAINTINGS.register(modEventBus);
        
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    public void initSetupClient()
    {
    	FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
    }

    private void setupCommon(final FMLCommonSetupEvent event) {
        SwampExData.registerBlockData();
        FeatureEditor.overrideFeatures();
        SwampExFeatures.generateFeatures();
    }
    
    @OnlyIn(Dist.CLIENT)
    private void setupClient(final FMLClientSetupEvent event) {
    	RenderingRegistry.registerEntityRenderingHandler(WillowBoatEntity.class, WillowBoatRenderer::new);
        SwampExData.registerBlockColors();
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
            GlStateManager.fogMode(FogMode.EXP);
            event.setDensity(1.0F);
            event.setCanceled(true);
        }

    }
}