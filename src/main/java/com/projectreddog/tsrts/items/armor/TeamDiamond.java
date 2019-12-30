package com.projectreddog.tsrts.items.armor;

import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;

public class TeamDiamond implements IArmorMaterial {

	@Override
	public int getDurability(EquipmentSlotType slotIn) {

		return ArmorMaterial.DIAMOND.getDurability(slotIn);
	}

	@Override
	public int getDamageReductionAmount(EquipmentSlotType slotIn) {
		return ArmorMaterial.DIAMOND.getDamageReductionAmount(slotIn);
	}

	@Override
	public int getEnchantability() {
		return ArmorMaterial.DIAMOND.getEnchantability();
	}

	@Override
	public SoundEvent getSoundEvent() {
		return ArmorMaterial.DIAMOND.getSoundEvent();
	}

	@Override
	public Ingredient getRepairMaterial() {
		return ArmorMaterial.DIAMOND.getRepairMaterial();
	}

	@Override
	public String getName() {
		return Reference.MODID + ":" + ArmorMaterial.DIAMOND.getName();
	}

	@Override
	public float getToughness() {
		return ArmorMaterial.DIAMOND.getToughness();
	}

}
