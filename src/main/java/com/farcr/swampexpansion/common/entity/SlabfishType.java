package com.farcr.swampexpansion.common.entity;

import java.util.Arrays;
import java.util.Comparator;

import net.minecraft.item.Rarity;
import net.minecraft.util.IStringSerializable;

public enum SlabfishType implements IStringSerializable {
	SWAMP(0, "swamp", Rarity.COMMON),
	OCEAN(1, "ocean", Rarity.COMMON),
	MARSH(2, "marsh", Rarity.COMMON),
	MIRE(3, "mire", Rarity.UNCOMMON),
	CAVE(4, "cave", Rarity.RARE),
	JUNGLE(5, "jungle", Rarity.UNCOMMON),
	DESERT(6, "desert", Rarity.COMMON),
	SAVANNA(7, "savanna", Rarity.COMMON),
	MESA(8, "mesa", Rarity.RARE),
	SNOWY(9, "snowy", Rarity.COMMON),
	TOTEM(10, "totem", Rarity.EPIC),
	TAIGA(11, "taiga", Rarity.COMMON),
	FOREST(12, "forest", Rarity.COMMON),
	PLAINS(13, "plains", Rarity.COMMON),
	SKELETON(14, "skeleton", Rarity.RARE),
	WITHER(15, "wither", Rarity.EPIC),
	RIVER(16, "river", Rarity.COMMON),
	MAPLE(17, "maple", Rarity.COMMON),
	ROSEWOOD(18, "rosewood", Rarity.UNCOMMON),
	DUNES(19, "dunes", Rarity.UNCOMMON),
	NIGHTMARE(20, "nightmare", Rarity.UNCOMMON),
	ICE_SPIKES(21, "ice_spikes", Rarity.RARE),
	STRAY(22, "stray", Rarity.EPIC),
	NETHER(23, "nether", Rarity.UNCOMMON),
	END(24, "end", Rarity.RARE),
	POISE(25, "poise", Rarity.EPIC),
	GHOST(26, "ghost", Rarity.RARE),
	BAGEL(27, "bagel", Rarity.UNCOMMON),
	CAMERON(28, "cameron", Rarity.UNCOMMON),
	GORE(29, "gore", Rarity.UNCOMMON),
	SNAKE_BLOCK(30, "snake_block", Rarity.UNCOMMON),
	DROWNED(31, "drowned", Rarity.RARE),
	FROZEN_OCEAN(32, "frozen_ocean", Rarity.UNCOMMON),
	WARM_OCEAN(33, "warm_ocean", Rarity.UNCOMMON),
	MOUNTAIN(34, "mountain", Rarity.COMMON),
	MUSHROOM(35, "mushroom", Rarity.RARE),
	BAMBOO(36, "bamboo", Rarity.RARE),
	CHORUS(37, "chorus", Rarity.EPIC),
	DARK_FOREST(38, "dark_forest", Rarity.UNCOMMON),
	FLOWER_FOREST(39, "flower_forest", Rarity.UNCOMMON),
	BEACH(40, "beach", Rarity.COMMON),
	SKY(41, "sky", Rarity.EPIC),
	BROWN_MUSHROOM(42, "brown_mushroom", Rarity.EPIC),
	JACKSON(43, "jackson", Rarity.UNCOMMON),
	MISTA_JUB(44, "mista_jub", Rarity.UNCOMMON),
	SMELLY(45, "smelly", Rarity.UNCOMMON),
	SQUART(46, "squart", Rarity.UNCOMMON);

	private static final SlabfishType[] VALUES = Arrays.stream(values()).sorted(Comparator.comparingInt(SlabfishType::getId)).toArray((array) -> {
		return new SlabfishType[array];
	});
	private final int id;
	private final String name;
	private final Rarity rarity;

	private SlabfishType(int idIn, String name, Rarity rarity) {
		this.id = idIn;
		this.name = name;
		this.rarity = rarity;
	}

	public int getId() {
		return this.id;
	}
	
	public Rarity getRarity() {
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