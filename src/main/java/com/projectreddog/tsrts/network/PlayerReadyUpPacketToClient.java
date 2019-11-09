package com.projectreddog.tsrts.network;

import com.projectreddog.tsrts.client.network.ClientPacketHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerReadyUpPacketToClient {
	public boolean isReady;
	public int playerEntityID;

	public PlayerReadyUpPacketToClient(PacketBuffer buf) {
		// DECODE
		this.isReady = buf.readBoolean();
		this.playerEntityID = buf.readInt();
	}

	public PlayerReadyUpPacketToClient(int playerEntityID, boolean isReady) {
		super();
		this.isReady = isReady;
		this.playerEntityID = playerEntityID;
	}

	public void encode(PacketBuffer buf) {

		buf.writeBoolean(isReady);
		buf.writeInt(playerEntityID);

	}

	public void handle(Supplier<NetworkEvent.Context> ctx) {

		ctx.get().enqueueWork(() -> {

			ClientPacketHandler.PlayerReadyUpPacketToClient(playerEntityID, isReady);
		});
		ctx.get().setPacketHandled(true);
	}

}
