package com.projectreddog.tsrts.items.armor;

import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;

public class TeamIron implements IArmorMaterial {

	@Override
	public int getDurability(EquipmentSlotType slotIn) {

		return ArmorMaterial.IRON.getDurability(slotIn);
	}

	@Override
	public int getDamageReductionAmount(EquipmentSlotType slotIn) {
		return ArmorMaterial.IRON.getDamageReductionAmount(slotIn);
	}

	@Override
	public int getEnchantability() {
		return ArmorMaterial.IRON.getEnchantability();
	}

	@Override
	public SoundEvent getSoundEvent() {
		return ArmorMaterial.IRON.getSoundEvent();
	}

	@Override
	public Ingredient getRepairMaterial() {
		return ArmorMaterial.IRON.getRepairMaterial();
	}

	@Override
	public String getName() {
		return Reference.MODID + ":" + ArmorMaterial.IRON.getName();
	}

	@Override
	public float getToughness() {
		return ArmorMaterial.IRON.getToughness();
	}

}
