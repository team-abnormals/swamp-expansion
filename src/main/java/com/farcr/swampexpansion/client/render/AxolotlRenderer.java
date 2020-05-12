//package com.farcr.swampexpansion.client.render;
//
//import com.farcr.swampexpansion.client.model.AxolotlModel;
//import com.farcr.swampexpansion.common.entity.AxolotlEntity;
//import com.farcr.swampexpansion.core.SwampExpansion;
//
//import net.minecraft.client.renderer.entity.EntityRendererManager;
//import net.minecraft.client.renderer.entity.MobRenderer;
//import net.minecraft.util.ResourceLocation;
//
//public class AxolotlRenderer extends MobRenderer<AxolotlEntity, AxolotlModel<AxolotlEntity, AxolotlEntity>> {	
//	public AxolotlRenderer(EntityRendererManager renderManager) {
//		super(renderManager, new AxolotlModel<>(), 0.3F);
//	}
//
//	@Override
//	public ResourceLocation getEntityTexture(AxolotlEntity slabby) {
//		return new ResourceLocation(SwampExpansion.MODID, "textures/entity/axolotl.png");
//	}
//}
