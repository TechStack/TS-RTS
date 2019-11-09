package com.projectreddog.tsrts.network;

import com.projectreddog.tsrts.utilities.Utilities;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class LobbyGuiButtonClickedPacketToServer {

	public int buttonid;

	public LobbyGuiButtonClickedPacketToServer(PacketBuffer buf) {
		// DECODE
		this.buttonid = buf.readInt();
	}

	public LobbyGuiButtonClickedPacketToServer(int buttonid) {
		super();
		this.buttonid = buttonid;
	}

	public void encode(PacketBuffer buf) {
		// TODO Auto-generated method stub
		buf.writeInt(buttonid);
	}

	public void handle(Supplier<NetworkEvent.Context> ctx) {
		// TODO Auto-generated method stub
		ctx.get().enqueueWork(() -> {
			ServerPlayerEntity player = ctx.get().getSender();
			if (player != null) {

				Utilities.LobbyGuiHandler(this.buttonid, player);

			}

		});
		ctx.get().setPacketHandled(true);
	}

}
