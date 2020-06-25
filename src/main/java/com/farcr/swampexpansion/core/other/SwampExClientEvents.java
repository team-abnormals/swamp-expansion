package com.farcr.swampexpansion.core.other;

import com.farcr.swampexpansion.common.block.fluid.MudFluid;
import com.farcr.swampexpansion.core.SwampExpansion;

import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.fluid.IFluidState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = SwampExpansion.MODID, value = Dist.CLIENT)
public class SwampExClientEvents {
    
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
            event.setDensity(1.0F);
            event.setCanceled(true);
        }
    }
}
