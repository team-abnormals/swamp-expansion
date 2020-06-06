package com.farcr.swampexpansion.client.model;

import com.farcr.swampexpansion.common.entity.SlabfishEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.teamabnormals.abnormals_core.core.library.endimator.EndimatorEntityModel;
import com.teamabnormals.abnormals_core.core.library.endimator.EndimatorModelRenderer;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelSlabfish - Undefined
 * Created using Tabula 7.1.0
 * @param <E>
 */
public class SlabfishModel<E extends SlabfishEntity> extends EndimatorEntityModel<E> {
	Entity entity;
    public EndimatorModelRenderer body;
    public EndimatorModelRenderer  rightLeg;
    public EndimatorModelRenderer  leftLeg;
    public EndimatorModelRenderer  rightArm;
    public EndimatorModelRenderer  leftArm;
    public EndimatorModelRenderer  fin;
    public EndimatorModelRenderer  backpack;
    
    public EndimatorModelRenderer  bodySwimming;
    public EndimatorModelRenderer  backpackSwimming;
    public EndimatorModelRenderer  leftLegSwimming;
    public EndimatorModelRenderer  rightLegSwimming;
    public EndimatorModelRenderer  finSwimming;
    public EndimatorModelRenderer  leftArmSwimming;
    public EndimatorModelRenderer  rightArmSwimming;

