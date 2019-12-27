package com.projectreddog.tsrts.network;

import java.nio.charset.Charset;
import java.util.function.Supplier;

import com.projectreddog.tsrts.client.network.ClientPacketHandler;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class ResearchUnlockedPacketToClient {
	public String teamName;

	private String key;
	private boolean unLocked;

	public ResearchUnlockedPacketToClient(PacketBuffer buf) {

		int lenght = buf.readInt();
		this.teamName = buf.readCharSequence(lenght, Charset.forName("UTF-8")).toString();
		lenght = buf.readInt();
		this.key = buf.readCharSequence(lenght, Charset.forName("UTF-8")).toString();
		this.unLocked = buf.readBoolean();
	}

	public ResearchUnlockedPacketToClient(String Key, String teamName, boolean unLocked) {
		super();

		this.teamName = teamName;
		this.key = Key;
		this.unLocked = unLocked;

	}

	public void encode(PacketBuffer buf) {

		buf.writeInt(this.teamName.length());
		buf.writeCharSequence(this.teamName, Charset.forName("UTF-8"));

		buf.writeInt(this.key.length());
		buf.writeCharSequence(this.key, Charset.forName("UTF-8"));
		buf.writeBoolean(unLocked);
	}

	public void handle(Supplier<NetworkEvent.Context> ctx) {
		// TODO Auto-generated method stub
		ctx.get().enqueueWork(() -> {
			ClientPacketHandler.SendResearchUnlockToClient(key, teamName, unLocked);

		});
		ctx.get().setPacketHandled(true);
	}

}
