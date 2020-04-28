package com.mew.netherreactor.blocks;

import com.mew.netherreactor.init.ModTileEntitiesInit;

import net.minecraft.block.BlockState;
import net.minecraft.block.StructureBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class ActiveReactorCore extends StructureBlock {
	
	public ActiveReactorCore(Properties properties) {
		super(properties);
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world	) {
		return ModTileEntitiesInit.REACTOR.get().create();
	}
	
}
