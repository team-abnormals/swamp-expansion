package com.farcr.swampexpansion.core.other;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.config.ModConfig;

/**
 * @author SmellyModder(Luke Tonon)
 * Based off of AbnormalsCore config :)
 */
public class SwampExConfig {

	public static class Common {
		public final ConfigValue<Boolean> allowSlabfishBucketing;
		public final ConfigValue<Boolean> canBucketSlabfishWearingBackpacks;
		public final ConfigValue<Boolean> canBucketBabySlabfish;
		public final ConfigValue<Boolean> displaySlabfishRarity;
		
		Common(ForgeConfigSpec.Builder builder) {
			builder.push("Slabfish Bucketing");
			
			allowSlabfishBucketing = builder
				.comment("If Slabfish can be bucketed; Default: True")
				.translation(makeTranslation("allow_slabfish_bucketing"))
				.define("allowSlabfishBucketing", true);
			
			canBucketSlabfishWearingBackpacks = builder
				.comment("If Slabfish with backpacks can be bucketed; Default: False")
				.translation(makeTranslation("can_bucket_slabfish_wearing_backpack"))
				.define("canBucketSlabfishWearingBackpacks", false);
			
			canBucketBabySlabfish = builder
					.comment("If baby Slabfish can be bucketed; Default: False")
					.translation(makeTranslation("can_bucket_baby_slabfish"))
					.define("canBucketBabySlabfish", false);
			
			displaySlabfishRarity = builder
					.comment("If the Rarity color of the Slabfish is visible in the bucket's tooltip; Default: True")
					.translation(makeTranslation("display_slabfish_rarity"))
					.define("displaySlabfishRarity", true);
			
			builder.pop();
		}
	}
	
	private static String makeTranslation(String name) {
		return "swampex.config." + name;
	}
	
	public static final ForgeConfigSpec COMMON_SPEC;
	public static final Common COMMON;
	static {
		final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = specPair.getRight();
		COMMON = specPair.getLeft();
	}
	
	public static class ValuesHolder {
		private static boolean allowSlabfishBucketing;
		private static boolean canBucketSlabfishWearingBackpacks;
		private static boolean canBucketBabySlabfish;
		private static boolean displaySlabfishRarity;
		
		public static void updateCommonValuesFromConfig(ModConfig config) {
			allowSlabfishBucketing = SwampExConfig.COMMON.allowSlabfishBucketing.get();
			canBucketSlabfishWearingBackpacks = SwampExConfig.COMMON.canBucketSlabfishWearingBackpacks.get();
			canBucketBabySlabfish = SwampExConfig.COMMON.canBucketBabySlabfish.get();
			displaySlabfishRarity = SwampExConfig.COMMON.displaySlabfishRarity.get();

		}
		
		public static boolean canBucketSlabfish() {
			return allowSlabfishBucketing;
		}
		
		public static boolean canBucketBackpackedSlabfish() {
			return canBucketSlabfishWearingBackpacks;
		}
		
		public static boolean canBucketBabySlabfish() {
			return canBucketBabySlabfish;
		}
		
		public static boolean canDisplaySlabfishRarity() {
			return displaySlabfishRarity;
		}
	}
 
}
