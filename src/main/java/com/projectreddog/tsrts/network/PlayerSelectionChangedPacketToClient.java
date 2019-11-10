package com.projectreddog.tsrts.network;

import com.projectreddog.tsrts.client.network.ClientPacketHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerSelectionChangedPacketToClient {
	public int[] entityIds;

	public PlayerSelectionChangedPacketToClient(PacketBuffer buf) {
		// DECODE
		int length = buf.readInt();
		entityIds = new int[length];
		for (int i = 0; i < length; i++) {
			entityIds[i] = buf.readInt();
		}

	}

	public PlayerSelectionChangedPacketToClient(int[] entityIds) {
		super();
		this.entityIds = entityIds;
	}

	public void encode(PacketBuffer buf) {


		buf.writeInt(entityIds.length);
		for (int i = 0; i < entityIds.length; i++) {
			buf.writeInt(entityIds[i]);
		}
	}

	public void handle(Supplier<NetworkEvent.Context> ctx) {

		ctx.get().enqueueWork(() -> {

			ClientPacketHandler.PlayerSelectionChangedPacketToClient(entityIds);
		});
		ctx.get().setPacketHandled(true);
	}
}
