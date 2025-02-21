package hunternif.mc.impl.atlas.network.packet.s2c.play;

import dev.architectury.networking.NetworkManager;
import hunternif.mc.impl.atlas.AntiqueAtlasMod;
import hunternif.mc.impl.atlas.core.AtlasData;
import hunternif.mc.impl.atlas.core.TileGroup;
import hunternif.mc.impl.atlas.core.WorldData;
import hunternif.mc.impl.atlas.network.packet.s2c.S2CPacket;
import io.netty.buffer.ByteBuf;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;


/**
 * Syncs tile groups to the client.
 *
 * @author Hunternif
 * @author Haven King
 */
public record TileGroupsS2CPacket(
        int atlasID, 
        RegistryKey<World> world, 
        List<TileGroup> tileGroups
) implements S2CPacket {
    public static final int TILE_GROUPS_PER_PACKET = 100;
    public static final Id<TileGroupsS2CPacket> ID = new Id<>(AntiqueAtlasMod.id("packet", "s2c", "tile", "groups"));
    public static final PacketCodec<PacketByteBuf, TileGroupsS2CPacket> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.VAR_INT, TileGroupsS2CPacket::atlasID,
            RegistryKey.createPacketCodec(RegistryKeys.WORLD), TileGroupsS2CPacket::world,
            TileGroup.PACKET_CODEC.collect(PacketCodecs.toList()), TileGroupsS2CPacket::tileGroups,
            TileGroupsS2CPacket::new
    );

    @Override
    public Id<?> getId() {
        return ID;
    }

    @Environment(EnvType.CLIENT)
    public static void apply(TileGroupsS2CPacket packet, NetworkManager.PacketContext context) {
        context.queue(() -> {
            AtlasData atlasData = AntiqueAtlasMod.tileData.getData(packet.atlasID, context.getPlayer().getEntityWorld());
            WorldData worldData = atlasData.getWorldData(packet.world);
            for (TileGroup t : packet.tileGroups) {
                worldData.putTileGroup(t);
            }
        });
    }
}
