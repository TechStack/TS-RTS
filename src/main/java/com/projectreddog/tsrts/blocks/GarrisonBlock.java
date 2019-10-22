package com.projectreddog.tsrts.blocks;

import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class GarrisonBlock extends Block {

	public GarrisonBlock() {
		super(Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(2.0f));
		setRegistryName(Reference.REIGSTRY_NAME_GARRISON_BLOCK);
	}

}
