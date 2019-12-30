package com.projectreddog.tsrts.items.armor;

import com.projectreddog.tsrts.init.ModItemGroups;
import com.projectreddog.tsrts.init.ModItems;
import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.Item;

public class TeamIronArmor extends DyeableArmorItem {

	public TeamIronArmor(EquipmentSlotType slot) {
		super(ModItems.TEAM_IRON, slot, new Item.Properties().group(ModItemGroups.weaponsItemGroup));
		switch (slot) {
		case CHEST:
			setRegistryName(Reference.REIGSTRY_NAME_TEAM_IRON_ARMOR_CHESTPLATE);

			break;
		case FEET:
			setRegistryName(Reference.REIGSTRY_NAME_TEAM_IRON_ARMOR_BOOTS);
			break;
		case HEAD:
			setRegistryName(Reference.REIGSTRY_NAME_TEAM_IRON_ARMOR_HELMET);
			break;
		case LEGS:
			setRegistryName(Reference.REIGSTRY_NAME_TEAM_IRON_ARMOR_LEGGINGS);
			break;
		default:
			break;

		}
	}

}
