package com.mew.netherreactor.init;

import com.mew.netherreactor.NetherReactor;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = NetherReactor.MOD_ID, bus = Bus.MOD)
@ObjectHolder(NetherReactor.MOD_ID)
public class iteminit {
//	Object holder method
	public static final Item ad_item = null;
	
	@SubscribeEvent
	public static void regiterItems(final RegistryEvent.Register<Item> event) {
		event.getRegistry().register(new Item(new Item.Properties().maxStackSize(1)).setRegistryName("ad_item"));
	}
	
}
