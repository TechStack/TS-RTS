package com.projectreddog.tsrts.network;

import java.nio.charset.Charset;
import java.util.function.Supplier;

import com.projectreddog.tsrts.client.network.ClientPacketHandler;
import com.projectreddog.tsrts.reference.Reference.UNIT_TYPES;
import com.projectreddog.tsrts.utilities.TeamInfo;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class SendTeamInfoPacketToClient {
	public String teamName;
	private final int resourceCount = TeamInfo.Resources.values().length;
	private int resourceAmt[] = new int[resourceCount];

	private String CurrenResearchKey;

	private int currentResearchWorkRemaining;
	private int fullResearchWorkRemaining;
	private int teamPopulationCap;

	private int[] unitCount = new int[UNIT_TYPES.values().length];

	public SendTeamInfoPacketToClient(PacketBuffer buf) {
		// DECODE
		for (int i = 0; i < resourceCount; i++) {
			resourceAmt[i] = buf.readInt();
		}

		int lenght = buf.readInt();
		this.teamName = buf.readCharSequence(lenght, Charset.forName("UTF-8")).toString();
		lenght = buf.readInt();
		this.CurrenResearchKey = buf.readCharSequence(lenght, Charset.forName("UTF-8")).toString();
		this.currentResearchWorkRemaining = buf.readInt();
		this.fullResearchWorkRemaining = buf.readInt();

		this.teamPopulationCap = buf.readInt();

		for (int i = 0; i < unitCount.length; i++) {
			unitCount[i] = buf.readInt();
		}

	}

	public SendTeamInfoPacketToClient(TeamInfo ti, String teamName) {
		super();

		resourceAmt = ti.GetResourceArray();

		this.teamName = teamName;
		this.CurrenResearchKey = ti.getCurrenResearchKey();
		this.currentResearchWorkRemaining = ti.getCurrenResearchWorkRemaining();
		this.fullResearchWorkRemaining = ti.getFullResearchWorkRemaining();
		this.teamPopulationCap = ti.getTeamPopulationCap();
		for (int i = 0; i < unitCount.length; i++) {
			unitCount[i] = ti.getUnitCount(UNIT_TYPES.values()[i]);
		}

	}

	public void encode(PacketBuffer buf) {
		// TODO Auto-generated method stub

		for (int i = 0; i < resourceCount; i++) {
			buf.writeInt(resourceAmt[i]);
		}

		buf.writeInt(this.teamName.length());
		buf.writeCharSequence(this.teamName, Charset.forName("UTF-8"));

		buf.writeInt(this.CurrenResearchKey.length());
		buf.writeCharSequence(this.CurrenResearchKey, Charset.forName("UTF-8"));
		buf.writeInt(this.currentResearchWorkRemaining);

		buf.writeInt(this.fullResearchWorkRemaining);
		buf.writeInt(this.teamPopulationCap);

		for (int i = 0; i < unitCount.length; i++) {
			buf.writeInt(unitCount[i]);
		}

	}

	public void handle(Supplier<NetworkEvent.Context> ctx) {
		// TODO Auto-generated method stub
		ctx.get().enqueueWork(() -> {
			ClientPacketHandler.SendTeamInfoPacketToClient(resourceAmt, teamName, CurrenResearchKey, currentResearchWorkRemaining, fullResearchWorkRemaining, teamPopulationCap, unitCount);

		});
		ctx.get().setPacketHandled(true);
	}

}
