package hunternif.mc.impl.atlas.network.packet.s2c.play;

import dev.architectury.networking.NetworkManager;
import hunternif.mc.impl.atlas.AntiqueAtlasMod;
import hunternif.mc.impl.atlas.core.AtlasData;
import hunternif.mc.impl.atlas.core.TileInfo;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public record DimensionUpdateS2CPacket(
		int atlasID,
		RegistryKey<World> world,
		Collection<TileInfo> tiles
) implements S2CPacket {
	public static final Id<DimensionUpdateS2CPacket> ID = new Id<>(AntiqueAtlasMod.id("packet", "s2c", "dimension", "update"));
	public static final PacketCodec<ByteBuf, DimensionUpdateS2CPacket> PACKET_CODEC = PacketCodec.tuple(
			PacketCodecs.VAR_INT, DimensionUpdateS2CPacket::atlasID,
			RegistryKey.createPacketCodec(RegistryKeys.WORLD), DimensionUpdateS2CPacket::world,
			TileInfo.PACKET_CODEC.collect(PacketCodecs.toCollection(ArrayList::new)), DimensionUpdateS2CPacket::tiles,
			DimensionUpdateS2CPacket::new
	);

	@Override
	public Id<?> getId() {
		return ID;
	}

	@Environment(EnvType.CLIENT)
	public static void apply(DimensionUpdateS2CPacket packet, NetworkManager.PacketContext context) {
		if (packet.world == null) {
			// TODO FABRIC
			return;
		}

		context.queue(() -> {
			AtlasData data = AntiqueAtlasMod.tileData.getData(packet.atlasID, context.getPlayer().getEntityWorld());

			for (TileInfo info : packet.tiles) {
				data.getWorldData(packet.world).setTile(info.x, info.z, info.id);
			}
		});
	}
}
