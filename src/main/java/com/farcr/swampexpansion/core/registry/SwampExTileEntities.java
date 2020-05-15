package com.farcr.swampexpansion.core.registry;

import com.farcr.swampexpansion.common.tile.MudVaseTileEntity;
import com.farcr.swampexpansion.core.SwampExpansion;
import com.teamabnormals.abnormals_core.core.utils.RegistryHelper;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SwampExpansion.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SwampExTileEntities {
	public static final RegistryHelper HELPER = SwampExpansion.REGISTRY_HELPER;

	public static final RegistryObject<TileEntityType<MudVaseTileEntity>> MUD_VASE = HELPER.createTileEntity("mud_vase", MudVaseTileEntity::new, () -> new Block[] {SwampExBlocks.MUD_VASE.get()});
}
