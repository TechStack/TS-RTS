package com.projectreddog.tsrts.network;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.projectreddog.tsrts.client.network.ClientPacketHandler;
import com.projectreddog.tsrts.reference.Reference.UNIT_TYPES;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class UnitQueueChangedPacketToClient {
	List<UNIT_TYPES> barracksQueue;
	List<UNIT_TYPES> archeryRangeQueue;
	List<UNIT_TYPES> stablesQueue;
	List<UNIT_TYPES> siegeWorkshopQueue;
	Boolean infinateBarracksQueue;
	Boolean infinateArcheryRangeQueue;
	Boolean infinateStablesQueue;
	Boolean infinateSiegeWorkshopQueue;
	int teamOrd;

	public UnitQueueChangedPacketToClient(PacketBuffer buf) {
		// DECODE

		if (barracksQueue == null) {
			barracksQueue = new ArrayList<UNIT_TYPES>();
		}

		if (archeryRangeQueue == null) {
			archeryRangeQueue = new ArrayList<UNIT_TYPES>();
		}

		if (stablesQueue == null) {
			stablesQueue = new ArrayList<UNIT_TYPES>();
		}

		if (siegeWorkshopQueue == null) {
			siegeWorkshopQueue = new ArrayList<UNIT_TYPES>();
		}

		teamOrd = buf.readInt();
		int size = buf.readInt();
		for (int i = 0; i < size; i++) {
			int tmp = buf.readInt();
			barracksQueue.add(UNIT_TYPES.values()[tmp]);
		}
		size = buf.readInt();
		for (int i = 0; i < size; i++) {
			int tmp = buf.readInt();
			archeryRangeQueue.add(UNIT_TYPES.values()[tmp]);
		}

		size = buf.readInt();
		for (int i = 0; i < size; i++) {
			int tmp = buf.readInt();
			stablesQueue.add(UNIT_TYPES.values()[tmp]);
		}
		size = buf.readInt();
		for (int i = 0; i < size; i++) {
			int tmp = buf.readInt();
			siegeWorkshopQueue.add(UNIT_TYPES.values()[tmp]);
		}

		infinateBarracksQueue = buf.readBoolean();
		infinateArcheryRangeQueue = buf.readBoolean();
		infinateStablesQueue = buf.readBoolean();
		infinateSiegeWorkshopQueue = buf.readBoolean();
	}

	public UnitQueueChangedPacketToClient(List<UNIT_TYPES> barracksQueue, List<UNIT_TYPES> archeryRangeQueue, List<UNIT_TYPES> stablesQueue, List<UNIT_TYPES> siegeWorkshopQueue, boolean infinateBarracksQueue, boolean infinateArcheryRangeQueue, boolean infinateStablesQueue, boolean infinateSiegeWorkshopQueue, int teamOrd) {
		super();
		this.barracksQueue = barracksQueue;
		this.archeryRangeQueue = archeryRangeQueue;
		this.stablesQueue = stablesQueue;
		this.teamOrd = teamOrd;
		this.siegeWorkshopQueue = siegeWorkshopQueue;
		this.infinateBarracksQueue = infinateBarracksQueue;
		this.infinateArcheryRangeQueue = infinateArcheryRangeQueue;
		this.infinateStablesQueue = infinateStablesQueue;
		this.infinateSiegeWorkshopQueue = infinateSiegeWorkshopQueue;

	}

	public void encode(PacketBuffer buf) {
		buf.writeInt(teamOrd);
		if (barracksQueue != null) {
			buf.writeInt(barracksQueue.size());
			for (int i = 0; i < barracksQueue.size(); i++) {

				buf.writeInt(barracksQueue.get(i).ordinal());
			}

		} else {
			buf.writeInt(0);
		}

		if (archeryRangeQueue != null) {
			buf.writeInt(archeryRangeQueue.size());
			for (int i = 0; i < archeryRangeQueue.size(); i++) {

				buf.writeInt(archeryRangeQueue.get(i).ordinal());
			}
		} else {
			buf.writeInt(0);

		}

		if (stablesQueue != null) {
			buf.writeInt(stablesQueue.size());
			for (int i = 0; i < stablesQueue.size(); i++) {

				buf.writeInt(stablesQueue.get(i).ordinal());
			}
		} else {
			buf.writeInt(0);

		}

		if (siegeWorkshopQueue != null) {
			buf.writeInt(siegeWorkshopQueue.size());
			for (int i = 0; i < siegeWorkshopQueue.size(); i++) {

				buf.writeInt(siegeWorkshopQueue.get(i).ordinal());
			}
		} else {
			buf.writeInt(0);

		}

		buf.writeBoolean(infinateBarracksQueue);
		buf.writeBoolean(infinateArcheryRangeQueue);
		buf.writeBoolean(infinateStablesQueue);
		buf.writeBoolean(infinateSiegeWorkshopQueue);

	}

	public void handle(Supplier<NetworkEvent.Context> ctx) {
		// TODO Auto-generated method stub
		ctx.get().enqueueWork(() -> {

			ClientPacketHandler.UnitQueueChangedPacketToClient(teamOrd, barracksQueue, archeryRangeQueue, stablesQueue, siegeWorkshopQueue, infinateBarracksQueue, infinateArcheryRangeQueue, infinateStablesQueue, infinateSiegeWorkshopQueue);

		});
		ctx.get().setPacketHandled(true);
	}

}
