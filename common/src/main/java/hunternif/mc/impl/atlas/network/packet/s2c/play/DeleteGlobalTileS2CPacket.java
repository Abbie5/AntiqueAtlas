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
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

/**
 * Sent from server to client to remove a custom global tile.
 * @author Hunternif
 * @author Haven King
 */
public record DeleteGlobalTileS2CPacket(
		RegistryKey<World> world,
		int chunkX,
		int chunkZ
) implements S2CPacket {
	public static final Id<DeleteGlobalTileS2CPacket> ID = new Id<>(AntiqueAtlasMod.id("packet", "c2s", "global_tile", "delete"));
	public static final PacketCodec<ByteBuf, DeleteGlobalTileS2CPacket> PACKET_CODEC = PacketCodec.tuple(
			RegistryKey.createPacketCodec(RegistryKeys.WORLD), DeleteGlobalTileS2CPacket::world,
			PacketCodecs.VAR_INT, DeleteGlobalTileS2CPacket::chunkX,
			PacketCodecs.VAR_INT, DeleteGlobalTileS2CPacket::chunkZ,
			DeleteGlobalTileS2CPacket::new
	);

	@Override
	public Id<?> getId() {
		return ID;
	}

	@Environment(EnvType.CLIENT)
	public static void apply(DeleteGlobalTileS2CPacket packet, NetworkManager.PacketContext context) {
		context.queue(() -> {
			TileDataStorage data = AntiqueAtlasMod.globalTileData.getData(packet.world);
			data.removeTile(packet.chunkX, packet.chunkZ);
		});
	}
}
