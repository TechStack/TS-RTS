package com.projectreddog.tsrts.network;

import java.nio.charset.Charset;
import java.util.function.Supplier;

import com.projectreddog.tsrts.entities.UnitEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class EntityOwnerChangedPacketToClient {
	public int entityID;
	public String ownerName;

	public EntityOwnerChangedPacketToClient(PacketBuffer buf) {
		// DECODE
		this.entityID = buf.readInt();
		int lenght = buf.readInt();
		this.ownerName = buf.readCharSequence(lenght, Charset.forName("UTF-8")).toString();

	}

	public EntityOwnerChangedPacketToClient(int entityID, String ownerName) {
		super();
		this.entityID = entityID;
		this.ownerName = ownerName;
	}

	public void encode(PacketBuffer buf) {
		// TODO Auto-generated method stub
		buf.writeInt(entityID);
		buf.writeInt(this.ownerName.length());
		buf.writeCharSequence(this.ownerName, Charset.forName("UTF-8"));

	}

	public void handle(Supplier<NetworkEvent.Context> ctx) {
		// TODO Auto-generated method stub
		ctx.get().enqueueWork(() -> {
			PlayerEntity player = Minecraft.getInstance().player;
			if (player != null) {
				Entity e = player.world.getEntityByID(this.entityID);
				if (e instanceof UnitEntity) {
					UnitEntity ue = (UnitEntity) e;
					ue.setOwnerName(this.ownerName);
				}
			}

		});
		ctx.get().setPacketHandled(true);
	}

}
