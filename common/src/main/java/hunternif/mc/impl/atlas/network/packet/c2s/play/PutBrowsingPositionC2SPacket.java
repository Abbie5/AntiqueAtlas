package hunternif.mc.impl.atlas.network.packet.c2s.play;

import dev.architectury.networking.NetworkManager;
import hunternif.mc.api.AtlasAPI;
import hunternif.mc.impl.atlas.AntiqueAtlasMod;
import hunternif.mc.impl.atlas.network.packet.c2s.C2SPacket;
import hunternif.mc.impl.atlas.util.Log;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

/**
 * Packet used to save the last browsing position for a dimension in an atlas.
 * @author Hunternif
 * @author Haven King
 */
public record PutBrowsingPositionC2SPacket(
		int atlasID,
		RegistryKey<World> world,
		int x,
		int y,
		double zoom
) implements C2SPacket {
	public static final Id<PutBrowsingPositionC2SPacket> ID = new Id<>(AntiqueAtlasMod.id("packet", "c2s", "browsing_position", "put"));
	public static final PacketCodec<ByteBuf, PutBrowsingPositionC2SPacket> PACKET_CODEC = PacketCodec.tuple(
			PacketCodecs.VAR_INT, PutBrowsingPositionC2SPacket::atlasID,
			RegistryKey.createPacketCodec(RegistryKeys.WORLD), PutBrowsingPositionC2SPacket::world,
			PacketCodecs.VAR_INT, PutBrowsingPositionC2SPacket::x,
			PacketCodecs.VAR_INT, PutBrowsingPositionC2SPacket::y,
			PacketCodecs.DOUBLE, PutBrowsingPositionC2SPacket::zoom,
			PutBrowsingPositionC2SPacket::new
	);

	@Override
	public Id<?> getId() {
		return ID;
	}

	public static void apply(PutBrowsingPositionC2SPacket packet, NetworkManager.PacketContext context) {
		context.queue(() -> {
			if (AntiqueAtlasMod.CONFIG.itemNeeded && !AtlasAPI.getPlayerAtlases(context.getPlayer()).contains(packet.atlasID)) {
				Log.warn("Player %s attempted to put position marker into someone else's Atlas #%d",
						context.getPlayer().getCommandSource().getName(), packet.atlasID);
				return;
			}

			AntiqueAtlasMod.tileData.getData(packet.atlasID, context.getPlayer().getEntityWorld())
					.getWorldData(packet.world).setBrowsingPosition(packet.x, packet.y, packet.zoom);
		});
	}
}
