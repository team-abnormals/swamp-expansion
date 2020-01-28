package com.farcr.swampexpansion.core;

import com.farcr.swampexpansion.common.worldgen.FeatureEditer;
import com.farcr.swampexpansion.core.proxy.ClientProxy;
import com.farcr.swampexpansion.core.proxy.ServerProxy;
import com.farcr.swampexpansion.core.registries.BlockRegistry;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("swampexpansion")
@Mod.EventBusSubscriber
public class SwampExpansion {
    public static ServerProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public SwampExpansion() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupCommon);
        registerFluidStuff();
    }

    private void setupCommon(final FMLCommonSetupEvent event) {
        proxy.init();
        BlockRegistry.registerBlockData();
        FeatureEditer.overRideFeatures();
    }

    @SubscribeEvent
    void preInit(final FMLCommonSetupEvent event)
    {

    }

    public static void registerFluidStuff()
    {
        BlockRegistry.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BlockRegistry.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BlockRegistry.FLUIDS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}