package com.projectreddog.tsrts.network;

import java.nio.charset.Charset;
import java.util.function.Supplier;

import com.projectreddog.tsrts.client.network.ClientPacketHandler;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class EntityOwnerChangedPacketToClient {
	public int entityID;
	public String ownerName;

	public EntityOwnerChangedPacketToClient(PacketBuffer buf) {
		// DECODE
		this.entityID = buf.readInt();
		int lenght = buf.readInt();
		this.ownerName = buf.readCharSequence(lenght, Charset.forName("UTF-8")).toString();

	}

	public EntityOwnerChangedPacketToClient(int entityID, String ownerName) {
		super();
		this.entityID = entityID;
		this.ownerName = ownerName;
	}

	public void encode(PacketBuffer buf) {
		// TODO Auto-generated method stub
		buf.writeInt(entityID);
		buf.writeInt(this.ownerName.length());
		buf.writeCharSequence(this.ownerName, Charset.forName("UTF-8"));

	}

	public void handle(Supplier<NetworkEvent.Context> ctx) {
		// TODO Auto-generated method stub

		ctx.get().enqueueWork(() -> {
			ClientPacketHandler.EntityOwnerChangedPacketToClient(this.entityID, this.ownerName);
		});
		ctx.get().setPacketHandled(true);
	}

}
