package com.mew.netherreactor.events;

import com.mew.netherreactor.NetherReactor;
import com.mew.netherreactor.init.blockinit;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = NetherReactor.MOD_ID, bus = Bus.FORGE)
public class CheckReactorEvent {

	@SubscribeEvent
	public static void checkReactorEvent(RightClickBlock event) {
		final BlockPos pos = event.getPos();
		final BlockState block = event.getWorld().getBlockState(pos);
		if (block == blockinit.nether_reactor_core.getDefaultState()) {
			if (event.isCancelable()) event.setCanceled(true);
			if (!event.getWorld().isRemote) {
				if (event.getHand() != Hand.MAIN_HAND) return;
				// CHECK CORE STRUCTURE VALIDITY
				World world = event.getWorld();
				BlockState cob = Blocks.COBBLESTONE.getDefaultState();
				BlockState gol = Blocks.GOLD_BLOCK.getDefaultState();
				BlockState air = Blocks.AIR.getDefaultState();
				int mode = 0; // easy
				if(world.getBlockState(pos.add(0, -1, 0)) == Blocks.OBSIDIAN.getDefaultState()) {
					cob = Blocks.OBSIDIAN.getDefaultState();
					mode = 1; // normal
				} else if(world.getBlockState(pos.add(0, -1, 0)) == Blocks.IRON_BLOCK.getDefaultState()) {
					cob = Blocks.IRON_BLOCK.getDefaultState();
					mode = 2; // hard
				}
				if (world.getBlockState(pos.add(-1, 0, 0)) == air && world.getBlockState(pos.add(1, 0, 0)) == air // air check
						&& world.getBlockState(pos.add(0, 0, -1)) == air && world.getBlockState(pos.add(0, 0, 1)) == air
						&& world.getBlockState(pos.add(-1, 1, -1)) == air && world.getBlockState(pos.add(-1, 1, 1)) == air
						&& world.getBlockState(pos.add(1, 1, -1)) == air && world.getBlockState(pos.add(1, 1, 1)) == air) {	
					if (world.getBlockState(pos.add(-1, 0, -1)) == cob && world.getBlockState(pos.add(1, 0, -1)) == cob // frame check
							&& world.getBlockState(pos.add(-1, 0, 1)) == cob && world.getBlockState(pos.add(1, 0, 1)) == cob
							&& world.getBlockState(pos.add(0, 1, -1)) == cob && world.getBlockState(pos.add(0, 1, 1)) == cob
							&& world.getBlockState(pos.add(1, 1, 0)) == cob && world.getBlockState(pos.add(-1, 1, 0)) == cob && world.getBlockState(pos.add(0, 1, 0)) == cob
							&& world.getBlockState(pos.add(-1, -1, 0)) == cob && world.getBlockState(pos.add(1, -1, 0)) == cob
							&& world.getBlockState(pos.add(0, -1, -1)) == cob && world.getBlockState(pos.add(0, -1, 1)) == cob && world.getBlockState(pos.add(0, -1, 0)) == cob) {
						if (world.getBlockState(pos.add(-1, -1, -1)) == gol && world.getBlockState(pos.add(1, -1, -1)) == gol // gold corner check
								&& world.getBlockState(pos.add(-1, -1, 1)) == gol && world.getBlockState(pos.add(1, -1, 1)) == gol) {
							PlayerEntity player = event.getPlayer();
							ReactorStartEvent.reactorStartEvent(block, event.getWorld(), pos, player, player.getActiveHand(), mode);
						} else event.getPlayer().sendMessage(new TranslationTextComponent("netherreactor.message.invalid_struc"));
					} else event.getPlayer().sendMessage(new TranslationTextComponent("netherreactor.message.invalid_struc"));
				} else event.getPlayer().sendMessage(new TranslationTextComponent("netherreactor.message.invalid_struc"));
			}
		}
	}
}
