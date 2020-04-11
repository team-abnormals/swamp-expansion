package com.farcr.swampexpansion.core.registries;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

@SuppressWarnings("deprecation")
public class SwampExFoods {
	
	// saturation value = (hunger * saturation * 2)
	
	public static Food RICE_BALL 			= new Food.Builder().hunger(4).saturation(0.5F).build(); //0.5

	public static Food COD_KELP_ROLL 		= new Food.Builder().hunger(5).saturation(0.7F).fastToEat().build(); //0.70
	public static Food CLOWNFISH_KELP_ROLL 	= new Food.Builder().hunger(6).saturation(1.3F).fastToEat().build(); //1.25
	public static Food CRAB_KELP_ROLL 		= new Food.Builder().hunger(8).saturation(0.7F).fastToEat().build(); //0.69
	public static Food PIKE_KELP_ROLL 		= new Food.Builder().hunger(7).saturation(0.9F).fastToEat().build(); //0.86
	public static Food CAVEFISH_KELP_ROLL 	= new Food.Builder().hunger(4).saturation(0.8F).fastToEat().build(); //0.75

	public static Food SALMON_RICE_CAKE 	= new Food.Builder().hunger(6).saturation(0.7F).fastToEat().build(); //0.67
	public static Food PUFFERFISH_RICE_CAKE = new Food.Builder().hunger(6).saturation(0.3F).fastToEat().effect(new EffectInstance(Effects.WEAKNESS, 600), 1.0F).build(); //0.25
	public static Food LIONFISH_RICE_CAKE 	= new Food.Builder().hunger(5).saturation(0.9F).fastToEat().effect(new EffectInstance(Effects.SLOWNESS, 600), 1.0F).build(); //0.90

	public static Food SQUID_INK_RISOTTO 	= new Food.Builder().hunger(8).saturation(0.9F).build(); //0.94
}