    public SlabfishModel() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.leftArm = new EndimatorModelRenderer (this, 16, 14);
        this.leftArm.mirror = true;
        this.leftArm.setRotationPoint(5.0F, -4.0F, 0.0F);
        this.leftArm.addBox(0.0F, 0.0F, -1.5F, 1, 3, 3, 0.0F);
        this.setRotateAngle(leftArm, 0.0F, 0.0F, -0.4363323129985824F);
        this.body = new EndimatorModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 19.0F, 0.0F);
        this.body.addBox(-5.0F, -10.0F, -2.0F, 10, 10, 4, 0.0F);
        this.rightLeg = new EndimatorModelRenderer (this, 0, 14);
        this.rightLeg.setRotationPoint(-2.5F, 0.0F, 1.0F);
        this.rightLeg.addBox(-1.5F, 0.0F, -3.0F, 3, 5, 3, 0.0F);
        this.rightArm = new EndimatorModelRenderer (this, 16, 14);
        this.rightArm.setRotationPoint(-5.0F, -4.0F, 0.0F);
        this.rightArm.addBox(-1.0F, 0.0F, -1.5F, 1, 3, 3, 0.0F);
        this.setRotateAngle(rightArm, 0.0F, 0.0F, 0.4363323129985824F);
        this.fin = new EndimatorModelRenderer (this, 24, 12);
        this.fin.setRotationPoint(0.0F, -4.0F, 2.0F);
        this.fin.addBox(0.0F, -1.0F, 0.0F, 0, 4, 4, 0.0F);
        this.setRotateAngle(fin, -0.2181661564992912F, 0.0F, 0.0F);
        this.backpack = new EndimatorModelRenderer (this, 8, 20);
        this.backpack.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.backpack.addBox(-4.0F, -8.0F, 2.0F, 8, 8, 4, 0.0F);
        this.leftLeg = new EndimatorModelRenderer (this, 0, 14);
        this.leftLeg.mirror = true;
        this.leftLeg.setRotationPoint(2.5F, 0.0F, 1.0F);
        this.leftLeg.addBox(-1.5F, 0.0F, -3.0F, 3, 5, 3, 0.0F);
        this.body.addChild(this.leftArm);
        this.body.addChild(this.rightLeg);
        this.body.addChild(this.rightArm);
        this.body.addChild(this.fin);
        this.body.addChild(this.backpack);
        this.body.addChild(this.leftLeg);
        
        this.finSwimming = new EndimatorModelRenderer (this, 24, 12);
        this.finSwimming.setRotationPoint(0.0F, 0.0F, 2.0F);
        this.finSwimming.addBox(0.0F, -1.0F, 0.0F, 0, 4, 4, 0.0F);
        this.setRotateAngle(finSwimming, -1.5707963267948966F, 0.0F, 0.0F);
        this.rightArmSwimming = new EndimatorModelRenderer (this, 16, 14);
        this.rightArmSwimming.setRotationPoint(-5.0F, -4.0F, 0.0F);
        this.rightArmSwimming.addBox(-1.0F, 0.0F, -1.5F, 1, 3, 3, 0.0F);
        this.backpackSwimming = new EndimatorModelRenderer (this, 8, 20);
        this.backpackSwimming.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.backpackSwimming.addBox(-4.0F, -8.0F, 2.0F, 8, 8, 4, 0.0F);
        this.rightLegSwimming = new EndimatorModelRenderer (this, 0, 14);
        this.rightLegSwimming.mirror = true;
        this.rightLegSwimming.setRotationPoint(-2.5F, 0.0F, 1.0F);
        this.rightLegSwimming.addBox(-1.5F, 0.0F, -3.0F, 3, 5, 3, 0.0F);
        this.leftLegSwimming = new EndimatorModelRenderer (this, 0, 14);
        this.leftLegSwimming.setRotationPoint(2.5F, 0.0F, 1.0F);
        this.leftLegSwimming.addBox(-1.5F, 0.0F, -3.0F, 3, 5, 3, 0.0F);
        this.bodySwimming = new EndimatorModelRenderer (this, 0, 0);
        this.bodySwimming.setRotationPoint(0.0F, 22.0F, 2.0F);
        this.bodySwimming.addBox(-5.0F, -10.0F, -2.0F, 10, 10, 4, 0.0F);
        this.setRotateAngle(bodySwimming, 1.5707963267948966F, 0.0F, 0.0F);
        this.leftArmSwimming = new EndimatorModelRenderer (this, 16, 14);
        this.leftArmSwimming.mirror = true;
        this.leftArmSwimming.setRotationPoint(5.0F, -4.0F, 0.0F);
        this.leftArmSwimming.addBox(0.0F, 0.0F, -1.5F, 1, 3, 3, 0.0F);
        this.bodySwimming.addChild(this.finSwimming);
        this.bodySwimming.addChild(this.rightArmSwimming);
        this.bodySwimming.addChild(this.backpackSwimming);
        this.bodySwimming.addChild(this.rightLegSwimming);
        this.bodySwimming.addChild(this.leftLegSwimming);
        this.bodySwimming.addChild(this.leftArmSwimming);
        
        this.body.setName("body");
        
        this.setDefaultBoxValues();
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
    	super.render(matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        if (this.entity.isInWater()) {
            this.bodySwimming.render(matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        } else {
        	this.body.render(matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    
    @Override
    public void setRotationAngles(E entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    	super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    	this.entity = entityIn;
    	
        this.rightLeg.rotateAngleX = entityIn.isSitting() ? -1.57F :  MathHelper.cos(limbSwing * 0.6662F) * 1.5F * limbSwingAmount;
        this.leftLeg.rotateAngleX = entityIn.isSitting() ? -1.57F :  MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.5F * limbSwingAmount;
        this.rightArm.rotateAngleZ = ageInTicks;
        this.leftArm.rotateAngleZ = -ageInTicks;
        
        this.rightLegSwimming.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.7F * limbSwingAmount;
        this.leftLegSwimming.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.7F * limbSwingAmount;
        this.rightArmSwimming.rotateAngleZ = ageInTicks;
        this.leftArmSwimming.rotateAngleZ = -ageInTicks;
    }
    
    @Override
    public void animateModel(E exampleEntity) {
    	super.animateModel(exampleEntity);
    	
    	if(this.tryToPlayEndimation(SlabfishEntity.DANCE)) {
    		SlabfishEntity.DANCE.processInstructions(this);
    	}
    }
}
