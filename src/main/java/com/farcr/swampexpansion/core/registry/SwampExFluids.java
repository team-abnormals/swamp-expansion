package com.farcr.swampexpansion.core.registry;

import com.farcr.swampexpansion.common.block.fluid.MudFluid;

import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder("swampexpansion")
@EventBusSubscriber(
    modid = "swampexpansion",
    bus = Bus.MOD
)
public class SwampExFluids {
    public static final Fluid MUD = new MudFluid.Source();
    public static final FlowingFluid FLOWING_MUD = new MudFluid.Flowing();

    public SwampExFluids() {
    }

    @SubscribeEvent
    public static void register(Register<Fluid> event) {
        event.getRegistry().register(MUD);
        event.getRegistry().register(FLOWING_MUD);
    }
}
