package com.farcr.swampexpansion.core.proxy;

import com.farcr.swampexpansion.client.render.WillowBoatRenderer;
import com.farcr.swampexpansion.common.entity.WillowBoatEntity;
import com.farcr.swampexpansion.core.registries.BlockRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends ServerProxy {
    @Override
    public void init() {
        RenderingRegistry.registerEntityRenderingHandler(WillowBoatEntity.class, WillowBoatRenderer::new);
        registerBlockColors();
    }

    public static void registerBlockColors() {
        BlockColors blockColors = Minecraft.getInstance().getBlockColors();
        blockColors.register((x, world, pos, u) -> world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.get(0.5D, 1.0D), BlockRegistry.WILLOW_LEAVES, BlockRegistry.WILLOW_LEAF_CARPET);
        ItemColors itemColors = Minecraft.getInstance().getItemColors();
        itemColors.register((color, items) -> FoliageColors.get(0.5D, 1.0D), BlockRegistry.WILLOW_LEAVES, BlockRegistry.WILLOW_LEAF_CARPET);
    }
}