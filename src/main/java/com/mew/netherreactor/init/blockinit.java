package com.mew.netherreactor.init;

import com.mew.netherreactor.NetherReactor;
import com.mew.netherreactor.blocks.ActiveReactorCore;
import com.mew.netherreactor.blocks.ExReactor;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(NetherReactor.MOD_ID)
@Mod.EventBusSubscriber(modid = NetherReactor.MOD_ID, bus = Bus.MOD)
public class blockinit {
	public static final Block glowing_obsidian = null;
	public static final Block crystaline_glowing_obsidian = null;
	public static final Block nether_reactor_core_deco = null;
	public static final Block nether_reactor_core_active_deco = null;
	public static final Block nether_reactor_core_off_deco = null;
	
	public static final Block nether_reactor_core = null;
	public static final Block nether_reactor_core_active = null;
	public static final Block nether_reactor_core_off = null;
	
	public static final Block false_obs = null; // used to prevent portals from forming in the main room of the obsidian spire.
	public static final Block ex_reactor = null; // example reactor structure block
	
	@SubscribeEvent
	public static void registerBlocks(final RegistryEvent.Register<Block> event) {
		event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK, MaterialColor.RED).hardnessAndResistance(50.0F, 1200.0F).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE).lightValue(15)).setRegistryName("glowing_obsidian"));
		event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK, MaterialColor.RED).hardnessAndResistance(50.0F, 1200.0F).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE).lightValue(15)).setRegistryName("crystaline_glowing_obsidian"));
		event.getRegistry().register(new Block(Block.Properties.create(Material.IRON, MaterialColor.BLUE).hardnessAndResistance(5.0F, 6.0F).harvestTool(ToolType.PICKAXE).sound(SoundType.METAL)).setRegistryName("nether_reactor_core_deco"));
		event.getRegistry().register(new Block(Block.Properties.create(Material.IRON, MaterialColor.RED).hardnessAndResistance(5.0F, 6.0F).harvestTool(ToolType.PICKAXE).sound(SoundType.METAL).lightValue(7)).setRegistryName("nether_reactor_core_active_deco"));
		event.getRegistry().register(new Block(Block.Properties.create(Material.IRON, MaterialColor.BLUE).hardnessAndResistance(5.0F, 6.0F).harvestTool(ToolType.PICKAXE).sound(SoundType.METAL)).setRegistryName("nether_reactor_core_off_deco"));

		event.getRegistry().register(new Block(Block.Properties.create(Material.IRON, MaterialColor.BLUE).hardnessAndResistance(5.0F, 10.0F).harvestTool(ToolType.PICKAXE).sound(SoundType.METAL)).setRegistryName("nether_reactor_core"));
		event.getRegistry().register(new ActiveReactorCore(Block.Properties.create(Material.IRON).hardnessAndResistance(6.0F, 10.0F).harvestTool(ToolType.PICKAXE).sound(SoundType.METAL).lightValue(14)).setRegistryName("nether_reactor_core_active"));
		event.getRegistry().register(new Block(Block.Properties.create(Material.IRON, MaterialColor.BLACK).hardnessAndResistance(5.0F, 10.0F).harvestTool(ToolType.PICKAXE).sound(SoundType.METAL)).setRegistryName("nether_reactor_core_off"));
		
		event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK, MaterialColor.BLACK).hardnessAndResistance(50.0F, 1200.0F).harvestTool(ToolType.PICKAXE)).setRegistryName("false_obs"));
		event.getRegistry().register(new ExReactor(Block.Properties.create(Material.ROCK, MaterialColor.GRAY).hardnessAndResistance(4.0F, 8.0F).harvestTool(ToolType.PICKAXE).notSolid()).setRegistryName("ex_reactor"));
	}
	
	@SubscribeEvent
	public static void registerBlockItems(final RegistryEvent.Register<Item> event) {
		event.getRegistry().register(new BlockItem(glowing_obsidian, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName("glowing_obsidian"));
		event.getRegistry().register(new BlockItem(crystaline_glowing_obsidian, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName("crystaline_glowing_obsidian"));
		event.getRegistry().register(new BlockItem(nether_reactor_core_deco, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName("nether_reactor_core_deco"));
		event.getRegistry().register(new BlockItem(nether_reactor_core_active_deco, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName("nether_reactor_core_active_deco"));
		event.getRegistry().register(new BlockItem(nether_reactor_core_off_deco, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName("nether_reactor_core_off_deco"));
				
		event.getRegistry().register(new BlockItem(nether_reactor_core, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("nether_reactor_core"));
		event.getRegistry().register(new BlockItem(nether_reactor_core_active, new Item.Properties()).setRegistryName("nether_reactor_core_active"));
		event.getRegistry().register(new BlockItem(nether_reactor_core_off, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("nether_reactor_core_off"));
		
		event.getRegistry().register(new BlockItem(false_obs, new Item.Properties()).setRegistryName("false_obs"));
		event.getRegistry().register(new BlockItem(ex_reactor, new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName("ex_reactor"));
	}
	
}
