package com.projectreddog.tsrts.network;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.client.network.ClientPacketHandler;
import com.projectreddog.tsrts.reference.Reference;
import com.projectreddog.tsrts.utilities.data.MapStructureData;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

public class SendMapDataPacketToClient {

	private HashMap<BlockPos, MapStructureData> structures;

	public void readFrom(PacketBuffer buf) {
		// private BlockPos position;
		// private Reference.STRUCTURE_TYPE type;
		// private String teamName;
		int x = buf.readInt();
		int y = buf.readInt();
		int z = buf.readInt();
		BlockPos bp = new BlockPos(x, y, z);
		int structTypeOrd = buf.readInt();
		Reference.STRUCTURE_TYPE structType = Reference.STRUCTURE_TYPE.values()[structTypeOrd];
		int lenght = buf.readInt();
		String teamName = buf.readCharSequence(lenght, Charset.forName("UTF-8")).toString();
		structures.put(bp, new MapStructureData(bp, structType, teamName));
	}

	public SendMapDataPacketToClient(PacketBuffer buf) {
		// DECODE
		structures = new HashMap<BlockPos, MapStructureData>();
		int msdSize = buf.readInt();

		for (int i = 0; i < msdSize; i++) {
			readFrom(buf);
		}

	}

	public SendMapDataPacketToClient() {
		super();
		structures = TSRTS.Structures;
	}

	public void writeTo(PacketBuffer buf, MapStructureData msd) {
		// private BlockPos position;
		// private Reference.STRUCTURE_TYPE type;
		// private String teamName;
		buf.writeInt(msd.getPosition().getX());
		buf.writeInt(msd.getPosition().getY());
		buf.writeInt(msd.getPosition().getZ());

		buf.writeInt(msd.getType().ordinal());

		buf.writeInt(msd.getTeamName().length());
		buf.writeCharSequence(msd.getTeamName(), Charset.forName("UTF-8"));

	}

	public void encode(PacketBuffer buf) {

		int msdSize = structures.size();
		buf.writeInt(msdSize);

		for (Map.Entry<BlockPos, MapStructureData> entry : structures.entrySet()) {
			MapStructureData msd = entry.getValue();
			writeTo(buf, msd);
		}
	}

	public void handle(Supplier<NetworkEvent.Context> ctx) {
		// TODO Auto-generated method stub
		ctx.get().enqueueWork(() -> {
			ClientPacketHandler.SendMapDataPacketToClient(structures);

		});
		ctx.get().setPacketHandled(true);
	}

}
