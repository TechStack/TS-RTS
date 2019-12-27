package com.projectreddog.tsrts.network;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.projectreddog.tsrts.client.network.ClientPacketHandler;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class UnitQueueChangedPacketToClient {
	List<Integer> barracksQueue;
	List<Integer> archeryRangeQueue;
	List<Integer> stablesQueue;
	List<Integer> siegeWorkshopQueue;

	int teamOrd;

	public UnitQueueChangedPacketToClient(PacketBuffer buf) {
		// DECODE

		if (barracksQueue == null) {
			barracksQueue = new ArrayList<Integer>();
		}

		if (archeryRangeQueue == null) {
			archeryRangeQueue = new ArrayList<Integer>();
		}

		if (stablesQueue == null) {
			stablesQueue = new ArrayList<Integer>();
		}

		if (siegeWorkshopQueue == null) {
			siegeWorkshopQueue = new ArrayList<Integer>();
		}

		teamOrd = buf.readInt();
		int size = buf.readInt();
		for (int i = 0; i < size; i++) {
			int tmp = buf.readInt();
			barracksQueue.add(tmp);
		}
		size = buf.readInt();
		for (int i = 0; i < size; i++) {
			int tmp = buf.readInt();
			archeryRangeQueue.add(tmp);
		}

		size = buf.readInt();
		for (int i = 0; i < size; i++) {
			int tmp = buf.readInt();
			stablesQueue.add(tmp);
		}
		size = buf.readInt();
		for (int i = 0; i < size; i++) {
			int tmp = buf.readInt();
			siegeWorkshopQueue.add(tmp);
		}

	}

	public UnitQueueChangedPacketToClient(List<Integer> barracksQueue, List<Integer> archeryRangeQueue, List<Integer> stablesQueue, List<Integer> siegeWorkshopQueue, int teamOrd) {
		super();
		this.barracksQueue = barracksQueue;
		this.archeryRangeQueue = archeryRangeQueue;
		this.stablesQueue = stablesQueue;
		this.teamOrd = teamOrd;
		this.siegeWorkshopQueue = siegeWorkshopQueue;
	}

	public void encode(PacketBuffer buf) {
		buf.writeInt(teamOrd);
		if (barracksQueue != null) {
			buf.writeInt(barracksQueue.size());
			for (int i = 0; i < barracksQueue.size(); i++) {

				buf.writeInt(barracksQueue.get(i));
			}

		} else {
			buf.writeInt(0);
		}

		if (archeryRangeQueue != null) {
			buf.writeInt(archeryRangeQueue.size());
			for (int i = 0; i < archeryRangeQueue.size(); i++) {

				buf.writeInt(archeryRangeQueue.get(i));
			}
		} else {
			buf.writeInt(0);

		}

		if (stablesQueue != null) {
			buf.writeInt(stablesQueue.size());
			for (int i = 0; i < stablesQueue.size(); i++) {

				buf.writeInt(stablesQueue.get(i));
			}
		} else {
			buf.writeInt(0);

		}

		if (siegeWorkshopQueue != null) {
			buf.writeInt(siegeWorkshopQueue.size());
			for (int i = 0; i < siegeWorkshopQueue.size(); i++) {

				buf.writeInt(siegeWorkshopQueue.get(i));
			}
		} else {
			buf.writeInt(0);

		}

	}

	public void handle(Supplier<NetworkEvent.Context> ctx) {
		// TODO Auto-generated method stub
		ctx.get().enqueueWork(() -> {

			ClientPacketHandler.UnitQueueChangedPacketToClient(teamOrd, barracksQueue, archeryRangeQueue, stablesQueue, siegeWorkshopQueue);

		});
		ctx.get().setPacketHandled(true);
	}

}
