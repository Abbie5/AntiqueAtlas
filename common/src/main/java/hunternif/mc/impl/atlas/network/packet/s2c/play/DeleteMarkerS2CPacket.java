package hunternif.mc.impl.atlas.network.packet.s2c.play;

import dev.architectury.networking.NetworkManager;
import hunternif.mc.impl.atlas.AntiqueAtlasMod;
import hunternif.mc.impl.atlas.AntiqueAtlasModClient;
import hunternif.mc.impl.atlas.marker.MarkersData;
import hunternif.mc.impl.atlas.network.packet.c2s.play.DeleteMarkerC2SPacket;
import hunternif.mc.impl.atlas.network.packet.s2c.S2CPacket;
import io.netty.buffer.ByteBuf;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;

/**
 * Deletes a marker. A client sends a {@link DeleteMarkerC2SPacket}
 * to the server as a request, and the server sends this back to all players as a response, including the
 * original sender.
 * @author Hunternif
 * @author Haven King
 */
public record DeleteMarkerS2CPacket(
		int atlasID,
		int markerID
) implements S2CPacket {
	public static final Id<DeleteMarkerS2CPacket> ID = new Id<>(AntiqueAtlasMod.id("packet", "s2c", "marker", "delete"));
	public static final PacketCodec<ByteBuf, DeleteMarkerS2CPacket> PACKET_CODEC = PacketCodec.tuple(
			PacketCodecs.VAR_INT, DeleteMarkerS2CPacket::atlasID,
			PacketCodecs.VAR_INT, DeleteMarkerS2CPacket::markerID,
			DeleteMarkerS2CPacket::new
	);

	private static final int GLOBAL = -1;

	@Override
	public Id<?> getId() {
		return ID;
	}

	@Environment(EnvType.CLIENT)
	public static void apply(DeleteMarkerS2CPacket packet, NetworkManager.PacketContext context) {
		context.queue(() -> {
			MarkersData data = packet.atlasID == GLOBAL ?
					AntiqueAtlasMod.globalMarkersData.getData() :
					AntiqueAtlasMod.markersData.getMarkersData(packet.atlasID, context.getPlayer().getEntityWorld());
			data.removeMarker(packet.markerID);

			AntiqueAtlasModClient.getAtlasGUI().updateBookmarkerList();
		});
	}
}
