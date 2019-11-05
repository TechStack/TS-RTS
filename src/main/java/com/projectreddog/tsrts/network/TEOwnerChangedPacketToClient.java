package com.projectreddog.tsrts.network;

import java.nio.charset.Charset;
import java.util.function.Supplier;

import com.projectreddog.tsrts.client.network.ClientPacketHandler;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class TEOwnerChangedPacketToClient {
	public int posX;
	public int posY;
	public int posZ;
	public String ownerName;

	public TEOwnerChangedPacketToClient(PacketBuffer buf) {
		// DECODE
		this.posX = buf.readInt();
		this.posY = buf.readInt();
		this.posZ = buf.readInt();
		int lenght = buf.readInt();
		this.ownerName = buf.readCharSequence(lenght, Charset.forName("UTF-8")).toString();
	}

	public TEOwnerChangedPacketToClient(int posX, int posY, int posZ, String ownerName) {
		super();
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.ownerName = ownerName;
	}

	public void encode(PacketBuffer buf) {
		// TODO Auto-generated method stub
		buf.writeInt(posX);
		buf.writeInt(posY);
		buf.writeInt(posZ);
		buf.writeInt(this.ownerName.length());
		buf.writeCharSequence(this.ownerName, Charset.forName("UTF-8"));
	}

	public void handle(Supplier<NetworkEvent.Context> ctx) {
		// TODO Auto-generated method stub
		ctx.get().enqueueWork(() -> {

			ClientPacketHandler.TEOwnerChangedPacketToClient(posX, posY, posZ, ownerName);

		});
		ctx.get().setPacketHandled(true);
	}

}
