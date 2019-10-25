package com.projectreddog.tsrts.network;

import java.util.function.Supplier;

import com.projectreddog.tsrts.TSRTS;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class TeGuiButtonClickedPacket {

	public static void encode(TeGuiButtonClickedPacket msg, PacketBuffer buf) {
		// TODO Auto-generated method stub

	}

	public static TeGuiButtonClickedPacket decode(PacketBuffer buf) {
		// TODO Auto-generated method stub

		return new TeGuiButtonClickedPacket();

	}

	public static void handle(TeGuiButtonClickedPacket msg, Supplier<NetworkEvent.Context> ctx) {
		// TODO Auto-generated method stub
		ctx.get().enqueueWork(() -> {
			ServerPlayerEntity player = ctx.get().getSender();

			TSRTS.LOGGER.info("PACKET HANDLER!");

		});
		ctx.get().setPacketHandled(true);
	}

}
