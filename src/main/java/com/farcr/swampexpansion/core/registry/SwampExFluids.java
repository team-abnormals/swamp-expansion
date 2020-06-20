package com.farcr.swampexpansion.core.registry;

import com.farcr.swampexpansion.common.block.fluid.MudFluid;
import com.farcr.swampexpansion.core.SwampExpansion;

import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(modid = SwampExpansion.MODID, bus = Bus.MOD)
public class SwampExFluids {
	public static final DeferredRegister<Fluid> FLUIDS = new DeferredRegister<>(ForgeRegistries.FLUIDS, SwampExpansion.MODID);

    public static final RegistryObject<Fluid> MUD = FLUIDS.register("mud", () -> new MudFluid.Source());
    public static final RegistryObject<FlowingFluid> FLOWING_MUD = FLUIDS.register("flowing_mud", () -> new MudFluid.Flowing());
}
