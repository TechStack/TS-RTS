package com.projectreddog.tsrts.network;

import java.util.function.Supplier;

import com.projectreddog.tsrts.client.network.ClientPacketHandler;
import com.projectreddog.tsrts.reference.Reference.RTS_COSTS;
import com.projectreddog.tsrts.utilities.ResourceValues;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class CostsPacketToClient {
	ResourceValues rv;
	RTS_COSTS costsType;

	public CostsPacketToClient(PacketBuffer buf) {
		// DECODE
		this.costsType = RTS_COSTS.values()[buf.readInt()];
		int food = buf.readInt();
		int wood = buf.readInt();
		int stone = buf.readInt();
		int iron = buf.readInt();
		int gold = buf.readInt();
		int diamond = buf.readInt();
		int emerald = buf.readInt();
		this.rv = new ResourceValues(food, wood, stone, iron, gold, diamond, emerald);
	}

	public CostsPacketToClient(RTS_COSTS costType, ResourceValues rv) {
		super();
		this.costsType = costType;
		this.rv = rv;
	}

	public void encode(PacketBuffer buf) {
		buf.writeInt(this.costsType.ordinal());
		buf.writeInt(rv.getFOOD());
		buf.writeInt(rv.getWOOD());
		buf.writeInt(rv.getSTONE());
		buf.writeInt(rv.getIRON());
		buf.writeInt(rv.getGOLD());
		buf.writeInt(rv.getDIAMOND());
		buf.writeInt(rv.getEMERALD());

	}

	public void handle(Supplier<NetworkEvent.Context> ctx) {
		// TODO Auto-generated method stub
		ctx.get().enqueueWork(() -> {

			ClientPacketHandler.CostsPacketToClient(costsType, rv);

		});
		ctx.get().setPacketHandled(true);
	}

}
