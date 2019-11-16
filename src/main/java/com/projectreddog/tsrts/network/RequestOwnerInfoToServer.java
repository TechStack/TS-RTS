package com.projectreddog.tsrts.network;

import java.util.function.Supplier;

import com.projectreddog.tsrts.entities.UnitEntity;
import com.projectreddog.tsrts.init.ModNetwork;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class RequestOwnerInfoToServer {

	public int entityID;

	public RequestOwnerInfoToServer(PacketBuffer buf) {
		// DECODE
		this.entityID = buf.readInt();
	}

	public RequestOwnerInfoToServer(int entityID) {
		super();
		this.entityID = entityID;
	}

	public void encode(PacketBuffer buf) {
		// TODO Auto-generated method stub
		buf.writeInt(entityID);
	}

	public void handle(Supplier<NetworkEvent.Context> ctx) {
		// TODO Auto-generated method stub
		ctx.get().enqueueWork(() -> {
			ServerPlayerEntity player = ctx.get().getSender();
			if (player != null) {

				Entity e = player.world.getEntityByID(this.entityID);
				if (e instanceof UnitEntity) {
					UnitEntity ue = (UnitEntity) e;
					ModNetwork.SendToPlayer(player, new EntityOwnerChangedPacketToClient(entityID, ue.getOwnerName()));
				}
			}

		});
		ctx.get().setPacketHandled(true);
	}

}
