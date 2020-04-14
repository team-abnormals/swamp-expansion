package com.farcr.swampexpansion.common.entity;

import java.util.Arrays;
import java.util.Comparator;

import net.minecraft.util.IStringSerializable;

public enum SlabfishType implements IStringSerializable {
   SWAMP(0, "swamp"),
   OCEAN(1, "ocean"),
   MARSH(2, "marsh"),
   MIRE(3, "mire"),
   CAVE(4, "cave"),
   JUNGLE(5, "jungle"),
   DESERT(6, "desert"),
   SAVANNA(7, "savanna"),
   MESA(8, "mesa"),
   SNOWY(9, "snowy"),
   TOTEM(10, "totem"),
   TAIGA(11, "taiga");

   private static final SlabfishType[] VALUES = Arrays.stream(values()).sorted(Comparator.comparingInt(SlabfishType::getId)).toArray((p_199795_0_) -> {
      return new SlabfishType[p_199795_0_];
   });
   private final int id;
   private final String translationKey;

   private SlabfishType(int idIn, String translationKeyIn) {
      this.id = idIn;
      this.translationKey = translationKeyIn;
   }

   public int getId() {
      return this.id;
   }

   public String getTranslationKey() {
      return this.translationKey;
   }

   public static SlabfishType byId(int id) {
      if (id < 0 || id >= VALUES.length) {
    	  id = 0;
      }

      return VALUES[id];
   }

   public static SlabfishType byTranslationKey(String key, SlabfishType type) {
      for(SlabfishType slabfishtype : values()) {
         if (slabfishtype.translationKey.equals(key)) {
            return slabfishtype;
         }
      }

      return type;
   }

   public String toString() {
      return this.translationKey;
   }

   public String getName() {
      return this.translationKey;
   }

}