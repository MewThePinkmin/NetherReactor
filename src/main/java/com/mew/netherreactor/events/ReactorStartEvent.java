package com.mew.netherreactor.events;

import com.mew.netherreactor.GenStructure;
import com.mew.netherreactor.NetherReactor;
import com.mew.netherreactor.init.blockinit;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class ReactorStartEvent {
	public static int mode;
	public static void reactorStartEvent(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, int modeIn) {
		setMode(modeIn);
		DimensionType dim = world.getDimension().getType();
		if (dim != DimensionType.THE_NETHER && dim != DimensionType.THE_END) {
			if (!world.isRemote) {
				if (pos.getY() >= 5 && pos.getY() <= 225) {
					if(pos.getY() <= player.getPosition().getY() + 1 ) {
					NetherReactor.LOGGER.info("Nether Reactor Event Starting in mode " + modeIn);
					player.sendMessage(new TranslationTextComponent("netherreactor.message.start"));
					switch(modeIn) {
					case 0: {
						ResourceLocation structure = new ResourceLocation(NetherReactor.MOD_ID, "spire_netherrack");
						GenStructure.generateBasicStructureFromCorner(pos.add(-8, -1, -8), world, structure);
						world.setBlockState(pos, blockinit.nether_reactor_core_active.getDefaultState());
						ResourceLocation floor = new ResourceLocation(NetherReactor.MOD_ID, "spire_floor_netherrack");
						GenStructure.generateBasicStructureFromCorner(pos.add(-8, -3, -8), world, floor);
						break;
					}
					case 1: {
						ResourceLocation structure = new ResourceLocation(NetherReactor.MOD_ID, "spire_obs");
						GenStructure.generateBasicStructureFromCorner(pos.add(-8, -1, -8), world, structure);
						world.setBlockState(pos, blockinit.nether_reactor_core_active.getDefaultState());
						ResourceLocation floor = new ResourceLocation(NetherReactor.MOD_ID, "spire_floor_obs");
						GenStructure.generateBasicStructureFromCorner(pos.add(-8, -3, -8), world, floor);
						break;
					}
					case 2: {
						ResourceLocation structure = new ResourceLocation(NetherReactor.MOD_ID, "spire_obs");
						GenStructure.generateBasicStructureFromCorner(pos.add(-8, -1, -8), world, structure);
						ResourceLocation structure2 = new ResourceLocation(NetherReactor.MOD_ID, "spire_netherrack");
						GenStructure.generateBasicStructureFromCorner(pos.add(-8, -1, -8), world, structure2, 0.4f);
						world.setBlockState(pos, blockinit.nether_reactor_core_active.getDefaultState());
						ResourceLocation floor = new ResourceLocation(NetherReactor.MOD_ID, "spire_floor_iron");
						GenStructure.generateBasicStructureFromCorner(pos.add(-8, -3, -8), world, floor);
						break;
					}
					}
					world.setDayTime(13500); } else player.sendMessage(new TranslationTextComponent("netherreactor.message.invalid_player")); 
				} else player.sendMessage(new TranslationTextComponent("netherreactor.message.invalid_y"));
			}
		} else player.sendMessage(new TranslationTextComponent("netherreactor.message.invalid_dim"));
	}
	
	private static void setMode(int modeIn) {
		mode = modeIn;
	}
	
	public static int getMode() {
		return mode;
	}
}
