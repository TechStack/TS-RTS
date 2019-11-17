package com.projectreddog.tsrts.network;

import java.nio.charset.Charset;
import java.util.function.Supplier;

import com.projectreddog.tsrts.client.network.ClientPacketHandler;
import com.projectreddog.tsrts.utilities.TeamInfo;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class SendTeamInfoPacketToClient {
	public String teamName;
	private final int resourceCount = TeamInfo.Resources.values().length;
	private int resourceAmt[] = new int[resourceCount];

	public SendTeamInfoPacketToClient(PacketBuffer buf) {
		// DECODE
		for (int i = 0; i < resourceCount; i++) {
			resourceAmt[i] = buf.readInt();
		}

		int lenght = buf.readInt();
		this.teamName = buf.readCharSequence(lenght, Charset.forName("UTF-8")).toString();
	}

	public SendTeamInfoPacketToClient(TeamInfo ti, String teamName) {
		super();

		TeamInfo.Resources[] res = TeamInfo.Resources.values();

		for (int i = 0; i < resourceCount; i++) {

			resourceAmt[i] = ti.GetResource(res[i]);
		}

		this.teamName = teamName;

	}

	public void encode(PacketBuffer buf) {
		// TODO Auto-generated method stub

		for (int i = 0; i < resourceCount; i++) {
			buf.writeInt(resourceAmt[i]);
		}

		buf.writeInt(this.teamName.length());
		buf.writeCharSequence(this.teamName, Charset.forName("UTF-8"));
	}

	public void handle(Supplier<NetworkEvent.Context> ctx) {
		// TODO Auto-generated method stub
		ctx.get().enqueueWork(() -> {
			ClientPacketHandler.SendTeamInfoPacketToClient(resourceAmt, teamName);

		});
		ctx.get().setPacketHandled(true);
	}

}
