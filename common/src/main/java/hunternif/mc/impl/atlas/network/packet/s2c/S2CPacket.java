package hunternif.mc.impl.atlas.network.packet.s2c;

import dev.architectury.networking.NetworkManager;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public interface S2CPacket extends CustomPayload {
	default void send(ServerPlayerEntity playerEntity) {
		NetworkManager.sendToPlayer(playerEntity, this);
	}

	default void send(ServerWorld world) {
		NetworkManager.sendToPlayers(world.getPlayers(), this);
	}

	default void send(MinecraftServer server) {
		NetworkManager.sendToPlayers(server.getPlayerManager().getPlayerList(), this);
	}
}
