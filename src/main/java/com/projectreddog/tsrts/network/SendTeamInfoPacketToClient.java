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

	private String CurrenResearchKey;

	private int currentResearchWorkRemaining;
	private int fullResearchWorkRemaining;
	private int teamPopulationCap;

	private int unitCountMinion = 0;
	private int unitCountArcher = 0;
	private int unitCountLancer = 0;
	private int unitCountPikeman = 0;
	private int unitCountTrebuchet = 0;
	private int unitCountKnight = 0;
	private int unitCountAdvancedKnight = 0;
	private int unitCountSapper = 0;

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

		this.unitCountMinion = buf.readInt();
		this.unitCountArcher = buf.readInt();
		this.unitCountLancer = buf.readInt();
		this.unitCountPikeman = buf.readInt();
		this.unitCountTrebuchet = buf.readInt();
		this.unitCountKnight = buf.readInt();
		this.unitCountAdvancedKnight = buf.readInt();
		this.unitCountSapper = buf.readInt();
	}

	public SendTeamInfoPacketToClient(TeamInfo ti, String teamName) {
		super();

		resourceAmt = ti.GetResourceArray();

		this.teamName = teamName;
		this.CurrenResearchKey = ti.getCurrenResearchKey();
		this.currentResearchWorkRemaining = ti.getCurrenResearchWorkRemaining();
		this.fullResearchWorkRemaining = ti.getFullResearchWorkRemaining();
		this.teamPopulationCap = ti.getTeamPopulationCap();
		this.unitCountMinion = ti.getUnitCountMinion();
		this.unitCountArcher = ti.getUnitCountArcher();
		this.unitCountLancer = ti.getUnitCountLancer();
		this.unitCountPikeman = ti.getUnitCountPikeman();
		this.unitCountTrebuchet = ti.getUnitCountTrebuchet();
		this.unitCountKnight = ti.getUnitCountKnight();
		this.unitCountAdvancedKnight = ti.getUnitCountAdvancedKnight();
		this.unitCountSapper = ti.getUnitCountSapper();

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

		buf.writeInt(this.unitCountMinion);
		buf.writeInt(this.unitCountArcher);
		buf.writeInt(this.unitCountLancer);
		buf.writeInt(this.unitCountPikeman);
		buf.writeInt(this.unitCountTrebuchet);
		buf.writeInt(this.unitCountKnight);
		buf.writeInt(this.unitCountAdvancedKnight);
		buf.writeInt(this.unitCountSapper);
	}

	public void handle(Supplier<NetworkEvent.Context> ctx) {
		// TODO Auto-generated method stub
		ctx.get().enqueueWork(() -> {
			ClientPacketHandler.SendTeamInfoPacketToClient(resourceAmt, teamName, CurrenResearchKey, currentResearchWorkRemaining, fullResearchWorkRemaining, teamPopulationCap, unitCountMinion, unitCountArcher, unitCountLancer, unitCountPikeman, unitCountTrebuchet, unitCountKnight, unitCountAdvancedKnight, unitCountSapper);

		});
		ctx.get().setPacketHandled(true);
	}

}
