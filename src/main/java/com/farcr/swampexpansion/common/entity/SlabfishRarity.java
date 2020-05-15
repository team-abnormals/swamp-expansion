package com.farcr.swampexpansion.common.entity;

import net.minecraft.util.text.TextFormatting;

public enum SlabfishRarity implements net.minecraftforge.common.IExtensibleEnum {
	COMMON(TextFormatting.GRAY),
	UNCOMMON(TextFormatting.GREEN),
	RARE(TextFormatting.AQUA),
	EPIC(TextFormatting.LIGHT_PURPLE),
	LEGENDARY(TextFormatting.GOLD);
	
	public final TextFormatting color;

	private SlabfishRarity(TextFormatting color) {
		this.color = color;
	}

	public static SlabfishRarity create(String name, TextFormatting p_i48837_3_) {
		throw new IllegalStateException("Enum not extended");
	}
}
