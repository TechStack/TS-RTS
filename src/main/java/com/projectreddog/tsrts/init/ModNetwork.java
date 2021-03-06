package com.projectreddog.tsrts.init;

import com.projectreddog.tsrts.network.AlertToastToClient;
import com.projectreddog.tsrts.network.CostsPacketToClient;
import com.projectreddog.tsrts.network.EntityOwnerChangedPacketToClient;
import com.projectreddog.tsrts.network.GenericGuiButtonClickedPacketToServer;
import com.projectreddog.tsrts.network.GuiRequestPacketToServer;
import com.projectreddog.tsrts.network.MarketRatesPacketToClient;
import com.projectreddog.tsrts.network.PlayerReadyUpPacketToClient;
import com.projectreddog.tsrts.network.PlayerSelectionChangedPacketToClient;
import com.projectreddog.tsrts.network.PlayerSelectionChangedPacketToServer;
import com.projectreddog.tsrts.network.RecruitTroopButtonClickedPacketToServer;
import com.projectreddog.tsrts.network.RequestOwnerInfoToServer;
import com.projectreddog.tsrts.network.ResearchButtonClickPacketToServer;
import com.projectreddog.tsrts.network.ResearchUnlockedPacketToClient;
import com.projectreddog.tsrts.network.SendGameOptionPacketToClient;
import com.projectreddog.tsrts.network.SendMapDataPacketToClient;
import com.projectreddog.tsrts.network.SendTeamInfoPacketToClient;
import com.projectreddog.tsrts.network.TEOwnerChangedPacketToClient;
import com.projectreddog.tsrts.network.TESetRallyPointToServer;
import com.projectreddog.tsrts.network.TeGuiButtonClickedPacketToServer;
import com.projectreddog.tsrts.network.TeamOptionButtonToServer;
import com.projectreddog.tsrts.network.TownHallButtonClickedPacketToServer;
import com.projectreddog.tsrts.network.UnitQueueChangedPacketToClient;
import com.projectreddog.tsrts.reference.Reference;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
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
		simpleChannel.registerMessage(MessageId++, GenericGuiButtonClickedPacketToServer.class, GenericGuiButtonClickedPacketToServer::encode, GenericGuiButtonClickedPacketToServer::new, GenericGuiButtonClickedPacketToServer::handle);
		simpleChannel.registerMessage(MessageId++, PlayerReadyUpPacketToClient.class, PlayerReadyUpPacketToClient::encode, PlayerReadyUpPacketToClient::new, PlayerReadyUpPacketToClient::handle);
		simpleChannel.registerMessage(MessageId++, PlayerSelectionChangedPacketToClient.class, PlayerSelectionChangedPacketToClient::encode, PlayerSelectionChangedPacketToClient::new, PlayerSelectionChangedPacketToClient::handle);
		simpleChannel.registerMessage(MessageId++, PlayerSelectionChangedPacketToServer.class, PlayerSelectionChangedPacketToServer::encode, PlayerSelectionChangedPacketToServer::new, PlayerSelectionChangedPacketToServer::handle);
		simpleChannel.registerMessage(MessageId++, RequestOwnerInfoToServer.class, RequestOwnerInfoToServer::encode, RequestOwnerInfoToServer::new, RequestOwnerInfoToServer::handle);
		simpleChannel.registerMessage(MessageId++, TESetRallyPointToServer.class, TESetRallyPointToServer::encode, TESetRallyPointToServer::new, TESetRallyPointToServer::handle);
		simpleChannel.registerMessage(MessageId++, AlertToastToClient.class, AlertToastToClient::encode, AlertToastToClient::new, AlertToastToClient::handle);
		simpleChannel.registerMessage(MessageId++, TownHallButtonClickedPacketToServer.class, TownHallButtonClickedPacketToServer::encode, TownHallButtonClickedPacketToServer::new, TownHallButtonClickedPacketToServer::handle);
		simpleChannel.registerMessage(MessageId++, GuiRequestPacketToServer.class, GuiRequestPacketToServer::encode, GuiRequestPacketToServer::new, GuiRequestPacketToServer::handle);
		simpleChannel.registerMessage(MessageId++, UnitQueueChangedPacketToClient.class, UnitQueueChangedPacketToClient::encode, UnitQueueChangedPacketToClient::new, UnitQueueChangedPacketToClient::handle);
		simpleChannel.registerMessage(MessageId++, ResearchButtonClickPacketToServer.class, ResearchButtonClickPacketToServer::encode, ResearchButtonClickPacketToServer::new, ResearchButtonClickPacketToServer::handle);
		simpleChannel.registerMessage(MessageId++, ResearchUnlockedPacketToClient.class, ResearchUnlockedPacketToClient::encode, ResearchUnlockedPacketToClient::new, ResearchUnlockedPacketToClient::handle);
		simpleChannel.registerMessage(MessageId++, CostsPacketToClient.class, CostsPacketToClient::encode, CostsPacketToClient::new, CostsPacketToClient::handle);
		simpleChannel.registerMessage(MessageId++, TeamOptionButtonToServer.class, TeamOptionButtonToServer::encode, TeamOptionButtonToServer::new, TeamOptionButtonToServer::handle);
		simpleChannel.registerMessage(MessageId++, SendMapDataPacketToClient.class, SendMapDataPacketToClient::encode, SendMapDataPacketToClient::new, SendMapDataPacketToClient::handle);
		simpleChannel.registerMessage(MessageId++, RecruitTroopButtonClickedPacketToServer.class, RecruitTroopButtonClickedPacketToServer::encode, RecruitTroopButtonClickedPacketToServer::new, RecruitTroopButtonClickedPacketToServer::handle);
		simpleChannel.registerMessage(MessageId++, SendGameOptionPacketToClient.class, SendGameOptionPacketToClient::encode, SendGameOptionPacketToClient::new, SendGameOptionPacketToClient::handle);
		simpleChannel.registerMessage(MessageId++, MarketRatesPacketToClient.class, MarketRatesPacketToClient::encode, MarketRatesPacketToClient::new, MarketRatesPacketToClient::handle);

	}

	public static <MSG> void SendToPlayer(ServerPlayerEntity player, MSG message) {
		// Sending to one player
//		simpleChannel.send(PacketDistributor.PLAYER.with((Supplier<ServerPlayerEntity>) player), message);
		simpleChannel.sendTo(message, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);

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
