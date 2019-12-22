package com.projectreddog.tsrts.network;

import java.nio.charset.Charset;
import java.util.function.Supplier;

import com.projectreddog.tsrts.utilities.Utilities;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class ResearchButtonClickPacketToServer {

	public String researchKey;

	public ResearchButtonClickPacketToServer(PacketBuffer buf) {
		// DECODE

		int lenght = buf.readInt();
		this.researchKey = buf.readCharSequence(lenght, Charset.forName("UTF-8")).toString();
	}

	public ResearchButtonClickPacketToServer(String researchKey) {
		super();
		this.researchKey = researchKey;
	}

	public void encode(PacketBuffer buf) {
		// TODO Auto-generated method stub
		buf.writeInt(this.researchKey.length());
		buf.writeCharSequence(this.researchKey, Charset.forName("UTF-8"));
	}

	public void handle(Supplier<NetworkEvent.Context> ctx) {
		// TODO Auto-generated method stub
		ctx.get().enqueueWork(() -> {
			ServerPlayerEntity player = ctx.get().getSender();
			if (player != null) {
				if (player.getTeam() != null) {
					Utilities.StartResearch(researchKey, player.getTeam().getName());
				}

			}

		});
		ctx.get().setPacketHandled(true);
	}

}
