package com.projectreddog.tsrts.network;

import java.nio.charset.Charset;
import java.util.function.Supplier;

import com.projectreddog.tsrts.tileentity.OwnedTileEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

public class TEOwnerChangedPacketToClient {
	public int posX;
	public int posY;
	public int posZ;
	public String ownerName;

	public TEOwnerChangedPacketToClient(PacketBuffer buf) {
		// DECODE
		this.posX = buf.readInt();
		this.posY = buf.readInt();
		this.posZ = buf.readInt();
		int lenght = buf.readInt();
		this.ownerName = buf.readCharSequence(lenght, Charset.forName("UTF-8")).toString();
	}

	public TEOwnerChangedPacketToClient(int posX, int posY, int posZ, String ownerName) {
		super();
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.ownerName = ownerName;
	}

	public void encode(PacketBuffer buf) {
		// TODO Auto-generated method stub
		buf.writeInt(posX);
		buf.writeInt(posY);
		buf.writeInt(posZ);
		buf.writeInt(this.ownerName.length());
		buf.writeCharSequence(this.ownerName, Charset.forName("UTF-8"));
	}

	public void handle(Supplier<NetworkEvent.Context> ctx) {
		// TODO Auto-generated method stub
		ctx.get().enqueueWork(() -> {
			PlayerEntity player = Minecraft.getInstance().player;
			if (player != null) {
				TileEntity te = player.world.getTileEntity(new BlockPos(this.posX, this.posY, this.posZ));
				if (te instanceof OwnedTileEntity) {
					OwnedTileEntity ote = (OwnedTileEntity) te;
					ote.setOwner(this.ownerName);
				}
			}

		});
		ctx.get().setPacketHandled(true);
	}

}
