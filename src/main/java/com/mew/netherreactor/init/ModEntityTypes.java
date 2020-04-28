package com.mew.netherreactor.init;

import com.mew.netherreactor.NetherReactor;
import com.mew.netherreactor.entities.HostilePigman;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityTypes {
	
	public static final DeferredRegister<EntityType<?>> ENTTY_TYPES = new DeferredRegister<>(ForgeRegistries.ENTITIES, NetherReactor.MOD_ID);

	public static final RegistryObject<EntityType<HostilePigman>> HOSTILE_PIGMAN = ENTTY_TYPES.register("hostile_pigman",
			() -> EntityType.Builder.<HostilePigman>create(HostilePigman::new, EntityClassification.MONSTER)
				.size(0.6f, 1.95f).immuneToFire().build(new ResourceLocation(NetherReactor.MOD_ID, "hostile_pigman").toString()));

}
