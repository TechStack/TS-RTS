package com.projectreddog.tsrts.network;

import java.util.function.Supplier;

import com.projectreddog.tsrts.reference.Reference.UNIT_TYPES;
import com.projectreddog.tsrts.utilities.Utilities;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class RecruitTroopButtonClickedPacketToServer {

	public UNIT_TYPES buttonid;

	public RecruitTroopButtonClickedPacketToServer(PacketBuffer buf) {
		// DECODE
		this.buttonid = UNIT_TYPES.values()[buf.readInt()];
	}

	public RecruitTroopButtonClickedPacketToServer(UNIT_TYPES buttonid) {
		super();
		this.buttonid = buttonid;
	}

	public void encode(PacketBuffer buf) {
		// TODO Auto-generated method stub
		buf.writeInt(buttonid.ordinal());
	}

	public void handle(Supplier<NetworkEvent.Context> ctx) {
		// TODO Auto-generated method stub
		ctx.get().enqueueWork(() -> {
			ServerPlayerEntity player = ctx.get().getSender();
			if (player != null) {

				Utilities.RecruitTroopHandler(this.buttonid, player);

			}

		});
		ctx.get().setPacketHandled(true);
	}

}
