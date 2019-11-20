package com.projectreddog.tsrts.network;

import java.nio.charset.Charset;
import java.util.function.Supplier;

import com.projectreddog.tsrts.client.network.ClientPacketHandler;
import com.projectreddog.tsrts.utilities.AlertToastBackgroundType;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class AlertToastToClient {
	public String title;
	public String subTitle;
	public AlertToastBackgroundType backgroundType;

	public AlertToastToClient(PacketBuffer buf) {
		// DECODE

		int lenght = buf.readInt();
		this.title = buf.readCharSequence(lenght, Charset.forName("UTF-8")).toString();

		lenght = buf.readInt();
		this.subTitle = buf.readCharSequence(lenght, Charset.forName("UTF-8")).toString();

		backgroundType = AlertToastBackgroundType.values()[buf.readInt()];

	}

	public AlertToastToClient(String title, String subTitle, AlertToastBackgroundType backgroundType) {
		super();
		this.title = title;
		this.subTitle = subTitle;
		this.backgroundType = backgroundType;
	}

	public void encode(PacketBuffer buf) {
		// TODO Auto-generated method stub

		buf.writeInt(this.title.length());
		buf.writeCharSequence(this.title, Charset.forName("UTF-8"));

		buf.writeInt(this.subTitle.length());
		buf.writeCharSequence(this.subTitle, Charset.forName("UTF-8"));

		buf.writeInt(backgroundType.ordinal());
	}

	public void handle(Supplier<NetworkEvent.Context> ctx) {
		// TODO Auto-generated method stub
		ctx.get().enqueueWork(() -> {

			ClientPacketHandler.AlertToastToClient(title, subTitle, backgroundType);

		});
		ctx.get().setPacketHandled(true);
	}

}
