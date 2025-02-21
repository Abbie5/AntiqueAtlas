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
 * Puts biome tile into one atlas. When sent to server, forwards it to every
 * client that has this atlas' data synced.
 * @author Hunternif
 * @author Haven King
 */
public record PutTileC2SPacket(
		int atlasID,
		int x,
		int z,
		Identifier tile
) implements C2SPacket {
	public static final Id<PutTileC2SPacket> ID = new Id<>(AntiqueAtlasMod.id("packet", "c2s", "tile", "put"));
	public static final PacketCodec<ByteBuf, PutTileC2SPacket> PACKET_CODEC = PacketCodec.tuple(
			PacketCodecs.VAR_INT, PutTileC2SPacket::atlasID,
			PacketCodecs.VAR_INT, PutTileC2SPacket::x,
			PacketCodecs.VAR_INT, PutTileC2SPacket::z,
			Identifier.PACKET_CODEC, PutTileC2SPacket::tile,
			PutTileC2SPacket::new
	);

	@Override
	public Id<?> getId() {
		return ID;
	}

	public static void apply(PutTileC2SPacket packet, NetworkManager.PacketContext context) {
		context.queue(() -> {
			if (AntiqueAtlasMod.CONFIG.itemNeeded && !AtlasAPI.getPlayerAtlases(context.getPlayer()).contains(packet.atlasID)) {
				Log.warn("Player %s attempted to modify someone else's Atlas #%d",
						context.getPlayer().getName(), packet.atlasID);
				return;
			}

			AtlasAPI.getTileAPI().putTile(context.getPlayer().getEntityWorld(), packet.atlasID, packet.tile, packet.x, packet.z);
		});
	}
}
