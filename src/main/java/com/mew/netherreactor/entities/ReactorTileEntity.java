package com.mew.netherreactor.entities;

import java.util.Random;

import com.mew.netherreactor.GenStructure;
import com.mew.netherreactor.NetherReactor;
import com.mew.netherreactor.events.ReactorStartEvent;
import com.mew.netherreactor.init.ModEntityTypes;
import com.mew.netherreactor.init.ModTileEntitiesInit;
import com.mew.netherreactor.init.blockinit;
import com.mew.netherreactor.util.helpers.NBThelper;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.BlazeEntity;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class ReactorTileEntity extends TileEntity implements ITickableTileEntity {

	public int tick;
	private boolean initialized = false;
	public int mode;
//	public static World worldR;
	public BlockPos posR;
	private static BlockState glo = blockinit.glowing_obsidian.getDefaultState();
	private static BlockState obs = Blocks.OBSIDIAN.getDefaultState();

	public ReactorTileEntity(final TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}

	public ReactorTileEntity() {
		this(ModTileEntitiesInit.REACTOR.get());
	}
	
	@Override
	public void tick() {
		if (!initialized)
			init();
		tick++;
		switch (mode) {
		case 0: {
			if (tick == 40) glow(1, false);
			if (tick == 70) glow(2, false);
			if (tick == 100) glow(3, false);
			if (tick == 160) glow(4, false);
			if (tick == 200) spawnPigmen(3); // pigs + items
			if (tick == 280) spawnPigmen(1); // pigs + items
			if (tick == 460) spawnPigmen(1); // pigs + items
			if (tick == 520) spawnItems(); // items
			if (tick == 620) spawnItems(); // items
			if (tick == 700) spawnItems(); // items
			if (tick == 880) glow(5, false);
			if (tick == 890) glow(6, false);
			if (tick == 900) glow(7, false);
			if (tick >= 940 && tick % 10 == 0) {
				if (!world.isRemote) {
					world.setBlockState(posR, blockinit.nether_reactor_core_off.getDefaultState());
					GenStructure.generateBasicStructureFromCorner(pos.add(-8, -1, -8), world,
							new ResourceLocation(NetherReactor.MOD_ID, "spire_air"), 0.22f);
					NetherReactor.LOGGER.info("Event Ended");
				}
			} break;
		}
		case 1: {
			if (tick == 40) glow(1, true);
			if (tick == 70) glow(2, true);
			if (tick == 100) glow(3, true);
			if (tick == 160) glow(4, true);
			if (tick == 200) spawnPigmen(3);
			if (tick == 280) {spawnPigmen(1); spawnItems();}
			if (tick == 460) spawnPigmen(2);
			if (tick == 520) {spawnItems(); spawnItems(); spawnItems();}
			if (tick == 620) spawnBlaze(3, true, false);
			if (tick == 700) spawnPigmen(1);
			if (tick == 880) glow(5, false);
			if (tick == 890) glow(6, false);
			if (tick == 900) glow(7, false);
			if (tick >= 940 && tick % 10 == 0) {
				if (!world.isRemote) {
					world.setBlockState(posR, blockinit.nether_reactor_core_off.getDefaultState());
					GenStructure.generateBasicStructureFromCorner(pos.add(-8, -1, -8), world,
							new ResourceLocation(NetherReactor.MOD_ID, "spire_air"), 0.28f);
					NetherReactor.LOGGER.info("Event Ended");
				}
			} break;
		}
		case 2: {
			if (tick == 20) glow(1, false);
			if (tick == 40) glow(2, false);
			if (tick == 60) glow(3, false);
			if (tick == 100) {glow(4, false); glow(8, false);}
			if (tick == 110) spawnPigmen(3);
			if (tick == 200) spawnBlaze(2, true, false);
			if (tick == 280) spawnPigmen(1);
			if (tick == 460) spawnBlaze(2, true, false);
			if (tick == 520) spawnPigmen(2);
			if (tick == 620) spawnBlaze(3, true, false);
			if (tick == 700) spawnPigmen(1);
			if (tick == 800) spawnBlaze(4, true, true);
			if (tick == 800) spawnBlaze(2, true, false);
			if (tick == 910) glow(5, false);
			if (tick == 920) glow(6, false);
			if (tick == 930) glow(7, false);
			if (tick >= 940 && tick % 10 == 0) {
				if (!world.isRemote) {
					world.setBlockState(posR, blockinit.nether_reactor_core_off.getDefaultState());
					GenStructure.generateBasicStructureFromCorner(pos.add(-8, -1, -8), world,
							new ResourceLocation(NetherReactor.MOD_ID, "spire_air"), 0.22f);
					NetherReactor.LOGGER.info("Event Ended");
				}
			} break;
		}
		}
	}
	
	private void spawnBlaze(int j, boolean boo, boolean wither) {
		spawnItems(boo);
		if(!world.isRemote) {
		int i = 0;
		while (i < j) {
			i++;
			double[] xz = getCord(2, 5.9);
			BlockPos p = posR.add(xz[0], 0, xz[1]);
			double px = p.getX();
			double pz = p.getZ();
			BlazeEntity blaze = new BlazeEntity(EntityType.BLAZE, world);
			if(wither) {WitherSkeletonEntity witherskel = new WitherSkeletonEntity(EntityType.WITHER_SKELETON, world);
			witherskel.setLocationAndAngles(px, p.getY()-1, pz, 0, 0);
			witherskel.setHeldItem(witherskel.getActiveHand(), new ItemStack(Items.STONE_SWORD));
			world.addEntity(witherskel);
			} else {
			blaze.setLocationAndAngles(px, p.getY(), pz, 0, 0);
			world.addEntity(blaze); } }
		}
	}
	
	private void spawnPigmen(int j) {
		spawnItems();
		if(!world.isRemote) {
		int i = 0;
		
		while (i < j) {
			i++;
			double[] xz = getCord(2, 5.9);
			BlockPos p = posR.add(xz[0], -1, xz[1]);
			double px = p.getX();
			double pz = p.getZ();
			HostilePigman zomboi = new HostilePigman(ModEntityTypes.HOSTILE_PIGMAN.get(), world);
			zomboi.setLocationAndAngles(px, p.getY(), pz, 0, 0);
			zomboi.setHeldItem(zomboi.getActiveHand(), new ItemStack(Items.GOLDEN_SWORD));
			world.addEntity(zomboi); }
		}
	}
	
	private void spawnItems() {
		if(!world.isRemote) {
		ItemStack[] stack = {
				new ItemStack(Items.GLOWSTONE_DUST, 8),
				new ItemStack(Items.QUARTZ, 12),
				new ItemStack(Items.RED_MUSHROOM, 3),
				new ItemStack(Items.BROWN_MUSHROOM, 3),
				new ItemStack(Items.MELON_SEEDS, 1),
				new ItemStack(Items.PUMPKIN_SEEDS, 1),
				new ItemStack(Items.CACTUS, 1),
				new ItemStack(Items.SUGAR_CANE, 1),
				new ItemStack(Items.BONE, 2),
				new ItemStack(Items.STRING, 3),
				new ItemStack(Items.SOUL_SAND, 3) };
		for(ItemStack item : stack) {
			int a = item.getCount();
			int count = getRandomInt(a-3, a+3);
			if(count < 0) count = 0;
			item.setCount(count);
			NetherReactor.LOGGER.info("count is: "+count);
		}
		for(ItemStack item : stack) {
			double[] xz = getCord(2, 5.9);
			BlockPos p = posR.add(xz[0], -1, xz[1]);
			double px = p.getX();
			double pz = p.getZ();
			world.addEntity(new ItemEntity(world, px, p.getY(), pz, item));
		}
		}
	}
	
	private void spawnItems(boolean boo) {
		if(!boo) {spawnItems(); return;}
		if(!world.isRemote) {
		ItemStack[] stack = {
				new ItemStack(Items.GLOWSTONE_DUST, 3),
				new ItemStack(Items.QUARTZ, 5),
				new ItemStack(Items.IRON_INGOT, 1),
				new ItemStack(Items.GOLD_INGOT, 1),
				new ItemStack(Items.IRON_NUGGET, 5),
				new ItemStack(Items.GOLD_NUGGET, 5),
				new ItemStack(Items.ENDER_PEARL, -1),
				new ItemStack(Items.GHAST_TEAR, -1),
				new ItemStack(Items.BLAZE_POWDER, 0),
				new ItemStack(Items.SOUL_SAND, 2) };
		for(ItemStack item : stack) {
			int a = item.getCount();
			int count = getRandomInt(a-3, a+3);
			if(count < 0) count = 0;
			item.setCount(count);
		}
		for(ItemStack item : stack) {
			double[] xz = getCord(2, 5.9);
			BlockPos p = posR.add(xz[0], -1, xz[1]);
			double px = p.getX();
			double pz = p.getZ();
			world.addEntity(new ItemEntity(world, px, p.getY(), pz, item));
		}
		}
	}
	
	private void glow(int flag, boolean crystal) {
		if(!world.isRemote) {
			if(crystal) glo = blockinit.crystaline_glowing_obsidian.getDefaultState();
		switch(flag) {
		case 1:
			world.setBlockState(posR.add(0, -1, 0), glo);
			world.setBlockState(posR.add(1, -1, 0), glo);
			world.setBlockState(posR.add(-1, -1, 0), glo);
			world.setBlockState(posR.add(0, -1, 1), glo);
			world.setBlockState(posR.add(0, -1, -1), glo);
			return;
		case 2:
			world.setBlockState(posR.add(1, 0, 1), glo);
			world.setBlockState(posR.add(1, 0, -1), glo);
			world.setBlockState(posR.add(-1, 0, 1), glo);
			world.setBlockState(posR.add(-1, 0, -1), glo);
			return;
		case 3:
			world.setBlockState(posR.add(0, 1, 0), glo);
			world.setBlockState(posR.add(1, 1, 0), glo);
			world.setBlockState(posR.add(-1, 1, 0), glo);
			world.setBlockState(posR.add(0, 1, 1), glo);
			world.setBlockState(posR.add(0, 1, -1), glo);
			return;
		case 4:
			world.setBlockState(posR.add(1, -1, 1), glo);
			world.setBlockState(posR.add(1, -1, -1), glo);
			world.setBlockState(posR.add(-1, -1, 1), glo);
			world.setBlockState(posR.add(-1, -1, -1), glo);
			return;
		case 5:
			fillCube(1, true);
			return;
		case 6:
			fillCube(0, false);
			return;
		case 7:
			fillCube(-1, true);
			return;
		case 8:
			world.setBlockState(posR.add(1, 1, 1), glo);
			world.setBlockState(posR.add(1, 1, -1), glo);
			world.setBlockState(posR.add(-1, 1, 1), glo);
			world.setBlockState(posR.add(-1, 1, -1), glo);
			return;
		} }
	}
	
	private void fillCube(int y, boolean middle) {
		world.setBlockState(posR.add(1, y, 0), obs);
		world.setBlockState(posR.add(-1, y, 0), obs);
		world.setBlockState(posR.add(0, y, 1), obs);
		world.setBlockState(posR.add(0, y, -1), obs);
		world.setBlockState(posR.add(1, y, 1), obs);
		world.setBlockState(posR.add(1, y, -1), obs);
		world.setBlockState(posR.add(-1, y, 1), obs);
		world.setBlockState(posR.add(-1, y, -1), obs);
		if(middle) world.setBlockState(posR.add(0, y, 0), obs);
		
	}

	private void init() {
		initialized = true;
		tick = 0;
		this.mode = ReactorStartEvent.getMode();
		getWorldForReaction();
	}
	
	private void getWorldForReaction() {
		posR = this.pos;
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound.put("initvalues", NBThelper.ToNBT(this));
		return super.write(compound);
	}

	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);
		CompoundNBT initvalues = compound.getCompound("initvalues");
		if (initvalues != null) {
			this.tick = initvalues.getInt("tick");
			initialized = true;
			this.mode = initvalues.getInt("mode");
			getWorldForReaction();
			return;
		}
		init();
	}
	
	private double[] getCord(double min, double max) {
		int t1 = new Random().nextInt()*100;
		int t2 = new Random().nextInt()*100;
		double x = 0;
		double z = 0;
		if(t1 >= 50) {
			if(t2 >= 50) {
				x = getRandomFloat(min+1, max+1);
			} else {
				x = getRandomFloat(-1*max, -1*min);
			}
			z = getRandomFloat(-1*max, max+1);
		} else {
			if(t2 >= 50) {
				z = getRandomFloat(min+1, max+1);
			} else {
				z = getRandomFloat(-1*max, -1*min);
			}
			x = getRandomFloat(-1*max, max+1);
		}
		
		double[] xz = {x,z};
		return xz;
	}
	
	private double getRandomFloat(double min, double max) {
		Random r = new Random();
		double rand = min + r.nextFloat()*(max-min);
		return rand;
	}
	
	private int getRandomInt(int min, int max) {
		Random r = new Random();
		int rand = min + r.nextInt(max-min);
		return rand;
	}

}