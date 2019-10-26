package com.projectreddog.tsrts.blocks;

import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class ArcheryRangeBlock extends OwnedBlock {

	public ArcheryRangeBlock() {
		super(Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(2.0f));
		setRegistryName(Reference.REIGSTRY_NAME_ARCHERY_RANGE_BLOCK);
	}

}
