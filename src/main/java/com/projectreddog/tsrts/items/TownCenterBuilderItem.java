package com.projectreddog.tsrts.items;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.blocks.OwnedBlock;
import com.projectreddog.tsrts.init.ModItemGroups;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.tileentity.OwnedTileEntity;

import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ResourceLocationException;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.server.ServerWorld;

public class TownCenterBuilderItem extends Item {

	private ResourceLocation templateName = new ResourceLocation(Reference.MODID + ":" + "garrison_red_2");

	public TownCenterBuilderItem() {
		super(new Item.Properties().group(ModItemGroups.weaponsItemGroup));
		setRegistryName(Reference.REIGSTRY_NAME_TOWN_CENTER_BUILDER_ITEM);
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		TSRTS.LOGGER.info("CLICKED of:" + context.getPos().up());

		if (!context.getPlayer().world.isRemote) {

			// TODO need to consider if the player is on the same team as the entity or not !

			TemplateManager templateManager = ((ServerWorld) context.getWorld()).getStructureTemplateManager();

			Template template;
			try {
				template = templateManager.getTemplate(templateName);
			} catch (ResourceLocationException e) {
				e.printStackTrace();
				return ActionResultType.FAIL;
			}

			if (template == null) {
				return ActionResultType.FAIL;
			} else {
				// has template

				int xSize = template.getSize().getX();
				int ySize = template.getSize().getY();
				int zSize = template.getSize().getZ();

				Direction d = Direction.getFacingFromVector(context.getPlayer().getLookVec().getX(), 0, context.getPlayer().getLookVec().getZ());

				BlockPos bp = context.getPos().up().offset(d.rotateYCCW(), (xSize / 2)).offset(d, ySize);
				Rotation r = Rotation.NONE;
				if (d == Direction.SOUTH) {
					r = Rotation.CLOCKWISE_180;
				}

				if (d == Direction.WEST) {
					r = Rotation.COUNTERCLOCKWISE_90;
				}

				if (d == Direction.EAST) {
					r = Rotation.CLOCKWISE_90;
				}

				PlacementSettings ps = (new PlacementSettings()).setRotation(r).setIgnoreEntities(true).setChunk((ChunkPos) null);
				template.addBlocksToWorldChunk(context.getWorld(), bp, ps);

				for (int x = 0; x < xSize; x++) {
					for (int y = 0; y < ySize; y++) {
						for (int z = 0; z < zSize; z++) {
							context.getWorld().notifyBlockUpdate(bp.add(x, y, z), context.getWorld().getBlockState(bp.add(x, y, z)), context.getWorld().getBlockState(bp.add(x, y, z)), 3);

							if (context.getWorld().getBlockState(bp.add(x, y, z)).getBlock() instanceof OwnedBlock) {
								TileEntity te = context.getWorld().getTileEntity(bp.add(x, y, z));
								if (te instanceof OwnedTileEntity) {
									// its ours so we can set the rally point.
									((OwnedTileEntity) te).setRallyPoint(context.getPos().up());
								}
							}
						}
					}

				}

			}

		}

		return super.onItemUse(context);
	}

}
