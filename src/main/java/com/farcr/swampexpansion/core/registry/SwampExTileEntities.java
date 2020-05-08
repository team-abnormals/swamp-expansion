package com.farcr.swampexpansion.core.registry;

import com.farcr.swampexpansion.common.tile.MudVaseTileEntity;
import com.farcr.swampexpansion.core.SwampExpansion;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SwampExTileEntities {
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, SwampExpansion.MODID);

	public static final RegistryObject<TileEntityType<MudVaseTileEntity>> MUD_VASE = TILE_ENTITIES.register("mud_vase", () -> TileEntityType.Builder.create(MudVaseTileEntity::new, SwampExBlocks.MUD_VASE.get()).build(null));
}
