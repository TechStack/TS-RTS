package com.projectreddog.tsrts.items;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.entities.UnitEntity;
import com.projectreddog.tsrts.init.ModItemGroups;
import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SampleItem extends Item {

	public SampleItem() {
		super(new Item.Properties().group(ModItemGroups.weaponsItemGroup));
		setRegistryName(Reference.REIGSTRY_NAME_SAMPLE_ITEM);
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		TSRTS.LOGGER.info("CLICKED of:" + context.getPos().up());
		int count = 0;
		if (!context.getPlayer().world.isRemote) {
//TODO need to consider if the player is on the same team as the entity or not !
			List<UnitEntity> unitentities = context.getPlayer().world.getEntitiesWithinAABB(UnitEntity.class, new AxisAlignedBB(context.getPlayer().getPosition()).grow(256));

			for (Iterator iterator = unitentities.iterator(); iterator.hasNext();) {
				UnitEntity ue = (UnitEntity) iterator.next();
				if (ue.isSelected) {
					// ue.owerControlledDestination = context.getPos();
					count++;
				}
			}

			List<BlockPos> lbp = SetFormationPoint(context.getWorld(), context.getPos().up(), count, Direction.getFacingFromVector(context.getPlayer().getLookVec().getX(), 0, context.getPlayer().getLookVec().getZ()));
			// unitentities = context.getPlayer().world.getEntitiesWithinAABB(UnitEntity.class, new AxisAlignedBB(context.getPlayer().getPosition()).grow(256));

			if (lbp != null && lbp.size() > 0) {
				int i = 0;
				for (Iterator iterator = unitentities.iterator(); iterator.hasNext();) {
					UnitEntity ue = (UnitEntity) iterator.next();
					if (ue.isSelected) {
						ue.ownerControlledDestination = lbp.get(i);/// context.getPos();
						TSRTS.LOGGER.info("Destination set to:" + ue.ownerControlledDestination);

						i++;
					}
				}
			}

		}

		return super.onItemUse(context);
	}

	public List<BlockPos> SetFormationPoint(World world, BlockPos bp, int size, Direction direction) {
		List<BlockPos> lbp = new ArrayList<BlockPos>();
		TSRTS.LOGGER.info("size = :" + size);
		if (size == 0) {
			return null;
		}
		boolean isEven = ((size % 2) == 0);

		int row = 0;
		int col = 0;
		int width = 0;
		int depth = 0;
		if (!isEven) {
			// ODD
			if (size <= 9) {
				width = size;
			} else {
				width = 9;
			}

		} else {
			// even
			if (size <= 9) {
				width = size - 1;
			} else {
				width = 9;
			}
		}

		if (width >= 9) {
			int tmpDepth = (size / 9);
			int tmpMod = (size % 9);
			if (tmpMod > 0) {
				depth = tmpDepth + 1;
			} else {
				depth = tmpDepth;
			}
		} else {
			depth = 1;
		}

		int widthOffset = 0;

		widthOffset = (width - 1) / 2;
		for (int j = 0; j < depth; j++) {
			for (int i = 0; i < width; i++) {

				if ((j + 1) == depth) {
					// last row
					int remain = (size - (width * j));

					widthOffset = (remain - 1) / 2;

					if (i < remain) {

						lbp.add(bp.offset(direction.rotateY(), (-widthOffset + i)).offset(direction.getOpposite(), j));
						SetFormationPoint(world, bp.offset(direction.rotateY(), (-widthOffset + i)).offset(direction.getOpposite(), j));
					}

				} else {

					lbp.add(bp.offset(direction.rotateY(), (-widthOffset + i)).offset(direction.getOpposite(), j));
					SetFormationPoint(world, bp.offset(direction.rotateY(), (-widthOffset + i)).offset(direction.getOpposite(), j));
				}

			}
		}

		if (isEven && size <= 9) {

			lbp.add(bp.offset(direction.getOpposite(), (depth - 1) + 1));
			SetFormationPoint(world, bp.offset(direction.getOpposite(), (depth - 1) + 1));
		}
		return lbp;
	}

	public void SetFormationPoint(World world, BlockPos bp) {
//		world.setBlockState(bp, Blocks.END_ROD.getDefaultState().with(BlockStateProperties.FACING, Direction.UP), 2);
		/// world.notifyBlockUpdate(bp, Blocks.END_ROD.getDefaultState().with(BlockStateProperties.FACING, Direction.UP), Blocks.END_ROD.getDefaultState().with(BlockStateProperties.FACING, Direction.UP), 2);
	}

}
