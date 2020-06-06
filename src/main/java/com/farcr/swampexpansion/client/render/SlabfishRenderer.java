package com.farcr.swampexpansion.client.render;

import com.farcr.swampexpansion.client.model.SlabfishModel;
import com.farcr.swampexpansion.client.render.layer.BackpackOverlayRenderLayer;
import com.farcr.swampexpansion.client.render.layer.BackpackRenderLayer;
import com.farcr.swampexpansion.client.render.layer.OverlayRenderLayer;
import com.farcr.swampexpansion.client.render.layer.SweaterRenderLayer;
import com.farcr.swampexpansion.common.entity.SlabfishEntity;
import com.farcr.swampexpansion.common.entity.SlabfishType;
import com.farcr.swampexpansion.core.SwampExpansion;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class SlabfishRenderer extends MobRenderer<SlabfishEntity, SlabfishModel<SlabfishEntity>> {	
	public SlabfishRenderer(EntityRendererManager renderManager) {
		super(renderManager, new SlabfishModel<>(), 0.3F);
		this.addLayer(new SweaterRenderLayer<>(this));
		this.addLayer(new BackpackRenderLayer<>(this));
		this.addLayer(new OverlayRenderLayer<>(this));
		this.addLayer(new BackpackOverlayRenderLayer<>(this));
	}

	@Override
	public ResourceLocation getEntityTexture(SlabfishEntity slabby) {
		return new ResourceLocation(SwampExpansion.MODID, "textures/entity/slabfish/slabfish_" + slabby.getSlabfishType().getName() + ".png");
	}
	
	@Override
	protected RenderType func_230042_a_(SlabfishEntity slabfish, boolean p_230042_2_, boolean p_230042_3_) {
		if (slabfish.getSlabfishType() == SlabfishType.GHOST) {
			return RenderType.getEntityTranslucent(this.getEntityTexture(slabfish));
		} else {
			return super.func_230042_a_(slabfish, p_230042_2_, p_230042_3_);
		}
	}
	
	protected float handleRotationFloat(SlabfishEntity livingBase, float partialTicks) {
		float f = MathHelper.lerp(partialTicks, livingBase.oFlap, livingBase.wingRotation);
		float f1 = MathHelper.lerp(partialTicks, livingBase.oFlapSpeed, livingBase.destPos);
		return (MathHelper.sin(f) + 1.0F) * f1;
	}
	
	@Override
	protected void preRenderCallback(SlabfishEntity slabfish, MatrixStack matrixStack, float partialTickTime) {
	    matrixStack.scale(1.0F, 1.0F, 1.0F);
	    if(slabfish.isChild()) matrixStack.scale(0.5F, 0.5F, 0.5F);
	    if(slabfish.isSitting()) matrixStack.translate(0F, 0.3125F, 0F);
	}
}
