package hunternif.mc.impl.atlas.network.packet.c2s.play;

import dev.architectury.networking.NetworkManager;
import hunternif.mc.impl.atlas.AntiqueAtlasMod;
import hunternif.mc.api.AtlasAPI;
import hunternif.mc.impl.atlas.network.packet.c2s.C2SPacket;
import hunternif.mc.impl.atlas.util.Log;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;

/**
 * Deletes a marker. A client sends this packet to the server as a request,
 * and the server sends to all players as a response, including the
 * original sender.
 * @author Hunternif
 */
public record DeleteMarkerC2SPacket(
		int atlasID,
		int markerID
) implements C2SPacket {
	public static final Id<DeleteMarkerC2SPacket> ID = new Id<>(AntiqueAtlasMod.id("packet", "c2s", "marker", "delete"));
	public static final PacketCodec<ByteBuf, DeleteMarkerC2SPacket> PACKET_CODEC = PacketCodec.tuple(
			PacketCodecs.VAR_INT, DeleteMarkerC2SPacket::atlasID,
			PacketCodecs.VAR_INT, DeleteMarkerC2SPacket::markerID,
			DeleteMarkerC2SPacket::new
	);

	private static final int GLOBAL = -1;

	@Override
	public Id<?> getId() {
		return ID;
	}
	public static void apply(DeleteMarkerC2SPacket packet, NetworkManager.PacketContext context) {
		context.queue(() -> {
			if (AntiqueAtlasMod.CONFIG.itemNeeded && !AtlasAPI.getPlayerAtlases(context.getPlayer()).contains(packet.atlasID)) {
				Log.warn("Player %s attempted to delete marker from someone else's Atlas #%d",
						context.getPlayer().getName(), packet.atlasID);
				return;
			}

			AtlasAPI.getMarkerAPI().deleteMarker(context.getPlayer().getEntityWorld(), packet.atlasID, packet.markerID);
		});
	}
}
