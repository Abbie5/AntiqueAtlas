package hunternif.mc.impl.atlas.network.packet.s2c.play;

import dev.architectury.networking.NetworkManager;
import hunternif.mc.impl.atlas.AntiqueAtlasMod;
import hunternif.mc.impl.atlas.core.AtlasData;
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
 * Puts biome tile into one atlas.
 * @author Hunternif
 * @author Haven King
 */
public record PutTileS2CPacket(
		int atlasID,
		RegistryKey<World> world,
		int x,
		int z,
		Identifier tile
) implements S2CPacket {
	public static final Id<PutTileS2CPacket> ID = new Id<>(AntiqueAtlasMod.id("packet", "s2c", "tile", "put"));
	public static final PacketCodec<ByteBuf, PutTileS2CPacket> PACKET_CODEC = PacketCodec.tuple(
			PacketCodecs.VAR_INT, PutTileS2CPacket::atlasID,
			RegistryKey.createPacketCodec(RegistryKeys.WORLD), PutTileS2CPacket::world,
			PacketCodecs.VAR_INT, PutTileS2CPacket::x,
			PacketCodecs.VAR_INT, PutTileS2CPacket::z,
			Identifier.PACKET_CODEC, PutTileS2CPacket::tile,
			PutTileS2CPacket::new
	);

	@Override
	public Id<?> getId() {
		return ID;
	}

	@Environment(EnvType.CLIENT)
	public static void apply(PutTileS2CPacket packet, NetworkManager.PacketContext context) {
		context.queue(() -> {
			AtlasData data = AntiqueAtlasMod.tileData.getData(packet.atlasID, context.getPlayer().getEntityWorld());
			data.setTile(packet.world, packet.x, packet.z, packet.tile);
		});
	}
}
