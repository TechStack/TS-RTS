package com.projectreddog.tsrts.items.builderitems;

import com.projectreddog.tsrts.blocks.OwnedBlock;
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
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.server.ServerWorld;

public abstract class BuilderItem extends Item {

	public BuilderItem(Properties properties) {
		super(properties);
		// TODO Auto-generated constructor stub
	}

	private ResourceLocation templateName;

	public abstract ResourceLocation getTemplateName();

	public void setTemplateName(ResourceLocation templateName) {
		this.templateName = templateName;
	}

	public abstract Vec3i getSize();

	public boolean isValidLocation(World world, BlockPos bp, Direction d) {
		boolean result = true;

		BlockPos bp2 = bp;
		Vec3i size = getSize();
		int xSize = size.getX();
		int ySize = size.getY();
		int zSize = size.getZ();

		Rotation r = Rotation.NONE;
		if (d == Direction.NORTH) {
			bp2 = bp.up().offset(d.rotateYCCW(), (xSize / 2)).offset(d, zSize).offset(d, (-1));
		}
		if (d == Direction.SOUTH) {
			r = Rotation.CLOCKWISE_180;
			bp2 = bp.up().offset(d.rotateYCCW(), -(xSize / 2));
		}

		if (d == Direction.WEST) {
			r = Rotation.COUNTERCLOCKWISE_90;

			bp2 = bp.up().offset(d.rotateYCCW(), -(zSize / 2)).offset(d, (xSize)).offset(d, (-1));

		}

		if (d == Direction.EAST) {
			r = Rotation.CLOCKWISE_90;
			bp2 = bp.up().offset(d.rotateYCCW(), (zSize / 2));
		}

		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {

				for (int z = 0; z < zSize; z++) {

					if (!world.getBlockState(bp2.add(x, y, z)).getBlock().isAir(world.getBlockState(bp2.add(x, y, z)))) {
						result = false;
						// BLOCK in the way
					}

					if (y == 0) {
						if (world.getBlockState(bp2.add(x, y - 1, z)).getBlock().isAir(world.getBlockState(bp2.add(x, y - 1, z)))) {
							result = false;
							// No block under !!

						}
					}
				}

			}

		}

		return result;
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {

		if (!context.getPlayer().world.isRemote) {

			Direction d = Direction.getFacingFromVector(context.getPlayer().getLookVec().getX(), 0, context.getPlayer().getLookVec().getZ());

			if (isValidLocation(context.getWorld(), context.getPos(), d)) {

				// TODO need to consider if the player is on the same team as the entity or not !

				TemplateManager templateManager = ((ServerWorld) context.getWorld()).getStructureTemplateManager();

				Template template;
				try {
					template = templateManager.getTemplate(getTemplateName());
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

					BlockPos bp = context.getPos().up().offset(d.rotateYCCW(), (xSize / 2)).offset(d, zSize);
					BlockPos bp2 = bp;
					Rotation r = Rotation.NONE;
					if (d == Direction.NORTH) {
						bp2 = bp;

					}
					if (d == Direction.SOUTH) {
						r = Rotation.CLOCKWISE_180;
						bp2 = context.getPos().up().offset(d.rotateYCCW(), -(xSize / 2)).offset(d, (1));
					}

					if (d == Direction.WEST) {
						r = Rotation.COUNTERCLOCKWISE_90;

						bp2 = context.getPos().up().offset(d.rotateYCCW(), -(zSize / 2)).offset(d, (xSize));

					}

					if (d == Direction.EAST) {
						r = Rotation.CLOCKWISE_90;
						bp2 = context.getPos().up().offset(d.rotateYCCW(), (zSize / 2)).offset(d, (1));
					}

					PlacementSettings ps = (new PlacementSettings()).setRotation(r).setIgnoreEntities(false).setChunk((ChunkPos) null);
					template.addBlocksToWorldChunk(context.getWorld(), bp, ps);

					for (int x = 0; x < xSize; x++) {
						for (int y = 0; y < ySize; y++) {
							for (int z = 0; z < zSize; z++) {

								// TSRTS.LOGGER.info("CHECKING FOR " + bp.add(x, y, z));
								context.getWorld().notifyBlockUpdate(bp2.add(x, y, z), context.getWorld().getBlockState(bp2.add(x, y, z)), context.getWorld().getBlockState(bp2.add(x, y, z)), 3);

								if (context.getWorld().getBlockState(bp2.add(x, y, z)).getBlock() instanceof OwnedBlock) {
									TileEntity te = context.getWorld().getTileEntity(bp2.add(x, y, z));
									if (te instanceof OwnedTileEntity) {
										// its ours so we can set the rally point.
										((OwnedTileEntity) te).setRallyPoint(context.getPos().up());
										((OwnedTileEntity) te).setOwner(context.getPlayer().getScoreboardName());
									}
								}
							}
						}

					}

				}

				context.getItem().shrink(1);

			}
		}

		return super.onItemUse(context);
	}

}
