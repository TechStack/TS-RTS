package com.projectreddog.tsrts.items;

import java.util.Iterator;
import java.util.List;

import com.projectreddog.tsrts.entities.UnitEntity;
import com.projectreddog.tsrts.init.ModItemGroups;
import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.AxisAlignedBB;

public class SampleItem extends Item {

	public SampleItem() {
		super(new Item.Properties().group(ModItemGroups.weaponsItemGroup));
		setRegistryName(Reference.REIGSTRY_NAME_SAMPLE_ITEM);
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		if (!context.getPlayer().world.isRemote) {
//TODO need to consider if the player is on the same team as the entity or not !
			List<UnitEntity> unitentities = context.getPlayer().world.getEntitiesWithinAABB(UnitEntity.class, new AxisAlignedBB(context.getPlayer().getPosition()).grow(256));
			for (Iterator iterator = unitentities.iterator(); iterator.hasNext();) {
				UnitEntity ue = (UnitEntity) iterator.next();
				if (ue.isSelected) {
					ue.owerControlledDestination = context.getPos();
				}
			}
		}

		return super.onItemUse(context);
	}

}
