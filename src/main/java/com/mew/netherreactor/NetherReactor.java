package com.mew.netherreactor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mew.netherreactor.init.ModEntityTypes;
import com.mew.netherreactor.init.ModTileEntitiesInit;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("netherreactor")
@Mod.EventBusSubscriber(modid = NetherReactor.MOD_ID, bus = Bus.MOD)
public class NetherReactor
{
    public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "netherreactor";
	public static NetherReactor instance;
	
	

    public NetherReactor() {
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.addListener(this::setup);
		
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        MinecraftForge.EVENT_BUS.register(this);
        
 //       ModTileEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);
        ModEntityTypes.ENTTY_TYPES.register(modEventBus);
        ModTileEntitiesInit.TILE_ENTITY_TYPES.register(modEventBus);
        
        instance = this;
		MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
    	
    }
    
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }

}
