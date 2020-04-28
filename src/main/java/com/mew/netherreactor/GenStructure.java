package com.mew.netherreactor;

import java.util.Random;

import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ResourceLocationException;
import net.minecraft.util.Rotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.template.IntegrityProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.server.ServerWorld;

public class GenStructure {
	   
	public static void generateBasicStructureFromCorner(BlockPos center, World world, ResourceLocation name) {generateBasicStructureFromCorner(center, world, name, 1);}
	
	public static void generateBasicStructureFromCorner(BlockPos center, World world, ResourceLocation name, float integrity) {
		// from north-west corner
		load(false, name, world, integrity, center);
		unloadStructure(name, world);
	}
	
	private static Random randSeed(long randlong) {
	      return randlong == 0L ? new Random(Util.milliTime()) : new Random(randlong);
	   }
	
	public static boolean load(boolean requireMatchingSize, ResourceLocation name, World world, float integrity, BlockPos center) {
	      if (!world.isRemote &&  name != null) {
	         ServerWorld serverworld = (ServerWorld)world;
	         TemplateManager templatemanager = serverworld.getStructureTemplateManager();
	         Template template;
	         try {
	            template = templatemanager.getTemplate(name);
	         } catch (ResourceLocationException var6) {
	            return false;
	         }
	         return template == null ? false : setTheBlocks(template, world, integrity, center);
	      } else {
	         return false;
	      }
	   }
	
	public static boolean setTheBlocks(Template template, World world, float integrity, BlockPos center) {
	         PlacementSettings placementsettings = (new PlacementSettings()).setMirror(Mirror.NONE).setRotation(Rotation.NONE).setIgnoreEntities(false).setChunk((ChunkPos)null);
	         if (integrity < 1.0F) {
	            placementsettings.clearProcessors().addProcessor(new IntegrityProcessor(MathHelper.clamp(integrity, 0.0F, 1.0F))).setRandom(randSeed(new Random().nextLong()));
	         }
	         template.addBlocksToWorldChunk(world, center, placementsettings);
	         return true;
	   }

	   public static void unloadStructure(ResourceLocation name, World world) {
	      if (name != null) {
	         ServerWorld serverworld = (ServerWorld)world;
	         TemplateManager templatemanager = serverworld.getStructureTemplateManager();
	         templatemanager.remove(name);
	      }
	   }
}
