package hunternif.mc.impl.atlas.network.packet.c2s.play;

import dev.architectury.networking.NetworkManager;
import hunternif.mc.impl.atlas.AntiqueAtlasMod;
import hunternif.mc.api.AtlasAPI;
import hunternif.mc.impl.atlas.network.packet.c2s.C2SPacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.text.Text;
import net.minecraft.text.TextCodecs;
import net.minecraft.util.Identifier;

/**
 * A request from a client to create a new marker. In order to prevent griefing,
 * the marker has to be local.
 * @author Hunternif
 * @author Haven King
 */
public record PutMarkerC2SPacket(
		int atlasID, 
		Identifier markerType,
		int x,
		int z,
		boolean visibleBeforeDiscovery, 
		Text label
) implements C2SPacket {
	public static final Id<PutMarkerC2SPacket> ID = new Id<>(AntiqueAtlasMod.id("packet", "c2s", "marker", "put"));
	public static final PacketCodec<ByteBuf, PutMarkerC2SPacket> PACKET_CODEC = PacketCodec.tuple(
			PacketCodecs.VAR_INT, PutMarkerC2SPacket::atlasID,
			Identifier.PACKET_CODEC, PutMarkerC2SPacket::markerType,
			PacketCodecs.VAR_INT, PutMarkerC2SPacket::x,
			PacketCodecs.VAR_INT, PutMarkerC2SPacket::z,
			PacketCodecs.BOOL, PutMarkerC2SPacket::visibleBeforeDiscovery,
			TextCodecs.PACKET_CODEC, PutMarkerC2SPacket::label,
			PutMarkerC2SPacket::new
	);

	@Override
	public Id<?> getId() {
		return ID;
	}

	public static void apply(PutMarkerC2SPacket packet, NetworkManager.PacketContext context) {
		context.queue(() -> {
			if (!AtlasAPI.getPlayerAtlases(context.getPlayer()).contains(packet.atlasID)) {
				AntiqueAtlasMod.LOG.warn(
								"Player {} attempted to put marker into someone else's Atlas #{}}",
						context.getPlayer().getName(), packet.atlasID);
				return;
			}

			AtlasAPI.getMarkerAPI().putMarker(context.getPlayer().getEntityWorld(), packet.visibleBeforeDiscovery, packet.atlasID, packet.markerType, packet.label, packet.x, packet.z);
		});
	}
}
