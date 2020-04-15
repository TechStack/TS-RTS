package com.projectreddog.tsrts.network;

import java.util.function.Supplier;

import com.projectreddog.tsrts.client.network.ClientPacketHandler;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class SendGameOptionPacketToClient {
	public int speedEffectAmount;
	public boolean weatherEnabled = true;

	public SendGameOptionPacketToClient(PacketBuffer buf) {
		// DECODE

		speedEffectAmount = buf.readInt();
		weatherEnabled = buf.readBoolean();
	}

	public SendGameOptionPacketToClient(int speedEffectAmount, boolean weatherEnabled) {
		super();

		this.speedEffectAmount = speedEffectAmount;

		this.weatherEnabled = weatherEnabled;
	}

	public void encode(PacketBuffer buf) {

		buf.writeInt(this.speedEffectAmount);
		buf.writeBoolean(this.weatherEnabled);
	}

	public void handle(Supplier<NetworkEvent.Context> ctx) {
		// TODO Auto-generated method stub
		ctx.get().enqueueWork(() -> {
			ClientPacketHandler.SendGameOptionPacketToClient(speedEffectAmount, weatherEnabled);

		});
		ctx.get().setPacketHandled(true);
	}

}
