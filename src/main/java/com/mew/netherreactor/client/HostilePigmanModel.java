package com.mew.netherreactor.client;

import com.mew.netherreactor.entities.HostilePigman;

import net.minecraft.client.renderer.entity.model.ZombieModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HostilePigmanModel<T extends HostilePigman> extends ZombieModel<T> {
   public HostilePigmanModel(float float1, float float2, int float3, int float4) {
      super(float1, float2, float3, float4);
   }

   public HostilePigmanModel(float float1, boolean boo) {
      super(float1, 0.0F, 64, boo ? 32 : 64);
   }

   public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
      super.setLivingAnimations(entityIn, limbSwing, limbSwingAmount, partialTick);
   }
   
   public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
   }
}