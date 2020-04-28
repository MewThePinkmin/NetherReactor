package com.mew.netherreactor.init;

import com.mew.netherreactor.NetherReactor;
import com.mew.netherreactor.entities.ReactorTileEntity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntitiesInit {
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, NetherReactor.MOD_ID);
	public static final RegistryObject<TileEntityType<ReactorTileEntity>> REACTOR = TILE_ENTITY_TYPES.register("reactor", () -> TileEntityType.Builder.create(ReactorTileEntity::new, blockinit.nether_reactor_core_active).build(null));
}
