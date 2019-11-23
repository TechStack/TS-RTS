package com.projectreddog.tsrts.items;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.init.ModItemGroups;
import com.projectreddog.tsrts.init.ModNetwork;
import com.projectreddog.tsrts.network.TESetRallyPointToServer;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.tileentity.OwnedCooldownTileEntity;

import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;

public class RallyPointToolItem extends Item {

	public RallyPointToolItem() {
		super(new Item.Properties().group(ModItemGroups.weaponsItemGroup));
		setRegistryName(Reference.REIGSTRY_NAME_RALLY_POINT_TOOL);
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {

		if (context.getPlayer().world.isRemote) {
			TileEntity te = context.getWorld().getTileEntity(context.getPos());

			if (te != null) {
				if (te instanceof OwnedCooldownTileEntity) {
					OwnedCooldownTileEntity octe = (OwnedCooldownTileEntity) te;
					TSRTS.RallyPointToolFrom = context.getPos();
				}
			} else {

				if (TSRTS.RallyPointToolFrom != null) {
					TSRTS.RallyPointToolTo = context.getPos().up();
				}
			}

			// Check if both are not null and set then send it to the server and clear them out for next rally point to be set.

			if (TSRTS.RallyPointToolTo != null && TSRTS.RallyPointToolFrom != null) {
				ModNetwork.SendToServer(new TESetRallyPointToServer(TSRTS.RallyPointToolFrom.getX(), TSRTS.RallyPointToolFrom.getY(), TSRTS.RallyPointToolFrom.getZ(), TSRTS.RallyPointToolTo.getX(), TSRTS.RallyPointToolTo.getY(), TSRTS.RallyPointToolTo.getZ()));
				TSRTS.RallyPointToolTo = null;
				TSRTS.RallyPointToolFrom = null;
			}

		}

		return super.onItemUse(context);
	}

}
