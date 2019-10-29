package com.projectreddog.tsrts.init;

import java.util.function.Supplier;

import com.projectreddog.tsrts.network.EntityOwnerChangedPacketToClient;
import com.projectreddog.tsrts.network.SendTeamInfoPacketToClient;
import com.projectreddog.tsrts.network.TEOwnerChangedPacketToClient;
import com.projectreddog.tsrts.network.TeGuiButtonClickedPacketToServer;
import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class ModNetwork {
	private static final String PROTOCOL_VERSION = Integer.toString(1);

	public static final SimpleChannel simpleChannel = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(Reference.MODID, "main_channel")).clientAcceptedVersions(PROTOCOL_VERSION::equals).serverAcceptedVersions(PROTOCOL_VERSION::equals).networkProtocolVersion(() -> PROTOCOL_VERSION).simpleChannel();

	public static void init() {
		int MessageId = 0;

		simpleChannel.registerMessage(MessageId++, TeGuiButtonClickedPacketToServer.class, TeGuiButtonClickedPacketToServer::encode, TeGuiButtonClickedPacketToServer::new, TeGuiButtonClickedPacketToServer::handle);
		simpleChannel.registerMessage(MessageId++, EntityOwnerChangedPacketToClient.class, EntityOwnerChangedPacketToClient::encode, EntityOwnerChangedPacketToClient::new, EntityOwnerChangedPacketToClient::handle);
		simpleChannel.registerMessage(MessageId++, TEOwnerChangedPacketToClient.class, TEOwnerChangedPacketToClient::encode, TEOwnerChangedPacketToClient::new, TEOwnerChangedPacketToClient::handle);
		simpleChannel.registerMessage(MessageId++, SendTeamInfoPacketToClient.class, SendTeamInfoPacketToClient::encode, SendTeamInfoPacketToClient::new, SendTeamInfoPacketToClient::handle);

	}

	public static <MSG> void SendToPlayer(ServerPlayerEntity player, MSG message) {
		// Sending to one player
		simpleChannel.send(PacketDistributor.PLAYER.with((Supplier<ServerPlayerEntity>) player), message);
	}

	// Send to all players tracking this chunk
	// INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(chunk), new MyMessage());
	public static <MSG> void SendToALLPlayers(MSG message) {

		simpleChannel.send(PacketDistributor.ALL.noArg(), message);

	}

	public static <MSG> void SendToServer(MSG message) {
		simpleChannel.sendToServer(message);
	}
}
