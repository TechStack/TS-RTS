package com.projectreddog.tsrts.network;

import java.util.function.Supplier;

import com.projectreddog.tsrts.tileentity.interfaces.ITEGuiButtonHandler;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

public class TeGuiButtonClickedPacketToServer {
	public int posX;
	public int posY;
	public int posZ;
	public int buttionid;

	public TeGuiButtonClickedPacketToServer(PacketBuffer buf) {
		// DECODE
		this.posX = buf.readInt();
		this.posY = buf.readInt();
		this.posZ = buf.readInt();
		this.buttionid = buf.readInt();
	}

	public TeGuiButtonClickedPacketToServer(int posX, int posY, int posZ, int buttionid) {
		super();
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.buttionid = buttionid;
	}

	public void encode(PacketBuffer buf) {
		// TODO Auto-generated method stub
		buf.writeInt(posX);
		buf.writeInt(posY);
		buf.writeInt(posZ);
		buf.writeInt(buttionid);
	}

	public void handle(Supplier<NetworkEvent.Context> ctx) {
		// TODO Auto-generated method stub
		ctx.get().enqueueWork(() -> {
			ServerPlayerEntity player = ctx.get().getSender();
			if (player != null) {
				TileEntity te = player.world.getTileEntity(new BlockPos(posX, posY, posZ));
				if (te instanceof ITEGuiButtonHandler) {
					ITEGuiButtonHandler bgh = (ITEGuiButtonHandler) te;
					bgh.HandleGuiButton(this.buttionid, player);

				}
			}

		});
		ctx.get().setPacketHandled(true);
	}

}
