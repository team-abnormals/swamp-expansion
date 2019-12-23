package com.farcr.swampexpansion.client.model;

import com.farcr.swampexpansion.common.entity.WillowBoatEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WillowBoatModel extends EntityModel<WillowBoatEntity> {
    private final RendererModel[] field_78103_a = new RendererModel[5];
    private final RendererModel[] paddles = new RendererModel[2];
    private final RendererModel noWater;

    public WillowBoatModel() {
        field_78103_a[0] = (new RendererModel(this, 0, 0)).setTextureSize(128, 64);
        field_78103_a[1] = (new RendererModel(this, 0, 19)).setTextureSize(128, 64);
        field_78103_a[2] = (new RendererModel(this, 0, 27)).setTextureSize(128, 64);
        field_78103_a[3] = (new RendererModel(this, 0, 35)).setTextureSize(128, 64);
        field_78103_a[4] = (new RendererModel(this, 0, 43)).setTextureSize(128, 64);
        field_78103_a[0].addBox(-14.0F, -9.0F, -3.0F, 28, 16, 3, 0.0F);
        field_78103_a[0].setRotationPoint(0.0F, 3.0F, 1.0F);
        field_78103_a[1].addBox(-13.0F, -7.0F, -1.0F, 18, 6, 2, 0.0F);
        field_78103_a[1].setRotationPoint(-15.0F, 4.0F, 4.0F);
        field_78103_a[2].addBox(-8.0F, -7.0F, -1.0F, 16, 6, 2, 0.0F);
        field_78103_a[2].setRotationPoint(15.0F, 4.0F, 0.0F);
        field_78103_a[3].addBox(-14.0F, -7.0F, -1.0F, 28, 6, 2, 0.0F);
        field_78103_a[3].setRotationPoint(0.0F, 4.0F, -9.0F);
        field_78103_a[4].addBox(-14.0F, -7.0F, -1.0F, 28, 6, 2, 0.0F);
        field_78103_a[4].setRotationPoint(0.0F, 4.0F, 9.0F);
        field_78103_a[0].rotateAngleX = ((float)Math.PI / 2F);
        field_78103_a[1].rotateAngleY = ((float)Math.PI * 1.5F);
        field_78103_a[2].rotateAngleY = ((float)Math.PI / 2F);
        field_78103_a[3].rotateAngleY = (float)Math.PI;
        paddles[0] = this.makePaddle(true);
        paddles[0].setRotationPoint(3.0F, -5.0F, 9.0F);
        paddles[1] = this.makePaddle(false);
        paddles[1].setRotationPoint(3.0F, -5.0F, -9.0F);
        paddles[1].rotateAngleY = (float)Math.PI;
        paddles[0].rotateAngleZ = 0.19634955F;
        paddles[1].rotateAngleZ = 0.19634955F;
        noWater = (new RendererModel(this, 0, 0)).setTextureSize(128, 64);
        noWater.addBox(-14.0F, -9.0F, -3.0F, 28, 16, 3, 0.0F);
        noWater.setRotationPoint(0.0F, -3.0F, 1.0F);
        noWater.rotateAngleX = ((float)Math.PI / 2F);
    }

    public void render(WillowBoatEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        GlStateManager.rotatef(90.0F, 0.0F, 1.0F, 0.0F);
        setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        for(int i = 0; i < 5; ++i) {
            field_78103_a[i].render(scale);
        }
        renderPaddle(entityIn, 0, scale, limbSwing);
        renderPaddle(entityIn, 1, scale, limbSwing);
    }

    public void renderMultipass(Entity entityIn, float partialTicks, float p_187054_3_, float p_187054_4_, float p_187054_5_, float p_187054_6_, float scale) {
        GlStateManager.rotatef(90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.colorMask(false, false, false, false);
        noWater.render(scale);
        GlStateManager.colorMask(true, true, true, true);
    }

    protected RendererModel makePaddle(boolean p_187056_1_) {
        RendererModel renderermodel = (new RendererModel(this, 62, p_187056_1_ ? 0 : 20)).setTextureSize(128, 64);
        renderermodel.addBox(-1.0F, 0.0F, -5.0F, 2, 2, 18);
        renderermodel.addBox(p_187056_1_ ? -1.001F : 0.001F, -3.0F, 8.0F, 1, 6, 7);
        return renderermodel;
    }

    protected void renderPaddle(WillowBoatEntity boat, int paddle, float scale, float limbSwing) {
        float f = boat.getRowingTime(paddle, limbSwing);
        RendererModel renderermodel = paddles[paddle];
        renderermodel.rotateAngleX = (float) MathHelper.clampedLerp((double)(-(float)Math.PI / 3F), (double)-0.2617994F, (double)((MathHelper.sin(-f) + 1.0F) / 2.0F));
        renderermodel.rotateAngleY = (float) MathHelper.clampedLerp((double)(-(float)Math.PI / 4F), (double)((float)Math.PI / 4F), (double)((MathHelper.sin(-f + 1.0F) + 1.0F) / 2.0F));
        if (paddle == 1) {
            renderermodel.rotateAngleY = (float)Math.PI - renderermodel.rotateAngleY;
        }
        renderermodel.render(scale);
    }
}