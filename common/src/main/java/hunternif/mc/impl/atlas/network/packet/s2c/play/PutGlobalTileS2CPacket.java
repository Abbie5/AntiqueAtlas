package hunternif.mc.impl.atlas.network.packet.s2c.play;

import dev.architectury.networking.NetworkManager;
import hunternif.mc.impl.atlas.AntiqueAtlasMod;
import hunternif.mc.impl.atlas.core.TileDataStorage;
import hunternif.mc.impl.atlas.network.packet.s2c.S2CPacket;
import io.netty.buffer.ByteBuf;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;

/**
 * Used to sync custom tiles from server to client.
 * @author Hunternif
 * @author Haven King
 */
public record PutGlobalTileS2CPacket(
		RegistryKey<World> world,
		List<Map.Entry<ChunkPos, Identifier>> tiles
) implements S2CPacket {
	public static final Id<PutGlobalTileS2CPacket> ID = new Id<>(AntiqueAtlasMod.id("packet", "s2c", "global_tile", "put"));
	private static final PacketCodec<ByteBuf, Map.Entry<ChunkPos, Identifier>> ENTRY_PACKET_CODEC = PacketCodec.tuple(
			PacketCodecs.VAR_LONG.xmap(ChunkPos::new, ChunkPos::toLong), Map.Entry::getKey,
			Identifier.PACKET_CODEC, Map.Entry::getValue,
			Map::entry
	);
	public static final PacketCodec<ByteBuf, PutGlobalTileS2CPacket> PACKET_CODEC = PacketCodec.tuple(
			RegistryKey.createPacketCodec(RegistryKeys.WORLD), PutGlobalTileS2CPacket::world,
			ENTRY_PACKET_CODEC.collect(PacketCodecs.toList()), PutGlobalTileS2CPacket::tiles,
			PutGlobalTileS2CPacket::new
	);

	public PutGlobalTileS2CPacket(RegistryKey<World> world, int chunkX, int chunkZ, Identifier tileId) {
		this(world, List.of(Map.entry(new ChunkPos(chunkX, chunkZ), tileId)));
	}

	@Override
	public Id<?> getId() {
		return ID;
	}

	@Environment(EnvType.CLIENT)
	public static void apply(PutGlobalTileS2CPacket packet, NetworkManager.PacketContext context) {
		TileDataStorage data = AntiqueAtlasMod.globalTileData.getData(packet.world);
		for (Map.Entry<ChunkPos, Identifier> entry : packet.tiles) {
			ChunkPos chunkPos = entry.getKey();
			Identifier tile = entry.getValue();
			data.setTile(chunkPos.x, chunkPos.z, tile);
		}
	}
}
