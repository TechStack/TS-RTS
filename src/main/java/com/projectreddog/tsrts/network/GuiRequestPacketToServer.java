package com.projectreddog.tsrts.network;

import java.util.function.Supplier;

import com.projectreddog.tsrts.utilities.Utilities;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class GuiRequestPacketToServer {

	public int guiid;

	public GuiRequestPacketToServer(PacketBuffer buf) {
		// DECODE
		this.guiid = buf.readInt();
	}

	public GuiRequestPacketToServer(int guiid) {
		super();
		this.guiid = guiid;
	}

	public void encode(PacketBuffer buf) {
		buf.writeInt(guiid);
	}

	public void handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ServerPlayerEntity player = ctx.get().getSender();
			if (player != null) {

				Utilities.GuiRequestHandler(this.guiid, player);

			}

		});
		ctx.get().setPacketHandled(true);
	}

}
