package com.projectreddog.tsrts.network;

import java.util.function.Supplier;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.init.ModNetwork;
import com.projectreddog.tsrts.reference.Reference.TEAM_OPTION_BUTTONS;
import com.projectreddog.tsrts.utilities.TeamEnum;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class TeamOptionButtonToServer {
	public TEAM_OPTION_BUTTONS buttonID;

	public TeamOptionButtonToServer(PacketBuffer buf) {
		// DECODE
		this.buttonID = TEAM_OPTION_BUTTONS.values()[buf.readInt()];

	}

	public TeamOptionButtonToServer(TEAM_OPTION_BUTTONS buttonID) {
		super();
		this.buttonID = buttonID;

	}

	public void encode(PacketBuffer buf) {
		buf.writeInt(buttonID.ordinal());

	}

	public void handle(Supplier<NetworkEvent.Context> ctx) {

		ctx.get().enqueueWork(() -> {
			ServerPlayerEntity player = ctx.get().getSender();

			if (player.getTeam() != null) {
				int teamID = TeamEnum.getIDFromName(player.getTeam().getName());
				switch (buttonID) {
				case INFINATE_BARRACKS_QUEUE:
					TSRTS.TeamQueues[teamID].setInfinateBarracksQueue(!TSRTS.TeamQueues[teamID].isInfinateBarracksQueue());

					break;
				case INFINANTE_ARCHERY_RANGE_QUEUE:
					TSRTS.TeamQueues[teamID].setInfinateArcheryRangeQueue(!TSRTS.TeamQueues[teamID].isInfinateArcheryRangeQueue());

					break;
				case INFINANTE_STABLES_QUEUE:
					TSRTS.TeamQueues[teamID].setInfinateStablesQueue(!TSRTS.TeamQueues[teamID].isInfinateStablesQueue());

					break;
				case INFINANTE_SIEGE_WORKSHOP_QUEUE:
					TSRTS.TeamQueues[teamID].setInfinateSiegeWorkshopQueue(!TSRTS.TeamQueues[teamID].isInfinateSiegeWorkshopQueue());

					break;

				case INFINANTE_TEMPLE_QUEUE:
					TSRTS.TeamQueues[teamID].setInfinateTempleQueue(!TSRTS.TeamQueues[teamID].isInfinateTempleQueue());

					break;
				default:
					break;
				}

				ModNetwork.SendToALLPlayers(new UnitQueueChangedPacketToClient(TSRTS.TeamQueues[teamID].getBarracks(), TSRTS.TeamQueues[teamID].getArcheryRange(), TSRTS.TeamQueues[teamID].getStables(), TSRTS.TeamQueues[teamID].getSiegeWorkshop(), TSRTS.TeamQueues[teamID].getTemple(), TSRTS.TeamQueues[teamID].isInfinateBarracksQueue(), TSRTS.TeamQueues[teamID].isInfinateArcheryRangeQueue(), TSRTS.TeamQueues[teamID].isInfinateStablesQueue(), TSRTS.TeamQueues[teamID].isInfinateSiegeWorkshopQueue(), TSRTS.TeamQueues[teamID].isInfinateTempleQueue(), teamID));

			}
		});
		ctx.get().setPacketHandled(true);
	}

}
