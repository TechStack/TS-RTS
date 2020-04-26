package com.projectreddog.tsrts.network;

import java.util.function.Supplier;

import com.projectreddog.tsrts.client.network.ClientPacketHandler;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class MarketRatesPacketToClient {

	private int food;
	private int wood;
	private int stone;
	private int iron;
	private int gold;
	private int diamond;

	public MarketRatesPacketToClient(PacketBuffer buf) {
		// DECODE

		this.food = buf.readInt();
		this.wood = buf.readInt();
		this.stone = buf.readInt();
		this.iron = buf.readInt();
		this.gold = buf.readInt();
		this.diamond = buf.readInt();

	}

	public MarketRatesPacketToClient(int food, int wood, int stone, int iron, int gold, int diamond) {
		super();
		this.food = food;
		this.wood = wood;
		this.stone = stone;
		this.iron = iron;
		this.gold = gold;
		this.diamond = diamond;

	}

	public void encode(PacketBuffer buf) {
		// TODO Auto-generated method stub

		buf.writeInt(this.food);
		buf.writeInt(this.wood);
		buf.writeInt(this.stone);
		buf.writeInt(this.iron);
		buf.writeInt(this.gold);
		buf.writeInt(this.diamond);

	}

	public void handle(Supplier<NetworkEvent.Context> ctx) {
		// TODO Auto-generated method stub
		ctx.get().enqueueWork(() -> {
			ClientPacketHandler.MarketRatesPacketToClient(food, wood, stone, iron, gold, diamond);

		});
		ctx.get().setPacketHandled(true);
	}

}
