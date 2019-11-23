package com.projectreddog.tsrts.network;

import java.util.function.Supplier;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.utilities.Utilities;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class PlayerSelectionChangedPacketToServer {
	public int[] entityIds;

	public PlayerSelectionChangedPacketToServer(PacketBuffer buf) {
		// DECODE
		int length = buf.readInt();
		entityIds = new int[length];
		for (int i = 0; i < length; i++) {
			entityIds[i] = buf.readInt();
		}

	}

	public PlayerSelectionChangedPacketToServer(int[] entityIds) {
		super();
		this.entityIds = entityIds;
		TSRTS.LOGGER.info("CONTROLGROUPBUG:" + "in PlayerSelectionChangedPacketToServer for " + entityIds.toString());

	}

	public void encode(PacketBuffer buf) {

		buf.writeInt(entityIds.length);
		for (int i = 0; i < entityIds.length; i++) {
			buf.writeInt(entityIds[i]);
		}
	}

	public void handle(Supplier<NetworkEvent.Context> ctx) {

		ctx.get().enqueueWork(() -> {
			String playerScoreboardname = ctx.get().getSender().getScoreboardName();
			Utilities.ServerControlGroupToSelectedUnits(ctx.get().getSender(), playerScoreboardname, entityIds);

		});
		ctx.get().setPacketHandled(true);
	}
}
