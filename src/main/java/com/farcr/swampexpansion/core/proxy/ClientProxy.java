package com.farcr.swampexpansion.core.proxy;

import com.farcr.swampexpansion.client.render.WillowBoatRenderer;
import com.farcr.swampexpansion.common.entity.WillowBoatEntity;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends ServerProxy {
    @Override
    public void init() {
        RenderingRegistry.registerEntityRenderingHandler(WillowBoatEntity.class, WillowBoatRenderer::new);
    }
}