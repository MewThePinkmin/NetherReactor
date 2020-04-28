package com.mew.netherreactor.client;

import com.mew.netherreactor.NetherReactor;
import com.mew.netherreactor.entities.HostilePigman;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HostilePigmanRender extends MobRenderer<HostilePigman, HostilePigmanModel<HostilePigman>> {
	protected static final ResourceLocation TEXTURE = new ResourceLocation(NetherReactor.MOD_ID, "textures/entity/hostile_zp.png");

	public HostilePigmanRender(EntityRendererManager renderManagerIn) { // shadow size
		super(renderManagerIn, new HostilePigmanModel<HostilePigman>(0f, false), 0.5f);
	      this.addLayer(new HeldItemLayer<>(this));
	      this.addLayer(new BipedArmorLayer<>(this, new HostilePigmanModel<HostilePigman>(0.5F, true), new HostilePigmanModel<HostilePigman>(1.0F, true)));
	}

	@Override
	public ResourceLocation getEntityTexture(HostilePigman entity) {
		return TEXTURE;
	}
}
