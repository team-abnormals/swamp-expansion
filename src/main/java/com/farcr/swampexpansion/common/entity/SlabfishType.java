package com.farcr.swampexpansion.common.entity;

import java.util.Arrays;
import java.util.Comparator;

import net.minecraft.util.IStringSerializable;

public enum SlabfishType implements IStringSerializable, net.minecraftforge.common.IExtensibleEnum {
	SWAMP(0, "swamp", SlabfishRarity.COMMON),
	OCEAN(1, "ocean", SlabfishRarity.COMMON),
	MARSH(2, "marsh", SlabfishRarity.COMMON),
	MIRE(3, "mire", SlabfishRarity.UNCOMMON),
	CAVE(4, "cave", SlabfishRarity.RARE),
	JUNGLE(5, "jungle", SlabfishRarity.UNCOMMON),
	DESERT(6, "desert", SlabfishRarity.COMMON),
	SAVANNA(7, "savanna", SlabfishRarity.COMMON),
	MESA(8, "mesa", SlabfishRarity.RARE),
	SNOWY(9, "snowy", SlabfishRarity.COMMON),
	TOTEM(10, "totem", SlabfishRarity.EPIC),
	TAIGA(11, "taiga", SlabfishRarity.COMMON),
	FOREST(12, "forest", SlabfishRarity.COMMON),
	PLAINS(13, "plains", SlabfishRarity.COMMON),
	SKELETON(14, "skeleton", SlabfishRarity.RARE),
	WITHER(15, "wither", SlabfishRarity.EPIC),
	RIVER(16, "river", SlabfishRarity.COMMON),
	MAPLE(17, "maple", SlabfishRarity.COMMON),
	ROSEWOOD(18, "rosewood", SlabfishRarity.RARE),
	DUNES(19, "dunes", SlabfishRarity.RARE),
	NIGHTMARE(20, "nightmare", SlabfishRarity.RARE),
	ICE_SPIKES(21, "ice_spikes", SlabfishRarity.EPIC),
	STRAY(22, "stray", SlabfishRarity.EPIC),
	NETHER(23, "nether", SlabfishRarity.UNCOMMON),
	END(24, "end", SlabfishRarity.RARE),
	POISE(25, "poise", SlabfishRarity.EPIC),
	GHOST(26, "ghost", SlabfishRarity.RARE),
	BAGEL(27, "bagel", SlabfishRarity.UNCOMMON),
	CAMERON(28, "cameron", SlabfishRarity.UNCOMMON),
	GORE(29, "gore", SlabfishRarity.UNCOMMON),
	SNAKE_BLOCK(30, "snake_block", SlabfishRarity.UNCOMMON),
	DROWNED(31, "drowned", SlabfishRarity.RARE),
	FROZEN_OCEAN(32, "frozen_ocean", SlabfishRarity.UNCOMMON),
	WARM_OCEAN(33, "warm_ocean", SlabfishRarity.UNCOMMON),
	MOUNTAIN(34, "mountain", SlabfishRarity.COMMON),
	MUSHROOM(35, "mushroom", SlabfishRarity.RARE),
	BAMBOO(36, "bamboo", SlabfishRarity.RARE),
	CHORUS(37, "chorus", SlabfishRarity.EPIC),
	DARK_FOREST(38, "dark_forest", SlabfishRarity.UNCOMMON),
	FLOWER_FOREST(39, "flower_forest", SlabfishRarity.UNCOMMON),
	BEACH(40, "beach", SlabfishRarity.COMMON),
	SKY(41, "sky", SlabfishRarity.EPIC),
	BROWN_MUSHROOM(42, "brown_mushroom", SlabfishRarity.EPIC),
	JACKSON(43, "jackson", SlabfishRarity.UNCOMMON),
	MISTA_JUB(44, "mista_jub", SlabfishRarity.UNCOMMON),
	SMELLY(45, "smelly", SlabfishRarity.UNCOMMON),
	SQUART(46, "squart", SlabfishRarity.UNCOMMON);

	private static final SlabfishType[] VALUES = Arrays.stream(values()).sorted(Comparator.comparingInt(SlabfishType::getId)).toArray((array) -> {
		return new SlabfishType[array];
	});
	private final int id;
	private final String name;
	private final SlabfishRarity rarity;

	private SlabfishType(int idIn, String name, SlabfishRarity rarity) {
		this.id = idIn;
		this.name = name;
		this.rarity = rarity;
	}

	public int getId() {
		return this.id;
	}
	
	public SlabfishRarity getRarity() {
		return this.rarity;
	}

	public static SlabfishType byId(int id) {
		if (id < 0 || id >= VALUES.length) {
			id = 0;
		}
		return VALUES[id];
	}

	public static SlabfishType byName(String key, SlabfishType type) {
		for(SlabfishType slabfishtype : values()) {
			if (slabfishtype.name.equals(key)) {
				return slabfishtype;
			}
		}
		return type;
	}

	public String getName() {
		return this.name;
	}
	
	public String getTranslationKey() {
		return "entity.swampexpansion.slabfish." + this.name;
	}
}